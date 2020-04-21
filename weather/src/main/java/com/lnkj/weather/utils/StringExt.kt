package com.lnkj.weather.utils

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/2 20:55
 * @描述
 */
fun Int.format0Str(): String{
    return if (this<10){
        "0$this"
    }else{
        this.toString()
    }
}