package com.lnkj.weather.ui.realweather

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.lnkj.weather.ui.hotrank.HotRankActivity
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.ui.rain.RainActivity
import com.lnkj.weather.utils.ColorUtils
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.ImageUtils
import com.lnkj.weather.widget.zzweatherview.WeatherModel
import com.lnkj.weather.widget.zzweatherview.hour.HourWeatherModel
import com.mufeng.mvvmlib.http.ApiException
import com.mufeng.mvvmlib.utilcode.ext.loge
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.*
import com.mufeng.mvvmlib.utilcode.utils.toast


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
    private lateinit var dailyListAdapter: RealTimeWeather15DailyListAdapter
    private val dailyListData = arrayListOf<DailyWeather>()

    private var myCityBean: MyCityBean? = null

    private var cityId = ""

    fun getBitmap(): Bitmap? {
        binding.nestedScrollView.setBackgroundResource(cityWeather?.weatherBg!!)
        val bitmap = ImageUtils.getScrollViewBitmap(binding.nestedScrollView, bottomColor)
        binding.nestedScrollView.background = null
        return bitmap
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.refreshLayout.finishAll()
    }

    override fun initView() {
        cityId = arguments?.getString("cityId", "0")!!
        viewModel = ViewModelProvider(requireActivity()).get(cityId, RealTimeWeatherViewModel::class.java)
        binding.vm = viewModel
        myCityBean = arguments?.getParcelable("myCityBean")

        if (myCityBean == null) {
            toast("城市不能为空")
            return
        }

        binding.clMainLayout.gone()

        binding.nestedScrollView.setOnScrollChangeListener { _: NestedScrollView?, _, scrollY, _, _ ->
            val alpha = scrollY / 2
            (parentFragment as RealTimeWeatherFragment).setRealtimeBlurView(alpha)
        }

        binding.tvRainTip.clickWithTrigger {
            RainActivity.launch(requireActivity(), myCityBean, cityWeather!!.weatherName,cityWeather?.rainTip)
        }

        binding.llAirQuality.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(2)
        }
        binding.clTodayWeather.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(0)
        }
        binding.clTomorrowWeather.clickWithTrigger {
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(1)
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

        LiveEventBus.get(EventKey.EVENT_MINUTE_CHANGE, Boolean::class.java)
            .observe(this){
                this.canRefresh = true
                if (isResumed) {
                    // 获取上次更新时间
                    viewModel.searchWeatherTimeByCity(myCityBean!!)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchWeatherTimeByCity(myCityBean!!)
        handler.postDelayed(loadData, 500)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(loadData)
    }

    private val loadData = Runnable {
        if (canRefresh){
            viewModel.searchWeatherByCity(myCityBean!!)
            canRefresh = false
        }
    }


    override fun initData() {
        // 语音播报
        binding.ivPlayAudio.clickWithTrigger {
            voiceAnnouncements(cityWeather?.voiceAnnouncements!!)
        }

        initAlertRecycler()
        initDailyList()

        binding.refreshLayout.setOnRefreshListener {
            viewModel?.getCaiYunRealtimeWeather(myCityBean!!, myCityBean?.lon!!, myCityBean?.lat!!)
        }

        binding.rgDayListChart.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_day_chart -> {
                    binding.rvDayChart.visible()
                    binding.rvDayList.gone()
                }
                R.id.rb_day_list -> {
                    binding.rvDayList.visible()
                    binding.rvDayChart.gone()
                }
            }
        }
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
        binding.zzWeatherView.setOnWeatherItemClickListener { itemView, position, weatherModel ->
            if (position == 0 || position > 3) return@setOnWeatherItemClickListener
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(position - 1)
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

    private fun initDailyList() {
        binding.rvDayList.layoutManager = LinearLayoutManager(requireActivity())
        dailyListAdapter = RealTimeWeather15DailyListAdapter(dailyListData)
        binding.rvDayList.adapter = dailyListAdapter
        binding.rvDayList.removeAllAnimation()

        dailyListAdapter.setOnItemClickListener { _, _, position ->
            if (position == 0 || position > 3) return@setOnItemClickListener
            (requireActivity() as MainActivity).selectFragment(1)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(position - 1)
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
        viewModel.cityWeatherTime.observe(this){
            // 判断更新时间距离当前时间超过30分钟进行刷新
            if (DateUtils.compareDate(it) >= 5 * 60) {
                binding.refreshLayout.autoRefresh(500)
                LiveEventBus.get(EventKey.EVENT_CHANGE_CITY)
                    .post(myCityBean)
            }
        }
        viewModel?.cityWeatherData?.observe(this) {
            if (it == null) {
                binding.refreshLayout.autoRefresh()
                return@observe
            }
            binding.refreshLayout.finishRefresh()
            binding.clMainLayout.visible()

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
            binding.tvAirQuality.text = "${it.airQualityName} ${it.airQualityValue}"
            // 设置预警信息
            alertData.clear()
            alertData.addAll(it.alertInfo)
            alertAdapter.notifyDataSetChanged()

            // 24小时趋势
            binding.rvHourWeather.list = generateHourWeatherData(it)
            binding.rvHourWeather.setColumnNumber(5)

            // 15天天气趋势
            binding.zzWeatherView.postDelayed({
                binding.zzWeatherView.list = generateDayWeatherData(it)
                binding.zzWeatherView.setColumnNumber(5)
            },500)

            // 15天天气列表
            dailyListData.clear()
            dailyListData.addAll(it.dailyWeatherList)
            dailyListAdapter.notifyDataSetChanged()

            // 判断更新时间距离当前时间超过30分钟进行刷新
            if (DateUtils.compareDate(it.updateDate) >= 5 * 60) {
                binding.refreshLayout.autoRefresh()
            }
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
        val list = arrayListOf<HourWeatherModel>()
        cityWeather.hourlyWeatherList.forEachIndexed { index, it ->
            list.add(
                HourWeatherModel(
                    it.temperature,
                    it.weatherName,
                    if (index == 0) "现在" else it.time,
                    it.weatherIcon,
                    it.airQualityValue,
                    index == 0
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
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放
        speechSynthesizer?.stopSpeaking()
        speechSynthesizer?.destroy()
    }


}