package com.lnkj.weather.ui.realweather

import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.library_base.db.bean.DailyWeather
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItem15calendarListBinding

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
        val binding = helper.getBinding<WeatherItem15calendarListBinding>()
        if (binding != null) {

//            if (helper.layoutPosition == 0){
//                binding.alpha = 0.5f
//            }else{
//            }
            binding.alpha = 1f

//            if (helper.layoutPosition == 1 || helper.layoutPosition == 2 || helper.layoutPosition == 3) {
//                helper.getView<LinearLayout>(R.id.ll_root_view)
//                    .setBackgroundResource(R.drawable.weather_selector_hour_press_bg)
//            }

            binding.dailyWeather = item
            binding.executePendingBindings()
        }
    }
}