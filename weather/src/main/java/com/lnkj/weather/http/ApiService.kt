package com.lnkj.weather.http

import com.lnkj.weather.HE_FENG_TIAN_QI_KEY
import com.lnkj.weather.http.bean.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/13 22:34
 * @描述
 */
interface ApiService {

    /**
     * 实况天气 https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/121.6544,25.1552/realtime.json?alert=true&lang=zh_CN
     * 分钟级降雨 https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/minutely.json?lang=zh_CN
     * 15天天气(包含昨天的天气) https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/118.39829254,35.0850029/daily.json?lang=zh_CN&dailysteps=16&hourlysteps=384&begin=1584979200
     * 昨天天气 https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/118.39829254,35.0850029/daily.json?lang=zh_CN&dailysteps=1&hourlysteps=24&begin=1584979200
     * 24小时天气 https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/121.6544,25.1552/hourly.json?langzh_CN&hourlysteps=72
     *
     * 和风生活指数
     *      商业版https://api.heweather.net/s6/weather/lifestyle?{parameters}
     *      免费版https://free-api.heweather.net/s6/weather/lifestyle?{parameters}
     */

    @GET("https://search.heweather.net/top")
    suspend fun getHotCityList(
        @Query("group")group: String = "cn",
        @Query("key")key: String = HE_FENG_TIAN_QI_KEY,
        @Query("number")number: Int = 7,
        @Query("lang")lang: String = "zh"
    ): HeWeatherCityBean

    /**
     * 搜索城市
     * @param location String
     * @param group String cn,overseas == 中国+海外城市
     * @param key String
     * @param number Int
     * @param lang String
     * @return HeWeatherCityBean
     */
    @GET("https://search.heweather.net/find")
    suspend fun searchCity(
        @Query("location")location: String,
        @Query("group")group: String = "cn",
        @Query("key")key: String = HE_FENG_TIAN_QI_KEY,
        @Query("number")number: Int = 20,
        @Query("lang")lang: String = "zh"
    ): HeWeatherCityBean

    /**
     * 获取实况天气
     * @param lat String
     * @param log String
     * @return RealtimeWeatherBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/realtime.json?alert=true&lang=zh_CN")
    suspend fun getCaiYunRealtimeWeather(@Path("lat")lat: String, @Path("log")log: String): RealtimeWeatherBean

    /**
     * 获取分钟级降雨量
     * @param lat String
     * @param log String
     * @return MinutelyRainfallBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/minutely.json?lang=zh_CN")
    suspend fun getCaiYunMinutelyRainfall(@Path("lat")lat: String, @Path("log")log: String): MinutelyRainfallBean

    /**
     * 获取未来15天天气
     * @param lat String
     * @param log String
     * @param begin Long
     * @return DailyWeatherBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/daily.json?lang=zh_CN&dailysteps=16&hourlysteps=384&begin={begin}")
    suspend fun getDailyWeather(@Path("lat")lat: String, @Path("log")log: String, @Path("begin")begin: Long = System.currentTimeMillis() - 86400): DailyWeatherBean

    /**
     * 获取昨天天气
     * @param lat String
     * @param log String
     * @param begin Long
     * @return DailyWeatherBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/daily.json?lang=zh_CN&dailysteps=1")
    suspend fun getYesterdayDailyWeather(@Path("lat")lat: String, @Path("log")log: String, @Query("begin")begin: Long): DailyWeatherBean

    /**
     * 获取未来24小时天气
     * @return HourlyWeatherBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/hourly.json?lang=zh_CN")
    suspend fun getHourlyWeather(@Path("lat")lat: String, @Path("log")log: String, @Query("hourlysteps")hourlysteps: Int): HourlyWeatherBean

    /**
     * 获取15天和24小时通用天气
     * @param lat String
     * @param log String
     * @return WeatherBean
     */
    @GET("https://api.caiyunapp.com/v2.5/6yo5uJgv8LldsIn2/{lat},{log}/weather.json?lang=zh_CN&hourlysteps=24&dailysteps=15&alert=true")
    suspend fun getCaiYunWeather(@Path("lat")lat: String, @Path("log")log: String): WeatherBean

    /**
     * 当天的生活指数
     * @return LifestyleBean
     */
    @GET("https://api.heweather.net/s6/weather/lifestyle?key=d0cbd0a986bd46dfba5ee1828cde4546")
    suspend fun getLifestyle(@Query("location")location: String): LifestyleBean


    /**
     * 获取体感温度
     * @param location String
     * @return HeFlWeather
     */
    @GET("https://api.heweather.net/s6/weather/now?key=d0cbd0a986bd46dfba5ee1828cde4546")
    suspend fun getFeltTemperature(@Query("location")location: String): HeFlWeather

    /**
     * 获取空气质量
     * @param location String
     * @return HeAirQualityBean
     */
    @GET("https://api.heweather.net/s6/air/now?key=d0cbd0a986bd46dfba5ee1828cde4546")
    suspend fun getAirData(@Query("location")location: String): HeAirQualityBean

    /**
     * 获取未来三天空气质量
     * @param location String
     * @return HeForecastAirBean
     */
    @GET("https://api.heweather.net/s6/air/forecast?key=d0cbd0a986bd46dfba5ee1828cde4546")
    suspend fun getForecastAirData(@Query("location")location: String): HeForecastAirBean

    /**
     * 高温排行
     * @return HotRankBean
     */
    @GET("http://yunduantianqi.com/api/rank")
    suspend fun getHotRankList(): HotRankBean
}