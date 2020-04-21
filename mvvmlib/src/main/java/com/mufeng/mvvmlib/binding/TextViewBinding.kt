package com.mufeng.mvvmlib.binding

import android.graphics.Paint
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/17 13:55
 * @描述
 */

/**
 * TextView 添加删除线
 * @receiver TextView
 * @param addDeleteLine Boolean
 */
@BindingAdapter("addDeleteLine")
fun TextView.addDeleteLine(addDeleteLine: Boolean){
    paintFlags = if (addDeleteLine) {
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }else{
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}