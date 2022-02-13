package com.lnkj.weather.ui.fifteen

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.DailyWeather
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityFifteenWeatherBinding
import com.lnkj.weather.ui.realweather.*
import com.lnkj.weather.ui.web.WebMainActivity
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.ImageUtils
import com.lnkj.weather.widget.zzweatherview.WeatherModel
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.GsonUtils
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import me.jessyan.autosize.utils.LogUtils
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

    // 15天天气日历
    private lateinit var calendarListAdapter: RealTimeWeather15CalendarListAdapter

    // 15天天气列表
    private lateinit var dailyListAdapter: RealTimeWeather15DailyListAdapter
    private var calendarListData = arrayListOf<DailyWeather>()
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

        binding.ivShare.clickWithTrigger {
            // 截图
            val bitmapTop =
                ImageUtils.getViewBitmap(binding.toolbar, R.color.white)
            val bitmapBottom = getBitmap()
            val bitmap = ImageUtils.combineImage(bitmapTop!!, bitmapBottom!!)
            LiveEventBus.get("event_share_real_time_weather").post(bitmap)
            startActivity<RealTimeWeatherShareActivity>()
        }
        binding.ivAdvertising1.clickWithTrigger {
            startActivity<WebMainActivity>(
                "webUrl" to "https://www.baidu.com",
                "webTitle" to "我是百度"
            )
        }
    }

    override fun initData() {
        binding.rgDayListChart.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_calendar_list -> {
                    binding.rvDayChart.gone()
                    binding.rvDayList.gone()
                    binding.llCalendar.visible()
                }
                R.id.rb_day_chart -> {
                    binding.rvDayChart.visible()
                    binding.rvDayList.gone()
                    binding.llCalendar.gone()
                }
                R.id.rb_day_list -> {
                    binding.rvDayList.visible()
                    binding.rvDayChart.gone()
                    binding.llCalendar.gone()
                }
            }
        }
        binding.ivReturnBack.clickWithTrigger {
            finish()
        }
        initTrendList();
        initDailyList()
        initCalendar();
    }

    fun getBitmap(): Bitmap? {
        binding.nestedScrollView.setBackgroundColor(resources.getColor(R.color.white))
        val bitmap = ImageUtils.getScrollViewBitmap(binding.nestedScrollView, R.color.white)
        binding.nestedScrollView.background = null
        return bitmap
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

    private fun initCalendar() {
        val todayCalendar = Calendar.getInstance()
        // 获取今天星期几
        val todayWeek = DateUtils.getWeekIndex(todayCalendar)
        LogUtils.d("获取今天星期的索引：${todayWeek}")
        // 获取开始补几天
        var startLackDay = todayWeek - 1
        if (todayWeek == 0) {
            startLackDay = 6
        }
        for (i in startLackDay downTo 1) {
            val dateCalendar = Calendar.getInstance()
            dateCalendar.add(Calendar.DAY_OF_MONTH, -i)
            val dateTime = DateUtils.formatTime(dateCalendar.time, DateUtils.PATTERN_14)
            LogUtils.d("开始要补的日期为：${dateTime}")
            calendarListData.add(
                DailyWeather(
                    dateTime, "", "", 0, "",
                    0, "", 0, 0, 0,
                    "", 0, "", ""
                )
            )
        }
        //添加最近15天的天气信息
        calendarListData.addAll(dailyListData)
        LogUtils.d("获取所有的数据源：${GsonUtils.INSTANCE.toJson(calendarListData)}")
        if (calendarListData.size < 21) {
            for (i in 1..(21 - calendarListData.size)) {
                val dateCalendar = Calendar.getInstance()
                dateCalendar.add(Calendar.DAY_OF_MONTH, i)
                val dateTime = DateUtils.formatTime(dateCalendar.time, DateUtils.PATTERN_14)
                LogUtils.d("结束要补的日期为：${dateTime}")
                calendarListData.add(
                    DailyWeather(
                        dateTime, "", "", 0,
                        "", 0, "",
                        0, 0, 0, "", 0, "", ""
                    )
                )
            }
        }

        //渲染页面
        binding.rlCalendar.layoutManager = GridLayoutManager(this, 7)
        calendarListAdapter = RealTimeWeather15CalendarListAdapter(calendarListData)
        binding.rlCalendar.addItemDecoration(SpaceItemDecoration(7, 1, true, true))
        binding.rlCalendar.adapter = calendarListAdapter
        binding.rlCalendar.removeAllAnimation()

        calendarListAdapter.setOnItemClickListener { _, _, position ->
            if (position > 2) return@setOnItemClickListener
//            (requireActivity() as MainActivity).selectFragment(1)
//            LiveEventBus.get(EventKey.EVENT_CHOOSE_HOUR_DETAILS_INDEX).post(position)
        }

    }
}