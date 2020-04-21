package com.lnkj.library_base.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnkj.library_base.db.bean.CityWeather

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/26 10:29
 * @描述
 */
@Dao
interface CityWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(cityWeather: CityWeather)

    @Query("SELECT * FROM CityWeather WHERE cityName = :cityName LIMIT 1")
    fun getWeatherByCity(cityName: String): CityWeather

}