package com.lnkj.cloudweather

import android.Manifest
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.viewModels
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.billy.cc.core.component.CC
import com.lnkj.cloudweather.custom.popup.AgreementPolicyPopup
import com.lnkj.cloudweather.databinding.SplashActivityBinding
import com.lnkj.cloudweather.util.SharedPreferencesUtil
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.database.WeatherDatabase
import com.lnkj.library_base.router.WeatherComponent
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.ConfirmPopupView
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.goToAppInfoPage
import com.mufeng.mvvmlib.utilcode.ext.permission.request
import com.mufeng.mvvmlib.utilcode.utils.ActivityUtils
import com.mufeng.mvvmlib.utilcode.utils.Preference
import com.mufeng.mvvmlib.utilcode.utils.context
import com.mufeng.mvvmlib.utilcode.utils.toast
import com.umeng.analytics.MobclickAgent
import kotlinx.coroutines.*

class SplashActivity : BaseVMActivity<SplashViewModel, SplashActivityBinding>(),
    CoroutineScope by MainScope(), AgreementPolicyPopup.AgreementPolicyListener {
    override val viewModel: SplashViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.splash_activity

    private var countDownTimer: CountDownTimer? = null

    private var isLocation by Preference("isLocation", true)
    private var confirmPopupView: ConfirmPopupView? = null

    override fun initView(savedInstanceState: Bundle?) {
        val isFirst = SharedPreferencesUtil.getSpBoolean(this, "isFirst")
        if (isFirst) {
            XPopup.Builder(this)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asCustom(AgreementPolicyPopup(this, this))
                .show();
        } else {
            requestPermissions()
        }
    }

    fun requestPermissions() {
        val permissions = arrayListOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
//            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
        )

        request(
            *permissions.toTypedArray()
        ) {
            onDenied {
                showPermissionSettingDialog()
            }
            onGranted {
                startLocation()
            }
            onNeverAskAgain {
                showPermissionSettingDialog()
            }
            onShowRationale {
                it.retry()
            }
        }
    }

    fun showPermissionSettingDialog() {
        if (confirmPopupView == null) {
            confirmPopupView = XPopup.Builder(this@SplashActivity)
                .asConfirm("", "必要权限未被允许, 云端天气将无法为您服务, 请在设置中打开权限",
                    "关闭", "打开设置", {
                        goToAppInfoPage()
                    }, {
                        finish()
                    }, false
                );
        }
        if (!confirmPopupView!!.isShow) {
            confirmPopupView!!.show()
        }
    }

    fun startLocation() {
        countDownTimer = object : CountDownTimer(1000, 2000) {
            override fun onFinish() {
                if (isLocation) {
                    getCurrentLocation { locationSuccess(it) }
                } else
                    CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                        .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_MAIN_ACTIVITY)
                        .build()
                        .callAsync() { cc, result ->
                            finish()
                        }
            }

            override fun onTick(millisUntilFinished: Long) {

            }

        }
        countDownTimer?.start()
    }

    override fun initData() {

    }

    private fun getCurrentLocation(success: (AMapLocation) -> Unit) {
        val mLocationClient = AMapLocationClient(context)
        val mLocationOption = AMapLocationClientOption()
        //高精度模式
        mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        //定位请求超时时间
        mLocationOption.httpTimeOut = 2000
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
                success(aMapLocation)
            } else {
                toast("定位失败，请重新定位")
            }
        }
    }

    //定位成功之后，构造Marker对象和数据
    private fun locationSuccess(location: AMapLocation) {

        try {
            launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
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
                        myCityBean.street = location.street
                    }
                    WeatherDatabase.get().myCityDao().deleteLocationCity()
                    WeatherDatabase.get().myCityDao().saveCity(myCityBean)
                }
                CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                    .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_MAIN_ACTIVITY)
                    .build()
                    .callAsync() { cc, result ->
                        finish()
                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onResume() {
        super.onResume()
        MobclickAgent.onResume(this);
    }

    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this);
    }

    override fun onCancel() {
        ActivityUtils.finishAllActivity()
    }

    override fun onOk() {
        SharedPreferencesUtil.setSpBoolean(this, "isFirst", false)
        requestPermissions()
    }
}
