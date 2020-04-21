package com.lnkj.library_base.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.lnkj.library_base.db.bean.CityBean

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 14:25
 * @描述
 */
@Dao
interface CityDao {

    // 插入数据
    @Insert(onConflict = REPLACE)
    fun save(cityBean: CityBean)

    @Insert(onConflict = REPLACE)
    fun save(cityBeans: List<CityBean>)

    // 查询州
    @Query("SELECT * FROM CityBean GROUP BY continentName")
    fun searchContinentList(): LiveData<MutableList<CityBean>>
    // 查询国家
    @Query("SELECT * FROM CityBean WHERE continentName = :continentName AND countryName != '中国' GROUP BY countryName")
    fun searchCountryList(continentName: String): MutableList<CityBean>
    // 查询海外国家城市
    @Query("SELECT * FROM CityBean WHERE countryName = :countryName")
    fun searchOverseasCityList(countryName: String): MutableList<CityBean>
    @Query("SELECT * FROM CityBean WHERE countryName != '中国'")
    fun searchOverseasCities(): MutableList<CityBean>

    // 查询中国省份
    @Query("SELECT * FROM CityBean WHERE countryName = '中国' GROUP BY provinceName")
    fun searchChinaProvinceList(): MutableList<CityBean>
    // 查询中国省份对应的城市
    @Query("SELECT * FROM CityBean WHERE provinceName = :provinceName GROUP BY cityName")
    fun searchChinaCityList(provinceName: String): MutableList<CityBean>
    // 查询中国城市对应的县区
    @Query("SELECT * FROM CityBean WHERE cityName = :cityName GROUP BY counties")
    fun searchChinaCountiesList(cityName: String): MutableList<CityBean>

    @Query("SELECT * FROM CityBean WHERE countryName = '中国'")
    fun searchChinaCities(): MutableList<CityBean>

    // 查询热门城市
    @Query("SELECT * FROM CityBean WHERE isHot = :isHot")
    fun getHotCityList(isHot: Boolean = true): MutableList<CityBean>
}