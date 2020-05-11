package com.lnkj.weather.ui.realweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.*
import com.lnkj.library_base.db.database.WeatherDatabase
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.DateUtils.PATTERN_1
import com.lnkj.weather.utils.DateUtils.PATTERN_14
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.mvvmlib.basic.Event
import com.mufeng.mvvmlib.http.handler.Request
import com.mufeng.mvvmlib.util.WeatherDayTimeManager
import com.mufeng.mvvmlib.utilcode.ext.formatDateStr
import com.mufeng.mvvmlib.utilcode.ext.loge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.absoluteValue

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:46
 * @描述
 */
class RealTimeWeatherViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(ApiService::class.java) }


    val imageRes = MutableLiveData<Int>()
    val currentCity = MutableLiveData<MyCityBean>()

    init {
        imageRes.value = -1
    }

    val cityLiveData = MutableLiveData<MyCityBean>()
    fun getLocationCity() {
        viewModelScope.launch(Dispatchers.IO) {
            val city = WeatherDatabase.get().myCityDao().searchLocationCity()
            cityLiveData.postValue(city)
        }
    }

    fun getCaiYunRealtimeWeather(cityBean: MyCityBean, lat: String, log: String) {
        Log.e("TAG", "${cityBean.counties} 开始请求天气数据 ${Date().formatDateStr()}")
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("TAG", "${cityBean.counties} 开始执行天气数据 ${Date().formatDateStr()}")
            try {
                // 获取15天和24小时通用天气
                val weatherBean = service.getCaiYunWeather(lat, log)
                // 获取昨天天气
                val yesterdayWeatherBean = service.getYesterdayDailyWeather(
                    lat,
                    log,
                    begin = System.currentTimeMillis() / 1000 - 86400
                )
                // 获取生活指数
                val lifestyleBean = service.getLifestyle("${lat},$log")

                // 获取今天空气质量
                val air = service.getAirData("${lat},$log")

                if (weatherBean.status != "ok" || yesterdayWeatherBean.status != "ok"
                    || lifestyleBean.heWeather6?.get(0)?.status != "ok" || air.heWeather6?.get(0)?.status != "ok"
                ) {
                    apiLoading.postValue(Event(false))
                    return@launch
                }

                // 今天天气
                val todayWeather = TodayWeather(
                    DateUtils.formatDateT(
                        weatherBean.result?.daily?.temperature?.get(0)?.date!!,
                        PATTERN_14
                    ),
                    weatherBean.result?.daily?.temperature?.get(0)?.max!!.toInt(),
                    weatherBean.result?.daily?.temperature?.get(0)?.min!!.toInt(),
                    WeatherUtils.formatWeather(
                        weatherBean.result?.daily?.skycon08h20h?.get(0)?.value!!,
                        weatherBean.result?.daily?.skycon20h32h?.get(0)?.value!!
                    ),
                    WeatherUtils.getWeatherIcon(weatherBean.result?.daily?.skycon08h20h?.get(0)?.value!!)
                )
                // 明日天气
                val tomorrowWeather = TodayWeather(
                    DateUtils.formatDateT(
                        weatherBean.result?.daily?.temperature?.get(1)?.date!!,
                        PATTERN_14
                    ),
                    weatherBean.result?.daily?.temperature?.get(1)?.max!!.toInt(),
                    weatherBean.result?.daily?.temperature?.get(1)?.min!!.toInt(),
                    WeatherUtils.formatWeather(
                        weatherBean.result?.daily?.skycon08h20h?.get(1)?.value!!,
                        weatherBean.result?.daily?.skycon20h32h?.get(1)?.value!!
                    ),
                    WeatherUtils.getWeatherIcon(weatherBean.result?.daily?.skycon08h20h?.get(1)?.value!!)
                )
                // 小时天气数据
                val hourly = weatherBean.result.hourly
                val hourlyWeatherList = mutableListOf<HourlyWeather>()
                hourly?.temperature?.forEachIndexed { index, temperature ->
                    val hourlyWeather = HourlyWeather(
                        time = DateUtils.formatDateT(temperature?.datetime!!, DateUtils.PATTERN_15),
                        weatherName = WeatherUtils.getWeatherName(hourly?.skycon?.get(index)?.value!!),
                        weatherIcon = WeatherUtils.getWeatherIcon(hourly?.skycon?.get(index)?.value!!),
                        temperature = temperature.value!!.toInt(),
                        airQualityName = WeatherUtils.getAirQualityDescription(
                            hourly?.airQuality?.aqi?.get(
                                index
                            )?.value?.chn!!
                        ),
                        airQualityValue = hourly?.airQuality?.aqi?.get(index)?.value?.chn!!
                    )
                    hourlyWeatherList.add(hourlyWeather)
                }

                // 天级天气数据
                val daily = weatherBean.result.daily
                val dailyWeatherList = mutableListOf<DailyWeather>()
                daily.temperature?.forEachIndexed { index, temperature ->
                    val dailyWeather = DailyWeather(
                        date = DateUtils.formatDateT(temperature?.date!!, PATTERN_14),
                        formatDate = DateUtils.formatWeekT(temperature.date),
                        weatherName = WeatherUtils.getWeatherName(daily.skycon?.get(index)?.value!!),
                        weatherIcon = WeatherUtils.getWeatherIcon(daily.skycon[index]?.value!!),
                        weatherDayName = WeatherUtils.getWeatherName(daily.skycon08h20h?.get(index)?.value!!),
                        weatherDayIcon = WeatherUtils.getWeatherIcon(daily.skycon08h20h[index]?.value!!),
                        weatherNightName = WeatherUtils.getWeatherName(daily.skycon20h32h?.get(index)?.value!!),
                        weatherNightIcon = WeatherUtils.getWeatherIcon(daily.skycon20h32h[index]?.value!!),
                        max = temperature.max!!.toInt(),
                        min = temperature.min!!.toInt(),
                        airQualityName = WeatherUtils.getAirQualityDescription(
                            daily.airQuality?.aqi?.get(
                                index
                            )?.max!!.chn!!
                        ),
                        airQualityValue = daily.airQuality.aqi.get(index)?.max!!.chn!!,
                        windSpeed = WeatherUtils.getWindSpeed(daily.wind?.get(index)?.max!!.speed!!.toInt()),
                        windDirection = WeatherUtils.getWindDirection(daily.wind.get(index)?.max!!.direction!!)
                    )
                    dailyWeatherList.add(dailyWeather)
                }
                // 昨天天气数据
                val dailyWeather = DailyWeather(
                    date = DateUtils.formatDateT(
                        yesterdayWeatherBean.result?.daily?.temperature?.get(
                            0
                        )?.date!!, PATTERN_14
                    ),
                    formatDate = DateUtils.formatWeekT(
                        yesterdayWeatherBean.result.daily.temperature.get(
                            0
                        )?.date!!
                    ),
                    weatherName = WeatherUtils.getWeatherName(
                        yesterdayWeatherBean.result.daily.skycon?.get(
                            0
                        )?.value!!
                    ),
                    weatherIcon = WeatherUtils.getWeatherIcon(yesterdayWeatherBean.result.daily.skycon[0]?.value!!),
                    weatherDayName = WeatherUtils.getWeatherName(
                        yesterdayWeatherBean.result.daily.skycon08h20h?.get(
                            0
                        )?.value!!
                    ),
                    weatherDayIcon = WeatherUtils.getWeatherIcon(yesterdayWeatherBean.result.daily.skycon08h20h[0]?.value!!),
                    weatherNightName = WeatherUtils.getWeatherName(
                        yesterdayWeatherBean.result.daily.skycon20h32h?.get(
                            0
                        )?.value!!
                    ),
                    weatherNightIcon = WeatherUtils.getWeatherIcon(yesterdayWeatherBean.result.daily.skycon20h32h[0]?.value!!),
                    max = yesterdayWeatherBean.result.daily.temperature[0]?.max!!.toInt(),
                    min = yesterdayWeatherBean.result.daily.temperature[0]?.min!!.toInt(),
                    airQualityName = WeatherUtils.getAirQualityDescription(
                        yesterdayWeatherBean.result.daily.airQuality?.aqi?.get(0)?.max!!.chn!!.toInt()
                    ),
                    airQualityValue = yesterdayWeatherBean.result.daily.airQuality.aqi[0]?.max!!.chn!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(
                        yesterdayWeatherBean.result.daily.wind?.get(
                            0
                        )?.max!!.speed!!.toInt()
                    ),
                    windDirection = WeatherUtils.getWindDirection(yesterdayWeatherBean.result.daily.wind[0]?.max!!.direction!!)
                )
                dailyWeatherList.add(0, dailyWeather)
                //穿衣指数
                val dress =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "drsg" }
                        ?.firstOrNull()
                val tt =
                    weatherBean.result?.daily?.temperature?.get(0)?.max!!.toInt() - yesterdayWeatherBean?.result?.daily?.temperature?.get(
                        0
                    )?.max!!.toInt()
                val dressLifeStyle = DressLifeStyle(
                    dressIcon = WeatherUtils.getDressIcon(dress?.brf!!),
                    dressTopTip = when {
                        tt == 0 -> {
                            "今天气温变化平稳(昨天${yesterdayWeatherBean?.result?.daily?.temperature?.get(0)?.min!!.toInt()}~${yesterdayWeatherBean?.result?.daily?.temperature?.get(
                                0
                            )?.max!!.toInt()}°)"
                        }
                        tt > 0 -> "今天最高气温上升${tt}°(昨天${yesterdayWeatherBean?.result?.daily?.temperature?.get(
                            0
                        )?.min!!.toInt()}~${yesterdayWeatherBean?.result?.daily?.temperature?.get(0)?.max!!.toInt()}°)"
                        else -> "今天最高气温下降${tt.absoluteValue}°(昨天${yesterdayWeatherBean?.result?.daily?.temperature?.get(
                            0
                        )?.min!!.toInt()}~${yesterdayWeatherBean?.result?.daily?.temperature?.get(0)?.max!!.toInt()}°)"
                    },
                    dressBottomTip = dress.txt!!,
                    indexName = "穿衣指数",
                    indexBrf = "天气${dress?.brf!!}",
                    indexTxt = dress.txt!!,
                    knowledge = "穿衣指数综合了天空状况、气温、温度及风速等气象因素，提醒大家要好好穿衣服，身体才能棒棒哒。一般温度越低、风速越大，我们就要穿的越多哦。",
                    knowledgeTitle = "穿衣指数小知识：",
                    isGood = WeatherUtils.isDressGood(dress?.brf),
                    weatherName = WeatherUtils.getWeatherName(weatherBean.result?.realtime?.skycon!!),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 雨伞指数
                val todayWeatherName = weatherBean?.result?.daily?.skycon08h20h?.get(0)?.value!!
                val umbrellaLifeStyle = LifeStyle(
                    indexName = "雨伞指数",
                    indexBrf = WeatherUtils.umbrellaBrf(todayWeatherName),
                    indexTxt = WeatherUtils.umbrellaTxt(todayWeatherName),
                    knowledge = "雨伞指数根据是否下雨或下雪，以及雨雪的等级，提醒大家该带伞时要带伞，不然可能会被淋湿的哦，感冒生病可就不好了。",
                    knowledgeTitle = "雨伞指数小知识：",
                    isGood = WeatherUtils.isDressGood(WeatherUtils.umbrellaBrf(todayWeatherName)),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 感冒指数
                val influenza =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "flu" }
                        ?.firstOrNull()
                val influenzaLifeStyle = LifeStyle(
                    indexName = "感冒指数",
                    indexBrf = influenza?.brf + "感冒",
                    indexTxt = influenza?.txt!!,
                    knowledge = "感冒指数通过综合温度、湿度、风速、天气现象及温差等因素提醒大家感冒发生的几率。尤其是老人和小孩，抵抗力较弱，更要多加防护哦。",
                    knowledgeTitle = "感冒指数小知识：",
                    isGood = WeatherUtils.isDressGood(influenza?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 紫外线指数
                val ultravioletLight =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "uv" }
                        ?.firstOrNull()
                val ultravioletLightLifeStyle = LifeStyle(
                    indexName = "紫外线指数",
                    indexBrf = "紫外线" + ultravioletLight?.brf,
                    indexTxt = ultravioletLight?.txt!!,
                    knowledge = "紫外线指数在紫外线辐射较强时，提醒大家外出可以披上长袖衬衣，涂点防晒油，能不晒太阳就别晒太阳啦，不然皮肤会被紫外线晒伤的，只能多年以后才能明白，一旦晒黑了就再也白不回来了。",
                    knowledgeTitle = "紫外线指数小知识：",
                    isGood = WeatherUtils.isDressGood(ultravioletLight?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 晾晒指数
                val airCure =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.firstOrNull { it?.type == "airc" }
                val airCureLightLifeStyle = LifeStyle(
                    indexName = "晾晒指数",
                    indexBrf = airCure?.brf + "晾晒",
                    indexTxt = airCure?.txt!!,
                    knowledge = "晾晒指数综合温度、风速及天空状况等因素，提醒大家是否适合晾晾衣服、晒晒农作物。若不参照此指数，可能会误判晾晒时机哦。",
                    knowledgeTitle = "晾晒指数小知识：",
                    isGood = WeatherUtils.isDressGood(airCure?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 晨练指数
                val sport =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.firstOrNull { it?.type == "sport" }
                val sportLightLifeStyle = LifeStyle(
                    indexName = "晨练指数",
                    indexBrf = sport?.brf + "晨练",
                    indexTxt = sport?.txt!!,
                    knowledge = "晨练指数综合温度、风速、天气现象及空气质量等因素，提示大家晨练也要看天气、空气。气象条件不好的时候去锻炼，可能会事倍功半。尤其是中老年朋友，晨练前更要关注是否适宜晨练。",
                    knowledgeTitle = "晨练指数小知识：",
                    isGood = WeatherUtils.isDressGood(sport?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 旅游指数
                val travel =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.firstOrNull { it?.type == "trav" }
                val travelLightLifeStyle = LifeStyle(
                    indexName = "旅游指数",
                    indexBrf = travel?.brf + "旅游",
                    indexTxt = travel?.txt!!,
                    knowledge = "旅游指数是根据天气的变化，结合气温、风速和其他天气情况，提供的综合性出游建议。关注旅游指数，便于更好的安排行程，收货快乐好心情。",
                    knowledgeTitle = "旅游指数小知识：",
                    isGood = WeatherUtils.isDressGood(travel?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                // 钓鱼指数
                val fish =
                    lifestyleBean.heWeather6?.get(0)?.lifestyle?.firstOrNull { it?.type == "fsh" }
                val fishLightLifeStyle = LifeStyle(
                    indexName = "钓鱼指数",
                    indexBrf = fish?.brf + "钓鱼",
                    indexTxt = fish?.txt!!,
                    knowledge = "钓鱼指数通过综合温度、风速、天气现象及温差等影响垂钓的因素，提醒爱钓鱼的你选择合适的水域、有利的天气去垂钓，才能收获满满。",
                    knowledgeTitle = "钓鱼指数小知识：",
                    isGood = WeatherUtils.isDressGood(fish?.brf!!),
                    weatherName = WeatherUtils.getWeatherName(todayWeatherName),
                    max = daily?.temperature?.get(0)?.max!!.toInt(),
                    min = daily?.temperature?.get(0)?.min!!.toInt(),
                    temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                        0
                    )?.min!!.toInt(),
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean?.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean?.result?.realtime?.wind?.direction!!),
                    sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
                    sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
                )

                val lifeStyleList = arrayListOf<LifeStyle>()
                lifeStyleList.add(umbrellaLifeStyle)
                lifeStyleList.add(influenzaLifeStyle)
                lifeStyleList.add(ultravioletLightLifeStyle)
                lifeStyleList.add(airCureLightLifeStyle)
                lifeStyleList.add(sportLightLifeStyle)
                lifeStyleList.add(travelLightLifeStyle)
                lifeStyleList.add(fishLightLifeStyle)

                // 预警信息
                val alertInfo = if (weatherBean?.result?.alert?.content?.isNullOrEmpty() == true) {
                    emptyList<String>()
                } else {
                    val list = arrayListOf<String>()
                    weatherBean.result.alert?.content?.forEach {
                        list.add(WeatherUtils.getAlertInfo(it?.code!!))
                    }
                    list
                }

                // 判断是否有雨
                val precipitation2h = weatherBean?.result?.minutely?.precipitation2h
                val indexs = mutableMapOf<Int, Double>()
                precipitation2h?.forEachIndexed { index, d ->
                    if (d!! > 0) {
                        indexs[index] = d
                    }
                }

                val rainTip = if (indexs.isEmpty()) {
                    "未来2小时不会下${WeatherUtils.isRain(
                        weatherBean?.result?.daily?.skycon08h20h?.get(0)?.value!!,
                        weatherBean?.result?.daily?.skycon20h32h?.get(0)?.value!!
                    )}，放心出门吧"
                } else {
                    weatherBean.result?.minutely?.description
                }

                //判断是否为白天
                val sunriseTime = fishLightLifeStyle.sunriseTime
                val sunsetTime = fishLightLifeStyle.sunsetTime
                val isDayTime = DateUtils.isDayTime(sunriseTime, sunsetTime)
                //保存是否为白天
                WeatherDayTimeManager.instance.setIsDayTime(isDayTime)
                weatherIsDayTime.postValue(isDayTime)

//                val weatherKey = if (isDayTime) {
//                    weatherBean?.result?.daily?.skycon08h20h?.get(0)?.value!!
//                } else {
//                    weatherBean?.result?.daily?.skycon20h32h?.get(0)?.value!!
//                }

                val weatherKey = hourly!!.skycon!![0]!!.value!!

                val cityWeather = CityWeather(
                    cityName = cityBean.counties,
                    updateDate = DateUtils.formatTime(Date(), PATTERN_1),
                    temperature = weatherBean.result?.realtime?.temperature!!.toInt(),
                    weatherName = WeatherUtils.getWeatherName(weatherKey),
                    weatherIcon = WeatherUtils.getWeatherIcon(weatherKey),
                    weatherBg = WeatherUtils.getWeatherBg(weatherKey, isDayTime),
                    voiceAnnouncements = """
                    云端天气为您播报天气情况,
                    今日,天气${todayWeather.weatherName},
                    温度${todayWeather.min}到${todayWeather.max}度,
                     明日,天气${tomorrowWeather.weatherName},
                     温度${tomorrowWeather.min}到${tomorrowWeather.max}度
                """,
                    airQualityName = WeatherUtils.getAirQualityDescription(air.heWeather6?.get(0)?.airNowCity?.aqi!!),
                    airQualityValue = air.heWeather6[0]?.airNowCity?.aqi!!,
                    windSpeed = WeatherUtils.getWindSpeed(weatherBean.result?.realtime?.wind?.speed!!.toInt()),
                    windDirection = WeatherUtils.getWindDirection(weatherBean.result?.realtime?.wind?.direction!!),
                    humidity = "${(weatherBean.result?.realtime?.humidity!! * 100).toInt()}%",
                    airPressure = "${weatherBean.result?.realtime?.pressure?.toInt()!! / 100}hPa",
                    rainTip = rainTip!!,
                    hasRain = WeatherUtils.hasRain(weatherBean?.result?.daily?.skycon08h20h?.get(0)?.value!!) || WeatherUtils.hasRain(
                        weatherBean?.result?.daily?.skycon20h32h?.get(0)?.value!!
                    ),
                    todayWeather = todayWeather,
                    tomorrowWeather = tomorrowWeather,
                    hourlyWeatherList = hourlyWeatherList,
                    dailyWeatherList = dailyWeatherList,
                    dressLifeStyle = dressLifeStyle,
                    lifeStyleList = lifeStyleList,
                    alertInfo = alertInfo
                )
                WeatherDatabase.get().cityWeatherDao().save(cityWeather)
                cityWeatherData.postValue(cityWeather)
            } catch (e: Exception) {
                e.printStackTrace()
                apiLoading.postValue(Event(false))
            }

        }
    }

    var cityWeatherTime = MutableLiveData<String>()
    fun searchWeatherTimeByCity(cityBean: MyCityBean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    cityWeatherTime.postValue(
                        WeatherDatabase.get().cityWeatherDao().getWeatherByCity(cityBean.counties).updateDate
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    var cityWeatherData = MutableLiveData<CityWeather>()
    fun searchWeatherByCity(cityBean: MyCityBean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    cityWeatherData.postValue(
                        WeatherDatabase.get().cityWeatherDao().getWeatherByCity(cityBean.counties)
                    )
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }

            }
        }

    }


    /** 获取我的城市 **/
    private val _myCityBeanData = MutableLiveData<MutableList<MyCityBean>>()
    val myCityBeanData: LiveData<MutableList<MyCityBean>>
        get() = _myCityBeanData

    fun getMyCities(isLocation: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val myCityBeans = withContext(Dispatchers.IO) {
                    if (isLocation) {
                        WeatherDatabase.get().myCityDao().searchCityList()
                    } else {
                        WeatherDatabase.get().myCityDao().searchCityListByNoLocation()
                    }
                }
                _myCityBeanData.postValue(myCityBeans)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

        }
    }

    var weatherIsDayTime = MutableLiveData<Boolean>()


    /** 获取我的城市 **/

    /**
     * 保存更新城市天气信息
     * @param myCityBean MyCityBean?
     * @param weatherIcon Int
     * @param temperature String
     */
    fun updateCityWeather(myCityBean: MyCityBean?, weatherIcon: Int, temperature: String) {
        myCityBean?.weatherIcon = weatherIcon
        myCityBean?.temperature = temperature
        viewModelScope.launch(Dispatchers.IO) {
            WeatherDatabase.get().myCityDao().saveCity(myCityBean!!)
        }
    }

    fun cancel() {
    }

    override fun onCleared() {
        super.onCleared()
        loge { "viewmodel被清除" }
    }
}