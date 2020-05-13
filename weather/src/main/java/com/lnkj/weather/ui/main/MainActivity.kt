package com.lnkj.weather.ui.main

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityMainBinding
import com.lnkj.weather.receiver.DateChangeReceiver
import com.lnkj.weather.ui.air.AirQualityFragment
import com.lnkj.weather.ui.hour.HourDetailsFragment
import com.lnkj.weather.ui.realweather.RealTimeWeatherFragment
import com.lnkj.weather.widget.popup.UpdateAppPopup
import com.lxj.xpopup.XPopup
import com.mufeng.mvvmlib.basic.adapter.BaseViewPagerAdapter
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.http.handler.Request
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.utils.ActivityUtils


class MainActivity : BaseVMActivity<MainViewModel, WeatherActivityMainBinding>() {

    override val viewModel: MainViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_main

    private lateinit var receiver: DateChangeReceiver

    override fun initView(savedInstanceState: Bundle?) {
        showUpdateAppPopup("1.0.1", "")
        Request.init(applicationContext, "http://tq.dt357.cn/") {
            okHttp {
                it
            }
            retrofit {
                it
            }
        }

        val fragments = arrayListOf<Fragment>()
        fragments.add(RealTimeWeatherFragment())
        fragments.add(HourDetailsFragment())
        fragments.add(AirQualityFragment())

        val adapter = BaseViewPagerAdapter(supportFragmentManager, fragments)
        binding.viewPager.setScroll(true)
        binding.viewPager.adapter = adapter

        binding.viewPager.offscreenPageLimit = 3

        binding.bbl.setViewPager(binding.viewPager)

        binding.bbiItemRealTime.clickWithTrigger {
            binding.bbl.currentItem = 0
        }

        binding.bbiItemHourDetails.clickWithTrigger {
            binding.bbl.currentItem = 1
            LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
                .post(true)
        }

        binding.bbiItemAir.clickWithTrigger {
            binding.bbl.currentItem = 2
            LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
                .post(true)
        }
        receiver = DateChangeReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun initData() {
    }

    fun selectFragment(index: Int) {
        LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
            .post(true)
        binding.viewPager.currentItem = index

    }

    override fun onPause() {
        super.onPause()
        LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
            .post(true)
    }

    override fun onStop() {
        super.onStop()
        LiveEventBus.get(EventKey.EVENT_STOP_VOICE_ANNOUNCEMENTS)
            .post(true)
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean { //判断用户是否点击了“返回键”
        if (keyCode == KeyEvent.KEYCODE_BACK) { //与上次点击返回键时刻作差
            if (System.currentTimeMillis() - mExitTime > 2000) { //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis()
            } else { //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                ActivityUtils.finishAllActivity()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun showUpdateAppPopup(version: String, downloadUrl: String) {
        XPopup.Builder(this)
            .dismissOnBackPressed(false)
            .dismissOnTouchOutside(false)
            .asCustom(UpdateAppPopup(this, version, downloadUrl))
            .show();
    }

}