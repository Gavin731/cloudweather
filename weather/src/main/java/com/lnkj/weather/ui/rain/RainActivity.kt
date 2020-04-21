package com.lnkj.weather.ui.rain

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityRainChartBinding
import com.lnkj.weather.http.bean.MinutelyRainfallBean
import com.lnkj.weather.utils.DateUtils
import com.lnkj.weather.utils.DateUtils.PATTERN_15
import com.lnkj.weather.utils.WeatherUtils
import com.lnkj.weather.widget.zzweatherview.rain.RainModel
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.getString
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import java.util.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/6 15:44
 * @描述
 */
class RainActivity : BaseVMActivity<RainViewModel, WeatherActivityRainChartBinding>() {

    companion object{
        fun launch(
            context: Context,
            cityBean: MyCityBean?,
            weatherName: String,
            rainTip: String?
        ){
            context.startActivity<RainActivity>(
                "cityBean" to cityBean,
                "weatherName" to weatherName,
                "rainTip" to rainTip
            )
        }
    }

    override val viewModel: RainViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_rain_chart

    private var cityBean: MyCityBean? = null
    private var rainTip = ""

    override fun initView(savedInstanceState: Bundle?) {

        binding.vm = viewModel
        cityBean = intent.getParcelableExtra("cityBean")
        rainTip = intent.getString("rainTip")

        if (cityBean == null){

            finish()
            return
        }

        StatusBarUtils.addTranslucentView(this, binding.toolbar)

        binding.rainingView.setOnTouchListener { v, event ->
            true
        }

        viewModel.title.postValue(cityBean?.counties)
        viewModel.rainType.postValue("降${WeatherUtils.isRainByName(intent.getStringExtra("weatherName")!!)}量")

        viewModel.getRainList(cityBean!!)

    }

    override fun initData() {
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.rainListData.observe(this){
            viewModel.publishDate.postValue(DateUtils.formatTime(Date(it.serverTime!!.toLong() * 1000), PATTERN_15)+"发布")
            viewModel.rainTitle.postValue(rainTip)
            // 生成趋势图数据
            binding.rainingView.list = generateData(it.result)
        }
    }

    private fun generateData(result: MinutelyRainfallBean.Result?): MutableList<RainModel>? {
        val list = arrayListOf<RainModel>()
        var model = RainModel()
        model.rainValue = getValue((result?.minutely?.precipitation2h?.get(0)!!*100).toInt())
        model.time = "现在"
        list.add(model)

        model = RainModel()
        model.rainValue = getValue((result?.minutely?.precipitation2h?.get(29)!!*100).toInt())
        model.time = "30分钟"
        list.add(model)

        model = RainModel()
        model.rainValue = getValue((result?.minutely?.precipitation2h?.get(59)!!*100).toInt())
        model.time = "60分钟"
        list.add(model)

        model = RainModel()
        model.rainValue = getValue((result?.minutely?.precipitation2h?.get(89)!!*100).toInt())
        model.time = "90分钟"
        list.add(model)

        model = RainModel()
        model.rainValue = getValue((result?.minutely?.precipitation2h?.get(119)!! * 100).toInt())
        model.time = "120分钟"
        list.add(model)
        return list
    }

    private fun getValue(aa: Int): Int {
        var v = 0
        v = when {
            aa <= 25 -> {
                return aa
            }
            aa <= 35 -> {
                (25 + (aa - 25) * 2.5).toInt()
            }
            else -> {
                (50 + (aa - 35) * 0.38).toInt()
            }
        }
        return v
    }
}