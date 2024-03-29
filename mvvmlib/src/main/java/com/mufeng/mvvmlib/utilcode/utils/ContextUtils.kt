package com.mufeng.mvvmlib.utilcode.utils

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import android.widget.Toast

/**
 * @创建者 MuFeng-T
 * @创建时间 2019/11/6 16:33
 * @描述
 */
private class GlobalContext(context: Context) : ContextWrapper(context)

private lateinit var sApplication: Application
private lateinit var sGlobalContext: GlobalContext

fun initContext(application: Application) {
    if (!::sApplication.isInitialized) {
        sApplication = application
    }
    if (!::sGlobalContext.isInitialized) {
        sGlobalContext =
            GlobalContext(application.applicationContext)
    }
}

val context: Context
    get() = sGlobalContext


val application: Application
    get() = sApplication


/* dp/px 相关工具 */
private val density by lazy {
    context.resources.displayMetrics.density
}
/* from dp to px */
val Int.dp: Int
    get() = (density * this + 0.5f).toInt()

val Float.dp: Float
    get() = density * this + 0.5f

/* from px to dp */
val Int.toDp: Int
    get() = (this / density + 0.5f).toInt()

val Float.toDp: Float
    get() = this / density + 0.5f


/* sp/px 相关工具 */
private val scaledDensity by lazy {
    context.resources.displayMetrics.scaledDensity
}

/* from sp to px */
val Int.sp: Int
    get() = (scaledDensity * this + 0.5f).toInt()

val Float.sp: Float
    get() = scaledDensity * this + 0.5f

/* from px to sp */
val Int.toSp: Int
    get() = (this / scaledDensity + 0.5f).toInt()

val Float.toSp: Float
    get() = this / scaledDensity + 0.5f

/* Toast 相关 */

private val globalToast by lazy {
    Toast.makeText(context, "", Toast.LENGTH_SHORT)
}

/**
 * 及时的、尽快的显示一个Toast，多次调用此方法的Toast会被后调用的覆盖
 * @param text String 要显示的文本
 * @return Toast 单例的Toast对象
 */
fun toast(text: String): Toast {
    var content = text
    if (TextUtils.isEmpty(content)) content = ""
    globalToast.duration = Toast.LENGTH_SHORT
    globalToast.setText(content)
    globalToast.show()
    return globalToast
}

/**
 * 同上，但是显示的时间稍长一点
 */
fun longToast(text: String): Toast {
    var content = text
    if (TextUtils.isEmpty(content)) content = ""
    globalToast.duration = Toast.LENGTH_LONG
    globalToast.setText(content)
    globalToast.show()
    return globalToast
}

inline fun <reified T : Context> T.toastShort(text: String): Toast {
    return Toast.makeText(this, text, Toast.LENGTH_SHORT).apply(Toast::show)
}

inline fun <reified T : Context> T.longToast(text: String): Toast {
    return Toast.makeText(this, text, Toast.LENGTH_LONG).apply(Toast::show)
}