package com.mufeng.mvvmlib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class ScrollViewPager : ViewPager {
    private var scroll = false

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}

    fun setScroll(scroll: Boolean) {
        this.scroll = scroll
    }

    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (scroll) false else super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (scroll) false else super.onInterceptTouchEvent(arg0)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}