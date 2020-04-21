package com.mufeng.mvvmlib.utilcode.ext.widget

import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mufeng.mvvmlib.R
import com.mufeng.mvvmlib.image.GlideApp

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/3/6 22:02
 * @描述
 */
var ImageView.imageResource: Int
    get() = throw Exception("Property does not have a getter")
    set(value) {
        setImageResource(value)
    }

fun ImageView.loadImage(path: Any?, errorRes: Int = R.drawable.ic_broken_image){
    GlideApp.with(context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading_animation)
            .error(errorRes)
            .load(path)
            .into(this)
}

fun ImageView.loadHeadImage(path: Any?, errorRes: Int = R.drawable.ic_broken_image){
    GlideApp.with(context)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.loading_animation)
            .error(errorRes)
            .load(path)
            .into(this)
}