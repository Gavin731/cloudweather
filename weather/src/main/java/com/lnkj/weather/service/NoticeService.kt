package com.lnkj.weather.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.lnkj.weather.R
import com.lnkj.weather.receiver.DateChangeReceiver
import com.lnkj.weather.ui.main.MainActivity


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 18:28
 * @描述
 */
class NoticeService : Service() {

    private lateinit var receiver: DateChangeReceiver

    override fun onCreate() {
        super.onCreate()
        // 获取服务通知
//        val notification = createForegroundNotification()
//        //将服务置于启动状态 ,NOTIFICATION_ID指的是创建的通知的ID
//        startForeground(1, notification)
        receiver = DateChangeReceiver()
        registerReceiver(receiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        // 移除通知
        stopForeground(true);
        unregisterReceiver(receiver)
        super.onDestroy()

    }

    /**
     * 创建服务通知
     */
    private fun createForegroundNotification(): Notification? {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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
            NotificationCompat.Builder(this, notificationChannelId)
        //通知小图标
        builder.setSmallIcon(R.drawable.weather_logo_notice)
        //通知标题
        builder.setContentTitle("ContentTitle")
        //通知内容
        builder.setContentText("ContentText")
        //设定通知显示的时间
        builder.setWhen(System.currentTimeMillis())
        //设定启动的内容
        val activityIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)
        //创建通知并返回
        return builder.build()
    }
}