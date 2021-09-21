package com.lnkj.weather.ui.settings

import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.widget.TextView
import androidx.activity.viewModels
import com.billy.cc.core.component.remote.RemoteCC
import com.google.gson.JsonObject
import com.lnkj.weather.R
import com.lnkj.weather.databinding.WeatherActivityPrivacyPolicyBinding
import com.lnkj.weather.model.settings.PrivacyPolicyViewModel
import com.mufeng.mvvmlib.basic.view.BaseVMActivity
import com.mufeng.mvvmlib.utilcode.ext.GsonUtils
import com.mufeng.mvvmlib.utilcode.ext.getString
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import org.json.JSONObject

/**
 * @ClassName: PrivacyPolicyActivity
 * @Description:隐私政策和用户协议页面
 * @Author: Pekon
 * @CreateDate: 2020/4/21 19:18
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/21 19:18
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class PrivacyPolicyActivity :
    BaseVMActivity<PrivacyPolicyViewModel, WeatherActivityPrivacyPolicyBinding>() {

    companion object {
        const val PRIVACY_POLICY = "privacy_policy"
        const val USER_AGREEMENT = "user_agreement"
    }

    override val viewModel: PrivacyPolicyViewModel
            by viewModels()
    override val layoutResId: Int
        get() = R.layout.weather_activity_privacy_policy

    private var pageType: String = ""
    override fun initView(savedInstanceState: Bundle?) {
        pageType = intent.getString("pageType", "")
        //校验组件是否有传参
        if (TextUtils.isEmpty(pageType) && intent.extras != null) {
            val ccExtraRemoteCc: RemoteCC = intent.extras!!.get("cc_extra_remote_cc") as RemoteCC
            val jsonObject = ccExtraRemoteCc.params;
            pageType = jsonObject.get("pageType") as String;
        }
        binding.tvTitle.text = "隐私政策"
        //用户协议
        if (pageType == USER_AGREEMENT) {
            binding.tvTitle.text = "用户协议"
        }
        binding.vm = viewModel
        StatusBarUtils.addTranslucentView(this, binding.toolbar)
    }

    override fun initData() {
        var contents = resources.getStringArray(R.array.weather_privacy_policy)
        //用户协议
        if (pageType == USER_AGREEMENT) {
            contents = resources.getStringArray(R.array.weather_user_agreement)
        }
        for (item in contents) {
            val textView = TextView(this)
            textView.text = Html.fromHtml(item)
            binding.llContent.addView(textView)
        }
    }
}