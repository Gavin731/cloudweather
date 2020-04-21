package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class MinutelyRainfallBean(
    @SerializedName("api_status")
    val apiStatus: String? = "",
    @SerializedName("api_version")
    val apiVersion: String? = "",
    @SerializedName("lang")
    val lang: String? = "",
    @SerializedName("location")
    val location: List<Double?>? = listOf(),
    @SerializedName("result")
    val result: Result? = Result(),
    @SerializedName("server_time")
    val serverTime: Int? = 0,
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("timezone")
    val timezone: String? = "",
    @SerializedName("tzshift")
    val tzshift: Int? = 0,
    @SerializedName("unit")
    val unit: String? = ""
) {
    data class Result(
        @SerializedName("forecast_keypoint")
        val forecastKeypoint: String? = "",
        @SerializedName("minutely")
        val minutely: Minutely? = Minutely(),
        @SerializedName("primary")
        val primary: Int? = 0
    ) {
        data class Minutely(
            @SerializedName("datasource")
            val datasource: String? = "",
            @SerializedName("description")
            val description: String? = "",
            @SerializedName("precipitation")
            val precipitation: List<Double?>? = listOf(),
            @SerializedName("precipitation_2h")
            val precipitation2h: List<Double?>? = listOf(),
            @SerializedName("probability")
            val probability: List<Double?>? = listOf(),
            @SerializedName("status")
            val status: String? = ""
        )
    }
}