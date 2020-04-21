package com.lnkj.weather.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mufeng.mvvmlib.image.GlideApp

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/21 17:11
 * @描述
 */
@BindingAdapter("binding_image_res")
fun ImageView.bindingImageRes(res: Int){
    if (res == -1) return
    GlideApp.with(context)
        .load(res)
        .into(this)
}