package com.lnkj.library_base.db.bean
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * 城市实体
 * @property provinceName String    省份
 * @property cid String 城市code
 * @property countryName String 国家名称
 * @property lat String 纬度
 * @property counties String    县区
 * @property lon String 经度
 * @property cityName String    城市名称
 * @property continentName String   洲名称
 * @constructor
 */
@Entity
data class CityBean(
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
    var continentName: String = "",
    var isHot: Boolean = false,
    @Ignore
    var isLocation: Boolean = false
)