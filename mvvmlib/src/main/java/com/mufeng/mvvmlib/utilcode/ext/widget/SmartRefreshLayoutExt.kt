package com.mufeng.mvvmlib.utilcode.ext.widget

import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout

/**
 * @创建者 MuFeng-T
 * @创建时间 2020/2/11 9:30
 * @描述
 */
fun SmartRefreshLayout.finishAll(){
    this.finishRefresh()
    this.finishLoadMore()
}

fun <T>SmartRefreshLayout.setupData(list: List<T?>?, data: ArrayList<T>, adapter: BaseQuickAdapter<*,*>,p: Int, pageSize: Int = 10){
    if (list?.size ?: 0 < pageSize) {
        finishLoadMoreWithNoMoreData()
        setNoMoreData(true)
    } else {
        setNoMoreData(false)
    }

    if (p == 1) {
        finishRefresh()
        data.clear()
        list?.forEach {
            data.add(it!!)
        }
        adapter.notifyDataSetChanged()
    } else {
        finishLoadMore()
        if (list?.size == 0){
            return
        }
        list?.forEach {
            data.add(it!!)
        }
        adapter.notifyDataSetChanged()
    }
}