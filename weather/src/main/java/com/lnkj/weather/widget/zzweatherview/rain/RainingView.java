package com.lnkj.weather.widget.zzweatherview.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.lnkj.weather.R;

import java.util.Comparator;
import java.util.List;


/**
 * ZzWeatherView
 *
 * @author Zz
 * 2016/12/8 9:29
 */
public class RainingView extends HorizontalScrollView {

    private List<RainModel> list;
    private Paint paint;

    protected Path path;

    private float lineWidth = 3f;

    private int lineColor = 0xFF5DBDFF;

    private int columnNumber = 5;

    // 绘制虚线
    private Paint dashedPaintLittle;
    private Path dashedPathLittle;

    private Paint dashedPaintMiddle;
    private Path dashedPathMiddle;

    private Paint dashedPaintLarge;
    private Path dashedPathLarge;


    public RainingView(Context context) {
        this(context, null);
    }

    public RainingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RainingView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setColor(lineColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(lineWidth);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();

        dashedPaintLittle = new Paint();
        dashedPaintLittle.setColor(0x80ffffff);
        dashedPaintLittle.setAntiAlias(true);
        dashedPaintLittle.setStrokeWidth(1f);
        dashedPaintLittle.setStyle(Paint.Style.STROKE);
        dashedPaintLittle.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        dashedPathLittle = new Path();

        dashedPaintMiddle = new Paint();
        dashedPaintMiddle.setColor(0x80ffffff);
        dashedPaintMiddle.setAntiAlias(true);
        dashedPaintMiddle.setStrokeWidth(1f);
        dashedPaintMiddle.setStyle(Paint.Style.STROKE);
        dashedPaintMiddle.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        dashedPathMiddle = new Path();

        dashedPaintLarge = new Paint();
        dashedPaintLarge.setColor(0x80ffffff);
        dashedPaintLarge.setAntiAlias(true);
        dashedPaintLarge.setStrokeWidth(1f);
        dashedPaintLarge.setStyle(Paint.Style.STROKE);
        dashedPaintLarge.setPathEffect(new DashPathEffect(new float[]{10, 5}, 0));

        dashedPathLarge = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getChildCount() > 0) {
            ViewGroup root = (ViewGroup) getChildAt(0);

            if (root.getChildCount() > 0) {
                showLLIndex(root);

                drawDashedLittle(canvas, root);
                drawDashedMiddle(canvas, root);
                drawDashedLarge(canvas, root);
                float intensity = 0.16f;

                RainItemView c = (RainItemView) root.getChildAt(0);
                int X = c.getTempX();
                int Y = c.getTempY();
                RainView tv = c.findViewById(R.id.ttv);
                tv.setRadius(4);
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

                for (int i = 0; i < lineSize; i++) {

                    //Day
                    if (Float.isNaN(currentPointX)) {
                        RainItemView curWI = (RainItemView) root.getChildAt(i);
                        int dayX = curWI.getTempX() + curWI.getWidth() * i;
                        int dayY = curWI.getTempY();
                        RainView tempV = curWI.findViewById(R.id.ttv);
                        tempV.setRadius(10);

                        //day2
                        currentPointX = dayX + tempV.getxPoint();
                        currentPointY = dayY + tempV.getyPoint();

                    }
                    if (Float.isNaN(previousPointX)) {
                        if (i > 0) {
                            RainItemView preWI = (RainItemView) root.getChildAt(Math.max(i - 1, 0));

                            int dayX0 = preWI.getTempX() + preWI.getWidth() * (i - 1);
                            int dayY0 = preWI.getTempY();
                            RainView tempV0 = preWI.findViewById(R.id.ttv);
                            tempV0.setRadius(4);


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

                            RainItemView preWI = (RainItemView) root.getChildAt(Math.max(i - 2, 0));

                            int dayX0 = preWI.getTempX() + preWI.getWidth() * (i - 2);
                            int dayY0 = preWI.getTempY();
                            RainView tempV0 = preWI.findViewById(R.id.ttv);
                            tempV0.setRadius(4);

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

                        RainItemView nextWI = (RainItemView) root.getChildAt(Math.min(root.getChildCount() - 1, i + 1));


                        int dayX1 = nextWI.getTempX() + nextWI.getWidth() * (i + 1);
                        int dayY1 = nextWI.getTempY();


                        RainView tempV1 = nextWI.findViewById(R.id.ttv);

                        tempV1.setRadius(4);
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


                }

                canvas.drawPath(path, paint);

            }

        }
    }

    private void showLLIndex(ViewGroup root) {
        int lineSize = root.getChildCount();
        for (int i = 0; i < lineSize; i++){
            RainItemView c = (RainItemView) root.getChildAt(i);
            LinearLayout linearLayout = c.findViewById(R.id.ll_index);
            if (i == 0){
                linearLayout.setVisibility(View.VISIBLE);
            }else {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    private void drawDashedLarge(Canvas canvas, ViewGroup root) {
        RainItemView c = (RainItemView) root.getChildAt(0);
        int X = c.getTempX();
        int Y = c.getTempY();
        RainView tv = c.findViewById(R.id.ttv);

        int x0 = X + tv.getxDashedLargeStart();
        int y0 = Y + tv.getyDashedLarge();

        dashedPathLarge.reset();
        dashedPathLarge.moveTo(x0, y0);

        int lineSize = root.getChildCount();

        for (int i = 0; i < lineSize; i++){

            RainItemView curWI = (RainItemView) root.getChildAt(i);
            int dX = curWI.getTempX() + curWI.getWidth() * i;
            int dY = curWI.getTempY();

            RainView tempV = curWI.findViewById(R.id.ttv);
            tempV.setRadius(4);

            int xs = dX + tempV.getxDashedLargeStart();
            int y = dY + tempV.getyDashedLarge();

            int xe = dX + tempV.getxDashedLargeEnd();

            if (i == 0){
                dashedPathLarge.moveTo(xs, y);
            }else {
                dashedPathLarge.lineTo(xs, y);
            }

            dashedPathLarge.lineTo(xe, y);

        }

        canvas.drawPath(dashedPathLarge, dashedPaintLarge);
    }

    private void drawDashedMiddle(Canvas canvas, ViewGroup root) {
        RainItemView c = (RainItemView) root.getChildAt(0);
        int X = c.getTempX();
        int Y = c.getTempY();
        RainView tv = c.findViewById(R.id.ttv);

        int x0 = X + tv.getxDashedMiddleStart();
        int y0 = Y + tv.getyDashedMiddle();

        dashedPathMiddle.reset();
        dashedPathMiddle.moveTo(x0, y0);

        int lineSize = root.getChildCount();

        for (int i = 0; i < lineSize; i++){

            RainItemView curWI = (RainItemView) root.getChildAt(i);
            int dX = curWI.getTempX() + curWI.getWidth() * i;
            int dY = curWI.getTempY();

            RainView tempV = curWI.findViewById(R.id.ttv);
            tempV.setRadius(4);

            int xs = dX + tempV.getxDashedMiddleStart();
            int y = dY + tempV.getyDashedMiddle();

            int xe = dX + tempV.getxDashedMiddleEnd();

            if (i == 0){
                dashedPathMiddle.moveTo(xs, y);
            }else {
                dashedPathMiddle.lineTo(xs, y);
            }

            dashedPathMiddle.lineTo(xe, y);

        }

        canvas.drawPath(dashedPathMiddle, dashedPaintMiddle);
    }

    /**
     * 绘制低级虚线
     * @param canvas
     * @param root
     */
    private void drawDashedLittle(Canvas canvas, ViewGroup root) {

        RainItemView c = (RainItemView) root.getChildAt(0);
        int X = c.getTempX();
        int Y = c.getTempY();
        RainView tv = c.findViewById(R.id.ttv);

        int x0 = X + tv.getxDashedLargeStart();
        int y0 = Y + tv.getyDashedLittle();

        dashedPathLittle.reset();
        dashedPathLittle.moveTo(x0, y0);

        int lineSize = root.getChildCount();

        for (int i = 0; i < lineSize; i++){

            RainItemView curWI = (RainItemView) root.getChildAt(i);
            int dX = curWI.getTempX() + curWI.getWidth() * i;
            int dY = curWI.getTempY();

            RainView tempV = curWI.findViewById(R.id.ttv);
            tempV.setRadius(4);

            int xs = dX + tempV.getxDashedLargeStart();
            int y = dY + tempV.getyDashedLittle();

            int xe = dX + tempV.getxDashedLargeEnd();

            if (i == 0){
                dashedPathLittle.moveTo(xs, y);
            }else {
                dashedPathLittle.lineTo(xs, y);
            }

            dashedPathLittle.lineTo(xe, y);

        }

        canvas.drawPath(dashedPathLittle, dashedPaintLittle);
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

    public List<RainModel> getList() {
        return list;
    }

    public void setList(final List<RainModel> list) {
        this.list = list;
        int screenWidth = getScreenWidth();
        int max = 75;
        int min = 0;
        removeAllViews();
        LinearLayout llRoot = new LinearLayout(getContext());
        llRoot.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        llRoot.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < list.size(); i++) {
            RainModel model = list.get(i);
            final RainItemView itemView = new RainItemView(getContext());
            itemView.setMaxTemp(max);
            itemView.setMinTemp(min);
            itemView.setTime(model.getTime());
            itemView.setTemp(model.getRainValue());

            itemView.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / columnNumber, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setClickable(true);
            final int finalI = i;
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

    private static class DayTempComparator implements Comparator<RainModel> {

        @Override
        public int compare(RainModel o1, RainModel o2) {
            if (o1.getRainValue() == o2.getRainValue()) {
                return 0;
            } else if (o1.getRainValue() > o2.getRainValue()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
