package com.lnkj.weather.ui.hour

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherFragmentHourDetailsBinding
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.ui.realweather.MyPageAdapter
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.mvvmlib.basic.view.BaseVMFragment
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView
import java.util.*


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:49
 * @描述
 */
class HourDetailsFragment :
    BaseVMFragment<HourDetailsViewModel, WeatherFragmentHourDetailsBinding>() {

    private var cityBean: MyCityBean? = null
    override val viewModel: HourDetailsViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_fragment_hour_details

    private val titleList = arrayListOf<Map<String, String>>()
    private val fragments = arrayListOf<Fragment>()

    private var index = 0
    private var loaded = false

    override fun initView() {

        binding.vm = viewModel
        StatusBarUtils.addTranslucentView(requireActivity(), binding.toolbar)

        Glide.with(requireActivity())
            .load(R.drawable.weather_main_bg)
            .into(binding.ivImageBg)

        binding.ivShare.clickWithTrigger {
            val cityWeather = (fragments[0] as HourDetailsItemFragment).getData()
            val isDayTime = DateUtils.isDayTime(cityWeather!!.sunriseTime, cityWeather!!.sunsetTime)
            HourDetailsShareActivity.launch(
                requireActivity(),
                cityWeather?.currentTemperature!!,
                cityWeather?.weatherName,
                cityWeather?.windDirection,
                cityWeather.windSpeed,
                cityWeather.humidity,
                cityWeather.pressure,
                cityBean?.counties!!,
                titleList[0]["date"] ?: error(""),
                cityWeather.dressLifeStyle?.indexTxt ?: "",
                WeatherUtils.getWeatherBgByWeatherName(cityWeather?.weatherName, isDayTime)
            )
        }
        binding.ivReturnBack.clickWithTrigger {
            (activity as MainActivity).returnHomeFragment()
        }

        LiveEventBus.get(EventKey.EVENT_CHANGE_CITY, MyCityBean::class.java)
            .observe(this) {
                loaded = false
                this.cityBean = it
            }

        LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX, Int::class.java)
            .observe(this) {
                binding.viewPager.currentItem = it
            }
    }

    private fun initViewPager(myCityBean: MyCityBean?) {
        // 初始化指示器
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val commonPagerTitleView = CommonPagerTitleView(context)
                commonPagerTitleView.setContentView(R.layout.weather_hour_details_title_item_view)
                val day = commonPagerTitleView.findViewById<TextView>(R.id.tv_day)
                val date = commonPagerTitleView.findViewById<TextView>(R.id.tv_date)
                day.text = titleList[index]["day"]
                date.text = titleList[index]["date"]
                day.setTextColor(Color.parseColor("#80ffffff"))
                date.setTextColor(Color.parseColor("#80ffffff"))
                commonPagerTitleView.onPagerTitleChangeListener =
                    object : CommonPagerTitleView.OnPagerTitleChangeListener {
                        override fun onDeselected(index: Int, totalCount: Int) {
                            day.setTextColor(Color.parseColor("#80ffffff"))
                            date.setTextColor(Color.parseColor("#80ffffff"))
                        }

                        override fun onSelected(index: Int, totalCount: Int) {
                            day.setTextColor(Color.parseColor("#ffffff"))
                            date.setTextColor(Color.parseColor("#ffffff"))
                            this@HourDetailsFragment.index = index
                        }

                        override fun onLeave(
                            index: Int,
                            totalCount: Int,
                            leavePercent: Float,
                            leftToRight: Boolean
                        ) {
                        }

                        override fun onEnter(
                            index: Int,
                            totalCount: Int,
                            enterPercent: Float,
                            leftToRight: Boolean
                        ) {
                        }
                    }

                commonPagerTitleView.setOnClickListener {
                    binding.viewPager.currentItem = index
                }
                return commonPagerTitleView
            }

            override fun getCount(): Int {
                return 3
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val linePagerIndicator = LinePagerIndicator(context)
                linePagerIndicator.setColors(resources.getColor(R.color.white))
                linePagerIndicator.mode = LinePagerIndicator.MODE_MATCH_EDGE
                return linePagerIndicator
            }
        }
        binding.magicIndicator.navigator = commonNavigator
        fragments.clear()
        val calendar: Calendar = GregorianCalendar()
        fragments.add(HourDetailsItemFragment.getInstance(Date().time / 1000, 1, myCityBean!!))
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1)
        fragments.add(
            HourDetailsItemFragment.getInstance(
                calendar.time.time / 1000,
                2,
                myCityBean!!
            )
        )
        calendar.time = Date()
        calendar.add(Calendar.DATE, 2)
        fragments.add(
            HourDetailsItemFragment.getInstance(
                calendar.time.time / 1000,
                3,
                myCityBean!!
            )
        )

        val adapter = MyPageAdapter(childFragmentManager, fragments)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 3

        ViewPagerHelper.bind(binding.magicIndicator, binding.viewPager)

    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        if (!loaded) {
            refreshData(cityBean)
            loaded = true
        }
    }

    private fun refreshData(myCityBean: MyCityBean?) {
        viewModel.supperTitle.postValue("${myCityBean?.counties}72小时详情")
        // 设置数据
        titleList.clear()
        fragments.clear()
        val calendar: Calendar = GregorianCalendar()
        titleList.add(
            mapOf(
                "day" to "今天", "date" to DateUtils.formatTime(
                    Date(),
                    DateUtils.PATTERN_14
                )
            )
        )
        // 明天的时间
        calendar.time = Date()
        calendar.add(Calendar.DATE, 1)
        titleList.add(
            mapOf(
                "day" to "明天", "date" to DateUtils.formatTime(
                    calendar.time,
                    DateUtils.PATTERN_14
                )
            )
        )
        // 后天时间
        calendar.time = Date()
        calendar.add(Calendar.DATE, 2)
        titleList.add(
            mapOf(
                "day" to "后天", "date" to DateUtils.formatTime(
                    calendar.time,
                    DateUtils.PATTERN_14
                )
            )
        )

        initViewPager(myCityBean)
    }


}