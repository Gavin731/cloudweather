package com.lnkj.weather.ui.air

import com.lnkj.weather.http.bean.HeAirQualityBean
import com.lnkj.weather.http.bean.WeatherBean

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/10 10:42
 * @描述
 */
data class AirSharedEvent(
    val cityName: String,
    val date: String,
    val airQualityBean: WeatherBean.Result.Realtime.AirQuality?
)