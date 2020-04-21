package com.mufeng.mvvmlib.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.mufeng.mvvmlib.utilcode.ext.widget.clickWithTrigger
import com.mufeng.mvvmlib.utilcode.ext.widget.gone
import com.mufeng.mvvmlib.utilcode.ext.widget.invisible
import com.mufeng.mvvmlib.utilcode.ext.widget.visible
import com.mufeng.mvvmlib.utilcode.utils.StatusBarUtils
import com.mufeng.mvvmlib.utilcode.utils.dp

/**
 * @创建者 MuFeng-T
 * @创建时间 2019/10/16 21:50
 * @描述
 */

//防重复点击间隔(毫秒)
val CLICK_INTERVAL = 500

/**
 * 点击事件， 可以处理防抖动 需要后期处理
 * @receiver View
 * @param bindingCommand BindingCommand
 */
@BindingAdapter("onClickCommand")
fun View.onClickCommand(bindingCommand: BindingCommand){
    clickWithTrigger {
        bindingCommand.apply()
    }
}

/**
 * 长按点击事件
 * @receiver View
 * @param bindingCommand BindingCommand
 */
@BindingAdapter("onLongClickCommand")
fun View.onLongClickCommand(bindingCommand: BindingCommand) {
    setOnLongClickListener {
        bindingCommand.apply()
        true
    }
}

/**
 * 设置View内边距
 * @receiver View
 * @param left Int
 * @param top Int
 * @param right Int
 * @param bottom Int
 */
@BindingAdapter("leftPadding", "topPadding", "rightPadding", "bottomPadding", requireAll = false)
fun View.setPadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0){
    this.setPadding(paddingLeft + left.dp, paddingTop + top.dp, paddingRight + right.dp, paddingBottom + bottom.dp)
}

/**
 * 给View增加状态栏高度的Padding
 * @receiver View
 * @param isShowStatusBar Boolean
 */
@BindingAdapter("isShowStatusBar")
fun View.addStatusBar(isShowStatusBar: Boolean){
    if (isShowStatusBar) {
        setPadding(paddingLeft, paddingTop + StatusBarUtils.getStatusBarHeight(context) , paddingRight, paddingBottom )
    }
}

/**
 * 设置View是否隐藏
 * @receiver View
 * @param isGone Boolean
 */
@BindingAdapter("isGone")
fun View.isGone(isGone: Boolean){
    if (isGone){
        gone()
    }else{
        visible()
    }
}

/**
 *
 * @receiver View
 * @param isVisible Boolean
 */
@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean){
    if (isVisible){
        visible()
    }else{
        invisible()
    }
}