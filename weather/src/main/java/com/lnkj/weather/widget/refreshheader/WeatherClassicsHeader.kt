package com.lnkj.weather.widget.refreshheader

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginLeft
import com.lnkj.weather.R
import com.mufeng.mvvmlib.utilcode.ext.widget.imageResource
import com.mufeng.mvvmlib.utilcode.ext.widget.textColor
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.api.RefreshKernel
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.constant.SpinnerStyle


/**
 * @ClassName: WeatherClassicsHeader
 * @Description:经典下拉头部
 * @Author: Pekon
 * @CreateDate: 2020/5/25 15:25
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/25 15:25
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class WeatherClassicsHeader : LinearLayout, RefreshHeader {

    lateinit var iconView: ImageView
    lateinit var contextView: TextView
    lateinit var defaultImgAnimator: ObjectAnimator

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet? = null
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    init {
        initView(context)
    }

    private fun initView(context: Context) {
        gravity = Gravity.CENTER
        orientation = HORIZONTAL

        iconView = ImageView(context)
        iconView.imageResource = R.mipmap.icon_refresh
        addView(iconView)

        val layoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(10, 0, 0, 0)

        contextView = TextView(context)
        contextView.textColor = context.resources.getColor(R.color.weather_translucent_white_50)
        contextView.textSize = 11f
        contextView.layoutParams = layoutParams
        addView(contextView)

        defaultImgAnimator = ObjectAnimator.ofFloat(iconView, "rotation", 360f)
        defaultImgAnimator.duration=500
        defaultImgAnimator.repeatCount=ValueAnimator.INFINITE
    }


    override fun getSpinnerStyle(): SpinnerStyle {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }

    override fun onFinish(refreshLayout: RefreshLayout, success: Boolean): Int {
        defaultImgAnimator.end()
        iconView.imageResource = R.mipmap.icon_refresh_complete
        if (success) {
            contextView.text = "更新成功"
        } else {
            contextView.text = "更新失败"
        }
        return 500;//延迟500毫秒之后再弹回
    }

    override fun onInitialized(kernel: RefreshKernel, height: Int, maxDragHeight: Int) {
    }

    override fun onHorizontalDrag(percentX: Float, offsetX: Int, offsetMax: Int) {
    }

    override fun onReleased(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun getView(): View {
        return this
    }

    override fun setPrimaryColors(vararg colors: Int) {
    }

    override fun onStartAnimator(refreshLayout: RefreshLayout, height: Int, maxDragHeight: Int) {
    }

    override fun onStateChanged(
        refreshLayout: RefreshLayout,
        oldState: RefreshState,
        newState: RefreshState
    ) {
        when (newState) {
            RefreshState.None, RefreshState.PullDownToRefresh -> {
                contextView.text = "下拉松手可更新"
                iconView.visibility = View.GONE
            }
            RefreshState.Refreshing -> {
                contextView.text = "正在更新"
                iconView.imageResource = R.mipmap.icon_refresh
                iconView.visibility = View.VISIBLE
                defaultImgAnimator.start()
            }
            RefreshState.ReleaseToRefresh -> {
//                contextView.text = "释放立即刷新"
                iconView.visibility = View.GONE
            }
        }
    }

    override fun onMoving(
        isDragging: Boolean,
        percent: Float,
        offset: Int,
        height: Int,
        maxDragHeight: Int
    ) {
    }

    override fun isSupportHorizontalDrag(): Boolean {
        return false
    }
}