package com.lnkj.library_base.db.converters

import androidx.room.TypeConverter
import com.lnkj.library_base.db.bean.DressLifeStyle
import com.mufeng.mvvmlib.utilcode.ext.fromJson
import com.mufeng.mvvmlib.utilcode.ext.toJson

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/26 9:37
 * @描述
 */
class DressLifeStyleConverters {
    @TypeConverter
    fun stringToObject(value: String): DressLifeStyle?{
        return value.fromJson()
    }
    @TypeConverter
    fun objectToString(list: DressLifeStyle): String{
        return list.toJson()
    }
}