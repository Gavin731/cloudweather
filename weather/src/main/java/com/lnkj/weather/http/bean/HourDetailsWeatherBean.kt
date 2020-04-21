package com.lnkj.weather.http.bean

import com.lnkj.library_base.db.bean.DressLifeStyle
import com.lnkj.library_base.db.bean.HourlyWeather
import com.lnkj.library_base.db.bean.LifeStyle

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/1 15:06
 * @描述
 */
data class HourDetailsWeatherBean(
    var min: Int,
    var max: Int,
    var weatherName: String,
    var weatherIcon: Int,
    var windSpeed: String,
    var windDirection: String,
    var humidity: String,
    var pressure: String,
    var ultravioletLight: String,
    var sunriseTime: String,
    var sunsetTime: String,
    var hourlyWeatherList: MutableList<HourlyWeather>,
    var airQualityValue: Int,
    var airQualityName: String,
    var airQualityTxt: String,
    var dressLifeStyle: DressLifeStyle? = null,
    var lifeStyleList: MutableList<LifeStyle> = mutableListOf(),
    var currentWeather: String = "",
    var currentTemperature: Int = 0,
    var flTemperature: Int = 0,
    var yesterdayMin: Int = 0,
    var yesterdayMax: Int = 0
)