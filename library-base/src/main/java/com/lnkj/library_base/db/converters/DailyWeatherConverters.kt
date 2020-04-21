package com.lnkj.library_base.db.converters

import androidx.room.TypeConverter
import com.lnkj.library_base.db.bean.DailyWeather
import com.mufeng.mvvmlib.utilcode.ext.fromJsonToList
import com.mufeng.mvvmlib.utilcode.ext.toJson

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/26 9:37
 * @描述
 */
class DailyWeatherConverters {
    @TypeConverter
    fun stringToObject(value: String): MutableList<DailyWeather>?{
        return value.fromJsonToList()
    }
    @TypeConverter
    fun objectToString(list: MutableList<DailyWeather>): String{
        return list.toJson()
    }
}