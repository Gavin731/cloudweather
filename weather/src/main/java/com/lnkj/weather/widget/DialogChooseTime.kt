package com.lnkj.weather.widget

import android.content.Context
import com.contrarywind.adapter.WheelAdapter
import com.lnkj.weather.R
import com.lxj.xpopup.core.CenterPopupView
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import kotlinx.android.synthetic.main.weather_dialog_choose_time.view.*


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/7 18:40
 * @描述
 */
class DialogChooseTime(context: Context,
                       private val isNight: Boolean,
                       private val currentTime: String,
                       private val listener: (time: String)-> Unit): CenterPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.weather_dialog_choose_time
    }

    private var hour: String = ""
    private var minute: String = ""

    override fun onCreate() {
        super.onCreate()

        val times = currentTime.split(":")
        hour = times[0]
        minute = times[1]

        // 小时
        val hourData = generateHourData(isNight)
        val index = hourData.indexOf(times[0])
        wheelViewHour.setCyclic(true)
        wheelViewHour.setItemsVisibleCount(3)
        wheelViewHour.adapter = ArrayWheelAdapter(hourData)
        wheelViewHour.currentItem = index
        wheelViewHour.setOnItemSelectedListener {
            hour = hourData[it]
        }

        // 分钟
        val minuteData = generateMinuteData()
        wheelViewMinute.setCyclic(true)
        wheelViewMinute.setItemsVisibleCount(3)
        wheelViewMinute.adapter = ArrayWheelAdapter(minuteData)
        wheelViewMinute.currentItem = minuteData.indexOf(times[1])
        wheelViewMinute.setOnItemSelectedListener {
            minute = minuteData[it]
        }

        rtv_cancel.clickWithTrigger {
            dismiss()
        }

        rtv_submit.clickWithTrigger {
            listener("${hour}:$minute")
            dismiss()
        }

    }

    private fun generateMinuteData(): List<String> {
        val list = arrayListOf<String>()
        list.add("00")
        list.add("01")
        list.add("02")
        list.add("03")
        list.add("04")
        list.add("05")
        list.add("06")
        list.add("07")
        list.add("08")
        list.add("09")
        list.add("10")
        list.add("11")
        list.add("12")
        list.add("13")
        list.add("14")
        list.add("15")
        list.add("16")
        list.add("17")
        list.add("18")
        list.add("19")
        list.add("20")
        list.add("21")
        list.add("22")
        list.add("23")
        list.add("24")
        list.add("25")
        list.add("26")
        list.add("27")
        list.add("28")
        list.add("29")
        list.add("30")
        list.add("31")
        list.add("32")
        list.add("33")
        list.add("34")
        list.add("35")
        list.add("36")
        list.add("37")
        list.add("38")
        list.add("39")
        list.add("40")
        list.add("41")
        list.add("42")
        list.add("43")
        list.add("44")
        list.add("45")
        list.add("46")
        list.add("47")
        list.add("48")
        list.add("49")
        list.add("50")
        list.add("51")
        list.add("52")
        list.add("53")
        list.add("54")
        list.add("55")
        list.add("56")
        list.add("57")
        list.add("58")
        list.add("59")
        return list
    }

    private fun generateHourData(isNight: Boolean): List<String> {
        val list = arrayListOf<String>()
        if (!isNight){
            list.add("06")
            list.add("07")
            list.add("08")
            list.add("09")
            list.add("10")
            list.add("11")
            list.add("12")
            list.add("13")
            list.add("14")
            list.add("15")
            list.add("16")
            list.add("17")
        }else{
            list.add("18")
            list.add("19")
            list.add("20")
            list.add("21")
            list.add("22")
            list.add("23")
            list.add("00")
            list.add("01")
            list.add("02")
            list.add("03")
            list.add("04")
            list.add("05")
        }
        return list
    }

    class ArrayWheelAdapter(items: List<String>) : WheelAdapter<String>{
        // items
        private var items: List<String> = items

        override fun getItem(index: Int): String {
            return if (index >= 0 && index < items.size) {
                items[index]
            } else ""
        }

        override fun getItemsCount(): Int {
            return items.size
        }

        override fun indexOf(o: String): Int {
            return items.indexOf(o)
        }
    }

}