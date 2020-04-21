package com.lnkj.weather.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import com.mufeng.mvvmlib.utilcode.utils.context


/**
 * @创建者 MuFeng-T
 * @创建时间 2020/4/3 8:53
 * @描述
 */
object ImageUtils {
    /**
     * 截取scrollview的屏幕
     */
    fun getScrollViewBitmap(scrollView: ViewGroup, colorRes: Int): Bitmap? {
        var h = 0
        val bitmap: Bitmap
        val paint = Paint()
        paint.color = context.resources.getColor(colorRes)
        for (i in 0 until scrollView.childCount) {
            h += scrollView.getChildAt(i).height
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(
            scrollView.width, h,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        canvas.drawRect(0f,0f,scrollView.width.toFloat(),h.toFloat(),paint)
        scrollView.draw(canvas)
        return bitmap
    }

    /**
     * 截取scrollview的屏幕
     */
    fun getViewBitmap(view: View, colorRes: Int = -1): Bitmap? {
        // 创建对应大小的bitmap

        val bitmap: Bitmap = Bitmap.createBitmap(
            view.width, view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        if (colorRes != -1) {
            val paint = Paint()
            paint.color = context.resources.getColor(colorRes)
            canvas.drawRect(0f, 0f, view.width.toFloat(), view.height.toFloat(), paint)
        }
        view.draw(canvas)
        return bitmap
    }

    /**
     * 横向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return 
    </功能详细描述> */
    private fun add2Bitmap(first: Bitmap, second: Bitmap): Bitmap? {
        val width = first.width + second.width
        val height = Math.max(first.height, second.height)
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        canvas.drawBitmap(first, 0f, 0f, null)
        canvas.drawBitmap(second, first.width.toFloat(), 0f, null)
        return result
    }


    /**
     * 纵向拼接
     * <功能详细描述>
     * @param first
     * @param second
     * @return 
    </功能详细描述> */
    fun addBitmap(first: Bitmap, second: Bitmap): Bitmap? {
        val width = first.width.coerceAtLeast(second.width)
        val height = first.height + second.height
        val result = Bitmap.createBitmap(second.width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(result)
        canvas.drawBitmap(first, 0f, 0f, null)
        canvas.drawBitmap(second, first.height.toFloat(), 0f, null)
        return result
    }

    fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap? {
        val scaleWidth = newWidth.toFloat() / bitmap.width
        val scaleHeight = newHeight.toFloat() / bitmap.height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    /**
     * 拼接图片
     *
     * @param bitmaps 原图片集
     * @return  拼接后的新图
     */
    fun combineImage(vararg bitmaps: Bitmap): Bitmap? {
        var isMultiWidth = false //是否为多宽度图片集
        var width = 0
        var height = 0
        //获取图纸宽度
        for (bitmap in bitmaps) {
            if (width != bitmap.width) {
                if (width != 0) { //过滤掉第一次不同
                    isMultiWidth = true
                }
                width = if (width < bitmap.width) bitmap.width else width
            }
        }
        //获取图纸高度
        for (bitmap in bitmaps) {
            height = if (isMultiWidth) {
                height + bitmap.height * width / bitmap.width
            } else {
                height + bitmap.height
            }
        }
        //创建图纸
        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        //创建画布,并绑定图纸
        val canvas = Canvas(newBitmap)
        var tempHeight = 0
        //画图
        for (i in 0 until bitmaps.size) {
            if (isMultiWidth) {
                if (width != bitmaps[i].width) {
                    val newSizeH =
                        bitmaps[i].height * width / bitmaps[i].width
                    val newSizeBmp = resizeBitmap(bitmaps[i], width, newSizeH)
                    canvas.drawBitmap(newSizeBmp!!, 0f, tempHeight.toFloat(), null)
                    tempHeight = tempHeight + newSizeH
                    newSizeBmp.recycle()
                } else {
                    canvas.drawBitmap(bitmaps[i], 0f, tempHeight.toFloat(), null)
                    tempHeight = tempHeight + bitmaps[i].height
                }
            } else {
                canvas.drawBitmap(bitmaps[i], 0f, tempHeight.toFloat(), null)
                tempHeight = tempHeight + bitmaps[i].height
            }
            bitmaps[i].recycle()
        }
        return newBitmap
    }
}