package com.lnkj.weather.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.utils.AndroidUtil
import com.mufeng.mvvmlib.http.handler.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/13 21:50
 * @描述
 */
class MainViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(ApiService::class.java) }

    val versionInfo = MutableLiveData<String>()

    fun getUpdateVersionInfo(version: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = service.getVersion(version)
            versionInfo.postValue(result)
        }
    }

}