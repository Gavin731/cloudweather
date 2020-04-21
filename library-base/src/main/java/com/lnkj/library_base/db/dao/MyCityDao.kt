package com.lnkj.library_base.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lnkj.library_base.db.bean.MyCityBean

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 14:33
 * @描述
 */
@Dao
interface MyCityDao {

    // 保存或修改定位城市
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCity(myCityBean: MyCityBean)

    @Query("DELETE FROM MyCityBean WHERE isLocation = 1")
    fun deleteLocationCity()

    // 查询我的城市列表
    @Query("SELECT * FROM MyCityBean ORDER BY isLocation DESC, addTime DESC")
    fun searchCityList(): MutableList<MyCityBean>

    @Query("SELECT * FROM MyCityBean WHERE isLocation = 0 ORDER BY addTime DESC")
    fun searchCityListByNoLocation(): MutableList<MyCityBean>

    @Query("SELECT * FROM MyCityBean WHERE isLocation = 1 LIMIT 1")
    fun searchLocationCity(): MyCityBean?

    @Query("SELECT * FROM MyCityBean WHERE counties = :name LIMIT 1")
    fun searchCityByName(name: String): MyCityBean?

    /**
     * 删除城市
     * @param cityId Int
     */
    @Query("DELETE FROM MyCityBean WHERE cid = :cityId")
    fun deleteCity(cityId: String)
}