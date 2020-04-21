package com.lnkj.weather.ui.city

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.library_base.db.bean.CityBean
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItemCityLabelBinding

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/24 17:08
 * @描述
 */
/**
 * 1. 洲
 * 2. 国
 * 3. 省
 * 4. 市
 * 5.县区
 * @constructor
 */
class CityAdapter(data: MutableList<CityBean>, private val level: Int): BaseQuickAdapter<CityBean, BaseViewHolder>(R.layout.weather_item_city_label, data) {

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItemCityLabelBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: CityBean) {
        val binding = helper.getBinding<WeatherItemCityLabelBinding>()
        if (binding != null) {
            binding.isLocation = item.isLocation
            when(level){
                1->{
                    binding.cityName = item.continentName
                }
                2->{
                    binding.cityName = item.countryName
                }
                3->{
                    binding.cityName = item.provinceName
                }
                4->{
                    binding.cityName = item.cityName
                }
                5->{
                    binding.cityName = item.counties
                }
            }

            binding.executePendingBindings()
        }
    }
}