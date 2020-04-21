package com.lnkj.weather.ui.air

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityAirWeatherShareBinding
import com.lnkj.weather.http.bean.HeAirQualityBean
import com.lnkj.weather.utils.ImageUtils
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import com.mufeng.mvvmlib.utilcode.utils.toast
import com.mufeng.sociallibrary.Social
import com.mufeng.sociallibrary.config.PlatformType

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/3 16:56
 * @描述
 */
class AirQualityShareActivity: BaseVMActivity<BaseViewModel, WeatherActivityAirWeatherShareBinding>() {
    private var bitmap: Bitmap? = null
    override val viewModel: BaseViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_air_weather_share

    companion object{
        fun launch(context: Context){
            context.startActivity<AirQualityShareActivity>()
        }
    }

    private var cityName: String = ""
    private var date: String = ""
    private var airQualityBean: HeAirQualityBean? = null

    override fun initView(savedInstanceState: Bundle?) {

        StatusBarUtils.addTranslucentView(this, binding.rclBit)

        LiveEventBus.get(EventKey.EVENT_AIR_SHARE, AirSharedEvent::class.java)
            .observeSticky(this, Observer {
                cityName = it.cityName
                date = it.date
                airQualityBean = it.airQualityBean

                val airNowCity = airQualityBean?.heWeather6?.get(0)?.airNowCity!!

                binding.tvCity.text = cityName
                binding.tvDate.text = date

                // 空气质量数值
                binding.circleProgressBar.hint = airNowCity.aqi.toString()
                // 空气质量
                binding.circleProgressBar.unit = WeatherUtils.getAirQualityDescription(airNowCity.aqi!!)
                binding.circleProgressBar.value = when {
                    airNowCity.aqi <= 150 -> {
                        Glide.with(this)
                            .load(R.drawable.weather_good_air_quality_bg)
                            .into(binding.ivImageBg)
                        airNowCity.aqi.toFloat()
                    }
                    airNowCity.aqi == 500 -> {
                        Glide.with(this)
                            .load(R.drawable.weather_bad_air_quality_bg)
                            .into(binding.ivImageBg)
                        300.toFloat()
                    }
                    else -> {
                        Glide.with(this)
                            .load(R.drawable.weather_bad_air_quality_bg)
                            .into(binding.ivImageBg)
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
            })





        binding.icClose.clickWithTrigger { finish() }

        binding.llShareQq.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.QQ)) {
                toast("应用未安装")
                return@clickWithTrigger
            }
            val bitmap = ImageUtils.getViewBitmap(binding.rclBit)
            Social.share(
                this,
                PlatformType.QQ,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareQzone.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.QQ_ZONE)) {
                toast("应用未安装")
                return@clickWithTrigger
            }
            val bitmap = ImageUtils.getViewBitmap(binding.rclBit)
            Social.share(
                this,
                PlatformType.QQ_ZONE,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareWechat.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.WEIXIN)) {
                toast("应用未安装")
                return@clickWithTrigger
            }
            val bitmap = ImageUtils.getViewBitmap(binding.rclBit)
            Social.share(
                this,
                PlatformType.WEIXIN,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareWechatTimeline.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.WEIXIN_CIRCLE)) {
                toast("应用未安装")
                return@clickWithTrigger
            }
            val bitmap = ImageUtils.getViewBitmap(binding.rclBit)
            Social.share(
                this,
                PlatformType.WEIXIN_CIRCLE,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

    }

    override fun initData() {
    }
}