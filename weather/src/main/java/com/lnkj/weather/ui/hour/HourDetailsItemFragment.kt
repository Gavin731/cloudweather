package com.lnkj.weather.ui.hour

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.bean.toLifeStyle
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherFragmentItemHourDetailsBinding
import com.lnkj.weather.http.bean.HourDetailsWeatherBean
import com.lnkj.weather.ui.exponent.ExponentActivity
import com.lnkj.weather.ui.hotrank.HotRankActivity
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.widget.zzweatherview.hour.HourWeatherModel
import com.mufeng.mvvmlib.basic.view.BaseVMFragment
import com.mufeng.mvvmlib.http.ApiException
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.*
import kotlinx.android.synthetic.main.weather_fragment_item_hour_details.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/1 10:06
 * @描述
 */
class HourDetailsItemFragment :
    BaseVMFragment<HourDetailsViewModel, WeatherFragmentItemHourDetailsBinding>() {

    companion object {
        fun getInstance(timestamp: Long, day: Int, cityBean: MyCityBean): HourDetailsItemFragment {
            val fragment = HourDetailsItemFragment()
            val bundle = Bundle()
            bundle.putLong("timestamp", timestamp)
            bundle.putInt("day", day)
            bundle.putParcelable("cityBean", cityBean)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var cityWeather: HourDetailsWeatherBean? = null
    private var timestamp: Long = 0
    private var day: Int = 1
    private var cityBean: MyCityBean? = null

    override val viewModel: HourDetailsViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_fragment_item_hour_details

    fun getData(): HourDetailsWeatherBean? {
        return cityWeather
    }

    override fun initView() {
        timestamp = arguments?.getLong("timestamp") ?: return
        day = arguments?.getInt("day")!!
        cityBean = arguments?.getParcelable("cityBean")
        binding.isToday = day == 1

        binding.nestedScrollView.gone()
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getWeather(
                timestamp,
                day,
                cityBean!!
            )
        }

        // 生活指数点击事件
        // 穿衣指数
        binding.clLiveIndexCoat.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.dressLifeStyle?.toLifeStyle()!!,
                cityBean?.counties!!,
                "今日"
            )
        }

        // 雨伞指数
        binding.llLiveIndexUmbrella.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(0)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 感冒指数
        binding.llLiveIndexCold.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(1)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 紫外线
        binding.llLiveIndexUltravioletLight.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(2)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 晾晒
        binding.llLiveIndexCoatHanger.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(3)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 晨练
        binding.llLiveIndexMorningExercise.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(4)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 旅游
        binding.llLiveIndexTravel.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(5)!!,
                cityBean?.counties!!,
                "今日"
            )
        }
        // 钓鱼
        binding.llLiveIndexAngling.clickWithTrigger {
            ExponentActivity.launch(
                requireContext(),
                cityWeather?.lifeStyleList?.get(6)!!,
                cityBean?.counties!!,
                "今日"
            )
        }

        binding.llLiveIndexHighTemperature.clickWithTrigger {
            startActivity<HotRankActivity>()
        }

        binding.clAir.clickWithTrigger {
            if (day == 1) {
                (requireActivity() as MainActivity).selectFragment(2)
            }
        }
    }

    override fun initData() {
        refreshLayout.autoRefresh()
    }

    override fun hideLoading() {
        super.hideLoading()
        refreshLayout.finishAll()
    }

    private var loaded = false
    override fun onResume() {
        super.onResume()
        if (!loaded) {
            loaded = true
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancel()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.hourlyDetailsWeatherData.observe(this) {
            refreshLayout.finishRefresh()
            this.cityWeather = it
            binding.nestedScrollView.visible()
            // 设置数据
            binding.tvTodayTemperature.text = "${it.min}~${it.max}°"
            binding.ivWeatherIcon.imageResource = it.weatherIcon
            binding.tvWeatherName.text = it.weatherName

            // 今天数据
            binding.llCurrentView.visible()
            // 当前天气+温度
            binding.tvCurrentWeather.text = "${it.currentWeather} ${it.currentTemperature}℃"
            // 体感温度
            binding.tvDegreeCentigrade.text = "${it.flTemperature}℃"
            // 昨天温度
            binding.tvYesterdayTemperature.text = "${it.yesterdayMin}~${it.yesterdayMax}℃"
            binding.view.visible()
            binding.llLiveIndex.visible()

            // 穿衣指数
            it.dressLifeStyle?.dressIcon?.let { it1 ->
                binding.ivWeatherClothes.setImageResource(
                    it1
                )
            }
            binding.tvWeatherClothesTip.text = it.dressLifeStyle?.indexBrf
            binding.tvWeatherTemperatureDifference.text = it.dressLifeStyle?.dressTopTip
            binding.tvWeatherLiveIndexTip.text = it.dressLifeStyle?.dressBottomTip

            binding.tvLiveIndexUmbrella.text = it.lifeStyleList[0].indexBrf
            binding.tvLiveIndexCold.text = it.lifeStyleList[1].indexBrf
            binding.tvLiveIndexUltravioletLight.text = it.lifeStyleList[2].indexBrf
            binding.tvLiveIndexCoatHanger.text = it.lifeStyleList[3].indexBrf
            binding.tvLiveIndexMorningExercise.text = it.lifeStyleList[4].indexBrf
            binding.tvLiveIndexTravel.text = it.lifeStyleList[5].indexBrf
            binding.tvLiveIndexAngling.text = it.lifeStyleList[6].indexBrf

            if (day == 1) {
                binding.llCurrentView.visible()
                binding.view.visible()

            } else {
                // 非今天
                binding.llCurrentView.gone()
                binding.view.gone()
            }

            // 风速
            binding.tvWindSpeed.text = it.windSpeed
            // 风向
            binding.tvWindDirection.text = it.windDirection
            // 湿度
            binding.tvHumidity.text = it.humidity
            // 气压
            binding.tvPressure.text = it.pressure
            // 紫外线
            binding.tvUltravioletLight.text = it.ultravioletLight
            // 日出时间
            binding.tvSunriseTime.text = it.sunriseTime
            // 日落时间
            binding.tvSunsetTime.text = it.sunsetTime
            // 24小时天气
            binding.rvHourWeather.isDrawPath(false)
            binding.rvHourWeather.list = generateHourWeatherData(it)
            binding.rvHourWeather.setColumnNumber(5)
            // 空气质量数值
            binding.circleProgressBar.hint = it.airQualityValue.toString()
            // 空气质量
            binding.circleProgressBar.unit = it.airQualityName
            binding.circleProgressBar.value = when {
                it.airQualityValue <= 150 -> {
                    it.airQualityValue.toFloat()
                }
                it.airQualityValue == 500 -> {
                    300.toFloat()
                }
                else -> {
                    150 + (it.airQualityValue - 150) * (150 / 350).toFloat()
                }
            }
            binding.tvAirTip.text = it.airQualityTxt


        }
    }

    private fun generateHourWeatherData(weather: HourDetailsWeatherBean): MutableList<HourWeatherModel>? {

        val list = arrayListOf<HourWeatherModel>()
        weather.hourlyWeatherList.forEachIndexed { index, it ->
            list.add(
                HourWeatherModel(
                    it.temperature,
                    it.weatherName,
                    if (day == 1 && index == 0) "现在" else it.time,
                    it.weatherIcon,
                    it.airQualityValue,
                    day == 1 && index == 0
                )
            )
        }
        return list

    }

    override fun onError(e: ApiException) {
        super.onError(e)
        binding.refreshLayout.finishAll()
    }
}