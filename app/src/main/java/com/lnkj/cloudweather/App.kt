package com.lnkj.cloudweather

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.gu.toolargetool.TooLargeTool
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mufeng.mvvmlib.utilcode.ext.initLogger
import com.mufeng.mvvmlib.utilcode.utils.ActivityLifecycleCallback
import com.mufeng.mvvmlib.utilcode.utils.context
import com.mufeng.mvvmlib.utilcode.utils.initContext
import com.mufeng.sociallibrary.Social
import com.mufeng.sociallibrary.config.PlatformType
import com.mufeng.sociallibrary.entity.platform.CommPlatConfigBean
import com.umeng.commonsdk.UMConfigure

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/19 16:27
 * @描述
 */
class App : Application(){

    companion object {
        lateinit var INSTANCE: App
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
        //友盟
//        UMConfigure.init(this, "5e9d7ba9dbc2ec07ad295668", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"");
        //设置组件化的Log开关
        UMConfigure.setLogEnabled(true);
        //设置日志加密
        UMConfigure.setEncryptEnabled(true);

        TooLargeTool.startLogging(this)
        initContext(this)
        initLogger(true)
        //CC
        CC.enableVerboseLog(true)
        CC.enableDebug(true)
        DatabaseInsertDataService.start(this)
//        LocationService.start(this)

        registerActivityLifecycleCallbacks(ActivityLifecycleCallback())

        Social.init(
            this,
            CommPlatConfigBean(PlatformType.WEIXIN, "wxc75fff41b8822e40"),  // 微信key
            CommPlatConfigBean(PlatformType.QQ, appkey = "101867668")// qqkey
        )

        LiveEventBus.config()
            .lifecycleObserverAlwaysActive(false)
        // 讯飞语音初始化
        SpeechUtility.createUtility(context, SpeechConstant.APPID +"=5e7308e4")

    }
}