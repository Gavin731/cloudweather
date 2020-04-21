package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

/**
 *
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
data class RealtimeWeatherBean(
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
    /**
     *
     * @property alert Alert?   预警信息
     * @property primary Int?
     * @property realtime Realtime? 实况信息
     * @constructor
     */
    data class Result(
        @SerializedName("alert")
        val alert: Alert? = Alert(),
        @SerializedName("primary")
        val primary: Int? = 0,
        @SerializedName("realtime")
        val realtime: Realtime? = Realtime()
    ) {
        data class Alert(
            @SerializedName("content")
            val content: List<Content?>? = listOf(),
            @SerializedName("status")
            val status: String? = ""
        ) {
            data class Content(
                @SerializedName("adcode")
                val adcode: String? = "",
                @SerializedName("alertId")
                val alertId: String? = "",
                @SerializedName("city")
                val city: String? = "",
                @SerializedName("code")
                val code: String? = "",
                @SerializedName("county")
                val county: String? = "",
                @SerializedName("description")
                val description: String? = "",
                @SerializedName("latlon")
                val latlon: List<Double?>? = listOf(),
                @SerializedName("location")
                val location: String? = "",
                @SerializedName("province")
                val province: String? = "",
                @SerializedName("pubtimestamp")
                val pubtimestamp: Int? = 0,
                @SerializedName("regionId")
                val regionId: String? = "",
                @SerializedName("request_status")
                val requestStatus: String? = "",
                @SerializedName("source")
                val source: String? = "",
                @SerializedName("status")
                val status: String? = "",
                @SerializedName("title")
                val title: String? = ""
            )
        }

        data class Realtime(
            @SerializedName("air_quality")
            val airQuality: AirQuality? = AirQuality(),
            @SerializedName("apparent_temperature")
            val apparentTemperature: Double? = 0.0,
            @SerializedName("cloudrate")
            val cloudrate: Int? = 0,
            @SerializedName("dswrf")
            val dswrf: Double? = 0.0,
            @SerializedName("humidity")
            val humidity: Double? = 0.0,
            @SerializedName("life_index")
            val lifeIndex: LifeIndex? = LifeIndex(),
            @SerializedName("precipitation")
            val precipitation: Precipitation? = Precipitation(),
            @SerializedName("pressure")
            val pressure: Double? = 0.0,
            @SerializedName("skycon")
            val skycon: String? = "",
            @SerializedName("status")
            val status: String? = "",
            @SerializedName("temperature")
            val temperature: Int? = 0,
            @SerializedName("visibility")
            val visibility: Double? = 0.0,
            @SerializedName("wind")
            val wind: Wind? = Wind()
        ) {
            data class AirQuality(
                @SerializedName("aqi")
                val aqi: Aqi? = Aqi(),
                @SerializedName("co")
                val co: Double? = 0.0,
                @SerializedName("description")
                val description: Description? = Description(),
                @SerializedName("no2")
                val no2: Int? = 0,
                @SerializedName("o3")
                val o3: Int? = 0,
                @SerializedName("pm10")
                val pm10: Int? = 0,
                @SerializedName("pm25")
                val pm25: Int? = 0,
                @SerializedName("so2")
                val so2: Int? = 0
            ) {
                data class Aqi(
                    @SerializedName("chn")
                    val chn: Int? = 0,
                    @SerializedName("usa")
                    val usa: Int? = 0
                )

                data class Description(
                    @SerializedName("chn")
                    val chn: String? = "",
                    @SerializedName("usa")
                    val usa: String? = ""
                )
            }

            data class LifeIndex(
                @SerializedName("comfort")
                val comfort: Comfort? = Comfort(),
                @SerializedName("ultraviolet")
                val ultraviolet: Ultraviolet? = Ultraviolet()
            ) {
                data class Comfort(
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: Int? = 0
                )

                data class Ultraviolet(
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: Int? = 0
                )
            }

            data class Precipitation(
                @SerializedName("local")
                val local: Local? = Local(),
                @SerializedName("nearest")
                val nearest: Nearest? = Nearest()
            ) {
                data class Local(
                    @SerializedName("datasource")
                    val datasource: String? = "",
                    @SerializedName("intensity")
                    val intensity: Double? = 0.0,
                    @SerializedName("status")
                    val status: String? = ""
                )

                data class Nearest(
                    @SerializedName("distance")
                    val distance: Double? = 0.0,
                    @SerializedName("intensity")
                    val intensity: Double? = 0.0,
                    @SerializedName("status")
                    val status: String? = ""
                )
            }

            data class Wind(
                @SerializedName("direction")
                val direction: Int? = 0,
                @SerializedName("speed")
                val speed: Double? = 0.0
            )
        }
    }
}