package com.lnkj.weather.utils

import android.annotation.SuppressLint
import com.lnkj.library_base.db.bean.DressLifeStyle
import com.lnkj.library_base.db.bean.HourlyWeather
import com.lnkj.library_base.db.bean.LifeStyle
import com.lnkj.weather.http.bean.*
import com.lnkj.weather.utils.DateUtils.PATTERN_16
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/1 15:03
 * @描述
 */
object WeatherDataUtils {
    /**
     * 合成今日小时天气详情
     * @param yesterdayWeatherBean DailyWeatherBean
     * @param todayWeatherBean DailyWeatherBean
     * @param heFlWeather HeFlWeather
     * @param hour24Weather HourlyWeatherBean
     * @param lifestyleBean LifestyleBean
     */
    fun compoundTodayHourDetailsWeatherData(
        yesterdayWeatherBean: DailyWeatherBean,
        todayWeatherBean: WeatherBean,
        heFlWeather: HeFlWeather,
        lifestyleBean: LifestyleBean,
        air: HeAirQualityBean
    ): HourDetailsWeatherBean {

        // 24小时天气
        val hourly = todayWeatherBean.result?.hourly
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
                airQualityValue = hourly?.airQuality?.aqi?.get(index)?.value?.chn!!,
                windQualityValue = WeatherUtils.getWindSpeed(hourly?.wind?.get(index)?.speed!!.toInt()),
                directionQualityValue = WeatherUtils.getWindDirection(hourly?.wind?.get(index)?.direction!!)
            )
            hourlyWeatherList.add(hourlyWeather)
        }

        val daily = todayWeatherBean.result?.daily
        val weatherName =
            WeatherUtils.formatWeather(
                todayWeatherBean.result?.daily?.skycon08h20h!![0]?.value!!,
                todayWeatherBean.result?.daily?.skycon20h32h!![0]?.value!!
            )
        val windSpeed =
            WeatherUtils.getWindSpeed(todayWeatherBean?.result?.daily?.wind?.get(0)?.max!!.speed!!.toInt())
        val windDirection =
            WeatherUtils.getWindDirection(todayWeatherBean?.result?.daily?.wind?.get(0)?.max!!.direction!!)
        val dress =
            lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "drsg" }
                ?.firstOrNull()
        val tt =
            todayWeatherBean.result?.daily?.temperature?.get(0)?.max!!.toInt() - yesterdayWeatherBean?.result?.daily?.temperature?.get(
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
            sunsetTime = daily?.astro?.get(0)?.sunset?.time!!
        )

        // 雨伞指数
        val todayWeatherName = todayWeatherBean?.result?.daily?.skycon?.get(0)?.value!!
        val umbrellaLifeStyle = LifeStyle(
            indexName = "雨伞指数",
            indexBrf = WeatherUtils.umbrellaBrf(todayWeatherName),
            indexTxt = WeatherUtils.umbrellaTxt(todayWeatherName),
            knowledge = "雨伞指数根据是否下雨或下雪，以及雨雪的等级，提醒大家该带伞时要带伞，不然可能会被淋湿的哦，感冒生病可就不好了。",
            knowledgeTitle = "雨伞指数小知识：",
            isGood = WeatherUtils.isDressGood(WeatherUtils.umbrellaBrf(todayWeatherName)),
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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
            weatherName = weatherName,
            max = daily?.temperature?.get(0)?.max!!.toInt(),
            min = daily?.temperature?.get(0)?.min!!.toInt(),
            temperatureDifference = daily?.temperature?.get(0)?.max!!.toInt() - daily?.temperature?.get(
                0
            )?.min!!.toInt(),
            windSpeed = windSpeed,
            windDirection = windDirection,
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

        val realtime = todayWeatherBean.result!!.realtime
        return HourDetailsWeatherBean(
            min = todayWeatherBean.result?.daily?.temperature?.get(0)?.min!!.toInt(),
            max = todayWeatherBean.result?.daily?.temperature?.get(0)?.max!!.toInt(),
            weatherName = weatherName,
            weatherIcon = WeatherUtils.getWeatherIcon(
                todayWeatherBean.result?.daily?.skycon08h20h?.get(
                    0
                )?.value!!
            ),
            windSpeed = windSpeed,
            windDirection = windDirection,
            humidity = "${(todayWeatherBean.result?.daily?.humidity!!?.get(0)?.max!! * 100).toInt()}%",
            pressure = "${todayWeatherBean.result?.daily?.pressure?.get(0)?.max!!.toInt()!! / 100}hPa",
            ultravioletLight = ultravioletLight.brf,
            sunriseTime = daily?.astro?.get(0)?.sunrise?.time!!,
            sunsetTime = daily?.astro?.get(0)?.sunset?.time!!,
            hourlyWeatherList = hourlyWeatherList,
            airQualityValue = realtime!!.airQuality!!.aqi!!.chn!!,
            airQualityName = WeatherUtils.getAirQualityDescription(realtime!!.airQuality!!.aqi!!.chn!!),
            airQualityTxt = WeatherUtils.getAirQualityTxt(realtime!!.airQuality!!.aqi!!.chn!!),
            dressLifeStyle = dressLifeStyle,
            lifeStyleList = lifeStyleList,
            currentWeather = hourlyWeatherList[0].weatherName,
            currentTemperature = hourlyWeatherList[0].temperature,
            flTemperature = heFlWeather.heWeather6?.get(0)?.now?.fl!!,
            yesterdayMin = yesterdayWeatherBean.result?.daily?.temperature?.get(0)?.min!!.toInt(),
            yesterdayMax = yesterdayWeatherBean.result?.daily?.temperature?.get(0)?.max!!.toInt()
        )

    }

    /**
     * 获取明天的天气
     * @param hourWeather HourlyWeatherBean
     * @param dayType Int
     * @param air HeForecastAirBean
     * @param lifestyleBean LifestyleBean
     * @param todayWeatherBean WeatherBean
     * @return HourDetailsWeatherBean
     */
    @SuppressLint("SimpleDateFormat")
    fun compoundDayHourDetailsWeatherData(
        hourWeather: HourlyWeatherBean,
        dayType: Int,
        air: HeForecastAirBean,
        lifestyleBean: LifestyleBean,
        todayWeatherBean: WeatherBean
    ): HourDetailsWeatherBean {

        // 24小时天气
        val hourly = hourWeather.result?.hourly
        val hourlyWeatherList = mutableListOf<HourlyWeather>()
        val currentHour = SimpleDateFormat(PATTERN_16).format(Date()).toInt()
        val currentIndex = 24 - currentHour

        val subList = hourly?.temperature?.subList(currentIndex, currentIndex + 24)

        subList?.forEachIndexed { index, temperature ->
            val hourlyWeather = HourlyWeather(
                time = DateUtils.formatDateT(temperature?.datetime!!, DateUtils.PATTERN_15),
                weatherName = WeatherUtils.getWeatherName(hourly?.skycon?.get(index + currentIndex)?.value!!),
                weatherIcon = WeatherUtils.getWeatherIcon(hourly?.skycon?.get(index + currentIndex)?.value!!),
                temperature = temperature.value!!.toInt(),
                airQualityName = WeatherUtils.getAirQualityDescription(
                    hourly?.airQuality?.aqi?.get(
                        index + currentIndex
                    )?.value?.chn!!
                ),
                airQualityValue = hourly?.airQuality?.aqi?.get(index + currentIndex)?.value?.chn!!,
                windQualityValue = WeatherUtils.getWindSpeed(hourly?.wind?.get(index)?.speed!!.toInt()),
                directionQualityValue = WeatherUtils.getWindDirection(hourly?.wind?.get(index)?.direction!!)
            )
            hourlyWeatherList.add(hourlyWeather)

        }
        val daily = todayWeatherBean.result?.daily

        val airValue = if (dayType == 2) {
            daily?.airQuality?.aqi!![1]!!.max!!.chn//明天
        } else {
            daily?.airQuality?.aqi!![2]!!.max!!.chn//后天
        }

        val weatherName = WeatherUtils.formatWeather(
            daily?.skycon08h20h!![1]?.value!!,
            daily?.skycon20h32h!![1]?.value!!
        )

        val windSpeed =
            WeatherUtils.getWindSpeed(todayWeatherBean?.result?.daily?.wind?.get(1)?.max!!.speed!!.toInt())
        val windDirection =
            WeatherUtils.getWindDirection(todayWeatherBean?.result?.daily?.wind?.get(1)?.max!!.direction!!)
        val todayWeatherName = todayWeatherBean?.result?.daily?.skycon?.get(1)?.value!!
        val max = daily?.temperature?.get(1)?.max!!.toInt()
        val min = daily?.temperature?.get(1)?.min!!.toInt()

        val temperatureDifference =
            daily?.temperature?.get(1)?.max!!.toInt() - daily?.temperature?.get(1)?.min!!.toInt()
        val sunriseTime = daily?.astro?.get(1)?.sunrise?.time!!
        val sunsetTime = daily?.astro?.get(1)?.sunset?.time!!


        // 昨天的天气区间
        val yesterdayTemperature =
            "${daily?.temperature?.get(0)?.max!!.toInt()}~${daily?.temperature?.get(0)?.min!!.toInt()}°"

        val dress = lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "drsg" }
            ?.firstOrNull()
        val tt =
            daily?.temperature?.get(1)?.max!!.toInt() - daily?.temperature?.get(0)?.max!!.toInt()
        val dressLifeStyle = DressLifeStyle(
            dressIcon = WeatherUtils.getDressIcon(dress?.brf!!),
            dressTopTip = when {
                tt == 0 -> {
                    "今天气温变化平稳(昨天${yesterdayTemperature})"
                }
                tt > 0 -> "今天最高气温上升${tt}°(昨天${yesterdayTemperature})"
                else -> "今天最高气温下降${tt.absoluteValue}°(昨天${yesterdayTemperature}°)"
            },
            dressBottomTip = dress.txt!!,
            indexName = "穿衣指数",
            indexBrf = "天气${dress?.brf!!}",
            indexTxt = dress.txt!!,
            knowledge = "穿衣指数综合了天空状况、气温、温度及风速等气象因素，提醒大家要好好穿衣服，身体才能棒棒哒。一般温度越低、风速越大，我们就要穿的越多哦。",
            knowledgeTitle = "穿衣指数小知识：",
            isGood = WeatherUtils.isDressGood(dress?.brf),
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
        )

        // 雨伞指数
        val umbrellaLifeStyle = LifeStyle(
            indexName = "雨伞指数",
            indexBrf = WeatherUtils.umbrellaBrf(todayWeatherName),
            indexTxt = WeatherUtils.umbrellaTxt(todayWeatherName),
            knowledge = "雨伞指数根据是否下雨或下雪，以及雨雪的等级，提醒大家该带伞时要带伞，不然可能会被淋湿的哦，感冒生病可就不好了。",
            knowledgeTitle = "雨伞指数小知识：",
            isGood = WeatherUtils.isDressGood(WeatherUtils.umbrellaBrf(todayWeatherName)),
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
        )

        val lifeStyleList = arrayListOf<LifeStyle>()
        lifeStyleList.add(umbrellaLifeStyle)
        lifeStyleList.add(influenzaLifeStyle)
        lifeStyleList.add(ultravioletLightLifeStyle)
        lifeStyleList.add(airCureLightLifeStyle)
        lifeStyleList.add(sportLightLifeStyle)
        lifeStyleList.add(travelLightLifeStyle)
        lifeStyleList.add(fishLightLifeStyle)

        return HourDetailsWeatherBean(
            min = min,
            max = max,
            weatherName = weatherName,
            weatherIcon = WeatherUtils.getWeatherIcon(daily?.skycon08h20h?.get(1)?.value!!),
            windSpeed = windSpeed,
            windDirection = windDirection,
            humidity = "${(daily?.humidity!!?.get(1)?.max!! * 100).toInt()}%",
            pressure = "${daily?.pressure?.get(1)?.max!!.toInt()!! / 100}hPa",
            ultravioletLight = ultravioletLight.brf,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            hourlyWeatherList = hourlyWeatherList,
            airQualityValue = airValue!!,
            airQualityName = WeatherUtils.getAirQualityDescription(airValue),
            airQualityTxt = WeatherUtils.getAirQualityTxt(airValue),
            dressLifeStyle = dressLifeStyle,
            lifeStyleList = lifeStyleList
        )

    }


    /**
     * 获取后天的天气
     * @param hourWeather HourlyWeatherBean
     * @param dayType Int
     * @param air HeForecastAirBean
     * @param lifestyleBean LifestyleBean
     * @param todayWeatherBean WeatherBean
     * @return HourDetailsWeatherBean
     */
    @SuppressLint("SimpleDateFormat")
    fun compoundHDayHourDetailsWeatherData(
        hourWeather: HourlyWeatherBean,
        dayType: Int,
        air: HeForecastAirBean,
        lifestyleBean: LifestyleBean,
        todayWeatherBean: WeatherBean
    ): HourDetailsWeatherBean {

        // 24小时天气
        val hourly = hourWeather.result?.hourly
        val hourlyWeatherList = mutableListOf<HourlyWeather>()
        val currentHour = SimpleDateFormat(PATTERN_16).format(Date()).toInt()
        val currentIndex = if (dayType == 2) {
            24 - currentHour
        } else {
            24 - currentHour + 24
        }

        val subList = hourly?.temperature?.subList(currentIndex, currentIndex + 24)

        subList?.forEachIndexed { index, temperature ->
            val hourlyWeather = HourlyWeather(
                time = DateUtils.formatDateT(temperature?.datetime!!, DateUtils.PATTERN_15),
                weatherName = WeatherUtils.getWeatherName(hourly?.skycon?.get(index + currentIndex)?.value!!),
                weatherIcon = WeatherUtils.getWeatherIcon(hourly?.skycon?.get(index + currentIndex)?.value!!),
                temperature = temperature.value!!.toInt(),
                airQualityName = WeatherUtils.getAirQualityDescription(
                    hourly?.airQuality?.aqi?.get(
                        index + currentIndex
                    )?.value?.chn!!
                ),
                airQualityValue = hourly?.airQuality?.aqi?.get(index + currentIndex)?.value?.chn!!,
                windQualityValue = WeatherUtils.getWindSpeed(hourly?.wind?.get(index)?.speed!!.toInt()),
                directionQualityValue = WeatherUtils.getWindDirection(hourly?.wind?.get(index)?.direction!!)
            )
            hourlyWeatherList.add(hourlyWeather)

        }
        val daily = todayWeatherBean.result?.daily
        val airValue = if (dayType == 2) {
            daily?.airQuality?.aqi!![1]!!.max!!.chn//明天
        } else {
            daily?.airQuality?.aqi!![2]!!.max!!.chn//后天
        }

        val weatherName = WeatherUtils.formatWeather(
            daily?.skycon08h20h!![2]?.value!!,
            daily?.skycon20h32h!![2]?.value!!
        )
        val windSpeed =
            WeatherUtils.getWindSpeed(todayWeatherBean?.result?.daily?.wind?.get(2)?.max!!.speed!!.toInt())
        val windDirection =
            WeatherUtils.getWindDirection(todayWeatherBean?.result?.daily?.wind?.get(2)?.max!!.direction!!)

        val max = daily?.temperature?.get(2)?.max!!.toInt()
        val min = daily?.temperature?.get(2)?.min!!.toInt()

        val temperatureDifference =
            daily?.temperature?.get(2)?.max!!.toInt() - daily?.temperature?.get(2)?.min!!.toInt()
        val sunriseTime = daily?.astro?.get(2)?.sunrise?.time!!
        val sunsetTime = daily?.astro?.get(2)?.sunset?.time!!

        val todayWeatherName = daily?.skycon?.get(2)?.value!!

        // 昨天的天气区间
        val yesterdayTemperature =
            "${daily?.temperature?.get(1)?.max!!.toInt()}~${daily?.temperature?.get(1)?.min!!.toInt()}°"

        val dress = lifestyleBean.heWeather6?.get(0)?.lifestyle?.filter { it?.type == "drsg" }
            ?.firstOrNull()
        val tt =
            daily?.temperature?.get(2)?.max!!.toInt() - daily?.temperature?.get(1)?.max!!.toInt()
        val dressLifeStyle = DressLifeStyle(
            dressIcon = WeatherUtils.getDressIcon(dress?.brf!!),
            dressTopTip = when {
                tt == 0 -> {
                    "今天气温变化平稳(昨天${yesterdayTemperature})"
                }
                tt > 0 -> "今天最高气温上升${tt}°(昨天${yesterdayTemperature})"
                else -> "今天最高气温下降${tt.absoluteValue}°(昨天${yesterdayTemperature})"
            },
            dressBottomTip = dress.txt!!,
            indexName = "穿衣指数",
            indexBrf = "天气${dress?.brf!!}",
            indexTxt = dress.txt!!,
            knowledge = "穿衣指数综合了天空状况、气温、温度及风速等气象因素，提醒大家要好好穿衣服，身体才能棒棒哒。一般温度越低、风速越大，我们就要穿的越多哦。",
            knowledgeTitle = "穿衣指数小知识：",
            isGood = WeatherUtils.isDressGood(dress?.brf),
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
        )

        // 雨伞指数
        val umbrellaLifeStyle = LifeStyle(
            indexName = "雨伞指数",
            indexBrf = WeatherUtils.umbrellaBrf(todayWeatherName),
            indexTxt = WeatherUtils.umbrellaTxt(todayWeatherName),
            knowledge = "雨伞指数根据是否下雨或下雪，以及雨雪的等级，提醒大家该带伞时要带伞，不然可能会被淋湿的哦，感冒生病可就不好了。",
            knowledgeTitle = "雨伞指数小知识：",
            isGood = WeatherUtils.isDressGood(WeatherUtils.umbrellaBrf(todayWeatherName)),
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
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
            weatherName = weatherName,
            max = max,
            min = min,
            temperatureDifference = temperatureDifference,
            windSpeed = windSpeed,
            windDirection = windDirection,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime
        )

        val lifeStyleList = arrayListOf<LifeStyle>()
        lifeStyleList.add(umbrellaLifeStyle)
        lifeStyleList.add(influenzaLifeStyle)
        lifeStyleList.add(ultravioletLightLifeStyle)
        lifeStyleList.add(airCureLightLifeStyle)
        lifeStyleList.add(sportLightLifeStyle)
        lifeStyleList.add(travelLightLifeStyle)
        lifeStyleList.add(fishLightLifeStyle)

        return HourDetailsWeatherBean(
            min = min,
            max = max,
            weatherName = weatherName,
            weatherIcon = WeatherUtils.getWeatherIcon(daily?.skycon08h20h?.get(2)?.value!!),
            windSpeed = windSpeed,
            windDirection = windDirection,
            humidity = "${(daily?.humidity!!?.get(2)?.max!! * 100).toInt()}%",
            pressure = "${daily?.pressure?.get(2)?.max!!.toInt()!! / 100}hPa",
            ultravioletLight = ultravioletLight.brf,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            hourlyWeatherList = hourlyWeatherList,
            airQualityValue = airValue!!,
            airQualityName = WeatherUtils.getAirQualityDescription(airValue),
            airQualityTxt = WeatherUtils.getAirQualityTxt(airValue),
            dressLifeStyle = dressLifeStyle,
            lifeStyleList = lifeStyleList
        )

    }

}