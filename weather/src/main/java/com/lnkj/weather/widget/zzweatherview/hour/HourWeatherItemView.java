package com.lnkj.weather.widget.zzweatherview.hour;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lnkj.weather.R;
import com.lnkj.weather.utils.WeatherUtils;
import com.mufeng.roundview.RoundTextView;


/**
 * WeatherItemView
 *
 * @author Zz
 * 2016/12/8 10:32
 */
public class HourWeatherItemView extends LinearLayout {

    private View rootView;
    private TextView tvTime,tvWind,tvDirection;
    private TextView tvWeather;
    private HourTemperatureView ttvTemp;//显示圆点和温度view
    private TextView tvAirLevel, tvTemperature;
    private ImageView ivWeather;
    private LinearLayout llItemView;
    private RoundTextView tvAirLevel2;

    private boolean isShowTempView = false;//是否显示圆点和温度view,隐藏则只显示温度text

    public HourWeatherItemView(Context context) {
        this(context, null);
    }

    public HourWeatherItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HourWeatherItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.weather_chart_hour_item, null);
        tvTime = rootView.findViewById(R.id.tv_time);
        tvWeather = rootView.findViewById(R.id.tv_weather);
        ttvTemp = rootView.findViewById(R.id.ttv);
        tvTemperature = rootView.findViewById(R.id.tv_temp);
        ivWeather = rootView.findViewById(R.id.iv_weather);
        tvAirLevel = rootView.findViewById(R.id.tv_air_level);
        tvAirLevel2 = rootView.findViewById(R.id.tv_air_level2);
        llItemView = rootView.findViewById(R.id.ll_item_view);
        tvWind=rootView.findViewById(R.id.tv_wind);
        tvDirection=rootView.findViewById(R.id.tv_direction);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
        if (isShowTempView) {
            ttvTemp.setVisibility(VISIBLE);
            tvTemperature.setVisibility(GONE);
        } else {
            ttvTemp.setVisibility(GONE);
            tvTemperature.setVisibility(VISIBLE);
        }
    }

    public void setTime(String time) {
        if (tvTime != null)
            tvTime.setText(time);
    }

    public void setTimeColor(int resId) {
        tvTime.setTextColor(resId);
    }

    public int getTempX() {
        if (ttvTemp != null)
            return (int) ttvTemp.getX();
        return 0;
    }

    public int getTempY() {
        if (ttvTemp != null)
            return (int) ttvTemp.getY();
        return 0;
    }

    public void setWeather(String weather) {
        if (tvWeather != null)
            tvWeather.setText(weather);
    }

    public void setAirLevel(int airLevel) {
        WeatherUtils.INSTANCE.setAirLevel(tvAirLevel, airLevel);
        tvAirLevel2.getDelegate().setBackgroundColor(getContext().getResources().getColor(WeatherUtils.INSTANCE.getAirQualityColor(airLevel)));
    }

    public void setTemp(int temp) {
        if (ttvTemp != null)
            ttvTemp.setTemperature(temp);

        tvTemperature.setText(temp + "°");
    }

    public void setImg(int resId) {
        if (ivWeather != null)
            ivWeather.setImageResource(resId);
    }

    public void setMaxTemp(int max) {
        if (ttvTemp != null)
            ttvTemp.setMaxTemp(max);
    }

    public void setMinTemp(int min) {
        if (ttvTemp != null) {
            ttvTemp.setMinTemp(min);
        }
    }

    public void setTvWeatherColor(int resId) {
        tvWeather.setTextColor(resId);
    }

    public void setTvTempColor(int resId) {
        tvTemperature.setTextColor(resId);
        tvWind.setTextColor(resId);
    }

    public void setWind(String wind){
        if(tvWind!=null){
            tvWind.setText(wind);
        }
    }

    public void setDirection(String direction){
        if(tvDirection!=null){
            tvDirection.setText(direction);
        }
    }
}
