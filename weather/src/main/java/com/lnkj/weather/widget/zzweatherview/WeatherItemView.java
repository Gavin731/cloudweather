package com.lnkj.weather.widget.zzweatherview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lnkj.weather.R;
import com.lnkj.weather.utils.WeatherUtils;


/**
 * WeatherItemView
 *
 * @author Zz
 * 2016/12/8 10:32
 */
public class WeatherItemView extends LinearLayout {

    private View rootView;
    private TextView tvWeek;
    private TextView tvDate;
    private TextView tvDayWeather;
    private TextView tvNightWeather;
    private TemperatureView ttvTemp;
    private TextView tvWindOri;
    private TextView tvWindLevel;
    private TextView tvAirLevel;
    private ImageView ivDayWeather;
    private ImageView ivNightWeather;
    private LinearLayout llItemView;

    public WeatherItemView(Context context) {
        this(context, null);
    }

    public WeatherItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeatherItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.weather_chart_day_item, null);
        tvWeek = rootView.findViewById(R.id.tv_week);
        tvDate = rootView.findViewById(R.id.tv_date);
        tvDayWeather = rootView.findViewById(R.id.tv_day_weather);
        tvNightWeather = rootView.findViewById(R.id.tv_night_weather);
        ttvTemp = rootView.findViewById(R.id.ttv_day);
        tvWindOri = rootView.findViewById(R.id.tv_wind_ori);
        tvWindLevel = rootView.findViewById(R.id.tv_wind_level);
        ivDayWeather = rootView.findViewById(R.id.iv_day_weather);
        ivNightWeather = rootView.findViewById(R.id.iv_night_weather);
        tvAirLevel = rootView.findViewById(R.id.tv_air_level);
        llItemView = rootView.findViewById(R.id.ll_item_view);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
    }

    public void setWeek(String week) {
        if (tvWeek != null)
            tvWeek.setText(week);
    }

    public void setDate(String date) {
        if (tvDate != null)
            tvDate.setText(date);
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

    public void setDayWeather(String dayWeather) {
        if (tvDayWeather != null)
            tvDayWeather.setText(dayWeather);
    }

    public void setNightWeather(String nightWeather) {
        if (tvNightWeather != null)
            tvNightWeather.setText(nightWeather);
    }

    public void setWindOri(String windOri) {
        if (tvWindOri != null)
            tvWindOri.setText(windOri);
    }

    public void setWindLevel(String windLevel) {
        if (tvWindLevel != null)
            tvWindLevel.setText(windLevel);
    }

    public void canClick(boolean clickable) {
        if (clickable) {
            llItemView.setBackgroundResource(R.drawable.weather_selector_hour_press_bg);
        }
    }

    public void setYesterdayBg(boolean isYesterday) {
        if (isYesterday) {
            tvWeek.setTextColor(Color.parseColor("#66ffffff"));
            tvDate.setTextColor(Color.parseColor("#66ffffff"));
            tvDayWeather.setTextColor(Color.parseColor("#66ffffff"));
            tvNightWeather.setTextColor(Color.parseColor("#66ffffff"));
            ttvTemp.setPointDayColor(0x66FFDF31);
            ttvTemp.setPointNightColor(0x665DBDFF);
            ttvTemp.setTextDayColor(Color.parseColor("#66ffffff"));
            ttvTemp.setTextNightColor(Color.parseColor("#66ffffff"));
            tvWindOri.setTextColor(Color.parseColor("#66ffffff"));
            tvWindLevel.setTextColor(Color.parseColor("#66ffffff"));
            tvAirLevel.setTextColor(Color.parseColor("#66ffffff"));
            ivDayWeather.setAlpha(0.5f);
            ivNightWeather.setAlpha(0.5f);
        } else {
            tvWeek.setTextColor(Color.parseColor("#ffffff"));
            tvDate.setTextColor(Color.parseColor("#ffffff"));
            tvDayWeather.setTextColor(Color.parseColor("#ffffff"));
            tvNightWeather.setTextColor(Color.parseColor("#ffffff"));
            ttvTemp.setPointDayColor(0xFFFFDF31);
            ttvTemp.setPointNightColor(0xFF5DBDFF);
            ttvTemp.setTextDayColor(Color.parseColor("#ffffff"));
            ttvTemp.setTextNightColor(Color.parseColor("#ffffff"));
            tvWindOri.setTextColor(Color.parseColor("#ffffff"));
            tvWindLevel.setTextColor(Color.parseColor("#ffffff"));
            tvAirLevel.setTextColor(Color.parseColor("#ffffff"));
            ivDayWeather.setAlpha(1f);
            ivNightWeather.setAlpha(1f);
        }
    }

    public void setAirLevel(int airLevel) {
        WeatherUtils.INSTANCE.setAirLevel(tvAirLevel, airLevel);
    }

    public void setDayTemp(int dayTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureDay(dayTemp);
    }

    public void setNightTemp(int nightTemp) {
        if (ttvTemp != null)
            ttvTemp.setTemperatureNight(nightTemp);
    }

    public void setDayImg(int resId) {
        if (ivDayWeather != null)
            ivDayWeather.setImageResource(resId);
    }

    public void setNightImg(int resId) {
        if (ivNightWeather != null)
            ivNightWeather.setImageResource(resId);
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
}
