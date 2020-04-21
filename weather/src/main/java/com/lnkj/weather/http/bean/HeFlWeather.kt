package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class HeFlWeather(
    @SerializedName("HeWeather6")
    val heWeather6: List<HeWeather6?>? = listOf()
) {
    data class HeWeather6(
        @SerializedName("basic")
        val basic: Basic? = Basic(),
        @SerializedName("now")
        val now: Now? = Now(),
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

        data class Now(
            @SerializedName("cloud")
            val cloud: String? = "",
            @SerializedName("cond_code")
            val condCode: String? = "",
            @SerializedName("cond_txt")
            val condTxt: String? = "",
            @SerializedName("fl")
            val fl: Int? = 0,
            @SerializedName("hum")
            val hum: String? = "",
            @SerializedName("pcpn")
            val pcpn: String? = "",
            @SerializedName("pres")
            val pres: String? = "",
            @SerializedName("tmp")
            val tmp: String? = "",
            @SerializedName("vis")
            val vis: String? = "",
            @SerializedName("wind_deg")
            val windDeg: String? = "",
            @SerializedName("wind_dir")
            val windDir: String? = "",
            @SerializedName("wind_sc")
            val windSc: String? = "",
            @SerializedName("wind_spd")
            val windSpd: String? = ""
        )

        data class Update(
            @SerializedName("loc")
            val loc: String? = "",
            @SerializedName("utc")
            val utc: String? = ""
        )
    }
}