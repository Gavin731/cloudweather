package com.mufeng.mvvmlib.util

/**
 * @ClassName: WeatherDayTimeManager
 * @Description:天气是否为白天
 * @Author: Pekon
 * @CreateDate: 2020/4/30 17:10
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/30 17:10
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class WeatherDayTimeManager {

    private var isDayTime:Boolean=true//保存是否为白天

    companion object{
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED){
            WeatherDayTimeManager()
        }
    }

    fun setIsDayTime(isDayTime:Boolean){
        this.isDayTime=isDayTime
    }

    fun getIsDayTime():Boolean{
        return isDayTime
    }

}