package com.lnkj.weather.http.bean


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HeAirQualityBean(
    @SerializedName("HeWeather6")
    val heWeather6: List<HeWeather6?>? = listOf()
) : Parcelable {
    @Parcelize
    data class HeWeather6(
        @SerializedName("air_now_city")
        val airNowCity: AirNowCity? = AirNowCity(),
        @SerializedName("air_now_station")
        val airNowStation: List<AirNowStation?>? = listOf(),
        @SerializedName("basic")
        val basic: Basic? = Basic(),
        @SerializedName("status")
        val status: String? = "",
        @SerializedName("update")
        val update: Update? = Update()
    ) : Parcelable {
        @Parcelize
        data class AirNowCity(
            @SerializedName("aqi")
            val aqi: Int? = 0,
            @SerializedName("co")
            val co: String? = "",
            @SerializedName("main")
            val main: String? = "",
            @SerializedName("no2")
            val no2: String? = "",
            @SerializedName("o3")
            val o3: String? = "",
            @SerializedName("pm10")
            val pm10: String? = "",
            @SerializedName("pm25")
            val pm25: String? = "",
            @SerializedName("pub_time")
            val pubTime: String? = "",
            @SerializedName("qlty")
            val qlty: String? = "",
            @SerializedName("so2")
            val so2: String? = ""
        ) : Parcelable

        @Parcelize
        data class AirNowStation(
            @SerializedName("air_sta")
            val airSta: String? = "",
            @SerializedName("aqi")
            val aqi: String? = "",
            @SerializedName("asid")
            val asid: String? = "",
            @SerializedName("co")
            val co: String? = "",
            @SerializedName("lat")
            val lat: String? = "",
            @SerializedName("lon")
            val lon: String? = "",
            @SerializedName("main")
            val main: String? = "",
            @SerializedName("no2")
            val no2: String? = "",
            @SerializedName("o3")
            val o3: String? = "",
            @SerializedName("pm10")
            val pm10: String? = "",
            @SerializedName("pm25")
            val pm25: String? = "",
            @SerializedName("pub_time")
            val pubTime: String? = "",
            @SerializedName("qlty")
            val qlty: String? = "",
            @SerializedName("so2")
            val so2: String? = ""
        ) : Parcelable

        @Parcelize
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
        ) : Parcelable
        @Parcelize
        data class Update(
            @SerializedName("loc")
            val loc: String? = "",
            @SerializedName("utc")
            val utc: String? = ""
        ) : Parcelable
    }
}