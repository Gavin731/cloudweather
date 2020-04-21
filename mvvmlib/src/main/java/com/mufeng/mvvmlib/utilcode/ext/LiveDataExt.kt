package com.mufeng.mvvmlib.utilcode.ext

import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/1/17 10:29
 * @描述
 */
@AnyThread
inline fun <reified T> MutableLiveData<T>.postNext(map: (T) -> T) {
    postValue(map(verifyLiveDataNotEmpty()))
}

@MainThread
inline fun <reified T> MutableLiveData<T>.setNext(map: (T) -> T) {
    value = map(verifyLiveDataNotEmpty())
}

@AnyThread
inline fun <reified T> LiveData<T>.verifyLiveDataNotEmpty(): T {
    return value
        ?: throw NullPointerException("MutableLiveData<${T::class.java}> not contain value.")
}

/**
 * LiveData 数据转化
 * @receiver LiveData<X>
 * @param mapFunction Function1<X, Y>
 * @return LiveData<Y>
 */
fun <X, Y> LiveData<X>.transformationsMap(
    mapFunction: (X) -> Y
): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(
        this
    ) { x -> result.value = mapFunction.invoke(x) }
    return result
}
