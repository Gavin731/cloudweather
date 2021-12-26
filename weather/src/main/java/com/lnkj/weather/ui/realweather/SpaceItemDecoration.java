package com.lnkj.weather.ui.realweather;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: zenglinggui
 * @description RecyclerView grid 间隔
 * @Modification History:
 * <p>
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2020/7/9     zenglinggui       v1.0.0        create
 **/
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int column;
    private int space;
    private boolean isSetLeft;
    private boolean isSetBottom;

    /**
     * 间隔
     *
     * @param column      多少列
     * @param space       间隔
     * @param isSetLeft   是否设置左边间隔
     * @param isSetBottom 是否设置底部间隔
     */
    public SpaceItemDecoration(int column, int space, boolean isSetLeft, boolean isSetBottom) {
        this.column = column;
        this.space = space;
        this.isSetLeft = isSetLeft;
        this.isSetBottom = isSetBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (isSetLeft) {
            outRect.left = space;
            if (parent.getChildLayoutPosition(view) % column == 0) {
                outRect.left = 0;
            }
        }
        if (isSetBottom) {
            outRect.bottom = space;
        }
    }

}
