package com.lnkj.weather.ui.air

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.http.bean.HeAirQualityBean
import com.mufeng.mvvmlib.basic.Event
import com.mufeng.mvvmlib.http.handler.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 15:51
 * @描述
 */
class AirQualityViewModel : BaseViewModel() {
    private val service by lazy { Request.apiService(ApiService::class.java) }

    val title = MutableLiveData<String>()

    private val _airData = MutableLiveData<HeAirQualityBean>()
    val airData: LiveData<HeAirQualityBean>
        get() = _airData

    fun getAirData(cityBean: MyCityBean) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                apiLoading.postValue(Event(true))
                val airData = service.getAirData("${cityBean.lon},${cityBean.lat}")
                if (airData.heWeather6?.get(0)?.status != "ok"){
                    apiLoading.postValue(Event(false))
                    return@launch
                }
                _airData.postValue(airData)
            } catch (e: Exception) {
                e.printStackTrace()
                apiLoading.postValue(Event(false))
            }
        }
    }

}