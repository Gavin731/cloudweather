package com.lnkj.weather.ui.hotrank

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityHotRankBinding
import com.lnkj.weather.http.bean.HotRankBean
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.image.GlideApp
import com.mufeng.mvvmlib.utilcode.ext.widget.removeAllAnimation
import com.mufeng.mvvmlib.utilcode.ext.widget.setupData
import kotlinx.android.synthetic.main.weather_activity_hot_rank.*

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/31 20:27
 * @描述
 */
class HotRankActivity: BaseVMActivity<HotRankViewModel, WeatherActivityHotRankBinding>() {
    override val viewModel: HotRankViewModel
        by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_hot_rank

    private lateinit var adapter: HotRankAdapter
    private val data = arrayListOf<HotRankBean.Data>()

    override fun initView(savedInstanceState: Bundle?) {

        binding.vm = viewModel
        binding.refreshLayout.setOnRefreshListener { viewModel.getHotRankList() }

        adapter = HotRankAdapter(data)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.removeAllAnimation()

        GlideApp.with(this)
            .asDrawable()
            .load(R.drawable.weather_main_bg)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.llBg.background = resource
                }

            })

    }

    override fun initData() {
        binding.refreshLayout.autoRefresh()
    }

    override fun startObserve() {
        super.startObserve()
        viewModel.hotRankData.observe(this){
            refreshLayout.setupData(it,data,adapter,1,100)
        }
    }
}