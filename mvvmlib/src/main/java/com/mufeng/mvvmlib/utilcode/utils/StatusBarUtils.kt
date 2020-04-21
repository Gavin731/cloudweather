package com.mufeng.mvvmlib.utilcode.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/9 17:01
 * @描述
 */
object StatusBarUtils {

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        // 获得状态栏高度
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        return context.resources.getDimensionPixelSize(resourceId)
    }

    fun addTranslucentView(activity: Activity, needOffsetView: View?) {
        if (needOffsetView != null) {
            val layoutParams = needOffsetView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(
                    layoutParams.leftMargin, layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin, layoutParams.bottomMargin
            )
            needOffsetView.layoutParams = layoutParams
        }
    }

    fun addTranslucentViewPadding(activity: Activity, needOffsetView: View?) {
        if (needOffsetView != null) {
            val statusBarHeight = getStatusBarHeight(activity)
            needOffsetView.setPadding(
                    needOffsetView.paddingLeft,
                    needOffsetView.paddingTop + statusBarHeight,
                    needOffsetView.paddingRight,
                    needOffsetView.paddingBottom)
        }
    }

}