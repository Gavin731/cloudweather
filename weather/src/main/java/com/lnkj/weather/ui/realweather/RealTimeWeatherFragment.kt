package com.lnkj.weather.ui.realweather

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherFragmentRealTimeWeatherBinding
import com.lnkj.weather.ui.city.SearchCityActivity
import com.lnkj.weather.ui.city.add.AddCityActivity
import com.lnkj.weather.utils.ColorUtils
import com.lnkj.weather.utils.ImageUtils
import com.mufeng.mvvmlib.basic.view.BaseVMFragment
import com.mufeng.mvvmlib.image.GlideApp
import com.mufeng.mvvmlib.image.GlideRequests
import com.mufeng.mvvmlib.utilcode.ext.GsonUtils
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.backgroundColorResource
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.Preference
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:46
 * @描述
 */
class RealTimeWeatherFragment :
    BaseVMFragment<RealTimeWeatherViewModel, WeatherFragmentRealTimeWeatherBinding>() {
    private lateinit var circleNavigator: CircleNavigator
    private lateinit var adapter: MyPageAdapter
    private var backgroundColor: Int = -1
    private var currentCity: MyCityBean? = null
    override val viewModel: RealTimeWeatherViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_fragment_real_time_weather

    val titles = arrayListOf<MyCityBean>()
    private val fragments = arrayListOf<Fragment>()

    private var isLocation by Preference("isLocation", true)

    private lateinit var drawableStart: Drawable

    private var index = 0

    var oldBgResId: Int = -1

    override fun initView() {
        binding.vm = viewModel
        drawableStart = resources.getDrawable(R.mipmap.weather_icon_location)
        drawableStart.setBounds(0, 0, drawableStart.minimumWidth, drawableStart.minimumHeight)
        StatusBarUtils.addTranslucentViewPadding(requireActivity(), binding.toolbar)

        viewModel.getMyCities(isLocation)

        binding.ivAddCity.clickWithTrigger {
            startActivity<AddCityActivity>()
        }

        binding.ivShare.clickWithTrigger {
            //防止数组越界
            if(index==fragments.size){
                return@clickWithTrigger
            }
            // 截图
            val bitmapTop = ImageUtils.getViewBitmap(binding.toolbar, backgroundColor)
            val bitmapBottom = (fragments[index] as RealTimeWeatherItemFragment).getBitmap()
                ?: return@clickWithTrigger
            val bitmap = ImageUtils.combineImage(bitmapTop!!, bitmapBottom!!)
            LiveEventBus.get("event_share_real_time_weather").post(bitmap)
            startActivity<RealTimeWeatherShareActivity>()
        }

        LiveEventBus.get(EventKey.EVENT_CHOOSE_REMOVE_CITY, MyCityBean::class.java)
            .observe(this) {
                this.currentCity = null
                viewModel.getMyCities(isLocation)
            }

        LiveEventBus.get(EventKey.EVENT_CHOOSE_ACC_CITY, MyCityBean::class.java)
            .observe(this) {
                this.currentCity = it
                // 1. 如果城市存在, 切换到对应城市
                // 2. 如果城市不存在,
                if (titles.contains(it)) {
                    viewModel.currentCity.postValue(it)
                    val index = titles.indexOf(it)
                    binding.viewPager.currentItem = index
                } else {
                    viewModel.getMyCities(isLocation)
                }
            }

        LiveEventBus.get(EventKey.EVENT_CHANGE_AUTO_LOCATION_STATUS, Boolean::class.java)
            .observe(this) {
                isLocation = it
                currentCity = null
                viewModel.getMyCities(isLocation)
            }

    }

    fun setWeatherBg(res: Int, cityId: String) {
        val title = titles.firstOrNull { it.cid == cityId }
        val i = titles.indexOf(title)
        if (this.index == i && oldBgResId != res) {
            this.backgroundColor = ColorUtils.getTopColor(res)
//            //原来的title颜色淡出
//            var defaultColorAnimator = ObjectAnimator.ofFloat(binding.toolbar, "alpha", 1f, 0.8f);
//            defaultColorAnimator.duration = 100
//            defaultColorAnimator.start()
//            //改变颜色
//            binding.toolbar.backgroundColorResource = backgroundColor
//            //更换后淡入
//            var newColorAnimator = ObjectAnimator.ofFloat(binding.toolbar, "alpha", 0.7f, 1f);
//            newColorAnimator.duration = 300
//            newColorAnimator.start()


            //原来的图片淡出
            var defaultImgAnimator = ObjectAnimator.ofFloat(binding.clRootView, "alpha", 1f, 0.8f);
            defaultImgAnimator.duration = 100
            defaultImgAnimator.start()
            //改变图片
            binding.clRootView.setBackgroundResource(res)
            //更换后淡入
            var newImgAnimator = ObjectAnimator.ofFloat(binding.clRootView, "alpha", 0.7f, 1f);
            newImgAnimator.duration = 300
            newImgAnimator.start()

            oldBgResId = res;
        }

    }

    fun setRealtimeBlurView(alpha: Int) {
//        binding.view.background.alpha = if (alpha < 150) 255 - 150 else 255 - alpha
        if (alpha > 150) {
            binding.realtimeBlurView.visible()
        } else {
            binding.realtimeBlurView.gone()
        }
    }

    override fun initData() {


    }

    override fun startObserve() {
        super.startObserve()
        viewModel.currentCity.observe(this) {
            binding.tvCity.text = it.counties + " " + it.street
        }
        viewModel.myCityBeanData.observe(this) {
            if (it.isEmpty()) {
                startActivity<SearchCityActivity>()
                return@observe
            }

            titles.clear()
            titles.addAll(it)
            Log.e("-----title", GsonUtils.INSTANCE.toJson(titles))

            val newFragments = LinkedHashMap<String, RealTimeWeatherItemFragment>()
            //查找是否存在新加的城市
            titles.forEachIndexed { index, myCityBean ->
                var existFragment: RealTimeWeatherItemFragment? = null
                //循环已存在的城市
                fragments.forEach { item ->
                    item as RealTimeWeatherItemFragment
                    if (myCityBean.cid == item.getCityId()) {
                        existFragment = item
                    }
                }
                if (existFragment == null) {
                    val cityFragment = RealTimeWeatherItemFragment.getInstance(
                        myCityBean.cid,
                        myCityBean
                    )
                    newFragments[myCityBean.cid] = cityFragment
                } else {
                    newFragments[existFragment!!.getCityId()] = existFragment!!
                }
            }
            Log.e("-----newFragments", newFragments.size.toString())

            fragments.clear()
            //删除不存在的城市
            newFragments.forEach { item ->
                var isExist = false
                titles.forEach { titleBean ->
                    if (titleBean.cid == item.key) {
                        isExist = true
                    }
                }
                if (isExist) {
                    fragments.add(item.value)
                }
            }
            newFragments.clear()
            Log.e("-----fragments", fragments.size.toString())

            if (currentCity == null) {
                currentCity = titles[0]
            }
            viewModel.currentCity.postValue(currentCity)

            if (currentCity!!.isLocation == 1) {
                binding.tvCity.setCompoundDrawables(drawableStart, null, null, null)
            } else {
                binding.tvCity.setCompoundDrawables(null, null, null, null)
            }
            LiveEventBus.get(EventKey.EVENT_CHANGE_CITY)
                .post(currentCity)

            circleNavigator = CircleNavigator(requireContext())
            circleNavigator.circleCount = if (titles.size > 10) 10 else titles.size
            circleNavigator.circleColor = Color.WHITE
            binding.magicIndicator.navigator = circleNavigator

            if (titles.size == 1) {
                binding.magicIndicator.gone()
            } else {
                binding.magicIndicator.visible()
            }

            adapter = MyPageAdapter(childFragmentManager, fragments)
            binding.viewPager.adapter = adapter
            binding.viewPager.offscreenPageLimit = 1
            binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                    binding.magicIndicator.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    binding.magicIndicator.onPageScrolled(
                        position,
                        positionOffset,
                        positionOffsetPixels
                    )
                }

                override fun onPageSelected(position: Int) {
                    if (index != 0 && index == position) return
                    this@RealTimeWeatherFragment.index = position
                    binding.magicIndicator.onPageSelected(
                        when {
                            titles.size <= 10 -> position
                            else -> {
                                when {
                                    position <= 8 -> {
                                        position
                                    }
                                    position == titles.size - 1 -> {
                                        9
                                    }
                                    else -> {
                                        8
                                    }
                                }
                            }
                        }
                    )
                    val item = titles[position]
                    viewModel.currentCity.postValue(item)
                    if (item.isLocation == 1) {
                        binding.tvCity.setCompoundDrawables(drawableStart, null, null, null)
                    } else {
                        binding.tvCity.setCompoundDrawables(null, null, null, null)
                    }

                    LiveEventBus.get(EventKey.EVENT_CHANGE_CITY)
                        .postDelay(item, 300)
//                    // 关闭语音播报
                    LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
                        .post(true)
                }
            })
            // 获取当前选中的item
            titles.forEachIndexed { index, myCityBean ->
                if (myCityBean.lat == currentCity?.lat && myCityBean.lon == currentCity?.lon) {
                    binding.viewPager.currentItem = index
                    return@observe
                }
            }

        }
    }
}