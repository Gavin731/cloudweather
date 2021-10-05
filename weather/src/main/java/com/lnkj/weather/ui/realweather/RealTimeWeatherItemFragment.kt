package com.lnkj.weather.ui.realweather

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.gyf.immersionbar.ImmersionBar
import com.iflytek.cloud.*
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.CityWeather
import com.lnkj.library_base.db.bean.DailyWeather
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.bean.toLifeStyle
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.base.BaseVMFragment
import com.lnkj.weather.databinding.WeatherFragmentItemRealTimeWeatherBinding
import com.lnkj.weather.ui.exponent.ExponentActivity
import com.lnkj.weather.ui.fifteen.FifteenWeatherActivity
import com.lnkj.weather.ui.hotrank.HotRankActivity
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.ui.rain.RainActivity
import com.lnkj.weather.utils.ColorUtils
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.ImageUtils
import com.lnkj.weather.utils.WeatherUtils
import com.lnkj.weather.widget.zzweatherview.WeatherModel
import com.lnkj.weather.widget.zzweatherview.hour.HourWeatherModel
import com.mufeng.mvvmlib.http.ApiException
import com.mufeng.mvvmlib.image.GlideApp
import com.mufeng.mvvmlib.utilcode.ext.loge
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.*
import com.mufeng.mvvmlib.utilcode.utils.toast
import java.util.*


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 10:07
 * @描述
 */
class RealTimeWeatherItemFragment :
    BaseVMFragment<RealTimeWeatherViewModel, WeatherFragmentItemRealTimeWeatherBinding>() {

    companion object {
        fun getInstance(cityId: String, myCityBean: MyCityBean): RealTimeWeatherItemFragment {
            val fragment = RealTimeWeatherItemFragment()
            val bundle = Bundle()
            bundle.putString("cityId", cityId)
            bundle.putParcelable("myCityBean", myCityBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var canRefresh: Boolean = true
    private var bottomColor: Int = -1
    private var cityWeather: CityWeather? = null
    private var speechSynthesizer: SpeechSynthesizer? = null

    override lateinit var viewModel: RealTimeWeatherViewModel
    override val layoutResId: Int
        get() = R.layout.weather_fragment_item_real_time_weather

    // 预警信息
    private lateinit var alertAdapter: RealTimeWeatherAlertAdapter
    private val alertData = arrayListOf<String>()

    private var handler: Handler = Handler()

    // 15天天气列表
    private val dailyListData = arrayListOf<DailyWeather>()
    val trendListData = arrayListOf<WeatherModel>()

    private var myCityBean: MyCityBean? = null

    private var cityId = ""

    private var isAlreadyRefresh = false //是否刷新过
    private var refreshTime = Calendar.getInstance()//刷新时间

    fun getBitmap(): Bitmap? {
        if (cityWeather == null) {
            return null
        }
        binding.nestedScrollView.setBackgroundResource(cityWeather?.weatherBg!!)
        val bitmap = ImageUtils.getScrollViewBitmap(binding.nestedScrollView, bottomColor)
        binding.nestedScrollView.background = null
        return bitmap
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.refreshLayout.finishAll()
        binding.vObscuration.visibility = View.GONE
    }

    override fun initView() {
        cityId = arguments?.getString("cityId", "0")!!
        viewModel =
            ViewModelProvider(requireActivity()).get(cityId, RealTimeWeatherViewModel::class.java)
        binding.vm = viewModel
        myCityBean = arguments?.getParcelable("myCityBean")

        if (myCityBean == null) {
            toast("城市不能为空")
            return
        }

        binding.nestedScrollView.setOnScrollChangeListener { _: NestedScrollView?, _, scrollY, _, _ ->
            val alpha = scrollY / 2
            (parentFragment as RealTimeWeatherFragment).setRealtimeBlurView(alpha)
        }

        binding.vObscuration.clickWithTrigger { }

        binding.tvRainTip.clickWithTrigger {
            RainActivity.launch(
                requireActivity(),
                myCityBean,
                cityWeather!!.weatherName,
                cityWeather?.rainTip
            )
        }

        binding.llAirQuality.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(2)
        }
        binding.llTodayTomorrowWeather.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(0)
        }
        binding.clTomorrowWeather.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(1)
        }
        binding.clAfterTomorrowWeather.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(2)
        }

        LiveEventBus.get(EventKey.EVENT_CHANGE_CITY, MyCityBean::class.java)
            .observe(this) {
                // 设置天气背景
                if (it.counties == cityWeather?.cityName) {
                    (parentFragment as RealTimeWeatherFragment).setWeatherBg(
                        cityWeather?.weatherBg!!,
                        cityId
                    )
                }
            }

        LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS, Boolean::class.java)
            .observe(this) {
                if (speechSynthesizer?.isSpeaking == true) {
                    speechSynthesizer?.stopSpeaking()
                }
            }

        //每次创建的时候先读取缓存
        viewModel.searchWeatherByCity(myCityBean!!)

        binding.tv15Weather.clickWithTrigger {
            startActivity<FifteenWeatherActivity>(
                "dailyListData" to dailyListData,
                "trendListData" to trendListData
            )
        }

        //获取后天周几
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        binding.tvAfterTomorrow.text = DateUtils.getWeek(calendar)
    }

    override fun onResume() {
        super.onResume()
        binding.vObscuration.visibility = View.VISIBLE
        if (!isRefreshData()) {
            viewModel.cityWeatherData.let {
                // 设置天气背景
                (parentFragment as RealTimeWeatherFragment).setWeatherBg(
                    it.value!!.weatherBg,
                    cityId
                )
                this.bottomColor =
                    ColorUtils.getBottomColor(it.value!!.weatherBg)
                binding.vObscuration.visibility = View.GONE
            }
            return
        }

        //如果是定位城市，则延迟50毫秒请求，因为第一次创建时可能view还没初始化好，自动刷新会无效
        if (myCityBean!!.isLocation == 1) {
            handler.postDelayed(Runnable {
                binding.refreshLayout.autoRefresh()
            }, 50)
        } else {
            binding.refreshLayout.autoRefresh()
        }
    }

    override fun initData() {
        // 语音播报
        binding.ivPlayAudio.clickWithTrigger {
            voiceAnnouncements(cityWeather?.voiceAnnouncements!!)
        }

        initAlertRecycler()

        binding.refreshLayout.setOnRefreshListener {
            if (!isRefreshData()) {
                binding.refreshLayout.finishRefresh()
                binding.vObscuration.visibility = View.GONE
                return@setOnRefreshListener
            }
            //更新刷新状态
            isAlreadyRefresh = true
            refreshTime = Calendar.getInstance()

            viewModel?.getCaiYunRealtimeWeather(myCityBean!!, myCityBean?.lon!!, myCityBean?.lat!!)
        }


        binding.rvHourWeather.isDrawPath(false)
        binding.rvHourWeather.setOnWeatherItemClickListener { itemView, position, weatherModel ->
            (requireActivity() as MainActivity).selectFragment(1)
            val index =
                cityWeather?.hourlyWeatherList?.indexOf(cityWeather?.hourlyWeatherList?.first { it.time == "00:00" })
            if (position >= index!!) {
                LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(1)
            } else {
                LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(0)
            }
        }
        // 生活指数点击事件
        // 穿衣指数
        binding.clLiveIndexCoat.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.dressLifeStyle?.toLifeStyle()!!,
                myCityBean?.counties!!,
                "今日"
            )
        }

        // 雨伞指数
        binding.llLiveIndexUmbrella.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(0)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 感冒指数
        binding.llLiveIndexCold.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(1)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 紫外线
        binding.llLiveIndexUltravioletLight.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(2)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 晾晒
        binding.llLiveIndexCoatHanger.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(3)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 晨练
        binding.llLiveIndexMorningExercise.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(4)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 旅游
        binding.llLiveIndexTravel.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(5)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }
        // 钓鱼
        binding.llLiveIndexAngling.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(6)!!,
                myCityBean?.counties!!,
                "今日"
            )
        }

        binding.llLiveIndexHighTemperature.clickWithTrigger {
            startActivity<HotRankActivity>()
        }
    }

    private fun initAlertRecycler() {
        binding.rvAlert.layoutManager = LinearLayoutManager(requireActivity())
        alertAdapter = RealTimeWeatherAlertAdapter(alertData)
        binding.rvAlert.adapter = alertAdapter
        binding.rvAlert.removeAllAnimation()
    }

    override fun startObserve() {
        super.startObserve()
        //更改状态字体颜色
        viewModel.weatherIsDayTime.observe(this) {
            ImmersionBar.with(this).statusBarDarkFont(it, 0.2f).init()
        }
        viewModel?.cityWeatherData?.observe(this) {
            if (it == null) {
                binding.refreshLayout.autoRefresh()
                return@observe
            }

            binding.refreshLayout.finishRefresh()
            binding.vObscuration.visibility = View.GONE
            binding.clMainLayout.visible()

            binding.tvRainTip.isSelected = true;

            // 保存当前城市天气信息
            viewModel?.updateCityWeather(
                myCityBean,
                it.weatherIcon,
                "${it.todayWeather.min}~${it.todayWeather.max}°"
            )

            // 设置天气背景
            (parentFragment as RealTimeWeatherFragment).setWeatherBg(it.weatherBg, cityId)
            this.bottomColor = ColorUtils.getBottomColor(it.weatherBg)
            // 设置数据
            this.cityWeather = it
            binding.cityWeather = it
            binding.tvTemperature.text = "${it.temperature}"
            // 空气质量
            GlideApp.with(this).load(WeatherUtils.getAirQualityDescription(it.airQualityName))
                .into(binding.ivAir)
            binding.tvAirQuality.text = "${it.airQualityName} ${it.airQualityValue}"
            // 设置预警信息
            alertData.clear()
            alertData.addAll(it.alertInfo)
            alertAdapter.notifyDataSetChanged()

            // 24小时趋势
            binding.rvHourWeather.list = generateHourWeatherData(it)
            binding.rvHourWeather.setTimeColor(resources.getColor(R.color.weather_color_999999))
            binding.rvHourWeather.setTvWeatherColor(resources.getColor(R.color.weather_color_222222))
            binding.rvHourWeather.setTvTempColor(resources.getColor(R.color.weather_color_222222))
            binding.rvHourWeather.setColumnNumber(5)

            // 15天天气趋势
            trendListData.clear()
            trendListData.addAll(generateDayWeatherData(it));


            // 15天天气列表
            dailyListData.clear()
            dailyListData.addAll(it.dailyWeatherList)

            //设置快捷天气的空气质量
            WeatherUtils.setAirLevel(binding.tvTodayAirLevel, it.todayWeather.airQualityValue)
            WeatherUtils.setAirLevel(binding.tvTomorrowAirLevel, it.tomorrowWeather.airQualityValue)
            WeatherUtils.setAirLevel(
                binding.tvAfterTomorrowAirLevel,
                it.afterTomorrowWeather.airQualityValue
            )

        }
    }

    private fun generateDayWeatherData(cityWeather: CityWeather): MutableList<WeatherModel> {
        val list = arrayListOf<WeatherModel>()
        cityWeather.dailyWeatherList.forEach {
            list.add(
                WeatherModel(
                    it.max,
                    it.min,
                    it.weatherDayName,
                    it.weatherNightName,
                    it.date,
                    it.formatDate,
                    it.weatherDayIcon,
                    it.weatherNightIcon,
                    it.windDirection,
                    it.windSpeed,
                    it.airQualityValue
                )
            )
        }
        return list
    }

    private fun generateHourWeatherData(cityWeather: CityWeather): MutableList<HourWeatherModel> {
        //获取明天的日期
        val calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        val tomorrowDate = DateUtils.formatTime(calendar.time, DateUtils.PATTERN_14);

        val list = arrayListOf<HourWeatherModel>()
        cityWeather.hourlyWeatherList.forEachIndexed { index, it ->
            list.add(
                HourWeatherModel(
                    it.temperature,
                    it.weatherName,
                    if (index == 0) "现在" else if (it.time == "00:00") tomorrowDate else it.time,
                    it.weatherIcon,
                    it.airQualityValue,
                    index == 0,
                    it.windQualityValue,
                    it.directionQualityValue
                )
            )
        }
        return list
    }


    private fun voiceAnnouncements(str: String) {

        if (speechSynthesizer == null) {
            speechSynthesizer =
                SpeechSynthesizer.createSynthesizer(com.mufeng.mvvmlib.utilcode.utils.context) {
                    if (it != ErrorCode.SUCCESS) {
                        loge { "初始化失败, 错误码: $it" }
                    } else {
                        loge { "初始化成功" }
                    }
                }
            // 清空参数
            speechSynthesizer?.setParameter(SpeechConstant.PARAMS, null)
            speechSynthesizer?.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            speechSynthesizer?.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            speechSynthesizer?.setParameter(SpeechConstant.VOICE_NAME, "aisjinger");
            speechSynthesizer?.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "false");
        }

        if (speechSynthesizer?.isSpeaking == true) {
            speechSynthesizer?.stopSpeaking()
        } else {
            val code = speechSynthesizer?.startSpeaking(str, object : SynthesizerListener {
                override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
                    loge { "合成进度 进度值 = $p0" }
                }

                override fun onSpeakBegin() {
                    loge { "开始播放" }
                }

                override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
                    loge { "播放进度 = $p0" }
                }

                override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {

                }

                override fun onSpeakPaused() {
                    loge { "暂停播放" }
                }

                override fun onSpeakResumed() {
                    loge { "继续播放" }
                }

                override fun onCompleted(p0: SpeechError?) {
                    loge { "播放完成" }
                }
            })

            loge { code.toString() }
        }


    }

    override fun onError(e: ApiException) {
        super.onError(e)
        binding.refreshLayout.finishAll()
        binding.vObscuration.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放
        speechSynthesizer?.stopSpeaking()
        speechSynthesizer?.destroy()
    }

    fun getCityId(): String {
        return this.cityId
    }

    /**
     * 已经刷新过，并且时间小雨5分钟就不刷新
     */
    private fun isRefreshData(): Boolean {
        val time = Calendar.getInstance().time.time - refreshTime.time.time
        if (isAlreadyRefresh && time <= 5 * 60 * 1000) {
            return false
        }
        return true
    }
}