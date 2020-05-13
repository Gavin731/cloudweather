package com.lnkj.weather.widget.popup

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lnkj.weather.R
import com.lxj.xpopup.core.CenterPopupView

/**
 * @ClassName: UpdateAppPopup
 * @Description: 更新app弹窗
 * @Author: Pekon
 * @CreateDate: 2020/5/13 15:56
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/13 15:56
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class UpdateAppPopup(context: Context, version: String, downloadUrl: String) :
    CenterPopupView(context),
    View.OnClickListener {

    private val version: String = version
    private val downloadUrl: String = downloadUrl

    override fun getImplLayoutId(): Int {
        return R.layout.weather_view_update_app_popup
    }

    override fun onCreate() {
        super.onCreate()
        findViewById<LinearLayout>(R.id.ll_close).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_update).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_version).text = "版本号${version}"
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_close -> {
                dismiss()
            }
            R.id.tv_update -> {
                dismiss()
            }
        }
    }
}