package com.lnkj.cloudweather.custom.popup

import android.content.Context
import android.view.View
import android.widget.TextView
import com.billy.cc.core.component.CC
import com.lnkj.cloudweather.R
import com.lnkj.library_base.router.WeatherComponent
import com.lxj.xpopup.core.CenterPopupView

/**
 * @ClassName: AgreementPolicyPopup
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/4/22 15:40
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/22 15:40
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class AgreementPolicyPopup(context: Context, listener: AgreementPolicyListener) :
    CenterPopupView(context), View.OnClickListener {

    private val listener: AgreementPolicyListener = listener

    override fun getImplLayoutId(): Int {
        return R.layout.view_agreement_policy
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<TextView>(R.id.tv_privacy_policy).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_user_agreement).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_close_app).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_ok).setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_privacy_policy -> {
                val params=HashMap<String,Any>()
                params.put("pageType","privacy_policy")
                CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                    .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_PRIVACY_POLICY)
                    .setParams(params)
                    .build()
                    .callAsync() { cc, result ->
                    }
            }
            R.id.tv_user_agreement -> {
                val params=HashMap<String,Any>()
                params.put("pageType","user_agreement")
                CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                    .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_USER_AGREEMENT)
                    .setParams(params)
                    .build()
                    .callAsync() { cc, result ->
                    }
            }
            R.id.tv_close_app -> {
                listener?.onCancel()
            }
            R.id.tv_ok -> {
                listener?.onOk()
            }
        }
    }

    interface AgreementPolicyListener {
        fun onCancel()
        fun onOk()
    }
}