package com.lnkj.library_base.db.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 11:14
 * @描述
 */
@Parcelize
@Entity
data class MyCityBean(
    var id: Int?,
    @SerializedName("admin_area")
    var provinceName: String = "",
    @SerializedName("cid")
    @PrimaryKey
    var cid: String = "",
    @SerializedName("cnty")
    var countryName: String = "",
    @SerializedName("lat")
    var lat: String = "",
    @SerializedName("location")
    var counties: String = "",
    @SerializedName("lon")
    var lon: String = "",
    @SerializedName("parent_city")
    var cityName: String = "",
    @SerializedName("street")
    var street: String = "",
    var continentName: String = "",
    var isLocation: Int = 0,
    var addTime: Long = 0,
    var weatherIcon: Int = -1,
    var temperature: String = ""

) : Parcelable

fun MyCityBean.toCityBean(): CityBean {
    return CityBean(
        provinceName,
        cid,
        countryName,
        lat,
        counties,
        lon,
        cityName,
        street,
        continentName,
        false
    )
}

fun CityBean.toMyCityBean(): MyCityBean {
    return MyCityBean(
        null,
        provinceName,
        cid,
        countryName,
        lat,
        counties,
        lon,
        cityName,
        street,
        continentName,
        addTime = System.currentTimeMillis()
    )
}