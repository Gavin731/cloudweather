package com.lnkj.weather.ui.city

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItemSearchCityBinding

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/24 18:08
 * @描述
 */
class SearchAdapter (data: MutableList<CityBean>): BaseQuickAdapter<CityBean, BaseViewHolder>(R.layout.weather_item_search_city, data) {

    var search: String = ""

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItemSearchCityBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: CityBean) {
        val binding = helper.getBinding<WeatherItemSearchCityBinding>()
        if (binding != null) {

            binding.tvCity.text = "${item.counties}, ${item.cityName}, ${item.provinceName}, ${item.countryName}"

            binding.executePendingBindings()
        }
    }
}