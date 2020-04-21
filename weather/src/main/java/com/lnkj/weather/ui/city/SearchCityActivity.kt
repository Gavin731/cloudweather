package com.lnkj.weather.ui.city

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.library_base.widget.SafeFlexboxLayoutManager
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivitySearchCityBinding
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.Preference

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/23 18:26
 * @描述
 */
class SearchCityActivity : BaseVMActivity<CityViewModel, WeatherActivitySearchCityBinding>() {

    /**
     * 洲
     */
    private lateinit var continentAdapter: CityAdapter
    private val continentData = arrayListOf<CityBean>()
    /**
     * 城市
     */
    private lateinit var cityAdapter: CityAdapter
    private val cityData = arrayListOf<CityBean>()
    /**
     * 国内省份
     */
    private lateinit var provinceAdapter: CityAdapter
    private val provinceData = arrayListOf<CityBean>()

    /**
     * 搜索
     */
    private lateinit var searchAdapter: SearchAdapter
    private val data = arrayListOf<CityBean>()


    override val viewModel: CityViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_search_city

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = viewModel
    }

    override fun initData() {
        try {
            continentAdapter = CityAdapter(continentData, 1)
            cityAdapter = CityAdapter(cityData, 4)
            provinceAdapter = CityAdapter(provinceData, 3)

            binding.rvHotCity.layoutManager = SafeFlexboxLayoutManager(this, FlexDirection.ROW)
            binding.rvChinaCity.layoutManager = SafeFlexboxLayoutManager(this, FlexDirection.ROW)
            binding.rvOverseasCity.layoutManager = SafeFlexboxLayoutManager(this, FlexDirection.ROW)

            binding.rvHotCity.adapter = cityAdapter
            binding.rvChinaCity.adapter = provinceAdapter
            binding.rvOverseasCity.adapter = continentAdapter

            binding.rvHotCity.removeAllAnimation()
            binding.rvChinaCity.removeAllAnimation()
            binding.rvOverseasCity.removeAllAnimation()

            searchAdapter = SearchAdapter(data)
            binding.rvSearch.layoutManager = LinearLayoutManager(this)
            binding.rvSearch.adapter = searchAdapter
            binding.rvSearch.removeAllAnimation()

            binding.etSearch.doOnTextChanged { text, start, count, after ->
                val content = binding.etSearch.text.toString()
                if (content.isEmpty()) {
                    data.clear()
                    searchAdapter.notifyDataSetChanged()
                    binding.rvSearch.gone()
                } else {
                    viewModel.searchCityList(content)
                }
            }

            binding.etSearch.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val content = binding.etSearch.text.toString()
                    if (content.isEmpty()) {
                        data.clear()
                        searchAdapter.notifyDataSetChanged()
                        binding.rvSearch.gone()
                    } else {
                        viewModel.searchCityList(content)
                    }
                }
                false
            }

            continentAdapter.setOnItemClickListener { _, _, position ->
                val item = continentAdapter.getItem(position)
                ChooseCityActivity.launch(this, "选择国家", item.continentName, false, 2)
            }

            cityAdapter.setOnItemClickListener { _, _, position ->
                val item = cityAdapter.getItem(position)
                // 添加选择热门城市
                viewModel.saveChooseCity(item)
            }

            provinceAdapter.setOnItemClickListener { _, _, position ->
                val item = provinceAdapter.getItem(position)
                ChooseCityActivity.launch(this, "选择城市", item.provinceName, true, 4)
            }

            searchAdapter.setOnItemClickListener { _, _, position ->
                val item = searchAdapter.getItem(position)
                viewModel.saveChooseCity(item)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }


        val isLocation by Preference("isLocation",true)
        viewModel.getHotCityList(isLocation)
        viewModel.getProvinceList()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.hotCityList.observe(this) {
            cityData.clear()
            cityData.addAll(it)
            cityAdapter.notifyDataSetChanged()
        }

        viewModel.chinaProvinceList.observe(this) {
            provinceData.clear()
            provinceData.addAll(it)
            provinceAdapter.notifyDataSetChanged()
        }

//        viewModel.continentList.observe(this) {
//            continentData.clear()
//            continentData.addAll(it)
//            continentAdapter.notifyDataSetChanged()
//        }

        viewModel.searchCityList.observe(this) {
            binding.rvSearch.visible()
            data.clear()
            data.addAll(it)
            searchAdapter.notifyDataSetChanged()
        }
    }
}