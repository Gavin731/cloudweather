package com.lnkj.weather.ui.hotrank

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItemHotRankBinding
import com.lnkj.weather.http.bean.HotRankBean

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/31 20:22
 * @描述
 */
class HotRankAdapter(data: MutableList<HotRankBean.Data>): BaseQuickAdapter<HotRankBean.Data, BaseViewHolder>(
    R.layout.weather_item_hot_rank, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItemHotRankBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: HotRankBean.Data) {
        val binding = helper.getBinding<WeatherItemHotRankBinding>()
        if (binding != null){
            binding.data = item
            binding.executePendingBindings()
        }
    }
}