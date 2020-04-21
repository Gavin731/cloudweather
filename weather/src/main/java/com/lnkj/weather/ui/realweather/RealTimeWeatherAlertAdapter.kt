package com.lnkj.weather.ui.realweather

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItemAlertBinding

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/27 17:32
 * @描述
 */
class RealTimeWeatherAlertAdapter (data: MutableList<String>): BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.weather_item_alert, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItemAlertBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        val binding = helper.getBinding<WeatherItemAlertBinding>()
        if (binding != null) {
            binding.alertInfo = item
            binding.executePendingBindings()
        }
    }
}