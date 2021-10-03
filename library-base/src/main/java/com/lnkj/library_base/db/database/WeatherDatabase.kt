package com.lnkj.library_base.db.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.library_base.db.bean.CityWeather
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.db.dao.CityDao
import com.lnkj.library_base.db.dao.CityWeatherDao
import com.lnkj.library_base.db.dao.MyCityDao
import com.mufeng.mvvmlib.utilcode.utils.application

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 14:57
 * @描述
 */
@Database(entities = [CityBean::class, MyCityBean::class, CityWeather::class], version = 3)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun myCityDao(): MyCityDao
    abstract fun cityWeatherDao(): CityWeatherDao

    companion object {
        private var instance: WeatherDatabase? = null
            get() {
                if (field == null){
                    field = Room.databaseBuilder(
                        application,
                        WeatherDatabase::class.java, "weather_database")
                        .fallbackToDestructiveMigration()
                        .build();

                }
                return field
            }

        @Synchronized
        fun get(): WeatherDatabase {
            return instance!!
        }

    }

}