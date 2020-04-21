package com.lnkj.weather.ui.city

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.google.android.flexbox.FlexDirection
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.library_base.widget.SafeFlexboxLayoutManager
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityChooseCityBinding
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.getString
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/24 19:01
 * @描述
 */
class ChooseCityActivity : BaseVMActivity<CityViewModel, WeatherActivityChooseCityBinding>(){

    companion object {
        fun launch(context: Context, title: String, name: String, isChina: Boolean, level: Int){
            context.startActivity<ChooseCityActivity>(
                "title" to title,
                "name" to name,
                "isChina" to isChina,
                "level" to level
            )
        }
    }

    private var name: String = ""
    private var level: Int = 1
    private var isChina: Boolean = false

    private lateinit var adapter: CityAdapter
    private val data = arrayListOf<CityBean>()

    override val viewModel: CityViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_choose_city

    override fun initView(savedInstanceState: Bundle?) {

        binding.vm = viewModel
        val title = intent.getString("title")
        name = intent.getString("name")
        isChina = intent.getBooleanExtra("isChina", false)
        level = intent.getIntExtra("level", 1)

        viewModel.title.postValue(title)
        viewModel.name.postValue(name)


        adapter = CityAdapter(data, level)
        binding.rvHotCity.layoutManager = SafeFlexboxLayoutManager(this, FlexDirection.ROW)
        binding.rvHotCity.adapter = adapter
        binding.rvHotCity.removeAllAnimation()

        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position)
            when(level){
                2 -> {
                    launch(this, "选择城市", item.countryName, false, 4)
                }
                4 -> {
                    if (isChina){
                        launch(this, "选择地区", item.cityName, false, 5)
                    }else {
                        viewModel.saveChooseCity(item)
                    }
                }
                5 -> {
                    viewModel.saveChooseCity(item)
                }
            }
        }


    }

    override fun initData() {

        when(level){
            1 -> {
                // 选择洲
            }
            2 -> {
                // 选择国家
                viewModel.getCountryList(name)
            }
            3 -> {
                // 选择省份

            }
            4 -> {
                // 选择城市
                if (isChina) {
                    viewModel.getChinaCityList(name)
                }else{
                    viewModel.getOverseasCityList(name)
                }
            }
            5 -> {
                // 选择地区
                viewModel.getChinaAreaList(name)
            }
        }

    }

    override fun startObserve() {
        super.startObserve()
        viewModel.apply {
            countryList.observe(this@ChooseCityActivity){
                data.clear()
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }

            overseasCityList.observe(this@ChooseCityActivity){
                data.clear()
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }

            chinaCityList.observe(this@ChooseCityActivity){
                data.clear()
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }
            chinaAreaList.observe(this@ChooseCityActivity){
                data.clear()
                data.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}