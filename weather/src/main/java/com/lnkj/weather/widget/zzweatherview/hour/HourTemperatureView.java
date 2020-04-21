package com.lnkj.weather.widget.zzweatherview.hour;

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
public class HourTemperatureView extends View {

    private int maxTemp;
    private int minTemp;

    private int temperature;

    private Paint pointPaint;
    private Paint textPaint;
    private int pointColor;
    private int textColor;

    private int radius = 4;
    private int textSize = 26;

    private int xPoint;
    private int yPoint;

    public HourTemperatureView(Context context) {
        this(context, null);
    }

    public HourTemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HourTemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        textColor = 0xffffffff;
        pointColor = 0xffffffff;
    }

    private void initPaint(Context context, AttributeSet attrs) {

        pointPaint = new Paint();

        textPaint = new Paint();

        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);

        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawPoint(canvas);

        drawText(canvas);

    }

    private void drawPoint(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int x = getWidth() / 2;
        int y = (int) (height - height * (temperature - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        xPoint = x;
        yPoint = y;
        canvas.drawCircle(x, y, radius, pointPaint);
    }

    private void drawText(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int yDay = (int) (height - height * (temperature - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        String temp = temperature + "°";
        float wid = textPaint.measureText(temp);
        float hei = textPaint.descent() - textPaint.ascent();
        canvas.drawText(temp, getWidth() / 2 - wid / 2, yDay - radius - hei / 2, textPaint);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getTemperature() {
        return temperature;
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

    public Paint getTextPaint() {
        return textPaint;
    }

    public void setTextPaint(Paint textPaint) {
        this.textPaint = textPaint;
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

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
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
}
