package com.lnkj.weather.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/25 18:27
 * @描述
 */
object DateUtils {

    const val PATTERN_0 = "yyyy-MM-dd HH:mm:ss.SSS"
    const val PATTERN_1 = "yyyy-MM-dd HH:mm:ss"
    const val PATTERN_2 = "yyyy-MM-dd HH:mm"
    const val PATTERN_3 = "yyyy-MM-dd"
    const val PATTERN_4 = "yyyy/MM/dd HH:mm:ss"
    const val PATTERN_5 = "yyyy/MM/dd HH:mm"
    const val PATTERN_6 = "yyyy/MM/dd"
    const val PATTERN_7 = "yyyy年MM月dd日"
    const val PATTERN_8 = "yyyyMMdd"
    const val PATTERN_9 = "MM"
    const val PATTERN_10 = "dd"
    const val PATTERN_11 = "yyyy/MM"
    const val PATTERN_12 = "yyyy-MM"
    const val PATTERN_13 = "yyyy-MM-dd'T'HH:mm"
    const val PATTERN_14 = "MM/dd"
    const val PATTERN_15 = "HH:mm"
    const val PATTERN_16 = "HH"


    @SuppressLint("SimpleDateFormat")
    fun formatDateT(dateStr: String, pattern: String): String {
        val date = SimpleDateFormat(PATTERN_13).parse(dateStr)
        return SimpleDateFormat(pattern).format(date!!)
    }

    @SuppressLint("SimpleDateFormat")
    fun formatWeekT(dateStr: String): String {
        val date = SimpleDateFormat(PATTERN_13).parse(dateStr)
        val format: SimpleDateFormat
        var hintDate = ""
        //先获取年份
        val year = Integer.valueOf(SimpleDateFormat("yyyy").format(date))
        //获取一年中的第几天
        val day = Integer.valueOf(SimpleDateFormat("d").format(date))
        //获取当前年份 和 一年中的第几天
        val currentDate = Date(System.currentTimeMillis())
        val currentYear = Integer.valueOf(SimpleDateFormat("yyyy").format(currentDate))
        val currentDay = Integer.valueOf(SimpleDateFormat("d").format(currentDate))
        //计算 如果是去年的
        if (currentYear - year == 1) {
            //如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
            if (currentDay == 1) {
                val yearDay: Int = if (year % 400 == 0) {
                    366//世纪闰年
                } else if (year % 4 == 0 && year % 100 != 0) {
                    366//普通闰年
                } else {
                    365//平年
                }
                if (day == yearDay) {
                    hintDate = "昨天"
                }
            }
        } else {
            when {
                currentDay - day == 1 -> hintDate = "昨天"
                currentDay - day == 0 -> hintDate = "今天"
                currentDay - day == -1 -> hintDate = "明天"
                currentDay - day == -2 -> hintDate = "后天"
                else -> {
                    val weekDays = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    var weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1
                    if (weekIndex < 0) weekIndex = 0
                    hintDate = weekDays[weekIndex]
                }
            }
        }
        return if (hintDate.isEmpty()) {
            format = SimpleDateFormat("yyyy-MM-dd HH:mm")
            format.format(date)
        } else {
            hintDate
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getWeek(calendar: Calendar): String {
        var weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (weekIndex < 0) weekIndex = 0
        val weekDays = arrayListOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        return weekDays[weekIndex]
    }

    @SuppressLint("SimpleDateFormat")
    fun getWeekIndex(calendar: Calendar): Int {
        var weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1
        return weekIndex;
    }

    @SuppressLint("SimpleDateFormat")
    fun formatTime(date: Date, pattern: String = PATTERN_1): String {
        val df = SimpleDateFormat(pattern)
        return df.format(date)
    }

    /**
     * 获取当天零点的时间戳
     * @param time Long
     * @return Long 返回秒数
     */
    @SuppressLint("SimpleDateFormat")
    fun formatTime00(time: Long): Long {
        val date = Date(time * 1000)
        val format = SimpleDateFormat(PATTERN_3)
        val dateStr = "${format.format(date)} 00:00:00"
        return SimpleDateFormat(PATTERN_1).parse(dateStr)!!.time / 1000
    }

    /**
     * 判断当前时间是否是晚上
     * @return Boolean
     */
    @SuppressLint("SimpleDateFormat")
    fun isNight(): Boolean {
        val format = SimpleDateFormat("HH")
        val hour = format.format(Date()).toInt()
        return hour !in 7..18
    }

    /**
     * 比较当前时间
     * @param dateStr String
     * @return Int 返回秒
     */
    @SuppressLint("SimpleDateFormat")
    fun compareDate(dateStr: String, pattern: String = PATTERN_1): Int {
        val dateTime = Date().time
        val dateUp = SimpleDateFormat(pattern).parse(dateStr)?.time!!
        return (dateTime / 1000 - dateUp / 1000).toInt()
    }

    /**
     * 判断当前小时分钟是否在范围之类
     */
    @SuppressLint("SimpleDateFormat")
    fun isDayTime(startTimeStr: String, endTimeStr: String): Boolean {
        val format = SimpleDateFormat(PATTERN_15)
        val startTime = format.parse(startTimeStr).time
        val endTime = format.parse(endTimeStr).time
        val calendar = Calendar.getInstance()
        val nowDate =
            format.parse("${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}").time
        if (nowDate in startTime..endTime) {
            return true
        }
        return false
    }
}