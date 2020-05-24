package com.lnkj.weather.widget.zzweatherview.hour;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.lnkj.weather.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * ZzWeatherView
 *
 * @author Zz
 * 2016/12/8 9:29
 */
public class HourWeatherView extends HorizontalScrollView {

    private List<HourWeatherModel> list;
    private Paint paint;

    protected Path path;

    private float lineWidth = 3f;

    private int lineColor = 0xFFFFFFFF;

    private int columnNumber = 6;

    private boolean isDrawPath = true;

    private int timeColorId, weatherColor, tvTempColor;

    private OnWeatherItemClickListener weatherItemClickListener;

    public HourWeatherView(Context context) {
        this(context, null);
    }

    public HourWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HourWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(lineColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dispatchDraw(canvas);
        if (getChildCount() > 0) {
            ViewGroup root = (ViewGroup) getChildAt(0);

            if (root.getChildCount() > 0) {

                float prevDx = 0f;
                float prevDy = 0f;
                float curDx = 0f;
                float curDy = 0f;
                float prevDx1 = 0f;
                float prevDy1 = 0f;
                float curDx1 = 0f;
                float curDy1 = 0f;
                float intensity = 0.16f;

                HourWeatherItemView c = (HourWeatherItemView) root.getChildAt(0);
                int X = c.getTempX();
                int Y = c.getTempY();


                HourTemperatureView tv = c.findViewById(R.id.ttv);

                tv.setRadius(10);

                int x0 = X + tv.getxPoint();
                int y0 = Y + tv.getyPoint();

                path.reset();

                path.moveTo(x0, y0);


                int lineSize = root.getChildCount();

                //曲线
                float prePreviousPointX = Float.NaN;
                float prePreviousPointY = Float.NaN;
                float previousPointX = Float.NaN;
                float previousPointY = Float.NaN;
                float currentPointX = Float.NaN;
                float currentPointY = Float.NaN;
                float nextPointX = Float.NaN;
                float nextPointY = Float.NaN;

                float prePreviousPointX1 = Float.NaN;
                float prePreviousPointY1 = Float.NaN;
                float previousPointX1 = Float.NaN;
                float previousPointY1 = Float.NaN;
                float currentPointX1 = Float.NaN;
                float currentPointY1 = Float.NaN;
                float nextPointX1 = Float.NaN;
                float nextPointY1 = Float.NaN;

                for (int i = 0; i < lineSize; i++) {

                    //Day
                    if (Float.isNaN(currentPointX)) {
                        HourWeatherItemView curWI = (HourWeatherItemView) root.getChildAt(i);
                        int dayX = curWI.getTempX() + curWI.getWidth() * i;
                        int dayY = curWI.getTempY();
                        HourTemperatureView tempV = curWI.findViewById(R.id.ttv);
                        tempV.setRadius(10);

                        //day2
                        currentPointX = dayX + tempV.getxPoint();
                        currentPointY = dayY + tempV.getyPoint();

                    }
                    if (Float.isNaN(previousPointX)) {
                        if (i > 0) {
                            HourWeatherItemView preWI = (HourWeatherItemView) root.getChildAt(Math.max(i - 1, 0));

                            int dayX0 = preWI.getTempX() + preWI.getWidth() * (i - 1);
                            int dayY0 = preWI.getTempY();
                            HourTemperatureView tempV0 = (HourTemperatureView) preWI.findViewById(R.id.ttv);
                            tempV0.setRadius(10);


                            //day1
                            previousPointX = (int) (dayX0 + tempV0.getxPoint());
                            previousPointY = (int) (dayY0 + tempV0.getyPoint());

                        } else {
                            previousPointX = currentPointX;
                            previousPointY = currentPointY;
                        }
                    }

                    if (Float.isNaN(prePreviousPointX)) {
                        if (i > 1) {

                            HourWeatherItemView preWI = (HourWeatherItemView) root.getChildAt(Math.max(i - 2, 0));

                            int dayX0 = preWI.getTempX() + preWI.getWidth() * (i - 2);
                            int dayY0 = preWI.getTempY();
                            HourTemperatureView tempV0 = (HourTemperatureView) preWI.findViewById(R.id.ttv);
                            tempV0.setRadius(10);

                            //pre pre day
                            prePreviousPointX = (int) (dayX0 + tempV0.getxPoint());
                            prePreviousPointY = (int) (dayY0 + tempV0.getyPoint());


                        } else {
                            prePreviousPointX = previousPointX;
                            prePreviousPointY = previousPointY;
                        }
                    }

                    // nextPoint is always new one or it is equal currentPoint.
                    if (i < lineSize - 1) {

                        HourWeatherItemView nextWI = (HourWeatherItemView) root.getChildAt(Math.min(root.getChildCount() - 1, i + 1));


                        int dayX1 = nextWI.getTempX() + nextWI.getWidth() * (i + 1);
                        int dayY1 = nextWI.getTempY();


                        HourTemperatureView tempV1 = (HourTemperatureView) nextWI.findViewById(R.id.ttv);

                        tempV1.setRadius(10);
                        //day3
                        nextPointX = (int) (dayX1 + tempV1.getxPoint());
                        nextPointY = (int) (dayY1 + tempV1.getyPoint());
                    } else {
                        nextPointX = currentPointX;
                        nextPointY = currentPointY;
                    }


                    //Day and Night
                    if (i == 0) {
                        // Move to start point.
                        path.moveTo(currentPointX, currentPointY);
                    } else {
                        // Calculate control points.
                        final float firstDiffX = (currentPointX - prePreviousPointX);
                        final float firstDiffY = (currentPointY - prePreviousPointY);
                        final float secondDiffX = (nextPointX - previousPointX);
                        final float secondDiffY = (nextPointY - previousPointY);
                        final float firstControlPointX = previousPointX + (intensity * firstDiffX);
                        final float firstControlPointY = previousPointY + (intensity * firstDiffY);
                        final float secondControlPointX = currentPointX - (intensity * secondDiffX);
                        final float secondControlPointY = currentPointY - (intensity * secondDiffY);
                        path.cubicTo(firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                                currentPointX, currentPointY);
                    }

                    // Shift values by one back to prevent recalculation of values that have
                    // been already calculated.
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                    currentPointX = nextPointX;
                    currentPointY = nextPointY;

                    prePreviousPointX1 = previousPointX1;
                    prePreviousPointY1 = previousPointY1;
                    previousPointX1 = currentPointX1;
                    previousPointY1 = currentPointY1;
                    currentPointX1 = nextPointX1;
                    currentPointY1 = nextPointY1;

                }
                if (isDrawPath) {
                    canvas.drawPath(path, paint);
                }

            }

        }
    }


    public float getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        paint.setStrokeWidth(lineWidth);
        invalidate();
    }

    public void setLineColor(int color) {
        this.lineColor = color;
        paint.setColor(lineColor);
        invalidate();
    }

    public List<HourWeatherModel> getList() {
        return list;
    }

    public void setOnWeatherItemClickListener(OnWeatherItemClickListener weatherItemClickListener) {
        this.weatherItemClickListener = weatherItemClickListener;
    }

    public void setList(final List<HourWeatherModel> list) {
        this.list = list;
        int screenWidth = getScreenWidth();
        int max = getMaxTemp(list);
        int min = getMinTemp(list);
        removeAllViews();
        LinearLayout llRoot = new LinearLayout(getContext());
        llRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llRoot.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < list.size(); i++) {
            HourWeatherModel model = list.get(i);
            final HourWeatherItemView itemView = new HourWeatherItemView(getContext());
            itemView.setTimeColor(timeColorId);
            itemView.setTvWeatherColor(weatherColor);
            itemView.setTvTempColor(tvTempColor);
            itemView.setMaxTemp(max);
            itemView.setMinTemp(min);
            itemView.setTime(model.getTime());
            itemView.setTemp(model.getHourTemp());
            itemView.setWeather(model.getHourWeather());
            itemView.setImg(model.getHourPic());


            itemView.setAirLevel(model.getAirLevel());
            itemView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / columnNumber, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setClickable(true);
            final int finalI = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (weatherItemClickListener != null) {
                        weatherItemClickListener.onItemClick(itemView, finalI, list.get(finalI));
                    }
                }
            });
            llRoot.addView(itemView);
        }
        addView(llRoot);
        invalidate();
    }

    public void setColumnNumber(int num) throws Exception {
        if (num > 2) {
            this.columnNumber = num;
            setList(this.list);
        } else {
            throw new Exception("ColumnNumber should lager than 2");
        }
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    private int getMinTemp(List<HourWeatherModel> list) {
        if (list != null) {
            return Collections.min(list, new DayTempComparator()).getHourTemp();
        }
        return 0;
    }

    private int getMaxTemp(List<HourWeatherModel> list) {
        if (list != null) {
            return Collections.max(list, new DayTempComparator()).getHourTemp();
        }
        return 0;
    }

    private static class DayTempComparator implements Comparator<HourWeatherModel> {

        @Override
        public int compare(HourWeatherModel o1, HourWeatherModel o2) {
            if (o1.getHourTemp() == o2.getHourTemp()) {
                return 0;
            } else if (o1.getHourTemp() > o2.getHourTemp()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public void isDrawPath(boolean isDraw) {
        this.isDrawPath = isDraw;
    }

    public interface OnWeatherItemClickListener {
        void onItemClick(HourWeatherItemView itemView, int position, HourWeatherModel weatherModel);
    }

    /**
     * 设置时间颜色
     *
     * @param resId
     */
    public void setTimeColor(int resId) {
        timeColorId = resId;
    }

    /**
     * 设置天气颜色
     *
     * @param resId
     */
    public void setTvWeatherColor(int resId) {
        weatherColor = resId;
    }

    /**
     * 设置文本温度颜色，不是有曲线图的温度颜色
     *
     * @param resId
     */
    public void setTvTempColor(int resId) {
        tvTempColor = resId;
    }
}
