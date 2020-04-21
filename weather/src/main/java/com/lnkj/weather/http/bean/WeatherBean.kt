package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class WeatherBean(
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
        @SerializedName("alert")
        val alert: Alert? = Alert(),
        @SerializedName("daily")
        val daily: Daily? = Daily(),
        @SerializedName("forecast_keypoint")
        val forecastKeypoint: String? = "",
        @SerializedName("hourly")
        val hourly: Hourly? = Hourly(),
        @SerializedName("minutely")
        val minutely: Minutely? = Minutely(),
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

        data class Daily(
            @SerializedName("air_quality")
            val airQuality: AirQuality? = AirQuality(),
            @SerializedName("astro")
            val astro: List<Astro?>? = listOf(),
            @SerializedName("cloudrate")
            val cloudrate: List<Cloudrate?>? = listOf(),
            @SerializedName("dswrf")
            val dswrf: List<Dswrf?>? = listOf(),
            @SerializedName("humidity")
            val humidity: List<Humidity?>? = listOf(),
            @SerializedName("life_index")
            val lifeIndex: LifeIndex? = LifeIndex(),
            @SerializedName("precipitation")
            val precipitation: List<Precipitation?>? = listOf(),
            @SerializedName("pressure")
            val pressure: List<Pressure?>? = listOf(),
            @SerializedName("skycon")
            val skycon: List<Skycon?>? = listOf(),
            @SerializedName("skycon_08h_20h")
            val skycon08h20h: List<Skycon08h20h?>? = listOf(),
            @SerializedName("skycon_20h_32h")
            val skycon20h32h: List<Skycon20h32h?>? = listOf(),
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
                    @SerializedName("avg")
                    val avg: Avg? = Avg(),
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("max")
                    val max: Max? = Max(),
                    @SerializedName("min")
                    val min: Min? = Min()
                ) {
                    data class Avg(
                        @SerializedName("chn")
                        val chn: Double? = 0.0,
                        @SerializedName("usa")
                        val usa: Double? = 0.0
                    )

                    data class Max(
                        @SerializedName("chn")
                        val chn: Int? = 0,
                        @SerializedName("usa")
                        val usa: Int? = 0
                    )

                    data class Min(
                        @SerializedName("chn")
                        val chn: Int? = 0,
                        @SerializedName("usa")
                        val usa: Int? = 0
                    )
                }

                data class Pm25(
                    @SerializedName("avg")
                    val avg: Double? = 0.0,
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("max")
                    val max: Int? = 0,
                    @SerializedName("min")
                    val min: Int? = 0
                )
            }

            data class Astro(
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("sunrise")
                val sunrise: Sunrise? = Sunrise(),
                @SerializedName("sunset")
                val sunset: Sunset? = Sunset()
            ) {
                data class Sunrise(
                    @SerializedName("time")
                    val time: String? = ""
                )

                data class Sunset(
                    @SerializedName("time")
                    val time: String? = ""
                )
            }

            data class Cloudrate(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Dswrf(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Humidity(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class LifeIndex(
                @SerializedName("carWashing")
                val carWashing: List<CarWashing?>? = listOf(),
                @SerializedName("coldRisk")
                val coldRisk: List<ColdRisk?>? = listOf(),
                @SerializedName("comfort")
                val comfort: List<Comfort?>? = listOf(),
                @SerializedName("dressing")
                val dressing: List<Dressing?>? = listOf(),
                @SerializedName("ultraviolet")
                val ultraviolet: List<Ultraviolet?>? = listOf()
            ) {
                data class CarWashing(
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: String? = ""
                )

                data class ColdRisk(
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: String? = ""
                )

                data class Comfort(
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: String? = ""
                )

                data class Dressing(
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: String? = ""
                )

                data class Ultraviolet(
                    @SerializedName("date")
                    val date: String? = "",
                    @SerializedName("desc")
                    val desc: String? = "",
                    @SerializedName("index")
                    val index: String? = ""
                )
            }

            data class Precipitation(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Pressure(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Skycon(
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("value")
                val value: String? = ""
            )

            data class Skycon08h20h(
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("value")
                val value: String? = ""
            )

            data class Skycon20h32h(
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("value")
                val value: String? = ""
            )

            data class Temperature(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Visibility(
                @SerializedName("avg")
                val avg: Double? = 0.0,
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Double? = 0.0,
                @SerializedName("min")
                val min: Double? = 0.0
            )

            data class Wind(
                @SerializedName("avg")
                val avg: Avg? = Avg(),
                @SerializedName("date")
                val date: String? = "",
                @SerializedName("max")
                val max: Max? = Max(),
                @SerializedName("min")
                val min: Min? = Min()
            ) {
                data class Avg(
                    @SerializedName("direction")
                    val direction: Double? = 0.0,
                    @SerializedName("speed")
                    val speed: Double? = 0.0
                )

                data class Max(
                    @SerializedName("direction")
                    val direction: Double? = 0.0,
                    @SerializedName("speed")
                    val speed: Double? = 0.0
                )

                data class Min(
                    @SerializedName("direction")
                    val direction: Double? = 0.0,
                    @SerializedName("speed")
                    val speed: Double? = 0.0
                )
            }
        }

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

        data class Realtime(
            @SerializedName("air_quality")
            val airQuality: AirQuality? = AirQuality(),
            @SerializedName("apparent_temperature")
            val apparentTemperature: Double? = 0.0,
            @SerializedName("cloudrate")
            val cloudrate: Double? = 0.0,
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
            val temperature: Double? = 0.0,
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
                val no2: Double? = 0.0,
                @SerializedName("o3")
                val o3: Double? = 0.0,
                @SerializedName("pm10")
                val pm10: Double? = 0.0,
                @SerializedName("pm25")
                val pm25: Double? = 0.0,
                @SerializedName("so2")
                val so2: Double? = 0.0
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
                val direction: Double? = 0.0,
                @SerializedName("speed")
                val speed: Double? = 0.0
            )
        }
    }
}