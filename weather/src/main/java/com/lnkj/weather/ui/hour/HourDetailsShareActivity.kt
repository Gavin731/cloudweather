package com.lnkj.weather.ui.hour

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityHourlyWeatherShareBinding
import com.lnkj.weather.utils.ImageUtils
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.getString
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.imageResource
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import com.mufeng.mvvmlib.utilcode.utils.toast
import com.mufeng.sociallibrary.Social
import com.mufeng.sociallibrary.config.PlatformType

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/3 15:58
 * @描述
 */
class HourDetailsShareActivity : BaseVMActivity<BaseViewModel, WeatherActivityHourlyWeatherShareBinding>() {
    private var bitmap: Bitmap? = null
    override val viewModel: BaseViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_hourly_weather_share

    companion object {
        fun launch(
            context: Context,
            currentT: Int,
            weatherName: String,
            windD: String,
            windSpeed: String,
            humidity: String,
            pressure: String,
            cityName: String,
            date: String,
            lifeTxt: String,
            weatherBg: Int
        ) {
            context.startActivity<HourDetailsShareActivity>(
                "currentT" to currentT,
                "weatherName" to weatherName,
                "windD" to windD,
                "windSpeed" to windSpeed,
                "humidity" to humidity,
                "pressure" to pressure,
                "cityName" to cityName,
                "date" to date,
                "lifeTxt" to lifeTxt,
                "weatherBg" to weatherBg
            )
        }
    }

    private var currentT: Int = 0
    private var weatherBg: Int = 0
    private var weatherName: String = ""
    private var windD: String = ""
    private var windSpeed: String = ""
    private var humidity: String = ""
    private var pressure: String = ""
    private var cityName: String = ""
    private var date: String = ""
    private var lifeTxt: String = ""

    override fun initView(savedInstanceState: Bundle?) {

        StatusBarUtils.addTranslucentView(this, binding.rclBit)

        currentT = intent.getIntExtra("currentT",0)
        weatherBg = intent.getIntExtra("weatherBg",-1)
        weatherName = intent.getString("weatherName","")
        windD = intent.getString("windD","")
        windSpeed = intent.getString("windSpeed","")
        humidity = intent.getString("humidity","")
        pressure = intent.getString("pressure","")
        cityName = intent.getString("cityName","")
        date = intent.getString("date","")
        lifeTxt = intent.getString("lifeTxt","")

        if (weatherBg != -1) {
//            binding.ivImageBg.imageResource = weatherBg
            Glide.with(this).load(weatherBg).into(binding.ivImageBg)
        }

        binding.tvTemperature.text = currentT.toString()
        binding.tvWeatherName.text = weatherName
        binding.tvWindDirection.text = windD
        binding.tvWindSpeed.text = windSpeed
        binding.tvHumidity.text = humidity
        binding.tvAirPressure.text = pressure
        binding.tvCity.text = cityName
        binding.tvDate.text = date
        binding.tvContent.text = lifeTxt

        if (lifeTxt.isNullOrEmpty()){
            binding.tvContent.gone()
        }else{
            binding.tvContent.visible()
        }

    }

    override fun initData() {
        binding.icClose.clickWithTrigger { finish() }

        binding.llShareQq.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.QQ)) {
                toast("应用未安装")
                return@clickWithTrigger
            }

            this.bitmap = ImageUtils.getViewBitmap(binding.rclBit)

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
            this.bitmap = ImageUtils.getViewBitmap(binding.rclBit)
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
            this.bitmap = ImageUtils.getViewBitmap(binding.rclBit)
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
            this.bitmap = ImageUtils.getViewBitmap(binding.rclBit)
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
}