package com.lnkj.weather.ui.city.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lnkj.library_base.db.bean.MyCityBean
import com.lnkj.library_base.event.EventKey
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityAddCityBinding
import com.lnkj.weather.databinding.WeatherItemAddCityBinding
import com.lnkj.weather.ui.city.SearchCityActivity
import com.lnkj.weather.ui.main.MainActivity
import com.lnkj.weather.ui.settings.SettingsActivity
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.observe
import com.mufeng.mvvmlib.utilcode.ext.startActivity
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation
import com.mufeng.mvvmlib.utilcode.utils.Preference
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import kotlinx.android.synthetic.main.weather_activity_add_city.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 9:42
 * @描述
 */
class AddCityActivity : BaseVMActivity<AddCityViewModel, WeatherActivityAddCityBinding>() {
    override val viewModel: AddCityViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_add_city

    private lateinit var adapter: AddCityAdapter
    private lateinit var addFooterView: WeatherItemAddCityBinding
    private val data = arrayListOf<MyCityBean>()

    private var isEdit = false

    override fun initView(savedInstanceState: Bundle?) {

        binding.vm = viewModel

        StatusBarUtils.addTranslucentView(this, toolbar)

        adapter = AddCityAdapter(data)

        addFooterView =
            DataBindingUtil.inflate(layoutInflater, R.layout.weather_item_add_city, null, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.removeAllAnimation()

        adapter.addFooterView(addFooterView.root)

        adapter.addChildClickViewIds(R.id.iv_delete)
        adapter.setOnItemClickListener { _, view, position ->
            val item = adapter.getItem(position)
            LiveEventBus.get(EventKey.EVENT_CHOOSE_ACC_CITY)
                .post(item)
            startActivity<MainActivity>()
        }

        adapter.setOnItemChildClickListener { _, view, position ->
            val item = adapter.getItem(position)
            if (view.id == R.id.iv_delete) {
                data.remove(item)
                adapter.notifyDataSetChanged()

                viewModel.delCity(item)

                LiveEventBus.get(EventKey.EVENT_CHOOSE_REMOVE_CITY)
                    .post(item)
            }
        }

        addFooterView.llAddCity.clickWithTrigger {
            startActivity<SearchCityActivity>()
            finish()
        }

        binding.llSettings.clickWithTrigger {
            startActivity<SettingsActivity>()
        }

        binding.tvEdit.setOnClickListener {
            if (isEdit) {
                isEdit = false
                binding.tvEdit.text = "编辑"
            } else {
                isEdit = true
                binding.tvEdit.text = "完成"
            }

            adapter.isEdit = isEdit
            adapter.notifyDataSetChanged()
        }

        LiveEventBus.get(EventKey.EVENT_CHANGE_AUTO_LOCATION_STATUS, Boolean::class.java)
            .observe(this) {
                viewModel.getCityList(it)
            }

    }

    override fun initData() {
        val isLocation by Preference("isLocation", true)
        viewModel.getCityList(isLocation)
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.cityListDataL.observe(this) {
            data.clear()
            data.addAll(it)
            adapter.notifyDataSetChanged()
        }

    }
}