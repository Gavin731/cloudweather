package com.lnkj.weather.utils

import com.lnkj.weather.R

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/3 10:32
 * @描述
 */
object ColorUtils {

    private val topColorMap = mutableMapOf<Int, Int>()
    private val bottomColorMap = mutableMapOf<Int, Int>()

    init {
        topColorMap[R.drawable.weather_bg_cloudy] = R.color.weather_bg_cloudy_top_color
        topColorMap[R.drawable.weather_bg_cloudy_night] = R.color.weather_bg_cloudy_night_top_color
        topColorMap[R.drawable.weather_bg_cold] = R.color.weather_bg_cold_top_color
        topColorMap[R.drawable.weather_bg_cold_night] = R.color.weather_bg_cold_night_top_color
        topColorMap[R.drawable.weather_bg_fine] = R.color.weather_bg_fine_top_color
        topColorMap[R.drawable.weather_bg_fine_night] = R.color.weather_bg_fine_night_top_color
        topColorMap[R.drawable.weather_bg_fog] = R.color.weather_bg_fog_top_color
        topColorMap[R.drawable.weather_bg_fog_night] = R.color.weather_bg_fog_night_top_color
        topColorMap[R.drawable.weather_bg_freezing_rain] = R.color.weather_bg_freezing_rain_top_color
        topColorMap[R.drawable.weather_bg_freezing_rain_night] = R.color.weather_bg_freezing_rain_night_top_color
        topColorMap[R.drawable.weather_bg_haze] = R.color.weather_bg_haze_top_color
        topColorMap[R.drawable.weather_bg_haze_night] = R.color.weather_bg_haze_night_top_color
        topColorMap[R.drawable.weather_bg_heat] = R.color.weather_bg_heat_top_color
        topColorMap[R.drawable.weather_bg_heat_night] = R.color.weather_bg_heat_night_top_color
        topColorMap[R.drawable.weather_bg_heavy_rain] = R.color.weather_bg_heavy_rain_top_color
        topColorMap[R.drawable.weather_bg_heavy_rain_bight] = R.color.weather_bg_heavy_rain_bight_top_color
        topColorMap[R.drawable.weather_bg_heavy_snow] = R.color.weather_bg_heavy_snow_top_color
        topColorMap[R.drawable.weather_bg_heavy_snow_night] = R.color.weather_bg_heavy_snow_night_top_color
        topColorMap[R.drawable.weather_bg_jansa] = R.color.weather_bg_jansa_top_color
        topColorMap[R.drawable.weather_bg_jansa_night] = R.color.weather_bg_jansa_night_top_color
        topColorMap[R.drawable.weather_bg_overcast] = R.color.weather_bg_overcast_top_color
        topColorMap[R.drawable.weather_bg_overcast_night] = R.color.weather_bg_overcast_night_top_color
        topColorMap[R.drawable.weather_bg_scouther] = R.color.weather_bg_scouther_top_color
        topColorMap[R.drawable.weather_bg_scouther_night] = R.color.weather_bg_scouther_night_top_color
        topColorMap[R.drawable.weather_bg_shower] = R.color.weather_bg_shower_top_color
        topColorMap[R.drawable.weather_bg_shower_night] = R.color.weather_bg_shower_night_top_color
        topColorMap[R.drawable.weather_bg_sleet] = R.color.weather_bg_sleet_top_color
        topColorMap[R.drawable.weather_bg_sleet_night] = R.color.weather_bg_sleet_night_top_color
        topColorMap[R.drawable.weather_bg_thunder_shower] = R.color.weather_bg_thunder_shower_top_color
        topColorMap[R.drawable.weather_bg_thunder_shower_night] = R.color.weather_bg_thunder_shower_night_top_color

        bottomColorMap[R.drawable.weather_bg_cloudy] = R.color.weather_bg_cloudy_bottom_color
        bottomColorMap[R.drawable.weather_bg_cloudy_night] = R.color.weather_bg_cloudy_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_cold] = R.color.weather_bg_cold_bottom_color
        bottomColorMap[R.drawable.weather_bg_cold_night] = R.color.weather_bg_cold_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_fine] = R.color.weather_bg_fine_bottom_color
        bottomColorMap[R.drawable.weather_bg_fine_night] = R.color.weather_bg_fine_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_fog] = R.color.weather_bg_fog_bottom_color
        bottomColorMap[R.drawable.weather_bg_fog_night] = R.color.weather_bg_fog_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_freezing_rain] = R.color.weather_bg_freezing_rain_bottom_color
        bottomColorMap[R.drawable.weather_bg_freezing_rain_night] = R.color.weather_bg_freezing_rain_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_haze] = R.color.weather_bg_haze_bottom_color
        bottomColorMap[R.drawable.weather_bg_haze_night] = R.color.weather_bg_haze_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_heat] = R.color.weather_bg_heat_bottom_color
        bottomColorMap[R.drawable.weather_bg_heat_night] = R.color.weather_bg_heat_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_heavy_rain] = R.color.weather_bg_heavy_rain_bottom_color
        bottomColorMap[R.drawable.weather_bg_heavy_rain_bight] = R.color.weather_bg_heavy_rain_bight_bottom_color
        bottomColorMap[R.drawable.weather_bg_heavy_snow] = R.color.weather_bg_heavy_snow_bottom_color
        bottomColorMap[R.drawable.weather_bg_heavy_snow_night] = R.color.weather_bg_heavy_snow_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_jansa] = R.color.weather_bg_jansa_bottom_color
        bottomColorMap[R.drawable.weather_bg_jansa_night] = R.color.weather_bg_jansa_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_overcast] = R.color.weather_bg_overcast_bottom_color
        bottomColorMap[R.drawable.weather_bg_overcast_night] = R.color.weather_bg_overcast_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_scouther] = R.color.weather_bg_scouther_bottom_color
        bottomColorMap[R.drawable.weather_bg_scouther_night] = R.color.weather_bg_scouther_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_shower] = R.color.weather_bg_shower_bottom_color
        bottomColorMap[R.drawable.weather_bg_shower_night] = R.color.weather_bg_shower_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_sleet] = R.color.weather_bg_sleet_bottom_color
        bottomColorMap[R.drawable.weather_bg_sleet_night] = R.color.weather_bg_sleet_night_bottom_color
        bottomColorMap[R.drawable.weather_bg_thunder_shower] = R.color.weather_bg_thunder_shower_bottom_color
        bottomColorMap[R.drawable.weather_bg_thunder_shower_night] = R.color.weather_bg_thunder_shower_night_bottom_color
    }

    fun getTopColor(res: Int): Int{
        return topColorMap[res]!!
    }

    fun getBottomColor(res: Int): Int{
        return bottomColorMap[res]!!
    }
}