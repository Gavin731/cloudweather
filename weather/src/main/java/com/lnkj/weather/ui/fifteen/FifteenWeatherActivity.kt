package com.lnkj.weather.ui.fifteen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lnkj.library_base.db.bean.DailyWeather
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityFifteenWeatherBinding
import com.lnkj.weather.ui.realweather.RealTimeWeather15DailyListAdapter
import com.lnkj.weather.widget.zzweatherview.WeatherModel
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import java.util.*

/**
 * @author: 曾令贵
 * @description TODO
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 *  2021/10/5     曾令贵       v1.0.0        create
 **/
class FifteenWeatherActivity :
    BaseVMActivity<FifteenWeatherViewModel, WeatherActivityFifteenWeatherBinding>() {

    // 15天天气列表
    private lateinit var dailyListAdapter: RealTimeWeather15DailyListAdapter
    private var dailyListData = arrayListOf<DailyWeather>()
    private var trendListData = arrayListOf<WeatherModel>()

    override val viewModel: FifteenWeatherViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_fifteen_weather

    override fun initView(savedInstanceState: Bundle?) {
        StatusBarUtils.addTranslucentView(this, binding.toolbar)

        dailyListData = intent.getParcelableArrayListExtra<DailyWeather>("dailyListData")
        trendListData = intent.getParcelableArrayListExtra<WeatherModel>("trendListData")
    }

    override fun initData() {
        binding.rgDayListChart.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_calendar_list -> {
                    binding.rvDayChart.gone()
                    binding.rvDayList.gone()
                }
                R.id.rb_day_chart -> {
                    binding.rvDayChart.visible()
                    binding.rvDayList.gone()
                }
                R.id.rb_day_list -> {
                    binding.rvDayList.visible()
                    binding.rvDayChart.gone()
                }
            }
        }
        initTrendList();
        initDailyList()

    }


    private fun initDailyList() {
        binding.rvDayList.layoutManager = LinearLayoutManager(this)
        dailyListAdapter = RealTimeWeather15DailyListAdapter(dailyListData)
        binding.rvDayList.adapter = dailyListAdapter
        binding.rvDayList.removeAllAnimation()

        dailyListAdapter.setOnItemClickListener { _, _, position ->
            if (position > 2) return@setOnItemClickListener
//            (requireActivity() as MainActivity).selectFragment(1)
//            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(position)
        }
    }

    private fun initTrendList() {
        // 15天天气趋势
        binding.zzWeatherView.postDelayed({
            binding.zzWeatherView.list = trendListData
            binding.zzWeatherView.setColumnNumber(5)
        }, 500)

        binding.zzWeatherView.setOnWeatherItemClickListener { itemView, position, weatherModel ->
            if (position > 2) return@setOnWeatherItemClickListener
//            (requireActivity() as MainActivity).selectFragment(1)
//            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(position)
        }
    }
}