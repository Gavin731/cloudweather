package com.lnkj.weather.ui.exponent

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import com.lnkj.library_base.db.bean.LifeStyle
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityExponentInfoBinding
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.getString
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.imageResource

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/31 16:36
 * @描述
 */
class ExponentActivity: BaseVMActivity<ExponentViewModel, WeatherActivityExponentInfoBinding>() {

    companion object {
        fun launch(context: Context, lifeStyle: LifeStyle, cityName: String, day: String){
            context.startActivity<ExponentActivity>(
                "lifeStyle" to lifeStyle,
                "cityName" to cityName,
                "day" to day
            )
        }
    }

    private var lifeStyle: LifeStyle? = null
    private var cityName: String = ""
    private var day: String = ""

    override val viewModel: ExponentViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_exponent_info

    override fun initView(savedInstanceState: Bundle?) {
        lifeStyle = intent.getParcelableExtra("lifeStyle")
        cityName = intent.getString("cityName")
        day = intent.getString("day")

        binding.ivBack.clickWithTrigger { finish() }

        // 背景
        binding.llRootView.setBackgroundResource(if (lifeStyle?.isGood == true) R.drawable.weather_life_index_good_bg else R.drawable.weather_life_index_bad_bg)
        // 标题
        binding.tvTitle.text = "${cityName}${day}${lifeStyle?.indexName}"
        // 表情
        binding.ivFace.imageResource = if (lifeStyle?.isGood == true) R.mipmap.weather_icon_smiling_face else R.mipmap.weather_icon_crying_face
        binding.tvLifeStyleName.text = lifeStyle?.indexBrf

        // 天气
        binding.tvWeatherName.text = lifeStyle?.weatherName
        // 温度
        binding.tvTemperature.text = "${lifeStyle?.min}~${lifeStyle?.max}°"
        //温差
        binding.tvTemperatureDifference.text = "${lifeStyle?.temperatureDifference}°"
        // 风向
        binding.tvWindDirection.text = lifeStyle?.windDirection
        // 风速
        binding.tvWindSpeed.text = lifeStyle?.windSpeed
        // 日出时间
        binding.tvSunriseTime.text = lifeStyle?.sunriseTime
        // 日落时间
        binding.tvSunsetTime.text = lifeStyle?.sunsetTime

        binding.tvLifeTxt.text = lifeStyle?.indexTxt
        binding.tvKnowledgeTitle.text = lifeStyle?.knowledgeTitle
        binding.tvKnowledge.text = lifeStyle?.knowledge

    }

    override fun initData() {

    }
}