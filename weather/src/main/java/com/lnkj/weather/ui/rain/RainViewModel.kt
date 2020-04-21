package com.lnkj.weather.ui.rain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.exception.BusinessException
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.http.bean.MinutelyRainfallBean
import com.mufeng.mvvmlib.basic.Event
import com.mufeng.mvvmlib.http.handler.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/6 15:34
 * @描述
 */
class RainViewModel : BaseViewModel() {
    private val service by lazy { Request.apiService(ApiService::class.java) }

    val title = MutableLiveData<String>()

    val rainTitle = MutableLiveData<String>()

    val rainType = MutableLiveData<String>()

    val publishDate = MutableLiveData<String>()

    private val _rainListData = MutableLiveData<MinutelyRainfallBean>()
    val rainListData: LiveData<MinutelyRainfallBean>
        get() = _rainListData

    fun getRainList(cityBean: MyCityBean) {
        viewModelScope.launch(Dispatchers.Main) {
            try {

                val rainList = withContext(Dispatchers.IO) {
                    service.getCaiYunMinutelyRainfall(cityBean.lon, cityBean.lat)
                }

                _rainListData.postValue(rainList)

            } catch (e: Exception) {
                e.printStackTrace()
                apiException.postValue(Event(BusinessException(10001, "获取降雨量数据失败")))
            }
        }
    }


}