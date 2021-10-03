package com.lnkj.library_base.db.bean

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lnkj.library_base.db.converters.*
import kotlinx.android.parcel.Parcelize

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/25 16:02
 * @描述
 */
@Entity
@TypeConverters(
    TodayWeatherConverters::class,
    HourlyWeatherConverters::class,
    DailyWeatherConverters::class,
    LifeStyleConverters::class,
    DressLifeStyleConverters::class,
    ListStringConverters::class
)
data class CityWeather(
    // 城市名称
    @PrimaryKey
    var cityName: String,
    // 更新时间
    var updateDate: String,
    // 当前温度
    var temperature: Int,
    // 天气名称
    var weatherName: String,
    // 天气图标
    var weatherIcon: Int,
    // 天气背景
    var weatherBg: Int,
    // 播报内容
    var voiceAnnouncements: String,
    // 空气质量名称
    var airQualityName: String,
    // 空气质量
    var airQualityValue: Int,
    // 预警信息
    var alertInfo: List<String>,
    // 风速等级
    var windSpeed: String,
    // 风向
    var windDirection: String,
    // 湿度
    var humidity: String,
    // 气压
    var airPressure: String,
    // 下雨下雪提示
    var rainTip: String,
    // 是否雨雪天气
    var hasRain: Boolean,
    // 今天天气
    var todayWeather: TodayWeather,
    // 明天天气
    var tomorrowWeather: TodayWeather,
    // 后天天气
    var afterTomorrowWeather: TodayWeather,
    // 24小时天气
    var hourlyWeatherList: MutableList<HourlyWeather>,
    // 15天天气列表
    var dailyWeatherList: MutableList<DailyWeather>,
    // 今日穿衣指数
    var dressLifeStyle: DressLifeStyle,
    // 其他生活指数
    var lifeStyleList: MutableList<LifeStyle>
)

data class DressLifeStyle(
    // 穿衣图标
    var dressIcon: Int,
    var dressTopTip: String,
    var dressBottomTip: String,
    // 指数名称
    var indexName: String,
    // 生活指数简介
    var indexBrf: String,
    // 生活指数详细描述
    var indexTxt: String,
    // 固定文字(小知识)
    var knowledge: String,
    var knowledgeTitle: String,
    // 指数是否好的
    var isGood: Boolean,
    // 天气名称
    var weatherName: String,
    var max: Int,
    var min: Int,
    var temperatureDifference: Int,
    var windSpeed: String,
    var windDirection: String,
    var sunriseTime: String,
    var sunsetTime: String
)

fun DressLifeStyle.toLifeStyle(): LifeStyle {
    return LifeStyle(
        indexName,
        indexBrf,
        indexTxt,
        knowledge,
        knowledgeTitle,
        isGood,
        weatherName,
        max,
        min,
        temperatureDifference,
        windSpeed,
        windDirection,
        sunriseTime,
        sunsetTime
    )
}

@Parcelize
data class LifeStyle(
    // 指数名称
    var indexName: String,
    // 生活指数简介
    var indexBrf: String,
    // 生活指数详细描述
    var indexTxt: String,
    // 固定文字(小知识)
    var knowledge: String,
    var knowledgeTitle: String,
    // 指数是否好的
    var isGood: Boolean,
    // 天气名称
    var weatherName: String,
    var max: Int,
    var min: Int,
    var temperatureDifference: Int,
    var windSpeed: String,
    var windDirection: String,
    var sunriseTime: String,
    var sunsetTime: String
) : Parcelable

/**
 * 天级天气
 * @property date String
 * @property formatDate String
 * @property weatherName String
 * @property weatherIcon Int
 * @property weatherDayName String
 * @property weatherDayIcon Int
 * @property weatherNightName String
 * @property weatherNightIcon Int
 * @property max Int
 * @property min Int
 * @property airQualityName String
 * @property airQualityValue Int
 * @property windSpeed String
 * @property windDirection String
 * @constructor
 */
data class DailyWeather(
    var date: String,
    var formatDate: String,
    var weatherName: String,
    var weatherIcon: Int,
    var weatherDayName: String,
    var weatherDayIcon: Int,
    var weatherNightName: String,
    var weatherNightIcon: Int,
    var max: Int,
    var min: Int,
    // 空气质量
    var airQualityName: String,
    var airQualityValue: Int,
    // 风速等级
    var windSpeed: String,
    // 风向
    var windDirection: String
)

/**
 * 小时天气
 * @property time String
 * @property weatherName String
 * @property weatherIcon Int
 * @property temperature Int
 * @property airQualityName String
 * @property airQualityValue Int
 * @constructor
 */
data class HourlyWeather(
    // 时间
    var time: String,
    // 天气名称
    var weatherName: String,
    // 天气图标
    var weatherIcon: Int,
    // 温度
    var temperature: Int,
    // 空气质量
    var airQualityName: String,
    var airQualityValue: Int,
    //风速
    var windQualityValue:Double,
    //风的方向
    var directionQualityValue:Double
)

/**
 * 今天天气
 * @property date String
 * @property max Int
 * @property min Int
 * @property weatherName String
 * @property weatherIcon Int
 * @constructor
 */
data class TodayWeather(
    // 今天日期
    var date: String,
    // 今天最高温度
    var max: Int,
    // 今天最低温度
    var min: Int,
    // 天气名称
    var weatherName: String,
    // 天气图标
    var weatherIcon: Int,
    // 空气质量
    var airQualityName: String,
    var airQualityValue: Int
)

