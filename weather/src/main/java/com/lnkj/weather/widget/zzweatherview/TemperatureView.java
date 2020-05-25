package com.lnkj.weather.widget.zzweatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lnkj.weather.R;

/**
 * TemperatureView
 *
 * @author Zz
 * 2016/12/8 10:25
 */
public class TemperatureView extends View {

    private int maxTemp;
    private int minTemp;

    private int temperatureDay;
    private int temperatureNight;

    private Paint pointDayPaint;
    private Paint pointNightPaint;
    private Paint textDayPaint;
    private Paint textNightPaint;
    private int pointDayColor;
    private int pointNightColor;
    private int textDayColor;
    private int textNightColor;

    private int radius = 4;
    private int textSize = 26;

    private int xPointDay;
    private int yPointDay;
    private int xPointNight;
    private int yPointNight;
    private int mWidth;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        textDayColor = context.getResources().getColor(R.color.weather_color_222222);
        textNightColor = context.getResources().getColor(R.color.weather_color_222222);
        pointDayColor = 0xFFFFDF31;
        pointNightColor = 0xFF5DBDFF;
    }

    private void initPaint(Context context, AttributeSet attrs) {

        pointDayPaint = new Paint();
        pointNightPaint = new Paint();

        textDayPaint = new Paint();
        textNightPaint = new Paint();

        pointDayPaint.setColor(pointDayColor);
        pointDayPaint.setAntiAlias(true);

        pointNightPaint.setColor(pointNightColor);
        pointNightPaint.setAntiAlias(true);

        textDayPaint.setColor(textDayColor);
        textDayPaint.setTextSize(textSize);
        textDayPaint.setAntiAlias(true);

        textNightPaint.setColor(textNightColor);
        textNightPaint.setTextSize(textSize);
        textNightPaint.setAntiAlias(true);

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
        int y = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        int x2 = getWidth() / 2;
        int y2 = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        xPointDay = x;
        yPointDay = y;
        xPointNight = x2;
        yPointNight = y2;
        mWidth = getWidth();
        canvas.drawCircle(x, y, radius, pointDayPaint);
        canvas.drawCircle(x2, y2, radius, pointNightPaint);
    }

    private void drawText(Canvas canvas) {
        int height = getHeight() - textSize * 4;
        int yDay = (int) (height - height * (temperatureDay - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        int yNight = (int) (height - height * (temperatureNight - minTemp) * 1.0f / (maxTemp - minTemp)) + textSize * 2;
        String dayTemp = temperatureDay + "°";
        String nightTemp = temperatureNight + "°";
        float widDay = textDayPaint.measureText(dayTemp);
        float widNight = textNightPaint.measureText(nightTemp);
        float hei = textDayPaint.descent() - textDayPaint.ascent();
        canvas.drawText(dayTemp, getWidth() / 2 - widDay / 2, yDay - radius - hei / 2, textDayPaint);
        canvas.drawText(nightTemp, getWidth() / 2 - widNight / 2, yNight + radius + hei, textNightPaint);
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

    public int getxPointDay() {
        return xPointDay;
    }

    public int getyPointDay() {
        return yPointDay;
    }

    public void setxPointDay(int xPointDay) {
        this.xPointDay = xPointDay;
    }

    public void setyPointDay(int yPointDay) {
        this.yPointDay = yPointDay;
    }

    public int getxPointNight() {
        return xPointNight;
    }

    public void setxPointNight(int xPointNight) {
        this.xPointNight = xPointNight;
    }

    public int getyPointNight() {
        return yPointNight;
    }

    public void setyPointNight(int yPointNight) {
        this.yPointNight = yPointNight;
    }

    public int getmWidth() {
        return mWidth;
    }

    public int getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureDay(int temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public int getPointDayColor() {
        return pointDayColor;
    }

    public void setPointDayColor(int pointDayColor) {
        this.pointDayColor = pointDayColor;
    }

    public int getPointNightColor() {
        return pointNightColor;
    }

    public void setPointNightColor(int pointNightColor) {
        this.pointNightColor = pointNightColor;
    }

    public int getTextDayColor() {
        return textDayColor;
    }

    public void setTextDayColor(int textDayColor) {
        this.textDayColor = textDayColor;
    }

    public int getTextNightColor() {
        return textNightColor;
    }

    public void setTextNightColor(int textNightColor) {
        this.textNightColor = textNightColor;
    }

    public int getTemperatureNight() {
        return temperatureNight;
    }

    public void setTemperatureNight(int temperatureNight) {
        this.temperatureNight = temperatureNight;
    }
}
