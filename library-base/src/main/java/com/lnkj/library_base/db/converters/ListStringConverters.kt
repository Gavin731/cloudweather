package com.lnkj.library_base.db.converters

import androidx.room.TypeConverter
import com.mufeng.mvvmlib.utilcode.ext.fromJsonToList
import com.mufeng.mvvmlib.utilcode.ext.toJson

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/27 17:53
 * @描述
 */
class ListStringConverters {
    @TypeConverter
    fun stringToObject(value: String): MutableList<String>?{
        return value.fromJsonToList()
    }
    @TypeConverter
    fun objectToString(list: MutableList<String>): String{
        return list.toJson()
    }
}