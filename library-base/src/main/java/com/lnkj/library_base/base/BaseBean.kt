package com.lnkj.library_base.base

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/17 9:12
 * @描述
 */
data class BaseBean<T> (
        val status: Int,
        val msg: String,
        val code: Int,
        val data: T? = null
){
    fun isSuccess(): Boolean{
        return status == 1
    }
}