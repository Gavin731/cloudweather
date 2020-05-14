package com.lnkj.weather.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * @ClassName: AndroidUtil
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/5/13 18:17
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/5/13 18:17
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class AndroidUtil {

    companion object {
        fun getVersionCode(context: Context): Int {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionCode
            } catch (e: Exception) {
            }
            return 0
        }
        fun getVersionName(context: Context): String {
            try {
                val packageManager = context.packageManager
                val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
                return packageInfo.versionName
            } catch (e: Exception) {
            }
            return "1.0"
        }
    }
}