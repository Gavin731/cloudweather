package com.lnkj.weather.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.library_base.db.bean.toCityBean
import com.lnkj.library_base.db.bean.toMyCityBean
import com.lnkj.library_base.db.database.WeatherDatabase
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.http.bean.HeWeatherCityBean
import com.lnkj.weather.ui.main.MainActivity
import com.mufeng.mvvmlib.http.handler.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 18:26
 * @描述
 */
class CityViewModel : BaseViewModel() {

    private val service by lazy { Request.apiService(ApiService::class.java) }

    val title = MutableLiveData<String>()
    val name = MutableLiveData<String>()

    // 热门城市
    private val _hotCityList = MutableLiveData<MutableList<CityBean>>()
    val hotCityList: LiveData<MutableList<CityBean>>
        get() = _hotCityList

    fun getHotCityList(isLocation: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val hotCities = WeatherDatabase.get().cityDao().getHotCityList()
            val comparator = Collator.getInstance(Locale.CHINA)
            // 排序实现
            hotCities.sortWith(Comparator {e1,e2->
                return@Comparator comparator.compare(e1.cityName, e2.cityName)
            })
            if (isLocation) {
                val locationCity =
                    WeatherDatabase.get().myCityDao().searchLocationCity()?.toCityBean()
                locationCity?.isLocation = true
                if (locationCity != null) {
                    hotCities.add(0, locationCity!!)
                }
            }

            _hotCityList.postValue(hotCities)
        }
    }

    // 国内省份
    val chinaProvinceList = MutableLiveData<MutableList<CityBean>>()

    // 获取洲
//    val continentList = WeatherDatabase.get().cityDao().searchContinentList()

    fun getProvinceList() {
        viewModelScope.launch(Dispatchers.IO) {
            val province = WeatherDatabase.get().cityDao().searchChinaProvinceList()
            val comparator = Collator.getInstance(Locale.CHINA)
            // 排序实现
            province.sortWith(Comparator {e1,e2->
                return@Comparator comparator.compare(e1.provinceName, e2.provinceName)
            })
            chinaProvinceList.postValue(province)
        }
    }

    // 搜索城市
    private val _searchCityList = MutableLiveData<MutableList<CityBean>>()
    val searchCityList: LiveData<MutableList<CityBean>>
        get() = _searchCityList

    fun searchCityList(location: String) {
        apiDSL<HeWeatherCityBean> {
            onStart { false }
            onRequest { service.searchCity(location) }
            onResponse {
                if (it.heWeather6.first().status == "ok") {
                    _searchCityList.postValue(it.heWeather6.first().basic)
                } else {
                    when (it.heWeather6.first().status) {
                        "invalid key" -> {
                            toastEvent("无效的key")
                        }
                        "invalid key type" -> {
                            toastEvent("你输入的key不适用于当前获取数据的方式")
                        }
                        "no data for this location" -> {
                            toastEvent("该城市/地区没有你所请求的数据")
                        }
                        "unknown location" -> {
                            toastEvent("没有你查询的这个地区，或者地区名称错误")
                        }
                    }
                }
            }
        }
    }

    /**获取国家列表**/
    private val _countryList = MutableLiveData<MutableList<CityBean>>()
    val countryList: LiveData<MutableList<CityBean>>
        get() = _countryList

    fun getCountryList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = WeatherDatabase.get().cityDao().searchCountryList(name)
            _countryList.postValue(cityList)
        }
    }
    /**获取国家列表**/
    /**获取海外城市列表**/
    private val _overseasCityList = MutableLiveData<MutableList<CityBean>>()
    val overseasCityList: LiveData<MutableList<CityBean>>
        get() = _overseasCityList

    fun getOverseasCityList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = WeatherDatabase.get().cityDao().searchOverseasCityList(name)
            _overseasCityList.postValue(cityList)
        }
    }
    /**获取海外城市列表**/
    /**获取国内城市列表**/
    private val _chinaCityList = MutableLiveData<MutableList<CityBean>>()
    val chinaCityList: LiveData<MutableList<CityBean>>
        get() = _chinaCityList

    fun getChinaCityList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = WeatherDatabase.get().cityDao().searchChinaCityList(name)
            val comparator = Collator.getInstance(Locale.CHINA)
            // 排序实现
            cityList.sortWith(Comparator {e1,e2->
                return@Comparator comparator.compare(e1.cityName, e2.cityName)
            })
            _chinaCityList.postValue(cityList)
        }
    }
    /**获取国内城市列表**/
    /**获取国内地区列表**/
    private val _chinaAreaList = MutableLiveData<MutableList<CityBean>>()
    val chinaAreaList: LiveData<MutableList<CityBean>>
        get() = _chinaAreaList

    fun getChinaAreaList(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = WeatherDatabase.get().cityDao().searchChinaCountiesList(name)
            val cityOList = mutableListOf<CityBean>()
            cityOList.addAll(cityList.filter { it.counties != name })
            val comparator = Collator.getInstance(Locale.CHINA)
            // 排序实现
            cityOList.sortWith(Comparator {e1,e2->
                return@Comparator comparator.compare(e1.counties, e2.counties)
            })
            cityOList.add(0,cityList.first { it.counties == name })
            _chinaAreaList.postValue(cityOList)
        }
    }

    /**获取国内地区列表**/

    fun saveChooseCity(item: CityBean) {
        viewModelScope.launch(Dispatchers.IO) {
            var myCityBean = WeatherDatabase.get().myCityDao().searchCityByName(item.counties)
            if (myCityBean == null) {
                myCityBean = item.toMyCityBean()
            }
            WeatherDatabase.get().myCityDao().saveCity(myCityBean)
            withContext(Dispatchers.Main) {
                startActivity(MainActivity::class.java, true)
            }
            // 通知首页添加或选中城市
            LiveEventBus.get(EventKey.EVENT_CHOOSE_ACC_CITY)
                .post(myCityBean)
        }
    }


}