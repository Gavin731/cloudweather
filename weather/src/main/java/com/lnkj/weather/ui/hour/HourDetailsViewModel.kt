package com.lnkj.weather.ui.hour

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.http.bean.HeAirQualityBean
import com.lnkj.weather.http.bean.HeForecastAirBean
import com.lnkj.weather.http.bean.HourDetailsWeatherBean
import com.lnkj.weather.utils.WeatherDataUtils
import com.mufeng.mvvmlib.basic.Event
import com.mufeng.mvvmlib.http.handler.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:49
 * @描述
 */
class HourDetailsViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(ApiService::class.java) }

    val supperTitle = MutableLiveData<String>()

    private val _hourlyDetailsWeatherData = MutableLiveData<HourDetailsWeatherBean>()
    val hourlyDetailsWeatherData: LiveData<HourDetailsWeatherBean>
        get() = _hourlyDetailsWeatherData

    fun getWeather(timestamp: Long, day: Int, cityBean: MyCityBean) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 获取今天天气
                val todayWeatherBean = service.getCaiYunWeather(cityBean.lon, cityBean.lat)
                when (day) {
                    1 -> {
                        // 获取昨天天气
                        val yesterdayWeatherBean = service.getYesterdayDailyWeather(
                            cityBean.lon,
                            cityBean.lat,
                            begin = System.currentTimeMillis() / 1000 - 86400
                        )
                        // 获取体感温度
                        val heFlWeather =
                            service.getFeltTemperature("${cityBean.lon},${cityBean.lat}")
                        // 获取生活指数
                        val lifestyleBean = service.getLifestyle("${cityBean.lon},${cityBean.lat}")
                        // 获取今天空气质量
                        val air = HeAirQualityBean()//service.getAirData("${cityBean.lon},${cityBean.lat}")

                        // || air.heWeather6?.get(0)?.status != "ok"
                        if (yesterdayWeatherBean.status != "ok" || todayWeatherBean.status != "ok" || heFlWeather.heWeather6?.get(0)?.status != "ok"
                            ||  lifestyleBean.heWeather6?.get(0)?.status != "ok"){
                            apiLoading.postValue(Event(false))
                            return@launch
                        }

                        val compoundTodayHourDetailsWeatherData =
                            WeatherDataUtils.compoundTodayHourDetailsWeatherData(
                                yesterdayWeatherBean,
                                todayWeatherBean,
                                heFlWeather,
                                lifestyleBean,
                                air
                            )

                        _hourlyDetailsWeatherData.postValue(compoundTodayHourDetailsWeatherData)

                    }
                    2 -> {
                        val hourWeather = service.getHourlyWeather(cityBean.lon, cityBean.lat, 48)
                        // 获取今天空气质量
                        val air = HeForecastAirBean()//service.getForecastAirData("${cityBean.lon},${cityBean.lat}")
                        // 获取生活指数
                        val lifestyleBean = service.getLifestyle("${cityBean.lon},${cityBean.lat}")

                        // || air.heWeather6?.get(0)?.status != "ok"
                        if (todayWeatherBean.status != "ok" ||hourWeather.status != "ok"
                            || lifestyleBean.heWeather6?.get(0)?.status != "ok"){
                            apiLoading.postValue(Event(false))
                            return@launch
                        }
                        val compoundDayHourDetailsWeatherData =
                            WeatherDataUtils.compoundDayHourDetailsWeatherData(
                                hourWeather, day,air,lifestyleBean, todayWeatherBean
                            )
                        _hourlyDetailsWeatherData.postValue(compoundDayHourDetailsWeatherData)
                    }
                    else -> {

                        val hourWeather = service.getHourlyWeather(cityBean.lon, cityBean.lat, 72)
                        val air = HeForecastAirBean()//service.getForecastAirData("${cityBean.lon},${cityBean.lat}")
                        // 获取生活指数
                        val lifestyleBean = service.getLifestyle("${cityBean.lon},${cityBean.lat}")

                        // || air.heWeather6?.get(0)?.status != "ok"
                        if (todayWeatherBean.status != "ok" ||  hourWeather.status != "ok"
                            || lifestyleBean.heWeather6?.get(0)?.status != "ok"){
                            apiLoading.postValue(Event(false))
                            return@launch
                        }

                        val compoundDayHourDetailsWeatherData =
                            WeatherDataUtils.compoundHDayHourDetailsWeatherData(
                                hourWeather, day,air,lifestyleBean,todayWeatherBean
                            )
                        _hourlyDetailsWeatherData.postValue(compoundDayHourDetailsWeatherData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                apiLoading.postValue(Event(false))
            }
        }


    }

    fun cancel(){
    }

}