package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class LifestyleBean(
    @SerializedName("HeWeather6")
    val heWeather6: List<HeWeather6?>? = listOf()
) {
    data class HeWeather6(
        @SerializedName("basic")
        val basic: Basic? = Basic(),
        @SerializedName("lifestyle")
        val lifestyle: List<Lifestyle?>? = listOf(),
        @SerializedName("status")
        val status: String? = "",
        @SerializedName("update")
        val update: Update? = Update()
    ) {
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

        data class Lifestyle(
            @SerializedName("brf")
            val brf: String? = "",
            @SerializedName("txt")
            val txt: String? = "",
            @SerializedName("type")
            val type: String? = ""
        )

        data class Update(
            @SerializedName("loc")
            val loc: String? = "",
            @SerializedName("utc")
            val utc: String? = ""
        )
    }
}