package com.lnkj.weather.ui.realweather

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.library_base.db.bean.DailyWeather
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItem15calendarListBinding
import com.lnkj.weather.utils.WeatherUtils
import com.mufeng.roundview.RoundTextView

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/27 21:01
 * @描述
 */
class RealTimeWeather15CalendarListAdapter(data: MutableList<DailyWeather>) :
    BaseQuickAdapter<DailyWeather, BaseViewHolder>(
        R.layout.weather_item_15calendar_list, data
    ) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItem15calendarListBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: DailyWeather) {
        if (item.weatherName == "") {
            helper.getView<ImageView>(R.id.iv_date).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_weatherName).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_temperature).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_direction).visibility = View.GONE
            helper.getView<TextView>(R.id.tv_wind).visibility = View.GONE
            helper.getView<RoundTextView>(R.id.tv_air_level2).visibility = View.GONE
            helper.getView<LinearLayout>(R.id.ll_root_view)
                .setBackgroundColor(context.resources.getColor(R.color.color_F8F8F8))
        } else {
            helper.getView<ImageView>(R.id.iv_date).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_weatherName).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_temperature).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_direction).visibility = View.VISIBLE
            helper.getView<TextView>(R.id.tv_wind).visibility = View.VISIBLE
            helper.getView<RoundTextView>(R.id.tv_air_level2).visibility = View.VISIBLE
            helper.getView<LinearLayout>(R.id.ll_root_view)
                .setBackgroundColor(context.resources.getColor(R.color.white))
        }
        val binding = helper.getBinding<WeatherItem15calendarListBinding>()
        if (binding != null) {
            binding.alpha = 1f
            binding.dailyWeather = item
            val roundTextView = helper.getView<RoundTextView>(R.id.tv_air_level2)
            roundTextView.delegate.backgroundColor = (
                    context.resources.getColor(
                        WeatherUtils.getAirQualityColor(item.airQualityValue)
                    )
                    )
            binding.executePendingBindings()
        }
    }
}