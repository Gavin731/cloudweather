package com.lnkj.weather.ui.city.add

import android.graphics.drawable.Drawable
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherItemCityBinding
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.imageResource
import com.mufeng.mvvmlib.utilcode.ext.widget.visible

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 9:32
 * @描述
 */
class AddCityAdapter(data: MutableList<MyCityBean>) :
    BaseQuickAdapter<MyCityBean, BaseViewHolder>(R.layout.weather_item_city, data) {

    var isEdit = false
    private lateinit var drawableStart: Drawable

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<WeatherItemCityBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: MyCityBean) {

        drawableStart = context.resources.getDrawable(R.mipmap.weather_icon_location)
        drawableStart.setBounds(0, 0, drawableStart.minimumWidth, drawableStart.minimumHeight)

        val binding = helper.getBinding<WeatherItemCityBinding>()
        if (binding != null) {
            if (isEdit) {
                binding.ivDelete.visible()
                binding.ivWeatherIcon.gone()
                binding.tvTemperature.gone()
            } else {
                binding.ivDelete.gone()
                binding.ivWeatherIcon.visible()
                binding.tvTemperature.visible()
            }

            try {
                binding.tvTemperature.text = item.temperature
                binding.tvCity.text = "${item.counties}"

                if (item.isLocation == 1) {
                    binding.tvCity.setCompoundDrawables(drawableStart, null, null, null)
                } else {
                    binding.tvCity.setCompoundDrawables(null, null, null, null)
                }

                binding.executePendingBindings()
                binding.ivWeatherIcon.imageResource = item.weatherIcon
            } catch (e: Exception) {
                binding.ivWeatherIcon.gone()
            }

        }
    }
}