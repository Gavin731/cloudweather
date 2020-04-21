package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

/**
 * 小时级天气
 * @property apiStatus String?
 * @property apiVersion String?
 * @property lang String?
 * @property location List<Double?>?
 * @property result Result?
 * @property serverTime Int?
 * @property status String?
 * @property timezone String?
 * @property tzshift Int?
 * @property unit String?
 * @constructor
 */
data class HourlyWeatherBean(
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
        @SerializedName("hourly")
        val hourly: Hourly? = Hourly(),
        @SerializedName("primary")
        val primary: Int? = 0
    ) {
        data class Hourly(
            @SerializedName("air_quality")
            val airQuality: AirQuality? = AirQuality(),
            @SerializedName("cloudrate")
            val cloudrate: List<Cloudrate?>? = listOf(),
            @SerializedName("description")
            val description: String? = "",
            @SerializedName("dswrf")
            val dswrf: List<Dswrf?>? = listOf(),
            @SerializedName("humidity")
            val humidity: List<Humidity?>? = listOf(),
            @SerializedName("precipitation")
            val precipitation: List<Precipitation?>? = listOf(),
            @SerializedName("pressure")
            val pressure: List<Pressure?>? = listOf(),
            @SerializedName("skycon")
            val skycon: List<Skycon?>? = listOf(),
            @SerializedName("status")
            val status: String? = "",
            @SerializedName("temperature")
            val temperature: List<Temperature?>? = listOf(),
            @SerializedName("visibility")
            val visibility: List<Visibility?>? = listOf(),
            @SerializedName("wind")
            val wind: List<Wind?>? = listOf()
        ) {
            data class AirQuality(
                @SerializedName("aqi")
                val aqi: List<Aqi?>? = listOf(),
                @SerializedName("pm25")
                val pm25: List<Pm25?>? = listOf()
            ) {
                data class Aqi(
                    @SerializedName("datetime")
                    val datetime: String? = "",
                    @SerializedName("value")
                    val value: Value? = Value()
                ) {
                    data class Value(
                        @SerializedName("chn")
                        val chn: Int? = 0,
                        @SerializedName("usa")
                        val usa: Int? = 0
                    )
                }

                data class Pm25(
                    @SerializedName("datetime")
                    val datetime: String? = "",
                    @SerializedName("value")
                    val value: Int? = 0
                )
            }

            data class Cloudrate(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Dswrf(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Humidity(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Precipitation(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Pressure(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Skycon(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: String? = ""
            )

            data class Temperature(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Visibility(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("value")
                val value: Double? = 0.0
            )

            data class Wind(
                @SerializedName("datetime")
                val datetime: String? = "",
                @SerializedName("direction")
                val direction: Double? = 0.0,
                @SerializedName("speed")
                val speed: Double? = 0.0
            )
        }
    }
}