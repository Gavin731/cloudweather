package com.lnkj.weather.ui.city.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.database.WeatherDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 9:26
 * @描述
 */
class AddCityViewModel : BaseViewModel() {

    private val myCityDao by lazy { WeatherDatabase.get().myCityDao() }

    private val _cityListData = MutableLiveData<MutableList<MyCityBean>>()
    val cityListDataL: LiveData<MutableList<MyCityBean>>
        get() = _cityListData

    fun getCityList(isLocation: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            val cityList = withContext(Dispatchers.IO) {
                if (isLocation) {
                    myCityDao.searchCityList()
                }else{
                    myCityDao.searchCityListByNoLocation()
                }
            }
            _cityListData.postValue(cityList)
        }
    }

    fun delCity(item: MyCityBean) {
        viewModelScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                myCityDao.deleteCity(item.cid!!)
            }
        }
    }

}