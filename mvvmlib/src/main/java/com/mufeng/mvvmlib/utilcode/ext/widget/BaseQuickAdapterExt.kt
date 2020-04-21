package com.mufeng.mvvmlib.utilcode.ext.widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mufeng.mvvmlib.R

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/2/7 14:25
 * @描述
 */
fun BaseQuickAdapter<*,*>.setEmptyData(context: Context, imgRes: Int = R.drawable.svg_empty_data, tip: String = "暂无数据", reRefreshData: ()->Unit = {}){

    val emptyView = LayoutInflater.from(context).inflate(R.layout.state_empty_layout, null)
    val imageView = emptyView.findViewById<ImageView>(R.id.ivNoData)
    imageView.loadImage(imgRes)
    val textView = emptyView.findViewById<TextView>(R.id.emptyStatusTextView)
    textView.text = tip

    imageView.clickWithTrigger {
        reRefreshData.invoke()
    }

    setEmptyView(emptyView)

}

fun BaseViewHolder.loadImage(@IdRes viewId: Int, url: Any?){
    val imageView = getView<ImageView>(viewId)
    imageView.loadImage(url)
}
fun BaseViewHolder.loadHeadImage(@IdRes viewId: Int,url: Any?){
    val imageView = getView<ImageView>(viewId)
    imageView.loadHeadImage(url)
}
