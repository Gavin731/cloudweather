package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName
import com.lnkj.library_base.db.bean.CityBean

data class HeWeatherCityBean(
    @SerializedName("HeWeather6")
    val heWeather6: MutableList<HeWeather6> = mutableListOf()
) {
    data class HeWeather6(
        @SerializedName("basic")
        val basic: MutableList<CityBean> = mutableListOf(),
        @SerializedName("status")
        val status: String? = ""
    )
}