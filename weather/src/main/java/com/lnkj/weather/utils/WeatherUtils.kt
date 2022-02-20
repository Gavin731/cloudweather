package com.lnkj.weather.utils

import android.widget.TextView
import com.lnkj.weather.R
import com.mufeng.mvvmlib.utilcode.ext.loge

object WeatherUtils {

    fun formatWeather(dayWeather: String, nightWeather: String): String {
        val day = getWeatherName(dayWeather)
        val night = getWeatherName(nightWeather)
        if (day != night) {
            return "${day}转$night"
        }
        return day
    }

    /**
     * 获取天气名称
     * @receiver String?
     * @return String
     */
    fun getWeatherName(str: String): String {
        return when (str) {
            "CLEAR_DAY" -> "晴"
            "CLEAR_NIGHT" -> "晴"
            "PARTLY_CLOUDY_DAY" -> "多云"
            "PARTLY_CLOUDY_NIGHT" -> "多云"
            "CLOUDY" -> "阴"
            "LIGHT_HAZE" -> "雾霾"
            "MODERATE_HAZE" -> "雾霾"
            "HEAVY_HAZE" -> "雾霾"
            "LIGHT_RAIN" -> "小雨"
            "MODERATE_RAIN" -> "中雨"
            "HEAVY_RAIN" -> "大雨"
            "STORM_RAIN" -> "暴雨"
            "FOG" -> "雾"
            "LIGHT_SNOW" -> "小雪"
            "MODERATE_SNOW" -> "中雪"
            "HEAVY_SNOW" -> "大雪"
            "STORM_SNOW" -> "暴雪"
            "DUST" -> "浮尘"
            "SAND" -> "沙尘"
            "WIND" -> "大风"
            "THUNDER_SHOWER" -> "雷阵雨"
            "HAIL" -> "冰雹"
            "SLEET" -> "雨夹雪"
            else -> ""
        }
    }

    /**
     * 获取天气图标
     * @receiver String?
     * @return Int
     */
    fun getWeatherIcon(str: String): Int {
        return when (str) {
            "CLEAR_DAY" -> R.drawable.weather_ico_clear_day  //晴-白天
            "CLEAR_NIGHT" -> R.drawable.weather_ico_clear_night  //晴-夜晚
            "PARTLY_CLOUDY_DAY" -> R.drawable.weather_ico_partly_cloudy_day   //多云-白天
            "PARTLY_CLOUDY_NIGHT" -> R.drawable.weather_ico_partly_cloudy_night  //多云-夜晚
            "CLOUDY" -> R.drawable.weather_ico_cloudy  // 阴天
            "LIGHT_HAZE" -> R.drawable.weather_ico_light_haze  // 雾霾
            "MODERATE_HAZE" -> R.drawable.weather_ico_moderate_haze  // 雾霾
            "HEAVY_HAZE" -> R.drawable.weather_ico_heavy_haze  // 雾霾
            "LIGHT_RAIN" -> R.drawable.weather_ico_light_rain  //小雨
            "MODERATE_RAIN" -> R.drawable.weather_ico_moderate_rain  // 雨
            "HEAVY_RAIN" -> R.drawable.weather_ico_heavy_rain  // 雨
            "STORM_RAIN" -> R.drawable.weather_ico_storm_rain  // 雨
            "FOG" -> R.drawable.weather_ico_fog  // 雾
            "LIGHT_SNOW" -> R.drawable.weather_ico_light_snow  //小雪
            "MODERATE_SNOW" -> R.drawable.weather_ico_moderate_snow   // 雪
            "HEAVY_SNOW" -> R.drawable.weather_ico_heavy_snow   // 雪
            "STORM_SNOW" -> R.drawable.weather_ico_storm_snow   // 雪
            "DUST" -> R.drawable.weather_ico_dust  // 浮尘
            "SAND" -> R.drawable.weather_ico_sand  // 浮尘
            "WIND" -> R.drawable.weather_ico_dust  // 浮尘
            "THUNDER_SHOWER" -> R.drawable.weather_ico_thunder_shower  // 雷阵雨
            "HAIL" -> R.drawable.weather_ico_hail  // 冰雹
            "SLEET" -> R.drawable.weather_ico_sleet   // 雨夹雪
            else -> R.drawable.weather_ico_clear_day
        }
    }

    fun getWeatherBgByWeatherName(str: String, isDayTime: Boolean): Int {
        return when {
            str.startsWith("晴") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_fine_night
                } else {
                    R.drawable.weather_bg_fine
                }
            }
            str.startsWith("多云") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_cloudy_night
                } else {
                    R.drawable.weather_bg_cloudy
                }
            }
            // 阴天
            str.startsWith("阴") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_overcast_night
                } else {
                    R.drawable.weather_bg_overcast
                }
            }
            // 轻度雾霾
            str.startsWith("雾霾") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_haze_night
                } else {
                    R.drawable.weather_bg_haze
                }
            }
            //小雨
//            str.startsWith("小雨") -> {
//                if (!isDayTime) {
//                    R.drawable.weather_bg_shower_night
//                } else {
//                    R.drawable.weather_bg_shower
//                }
//            }
            // 雨
            str.startsWith("小雨") || str.startsWith("中雨")
                    || str.startsWith("大雨") || str.startsWith("暴雨") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_heavy_rain_bight
                } else {
                    R.drawable.weather_bg_heavy_rain
                }
            }
            // 雾
            str.startsWith("雾") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_fog_night
                } else {
                    R.drawable.weather_bg_fog
                }
            }
            //小雪
//            str.startsWith("小雪") -> {
//                if (!isDayTime) {
//                    R.drawable.weather_bg_scouther_night
//                } else {
//                    R.drawable.weather_bg_scouther
//                }
//            }
            // 雪
            str.startsWith("小雪") || str.startsWith("中雪")
                    || str.startsWith("大雪") || str.startsWith("暴雪") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_heavy_snow_night
                } else {
                    R.drawable.weather_bg_heavy_snow
                }
            }

            // 浮尘
            str.startsWith("浮尘") ||
                    str.startsWith("沙尘") ||
                    str.startsWith("大风") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_jansa_night
                } else {
                    R.drawable.weather_bg_jansa
                }
            }
            // 雷阵雨
            str.startsWith("雷阵雨") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_thunder_shower_night
                } else {
                    R.drawable.weather_bg_thunder_shower
                }
            }
            // 冰雹
            str.startsWith("冰雹") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_freezing_rain_night
                } else {
                    R.drawable.weather_bg_freezing_rain
                }
            }
            // 雨夹雪
            str.startsWith("雨夹雪") -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_sleet_night
                } else {
                    R.drawable.weather_bg_sleet
                }
            }
            else -> R.drawable.weather_bg_fine_night
        }
    }

    /**
     * 获取首页天气背景
     * @receiver String?
     * @return Int
     */
    fun getWeatherBg(str: String, isDayTime: Boolean): Int {
        return when (str) {
            "CLEAR_DAY" -> R.drawable.weather_bg_fine
            "CLEAR_NIGHT" -> R.drawable.weather_bg_fine_night
            // 多云白天
            "PARTLY_CLOUDY_DAY" -> R.drawable.weather_bg_cloudy
            // 多云夜间
            "PARTLY_CLOUDY_NIGHT" -> R.drawable.weather_bg_cloudy_night
            // 阴天
            "CLOUDY" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_overcast_night
                } else {
                    R.drawable.weather_bg_overcast
                }
            }
            // 轻度雾霾
            "LIGHT_HAZE", "MODERATE_HAZE", "HEAVY_HAZE" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_haze_night
                } else {
                    R.drawable.weather_bg_haze
                }
            }
//            //小雨
//            "LIGHT_RAIN" -> {
//                if (!isDayTime) {
//                    R.drawable.weather_bg_shower_night
//                } else {
//                    R.drawable.weather_bg_shower
//                }
//            }
            // 雨
            "LIGHT_RAIN", "MODERATE_RAIN", "HEAVY_RAIN", "STORM_RAIN" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_heavy_rain_bight
                } else {
                    R.drawable.weather_bg_heavy_rain
                }
            }
            // 雾
            "FOG" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_fog_night
                } else {
                    R.drawable.weather_bg_fog
                }
            }
//            //小雪
//            "LIGHT_SNOW" -> {
//                if (!isDayTime) {
//                    R.drawable.weather_bg_scouther_night
//                } else {
//                    R.drawable.weather_bg_scouther
//                }
//            }
            // 雪
            "LIGHT_SNOW", "MODERATE_SNOW", "HEAVY_SNOW", "STORM_SNOW" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_heavy_snow_night
                } else {
                    R.drawable.weather_bg_heavy_snow
                }
            }

            // 浮尘
            "DUST", "SAND", "WIND" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_jansa_night
                } else {
                    R.drawable.weather_bg_jansa
                }
            }
            // 雷阵雨
            "THUNDER_SHOWER" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_thunder_shower_night
                } else {
                    R.drawable.weather_bg_thunder_shower
                }
            }
            // 冰雹
            "HAIL" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_freezing_rain_night
                } else {
                    R.drawable.weather_bg_freezing_rain
                }
            }
            // 雨夹雪
            "SLEET" -> {
                if (!isDayTime) {
                    R.drawable.weather_bg_sleet_night
                } else {
                    R.drawable.weather_bg_sleet
                }
            }
            else -> R.drawable.weather_bg_fine
        }
    }

    /**
     * 获取预警信息
     * @param code String
     * @return String
     */
    fun getAlertInfo(code: String): String {
        val firstCode = code.substring(0, 2)
        val secondCode = code.substring(2, 4)
        val alertType = when (firstCode) {
            "01" -> "台风"
            "02" -> "暴雨"
            "03" -> "暴雪"
            "04" -> "寒潮"
            "05" -> "大风"
            "06" -> "沙尘暴"
            "07" -> "高温"
            "08" -> "干旱"
            "09" -> "雷电"
            "10" -> "冰雹"
            "11" -> "霜冻"
            "12" -> "大雾"
            "13" -> "霾"
            "14" -> "道路结冰"
            "15" -> "森林火灾"
            "16" -> "雷雨大风"
            else -> ""
        }
        val alertLevel = when (secondCode) {
            "01" -> "蓝色"
            "02" -> "黄色"
            "03" -> "橙色"
            "04" -> "红色"
            else -> ""
        }
        return "${alertType}${alertLevel}预警"
    }

    /**
     * 空气质量
     * @param chn Int
     * @return String
     */
    fun getAirQualityDescription(chn: Int): String {
        return when (chn) {
            in 0..50 -> "优"
            in 51..100 -> "良"
            in 101..150 -> "轻度污染"
            in 151..200 -> "中度污染"
            in 201..300 -> "重度污染"
            else -> "严重污染"
        }
    }

    fun getAirQualityDescription(chn: String): Int {
        return when (chn) {
            in "优" -> R.mipmap.weather_icon_air
            in "良" -> R.mipmap.weather_icon_good
            in "轻度污染" -> R.mipmap.weather_icon_mild
            in "中度污染" -> R.mipmap.weather_icon_middle
            in "重度污染" -> R.mipmap.weather_icon_serious
            else -> R.mipmap.weather_icon_severity
        }
    }

    fun getAirQualityTxt(chn: Int): String {
        return when (chn) {
            in 0..50 -> "空气很好，出去呼吸下清新空气吧。"
            in 51..100 -> "空气不错，极少数敏感人群应减少户外活动。"
            in 101..150 -> "轻度污染，老人、儿童、呼吸系统等疾病者减少长时间、高强度的户外运动。"
            in 151..200 -> "中度污染，儿童、老人、呼吸系统等疾病患者及一般人群减少户外活动。"
            in 201..300 -> "重度污染，儿童、老人、呼吸系统等疾病患者及一般人群停止或减少户外运动。"
            else -> "严重污染，儿童、老人、呼吸系统等疾病患者及一般人群停止户外活动。"
        }
    }

    fun getAirQualityColor(chn: Int): Int {
        return when (chn) {
            in 0..50 -> R.color.weather_color_high_quality
            in 51..100 -> R.color.weather_color_fine
            in 101..150 -> R.color.weather_color_mild
            in 151..200 -> R.color.weather_color_moderate
            in 201..300 -> R.color.weather_color_severe
            else -> R.color.weather_color_seriousness
        }
    }

    /**
     * 风速等级
     * @param speed Double
     * @return String
     */
    fun getWindSpeed(speed: Int): String {

        return when {
            speed < 1 -> "0级"
            speed in 1..5 -> "1级"
            speed in 6..11 -> "2级"
            speed in 12..19 -> "3级"
            speed in 20..28 -> "4级"
            speed in 29..38 -> "5级"
            speed in 39..49 -> "6级"
            speed in 50..61 -> "7级"
            speed in 62..74 -> "8级"
            speed in 75..88 -> "9级"
            speed in 89..102 -> "10级"
            speed in 103..117 -> "11级"
            speed in 118..133 -> "12级"
            speed in 134..149 -> "13级"
            speed in 150..166 -> "14级"
            speed in 167..183 -> "15级"
            speed in 184..201 -> "16级"
            else -> "17级"
        }
    }

    /**
     * 获取风向
     * @param direction Double
     * @return String
     */
    fun getWindDirection(direction: Double): String {
        loge { "$direction" }
        return when (direction) {
            in 0.0..11.25, in 348.76..360.0 -> "北风"
            in 11.26..78.75 -> "东北风"
            in 78.76..101.25 -> "东风"
            in 101.26..168.75 -> "东南风"
            in 168.76..191.25 -> "南风"
            in 191.26..258.75 -> "西南风"
            in 258.76..281.25 -> "西风"
            else -> "西北风"
        }
    }

    /**
     * 穿衣指数衣服图标
     * @param dress String
     * @return Int
     */
    fun getDressIcon(dress: String): Int {
        return when (dress) {
            "炎热", "热" -> R.mipmap.weather_icon_t_shirt
            "舒适", "较舒适" -> R.mipmap.weather_icon_long_sleeve
            "较冷" -> R.mipmap.weather_icon_coat
            else -> R.mipmap.weather_icon_down_coat
        }
    }

    /**
     * 穿衣指数的好坏
     * @param dress String
     * @return Boolean
     */
    fun isDressGood(dress: String): Boolean {
        return when (dress) {
            "炎热", "热", "较冷", "冷", "寒冷", "需要带伞", "较易发", "易发", "极易发", "强", "很强", "不太适宜", "不宜", "不适宜", "较不宜"
            -> false
            else -> true
        }
    }

    /**
     * 雨伞指数介绍
     * @param weather String
     * @return String
     */
    fun umbrellaBrf(weather: String): String {
        return when (weather) {
            "LIGHT_RAIN", "MODERATE_RAIN", "HEAVY_RAIN", "STORM_RAIN", "LIGHT_SNOW", "MODERATE_SNOW", "HEAVY_SNOW", "STORM_SNOW", "THUNDER_SHOWER", "HAIL", "SLEET"
            -> "需要带伞"
            else -> "无需带伞"
        }
    }

    fun hasRain(weather: String): Boolean {
        return when (weather) {
            "LIGHT_RAIN", "MODERATE_RAIN", "HEAVY_RAIN", "STORM_RAIN", "LIGHT_SNOW", "MODERATE_SNOW", "HEAVY_SNOW", "STORM_SNOW", "THUNDER_SHOWER", "HAIL", "SLEET"
            -> true
            else -> false
        }
    }

    fun isRain(dayWeather: String, nightWeather: String): String {
        val dayRain = isRain(dayWeather)
        val nightRain = isRain(nightWeather)
        return if (dayRain == "雨" || nightRain == "雨") {
            "雨"
        } else {
            "雪"
        }
    }

    fun isRain(weather: String): String {
        return when (weather) {
            "LIGHT_RAIN", "MODERATE_RAIN", "HEAVY_RAIN", "STORM_RAIN", "THUNDER_SHOWER", "HAIL", "SLEET"
            -> "雨"
            "LIGHT_SNOW", "MODERATE_SNOW", "HEAVY_SNOW", "STORM_SNOW" -> "雪"
            else -> "雨"
        }
    }

    fun isRainByName(weather: String): String {
        return when (weather) {
            "小雨", "中雨", "大雨", "暴雨", "雷阵雨", "冰雹", "雨夹雪"
            -> "雨"
            "小雪", "中雪", "大雪", "暴雪" -> "雪"
            else -> "雨"
        }
    }

    /**
     * 雨伞指数详细介绍
     * @param weather String
     * @return String
     */
    fun umbrellaTxt(weather: String): String {
        return when (weather) {
            "CLEAR_DAY", "CLEAR_NIGHT", "PARTLY_CLOUDY_DAY", "PARTLY_CLOUDY_NIGHT" -> "天气较好，不会降水，因此您可以放心出门，无需带雨伞。"
            "CLOUDY" -> "阴天，但降水概率较低，因此您在出门的时候无需带雨伞。"
            "LIGHT_RAIN", "MODERATE_RAIN" -> "有降水，请带上雨伞。如果你喜欢雨中漫步，享受大自然的温馨和快乐，可短时收起雨伞。"
            "HEAVY_RAIN", "STORM_RAIN", "THUNDER_SHOWER" -> "有强降水，请带上雨伞，尽量穿戴雨衣。出行请注意安全。"
            "SLEET" -> "有降水且伴有下雪，请带上雨伞，做好防护措施，出行请注意安全。"
            "LIGHT_SNOW", "MODERATE_SNOW" -> "有降雪，请带上雨伞。在短时间外出可收起雨伞雪中漫步也会十分浪漫。"
            "HEAVY_SNOW", "STORM_SNOW" -> "有强降雪，请带上雨伞，尽量穿戴雨衣。出行请注意安全。"
            "HAIL" -> "有冰雹，请带上雨伞，尽量不要出门。出行请注意躲避。"
            else -> ""
        }
    }

    fun precipitationTxt(precipitation: Double): String {
        return when {
            precipitation < 0.031 -> "无"
            precipitation in 0.031..0.25 -> "小"
            precipitation in 0.25..0.35 -> "中"
            precipitation in 0.35..0.48 -> "大"
            else -> "暴"
        }
    }

    fun setAirLevel(tvAirLevel: TextView, airLevel: Int) {
        if (tvAirLevel != null) {
            when (airLevel) {
                in 0..50 -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_best_level_shape)
                    tvAirLevel.text = "优质"
                }
                in 51..100 -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_good_level_shape)
                    tvAirLevel.text = "良好"
                }
                in 101..150 -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_small_level_shape)
                    tvAirLevel.text = "轻度"
                }
                in 151..200 -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_mid_level_shape)
                    tvAirLevel.text = "中度"
                }
                in 201..300 -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_big_level_shape)
                    tvAirLevel.text = "重度"
                }
                else -> {
                    tvAirLevel.setBackgroundResource(R.drawable.weather_poison_level_shape)
                    tvAirLevel.text = "严重"
                }
            }
        }
    }

    fun getWeatherWindDirectionByWeatherName(str: String): Int {
        return when (str) {
            "东风" -> R.mipmap.weather_icon_fx7
            "南风" -> R.mipmap.weather_icon_fx5
            "西风" -> R.mipmap.weather_icon_fx3
            "北风" -> R.mipmap.weather_icon_fx1
            "东南风" -> R.mipmap.weather_icon_fx6
            "东北风" -> R.mipmap.weather_icon_fx8
            "西南风" -> R.mipmap.weather_icon_fx4
            "西北风" -> R.mipmap.weather_icon_fx2
            else -> R.mipmap.weather_icon_fx6
        }
    }

    fun getAlertIcon(alertInfo: String): Int {
        var icon: Int = 0
        if (alertInfo.indexOf("台风") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjtf1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjtf2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjtf3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjtf4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("暴雨") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjby1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjby2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjby3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjby4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("暴雪") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjbx1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjbx2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjbx3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjbx4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("寒潮") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjhc1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjhc2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjhc3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjhc4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("大风") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjdf1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjdf2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjdf3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjdf4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("沙尘暴") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("高温") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjgw2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjgw3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjgw4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("干旱") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjgh3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjgh4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("雷电") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjld2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjld3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjld4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("冰雹") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("霜冻") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_yjsd1
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjsd2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjsd3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("大雾") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjdw2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjdw3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjdw4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("霾") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjm2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjm3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjm4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("道路结冰") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_yjjb2
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_yjjb3
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_yjjb4
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("森林火灾") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else if (alertInfo.indexOf("雷雨大风") >= 0) {
            if (alertInfo.indexOf("蓝色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("黄色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("橙色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else if (alertInfo.indexOf("红色") >= 0) {
                icon = R.mipmap.weather_icon_alert
            } else {
                icon = R.mipmap.weather_icon_alert
            }
        } else {
            icon = R.mipmap.weather_icon_alert
        }
        return icon
    }
}



