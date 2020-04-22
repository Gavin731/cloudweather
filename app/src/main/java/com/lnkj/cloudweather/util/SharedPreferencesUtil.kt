package com.lnkj.cloudweather.util

import android.content.Context
import android.preference.PreferenceManager

/**
 * @ClassName: SharedPreferencesUtil
 * @Description:
 * @Author: Pekon
 * @CreateDate: 2020/4/22 21:01
 * @UpdateUser: 更新者：
 * @UpdateDate: 2020/4/22 21:01
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
class SharedPreferencesUtil {

    companion object {

        fun setSpBoolean(context: Context, key: String, value: Boolean) {
            var sp = PreferenceManager.getDefaultSharedPreferences(context);
            var e = sp.edit();
            e.putBoolean(key, value);
            e.commit()
        }

        fun getSpBoolean(context: Context, key: String): Boolean {
            var sp = PreferenceManager.getDefaultSharedPreferences(context);
            return sp.getBoolean(key, true)
        }
    }
}