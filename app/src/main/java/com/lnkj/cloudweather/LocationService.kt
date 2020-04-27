package com.lnkj.cloudweather

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.database.WeatherDatabase
import com.mufeng.mvvmlib.utilcode.ext.loge
import com.mufeng.mvvmlib.utilcode.utils.context
import com.mufeng.mvvmlib.utilcode.utils.toast

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 15:38
 * @描述
 */
class LocationService : Service() {

    companion object {
        private val ACTION_INIT = "com.lnkj.cloudweather.action.LOCATION_CURRENT_SERVICE"

        fun start(context: Context) {
            val intent = Intent(context, LocationService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        getCurrentLocation { locationSuccess(it) }
    }

    private fun getCurrentLocation(success: (AMapLocation) -> Unit) {
        val mLocationClient = AMapLocationClient(context)
        val mLocationOption = AMapLocationClientOption()
        //高精度模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //定位请求超时时间
        mLocationOption.httpTimeOut = 50000
        // 关闭缓存机制
        mLocationOption.isLocationCacheEnable = false
        // 设置是否只定位一次
        mLocationOption.isOnceLocation = true
        //设置参数
        mLocationClient.setLocationOption(mLocationOption)
        // 启动定位
        mLocationClient.startLocation()
        //定位监听
        mLocationClient.setLocationListener { aMapLocation ->
            //定位成功之后取消定位
            mLocationClient.stopLocation()
            if (aMapLocation != null && aMapLocation.errorCode == 0) {
                loge { "定位成功" }
                success(aMapLocation)
            } else {
                toast("定位失败，请重新定位")
            }
        }
    }

    //定位成功之后，构造Marker对象和数据
    private fun locationSuccess(location: AMapLocation) {

        var myCityBean = WeatherDatabase.get().myCityDao().searchLocationCity()
        if (myCityBean == null) {
            myCityBean = MyCityBean(
                1,
                location.province,
                location.adCode,
                location.country,
                location.latitude.toString(),
                location.district,
                location.longitude.toString(),
                location.city,
                location.street,
                "",
                1,
                System.currentTimeMillis()
            )
        } else {
            myCityBean.provinceName = location.province
            myCityBean.cid = location.adCode
            myCityBean.countryName = location.country
            myCityBean.lat = location.latitude.toString()
            myCityBean.counties = location.district
            myCityBean.lon = location.longitude.toString()
            myCityBean.cityName = location.city
            myCityBean.street=location.street
        }
        WeatherDatabase.get().myCityDao().saveCity(myCityBean)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}