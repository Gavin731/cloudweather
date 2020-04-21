package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class HeForecastAirBean(
    @SerializedName("HeWeather6")
    val heWeather6: List<HeWeather6?>? = listOf()
) {
    data class HeWeather6(
        @SerializedName("air_forecast")
        val airForecast: List<AirForecast?>? = listOf(),
        @SerializedName("basic")
        val basic: Basic? = Basic(),
        @SerializedName("status")
        val status: String? = "",
        @SerializedName("update")
        val update: Update? = Update()
    ) {
        data class AirForecast(
            @SerializedName("aqi")
            val aqi: Int? = 0,
            @SerializedName("date")
            val date: String? = "",
            @SerializedName("main")
            val main: String? = "",
            @SerializedName("qlty")
            val qlty: String? = ""
        )

        data class Basic(
            @SerializedName("admin_area")
            val adminArea: String? = "",
            @SerializedName("cid")
            val cid: String? = "",
            @SerializedName("cnty")
            val cnty: String? = "",
            @SerializedName("lat")
            val lat: String? = "",
            @SerializedName("location")
            val location: String? = "",
            @SerializedName("lon")
            val lon: String? = "",
            @SerializedName("parent_city")
            val parentCity: String? = "",
            @SerializedName("tz")
            val tz: String? = ""
        )

        data class Update(
            @SerializedName("loc")
            val loc: String? = "",
            @SerializedName("utc")
            val utc: String? = ""
        )
    }
}