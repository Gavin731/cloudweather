package com.lnkj.cloudweather

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.library_base.db.database.WeatherDatabase
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 15:38
 * @描述
 */
class DatabaseInsertDataService : IntentService("DatabaseInsertDataService") {

    companion object {
        private val ACTION_INIT = "com.lnkj.cloudweather.action.DATABASE_INSERT_DATA"

        fun start(context: Context) {
            val intent = Intent(context, DatabaseInsertDataService::class.java)
            intent.action = ACTION_INIT
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (intent != null && ACTION_INIT == intent.action) {
            val chinaCityList = WeatherDatabase.get().cityDao().searchChinaCities()
            if (chinaCityList.isEmpty()) {
                saveChinaCityList()
            }
//            val overseasCityList = WeatherDatabase.get().cityDao().searchOverseasCities()
//            if (overseasCityList.isEmpty()) {
//                saveOverseasCityList()
//            }
        }
    }

    /**
     * 保存海外城市
     */
    private fun saveOverseasCityList() {
        val cityList = arrayListOf<CityBean>()
        try {
            val fileInputStream = resources.openRawResource(R.raw.world_top_city_list)
            val scanner = Scanner(fileInputStream, "GBK")
            while (scanner.hasNextLine()) {
                val lines = scanner.nextLine().split(",")
                val bean = CityBean(
                    provinceName = lines[3],
                    cid = lines[0],
                    countryName = lines[2],
                    lat = lines[4],
                    counties = lines[3],
                    lon = lines[5],
                    cityName = lines[3],
                    continentName = lines[1]
                )
                cityList.add(bean)
            }

            WeatherDatabase.get().cityDao().save(cityList)

            fileInputStream.close()
            scanner.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 保存国内城市
     */
    private fun saveChinaCityList() {
        val cityList = arrayListOf<CityBean>()
        try {
            val fileInputStream = resources.openRawResource(R.raw.china_city_list)
            val scanner = Scanner(fileInputStream, "GBK")
            while (scanner.hasNextLine()) {
                val lines = scanner.nextLine().split(",")
                val bean = CityBean(
                    provinceName = lines[3],
                    cid = lines[0],
                    countryName = lines[4],
                    lat = lines[5],
                    counties = lines[1],
                    lon = lines[6],
                    cityName = lines[2],
                    continentName = "亚洲",
                    isHot = false
                )
                if (bean.counties == "北京" || bean.counties == "上海"
                    || bean.counties == "广州" || bean.counties == "深圳" ||
                    bean.counties == "杭州" || bean.counties == "南京" || bean.counties == "武汉") {
                    bean.isHot = true
                }
                cityList.add(bean)
            }

            WeatherDatabase.get().cityDao().save(cityList)

            fileInputStream.close()
            scanner.close()

            // 保存海外城市
//            saveOverseasCityList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}