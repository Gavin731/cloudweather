package com.lnkj.weather.ui.realweather

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.base.BaseViewModel
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityRealTimeWeatherShareBinding
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import com.mufeng.mvvmlib.utilcode.utils.toast
import com.mufeng.sociallibrary.Social
import com.mufeng.sociallibrary.config.PlatformType

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/3 8:55
 * @描述
 */
class RealTimeWeatherShareActivity : BaseVMActivity<BaseViewModel, WeatherActivityRealTimeWeatherShareBinding>() {
    private var bitmap: Bitmap? = null
    override val viewModel: BaseViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_real_time_weather_share


    override fun initView(savedInstanceState: Bundle?) {

        StatusBarUtils.addTranslucentView(this, binding.ivShareView)

        binding.icClose.clickWithTrigger { finish() }

        binding.llShareQq.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.QQ)) {
                toast("应用未安装")
                return@clickWithTrigger
            }

            Social.share(
                this,
                PlatformType.QQ,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareQzone.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.QQ_ZONE)) {
                toast("应用未安装")
                return@clickWithTrigger
            }

            Social.share(
                this,
                PlatformType.QQ_ZONE,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareWechat.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.WEIXIN)) {
                toast("应用未安装")
                return@clickWithTrigger
            }

            Social.share(
                this,
                PlatformType.WEIXIN,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        binding.llShareWechatTimeline.clickWithTrigger {
            if (!Social.available4Plat(PlatformType.WEIXIN_CIRCLE)) {
                toast("应用未安装")
                return@clickWithTrigger
            }

            Social.share(
                this,
                PlatformType.WEIXIN_CIRCLE,
                img = bitmap,
                onSuccess = {type ->
                    toast("分享成功")
                },
                onError = {type, errorCode, errorMsg ->
                    toast("$type:$errorCode == $errorMsg")
                },
                onCancel = {
                    toast("取消分享")
                }
            )
        }

        LiveEventBus.get("event_share_real_time_weather", Bitmap::class.java)
            .observeSticky(this, Observer {
                binding.ivShareView.setImageBitmap(it)
                this.bitmap = it
            })

    }

    override fun initData() {
    }
}