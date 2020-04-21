package com.lnkj.weather.ui.air

import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherFragmentAirQualityBinding
import com.lnkj.weather.http.bean.HeAirQualityBean
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.mvvmlib.basic.view.BaseVMFragment
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.finishAll
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:51
 * @描述
 */
class AirQualityFragment : BaseVMFragment<AirQualityViewModel, WeatherFragmentAirQualityBinding>() {
    private var airQuality: HeAirQualityBean? = null
    private var cityBean: MyCityBean? = null
    override val viewModel: AirQualityViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_fragment_air_quality

    override fun initView() {
        binding.vm = viewModel
        StatusBarUtils.addTranslucentView(requireActivity(), binding.toolbar)
        binding.ivShare.clickWithTrigger {

            LiveEventBus.get(EventKey.EVENT_AIR_SHARE)
                .post(AirSharedEvent(cityBean?.counties!!, DateUtils.formatTime(
                    Date(), DateUtils.PATTERN_14
                ), airQuality))

            AirQualityShareActivity.launch(requireActivity())
        }

        binding.refreshLayout.setOnRefreshListener {
            refreshData(cityBean)
        }
    }

    override fun initData() {

        LiveEventBus.get(EventKey.EVENT_CHANGE_CITY, MyCityBean::class.java)
            .observe(this) {
                loaded = false
                this.cityBean = it
                refreshData(cityBean)
            }
    }

    private fun refreshData(myCityBean: MyCityBean?) {
        this.cityBean = myCityBean
        viewModel.title.postValue("${myCityBean?.counties}空气质量")
        viewModel.getAirData(myCityBean!!)
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.airData.observe(this) {
            binding.refreshLayout.finishAll()
            this.airQuality = it
            val update = it.heWeather6?.get(0)?.update!!
            val airNowCity = it.heWeather6?.get(0)?.airNowCity!!

            binding.tvPushTime.text = update.loc?.split(" ")?.get(1) + "发布"
            // 空气质量数值
            binding.circleProgressBar.hint = airNowCity.aqi.toString()
            // 空气质量
            binding.circleProgressBar.unit = WeatherUtils.getAirQualityDescription(airNowCity.aqi!!)
            binding.circleProgressBar.value = when {
                airNowCity.aqi <= 150 -> {
                    Glide.with(requireActivity())
                        .load(R.drawable.weather_good_air_quality_bg)
                        .into(binding.ivMainBg)
                    airNowCity.aqi.toFloat()
                }
                airNowCity.aqi == 500 -> {
                    Glide.with(requireActivity())
                        .load(R.drawable.weather_bad_air_quality_bg)
                        .into(binding.ivMainBg)
                    300.toFloat()
                }
                else -> {
                    Glide.with(requireActivity())
                        .load(R.drawable.weather_bad_air_quality_bg)
                        .into(binding.ivMainBg)
                    150 + (airNowCity.aqi - 150) * (150 / 350).toFloat()
                }
            }
            binding.tvAirTip.text = WeatherUtils.getAirQualityTxt(airNowCity.aqi)

            binding.tvPm25Value.text = airNowCity.pm25
            binding.tvPm10Value.text = airNowCity.pm10
            binding.tvPmSO2Value.text = airNowCity.so2
            binding.tvNo2Value.text = airNowCity.no2
            binding.tvCoValue.text = airNowCity.co
            binding.tvO3Value.text = airNowCity.o3

            binding.tvPm25Color.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.pm25?.toDouble()?.toInt()!!))
            binding.tvPm10Color.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.pm10?.toDouble()?.toInt()!!))
            binding.tvPmSO2Color.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.so2?.toDouble()?.toInt()!!))
            binding.tvNo2Color.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.no2?.toDouble()?.toInt()!!))
            binding.tvCoColor.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.co?.toDouble()?.toInt()!!))
            binding.tvO3Color.delegate.backgroundColor =
                resources.getColor(WeatherUtils.getAirQualityColor(airNowCity.o3?.toDouble()?.toInt()!!))

        }
    }

    private var loaded = false
    override fun onResume() {
        super.onResume()
        if (!loaded) {
            refreshData(cityBean)
            loaded = true
        }
    }
}