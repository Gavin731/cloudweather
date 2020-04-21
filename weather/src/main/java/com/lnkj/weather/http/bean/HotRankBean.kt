package com.lnkj.weather.http.bean


import com.google.gson.annotations.SerializedName

data class HotRankBean(
    @SerializedName("data")
    val data: MutableList<Data> = mutableListOf(),
    @SerializedName("info")
    val info: String = "",
    @SerializedName("status")
    val status: Int = 0
) {
    data class Data(
        @SerializedName("avg")
        val avg: Int = 0,
        @SerializedName("city")
        val city: String = "",
        @SerializedName("max")
        val max: Int = 0,
        @SerializedName("min")
        val min: Int = 0,
        @SerializedName("province")
        val province: String = "",
        @SerializedName("sort")
        val sort: Int = 0
    )
}