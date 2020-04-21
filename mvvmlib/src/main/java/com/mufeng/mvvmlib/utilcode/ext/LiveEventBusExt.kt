package com.mufeng.mvvmlib.utilcode.ext

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.core.Observable


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/16 14:11
 * @描述
 */
@MainThread
inline fun <T> Observable<T>.observe(
        owner: LifecycleOwner,
        crossinline onChanged: (T) -> Unit
): Observer<T> {
    val wrappedObserver = Observer<T> { t -> onChanged.invoke(t) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}