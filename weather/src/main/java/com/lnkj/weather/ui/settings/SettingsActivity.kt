package com.lnkj.weather.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.app.NotificationManagerCompat
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivitySettingsBinding
import com.lnkj.weather.widget.DialogChooseTime
import com.lxj.xpopup.XPopup
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.versionName
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.Preference
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 16:15
 * @描述
 */
class SettingsActivity: BaseVMActivity<SettingsViewModel, WeatherActivitySettingsBinding>() {
    override val viewModel: SettingsViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_settings

    private var isNotice by Preference("isNotice", false)
    private var isLocation by Preference("isLocation", true)

    private var nightTime by Preference("night_time","18:00")
    private var morningTime by Preference("morning_time","06:00")

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = viewModel
        viewModel.version.value = "V${versionName}"

        StatusBarUtils.addTranslucentView(this, binding.toolbar)

        binding.switchLocation.isChecked = isLocation
        binding.switchNotice.isChecked = isNotice

        binding.tvMorningTime.text = morningTime
        binding.tvNightTime.text = nightTime

        initNoticeView(isNotice)
        binding.switchLocation.setOnCheckedChangeListener { _, isChecked ->
            isLocation = isChecked
            LiveEventBus.get(EventKey.EVENT_CHANGE_AUTO_LOCATION_STATUS)
                .post(isLocation)
        }

        binding.switchNotice.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                if (!NotificationManagerCompat.from(this).areNotificationsEnabled()){
                    XPopup.Builder(this)
                        .asConfirm("", "通知权限未开启, 无法收到天气推送, 请前往设置", "取消","设置",{
                            binding.switchNotice.isChecked = false
                            isNotice = false
                            goToSetting()
                        },{
                            binding.switchNotice.isChecked = false
                            isNotice = false
                        }, false)
                        .show()

                }else{
                    isNotice = isChecked
                    initNoticeView(isNotice)
                }
            }else{
                isNotice = isChecked
                initNoticeView(isNotice)
            }

        }

        binding.llMorningTime.clickWithTrigger {

            val dialog = DialogChooseTime(this, false, morningTime){
                morningTime = it
                binding.tvMorningTime.text = morningTime
            }

            XPopup.Builder(this)
                .asCustom(dialog)
                .show()
        }

        binding.llNightTime.clickWithTrigger {

            val dialog = DialogChooseTime(this, true, nightTime){
                nightTime = it
                binding.tvNightTime.text = nightTime
            }

            XPopup.Builder(this)
                .asCustom(dialog)
                .show()
        }

    }

    private fun initNoticeView(isChecked: Boolean){
        if (isChecked){
            binding.llNoticeView.visible()
        }else{
            binding.llNoticeView.gone()
        }
    }

    override fun initData() {
    }

    private fun goToSetting() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT >= 26 -> { // android 8.0引导
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
            }
            Build.VERSION.SDK_INT >= 21 -> { // android 5.0-7.0
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)
            }
            else -> { //其它
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                intent.data = Uri.fromParts("package", packageName, null)
            }
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}