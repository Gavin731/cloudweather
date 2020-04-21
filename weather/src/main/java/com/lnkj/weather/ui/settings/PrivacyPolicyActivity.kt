package com.lnkj.weather.ui.settings

import android.os.Bundle
import androidx.activity.viewModels
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityPrivacyPolicyBinding
import com.lnkj.weather.model.settings.PrivacyPolicyViewModel
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils

/**
 * @ClassName: PrivacyPolicyActivity
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/4/21 19:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/21 19:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class PrivacyPolicyActivity :
    BaseVMActivity<PrivacyPolicyViewModel, WeatherActivityPrivacyPolicyBinding>() {
    override val viewModel: PrivacyPolicyViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_privacy_policy

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = viewModel
        StatusBarUtils.addTranslucentView(this, binding.toolbar)
    }

    override fun initData() {
    }
}