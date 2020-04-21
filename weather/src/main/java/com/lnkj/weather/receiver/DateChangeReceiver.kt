package com.lnkj.weather.receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.database.WeatherDatabase
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.http.ApiService
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.DateUtils.PATTERN_15
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.mvvmlib.http.handler.Request
import com.mufeng.mvvmlib.utilcode.utils.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 19:21
 * @描述
 */
class DateChangeReceiver : BroadcastReceiver() {

    private var nightTime by Preference("night_time", "18:00")
    private var morningTime by Preference("morning_time", "06:00")
    private var isNotice by Preference("isNotice", false)
    private val service by lazy { Request.apiService(ApiService::class.java) }

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_TIME_TICK && isNotice) {
            LiveEventBus.get(EventKey.EVENT_MINUTE_CHANGE)
                .postDelay(true, 500)
            val times = DateUtils.formatTime(Date(), PATTERN_15).split(":")
            val nightTimes = nightTime.split(":")
            val morningTimes = morningTime.split(":")
            GlobalScope.launch(Dispatchers.IO) {
                // 获取定位城市
                val searchLocationCity = WeatherDatabase.get().myCityDao().searchLocationCity()
                // 获取15日天气
                // 获取24小时天气
                val weatherBean = service.getCaiYunWeather(searchLocationCity?.lon!!, searchLocationCity?.lat!!)
                val formatWeather = WeatherUtils.formatWeather(
                    weatherBean.result?.daily?.skycon08h20h?.get(0)?.value!!,
                    weatherBean.result?.daily?.skycon20h32h?.get(0)?.value!!
                )
                val currentHour = SimpleDateFormat(DateUtils.PATTERN_16).format(Date()).toInt()

                val nightWeather = WeatherUtils.formatWeather(
                    weatherBean.result?.hourly?.skycon?.get(0)?.value!!,
                    weatherBean.result?.hourly?.skycon?.get(23-currentHour)?.value!!
                )

                val t1 = weatherBean.result?.hourly?.temperature?.get(0)?.value!!.toInt()
                val t2 = weatherBean.result?.hourly?.temperature?.get(23-currentHour)?.value!!.toInt()

                withContext(Dispatchers.Main) {
                    if (times[0] == nightTimes[0] && times[1] == nightTimes[1]) {
                        // 推送晚间天气通知
                        val content =
                            "${searchLocationCity?.counties}晚间天气$formatWeather, 温度 ${t1}~${t2}°"
                        createForegroundNotification(context!!, content)
                    } else if (times[0] == morningTimes[0] && times[1] == morningTimes[1]) {
                        // 推送早间天气通知
                        val content =
                            "${searchLocationCity?.counties}今日天气$nightWeather, 温度 ${searchLocationCity?.temperature}"
                        createForegroundNotification(context!!, content)
                    }
                }

            }


        }
    }

    /**
     * 创建服务通知
     */
    private fun createForegroundNotification(context: Context, tip: String) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 唯一的通知通道的id.
        val notificationChannelId = "notification_channel_id_01"
        // Android8.0以上的系统，新建消息通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //用户可见的通道名称
            val channelName = "Foreground Service Notification"
            //通道的重要程度
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel =
                NotificationChannel(notificationChannelId, channelName, importance)
            notificationChannel.description = "Channel description"
            //LED灯
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            //震动
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notificationChannelId)
        //通知小图标
        builder.setSmallIcon(R.drawable.weather_logo_notice)
        //通知标题
        builder.setContentTitle("云端天气预报")
        builder.setAutoCancel(true)
        //通知内容
        builder.setContentText(tip)
        //设定通知显示的时间
        builder.setWhen(System.currentTimeMillis())
        //设定启动的内容
        val activityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        // 显示通知
        notificationManager.notify(1, builder.build())

    }
}