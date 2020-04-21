package com.lnkj.weather.widget.zzweatherview.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TemperatureView
 *
 * @author Zz
 * 2016/12/8 10:25
 */
public class RainView extends View {

    private int maxTemp;
    private int minTemp;

    private int temperature;

    private Paint pointPaint;
    private int pointColor;
    private int textColor;

    private Paint dashedPaintLittleStart;
    private Paint dashedPaintLittleEnd;
    private Paint dashedPaintMiddleStart;
    private Paint dashedPaintMiddleEnd;
    private Paint dashedPaintLargeStart;
    private Paint dashedPaintLargeEnd;

    private int dashedPaintColor;

    private int radius = 4;

    private int xPoint;
    private int yPoint;

    private int xDashedLittleStart;
    private int xDashedLittleEnd;
    private int yDashedLittle;

    private int xDashedMiddleStart;
    private int xDashedMiddleEnd;
    private int yDashedMiddle;

    private int xDashedLargeStart;
    private int xDashedLargeEnd;
    private int yDashedLarge;

    public RainView(Context context) {
        this(context, null);
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RainView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        pointColor = 0xff5DBDFF;
        dashedPaintColor = 0xffffff;
    }

    private void initPaint(Context context, AttributeSet attrs) {

        pointPaint = new Paint();
        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);

        dashedPaintLittleStart = new Paint();
        dashedPaintLittleStart.setColor(dashedPaintColor);
        dashedPaintLittleStart.setAntiAlias(true);

        dashedPaintLittleEnd = new Paint();
        dashedPaintLittleEnd.setColor(dashedPaintColor);
        dashedPaintLittleEnd.setAntiAlias(true);

        dashedPaintMiddleStart = new Paint();
        dashedPaintMiddleStart.setColor(dashedPaintColor);
        dashedPaintMiddleStart.setAntiAlias(true);

        dashedPaintMiddleEnd = new Paint();
        dashedPaintMiddleEnd.setColor(dashedPaintColor);
        dashedPaintMiddleEnd.setAntiAlias(true);

        dashedPaintLargeStart = new Paint();
        dashedPaintLargeStart.setColor(dashedPaintColor);
        dashedPaintLargeStart.setAntiAlias(true);

        dashedPaintLargeEnd = new Paint();
        dashedPaintLargeEnd.setColor(dashedPaintColor);
        dashedPaintLargeEnd.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPoint(canvas);
        drawDashed(canvas);

    }

    /**
     * 绘制虚线
     * @param canvas
     */
    private void drawDashed(Canvas canvas) {
        int height = getHeight();
        int xs = 0;
        int xe = getWidth();
        int y1 = (int) (height - height * 25 * 1.0f / (maxTemp - minTemp));
        int y2 = (int) (height - height * 50 * 1.0f / (maxTemp - minTemp));
        int y3 = (int) (height - height * 75 * 1.0f / (maxTemp - minTemp));

        xDashedLittleStart = xs;
        xDashedLittleEnd = xe;
        yDashedLittle = y1;

        xDashedMiddleStart = xs;
        xDashedMiddleEnd = xe;
        yDashedMiddle = y2;

        xDashedLargeStart = xs;
        xDashedLargeEnd = xe;
        yDashedLarge = y3;

        canvas.drawCircle(xs, y1, 4, dashedPaintLittleStart);
        canvas.drawCircle(xe, y1, 4, dashedPaintLittleEnd);
        canvas.drawCircle(xs, y2, 4, dashedPaintMiddleStart);
        canvas.drawCircle(xe, y2, 4, dashedPaintMiddleEnd);
        canvas.drawCircle(xs, y3, 4, dashedPaintLargeStart);
        canvas.drawCircle(xe, y3, 4, dashedPaintLargeEnd);
    }

    private void drawPoint(Canvas canvas) {
        int height = getHeight();
        int x = getWidth() / 2;
        int y = (int) (height - height * temperature * 1.0f / (maxTemp - minTemp));
        xPoint = x;
        yPoint = y;
        canvas.drawCircle(x, y, radius, pointPaint);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Paint getPointPaint() {
        return pointPaint;
    }

    public void setPointPaint(Paint pointPaint) {
        this.pointPaint = pointPaint;
    }

    public int getPointColor() {
        return pointColor;
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        this.xPoint = xPoint;
    }

    public int getyPoint() {
        return yPoint;
    }

    public void setyPoint(int yPoint) {
        this.yPoint = yPoint;
    }

    public int getxDashedLittleStart() {
        return xDashedLittleStart;
    }

    public void setxDashedLittleStart(int xDashedLittleStart) {
        this.xDashedLittleStart = xDashedLittleStart;
    }

    public int getxDashedLittleEnd() {
        return xDashedLittleEnd;
    }

    public void setxDashedLittleEnd(int xDashedLittleEnd) {
        this.xDashedLittleEnd = xDashedLittleEnd;
    }

    public int getyDashedLittle() {
        return yDashedLittle;
    }

    public void setyDashedLittle(int yDashedLittle) {
        this.yDashedLittle = yDashedLittle;
    }

    public int getxDashedMiddleStart() {
        return xDashedMiddleStart;
    }

    public void setxDashedMiddleStart(int xDashedMiddleStart) {
        this.xDashedMiddleStart = xDashedMiddleStart;
    }

    public int getxDashedMiddleEnd() {
        return xDashedMiddleEnd;
    }

    public void setxDashedMiddleEnd(int xDashedMiddleEnd) {
        this.xDashedMiddleEnd = xDashedMiddleEnd;
    }

    public int getyDashedMiddle() {
        return yDashedMiddle;
    }

    public void setyDashedMiddle(int yDashedMiddle) {
        this.yDashedMiddle = yDashedMiddle;
    }

    public int getxDashedLargeStart() {
        return xDashedLargeStart;
    }

    public void setxDashedLargeStart(int xDashedLargeStart) {
        this.xDashedLargeStart = xDashedLargeStart;
    }

    public int getxDashedLargeEnd() {
        return xDashedLargeEnd;
    }

    public void setxDashedLargeEnd(int xDashedLargeEnd) {
        this.xDashedLargeEnd = xDashedLargeEnd;
    }

    public int getyDashedLarge() {
        return yDashedLarge;
    }

    public void setyDashedLarge(int yDashedLarge) {
        this.yDashedLarge = yDashedLarge;
    }
}
