package com.lnkj.weather.ui.hotrank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.exception.BusinessException
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.http.bean.HotRankBean
import com.mufeng.mvvmlib.basic.Event
import com.mufeng.mvvmlib.http.handler.Request

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/31 19:58
 * @描述
 */
class HotRankViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(ApiService::class.java) }


    private val _hotRankData = MutableLiveData<MutableList<HotRankBean.Data>>()
    val hotRankData: LiveData<MutableList<HotRankBean.Data>>
        get() = _hotRankData

    fun getHotRankList() {
        apiDSL<HotRankBean> {
            onStart { true }
            onRequest {
                service.getHotRankList()
            }
            onResponse {
                if (it.status == 1){
                    _hotRankData.postValue(it.data)
                }else{
                    apiException.value = Event(BusinessException(it.status,it.info))
                }
            }
        }
    }

}