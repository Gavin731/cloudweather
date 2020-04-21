package com.lnkj.library_base.db.converters

import androidx.room.TypeConverter
import com.lnkj.library_base.db.bean.HourlyWeather
import com.mufeng.mvvmlib.utilcode.ext.fromJsonToList
import com.mufeng.mvvmlib.utilcode.ext.toJson

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/26 9:37
 * @描述
 */
class HourlyWeatherConverters {
    @TypeConverter
    fun stringToObject(value: String): MutableList<HourlyWeather>?{
        return value.fromJsonToList()
    }
    @TypeConverter
    fun objectToString(list: MutableList<HourlyWeather>): String{
        return list.toJson()
    }
}