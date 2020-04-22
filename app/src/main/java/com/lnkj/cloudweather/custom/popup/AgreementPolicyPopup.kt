package com.lnkj.cloudweather.custom.popup

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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

    companion object {
        const val USER_TEXT = "user"
        const val POLICY_TEXT = "policy"
    }

    private val listener: AgreementPolicyListener = listener

    override fun getImplLayoutId(): Int {
        return R.layout.view_agreement_policy
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<TextView>(R.id.tv_close_app).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_ok).setOnClickListener(this)

        var userText = SpannableString("《服务协议》")
        userText.setSpan(
            SpanClick(USER_TEXT),
            0,
            userText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        var privacyText = SpannableString("《隐私政策》")
        privacyText.setSpan(
            SpanClick(POLICY_TEXT),
            0,
            privacyText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        val textView = findViewById<TextView>(R.id.tv_click_text);
        textView.text = "你可阅读";
        textView.append(userText);
        textView.append("和");
        textView.append(privacyText);
        textView.append("了解详细信息。如你同意，请点击“同意”开始接受我们的服务。");
        textView.movementMethod = LinkMovementMethod.getInstance();//开始响应点击事件

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_close_app -> {
                listener?.onCancel()
            }
            R.id.tv_ok -> {
                dismiss()
                listener?.onOk()
            }
        }
    }

    interface AgreementPolicyListener {
        fun onCancel()
        fun onOk()
    }

    class SpanClick(val content: String) : ClickableSpan() {

        override fun onClick(widget: View) {
            when (content) {
                USER_TEXT -> {
                    val params = HashMap<String, Any>()
                    params.put("pageType", "user_agreement")
                    CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                        .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_USER_AGREEMENT)
                        .setParams(params)
                        .build()
                        .callAsync() { cc, result ->
                        }
                }
                POLICY_TEXT -> {
                    val params = HashMap<String, Any>()
                    params.put("pageType", "privacy_policy")
                    CC.obtainBuilder(WeatherComponent.WEATHER_COMPONENT_NAME)
                        .setActionName(WeatherComponent.WEATHER_ACTION_OPEN_PRIVACY_POLICY)
                        .setParams(params)
                        .build()
                        .callAsync() { cc, result ->
                        }
                }
            }
        }

    }
}