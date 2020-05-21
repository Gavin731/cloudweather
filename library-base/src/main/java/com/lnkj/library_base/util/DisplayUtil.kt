package com.lnkj.library_base.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics


/**
 * @ClassName: DisplayUtil
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/5/21 17:28
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/21 17:28
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class DisplayUtil {
    companion object {
        fun getDensity(context: Context): Float {
            val resources: Resources = context.resources
            val dm: DisplayMetrics = resources.displayMetrics
            return dm.density
        }

        fun getDensityWdith(context: Context): Int {
            val resources: Resources = context.resources
            val dm: DisplayMetrics = resources.displayMetrics
            return dm.widthPixels
        }

        fun getDensityHeight(context: Context): Int {
            val resources: Resources = context.resources
            val dm: DisplayMetrics = resources.displayMetrics
            return dm.heightPixels
        }

        // 1.代码中设置setXXSize的都是px单位，都需要把布局中的dp，sp转成px才能使用
        // 1.代码中设置setXXSize的都是px单位，都需要把布局中的dp，sp转成px才能使用
        /**
         * 根据手机分辨率从 px(像素) 单位 转成 dp
         */
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        /**
         * 根据手机分辨率从 dp 单位 转成 px(像素)
         */
        fun dip2px(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         *
         * @param pxValue
         * @param
         * （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        fun px2sp(context: Context, pxValue: Float): Int {
            val fontScale: Float = context.resources.displayMetrics.scaledDensity
            return (pxValue / fontScale + 0.5f).toInt()
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue
         * @param
         * （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        fun sp2px(context: Context, spValue: Float): Int {
            val fontScale: Float = context.resources.displayMetrics.scaledDensity
            return (spValue * fontScale + 0.5f).toInt()
        }
    }

}