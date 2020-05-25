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


/**
 * WeatherItemView
 *
 * @author Zz
 * 2016/12/8 10:32
 */
public class HourWeatherItemView extends LinearLayout {

    private View rootView;
    private TextView tvTime;
    private TextView tvWeather;
    private HourTemperatureView ttvTemp;//显示圆点和温度view
    private TextView tvAirLevel, tvTemperature;
    private ImageView ivWeather;
    private LinearLayout llItemView;

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
        llItemView = rootView.findViewById(R.id.ll_item_view);
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
        if (tvAirLevel != null) {
            if (airLevel >= 0 && airLevel <= 50) {
                tvAirLevel.setBackgroundResource(R.drawable.weather_best_level_shape);
                tvAirLevel.setText("优质");
            } else if (airLevel > 50 && airLevel <= 100) {
                tvAirLevel.setBackgroundResource(R.drawable.weather_good_level_shape);
                tvAirLevel.setText("良好");
            } else if (airLevel > 100 && airLevel <= 150) {
                tvAirLevel.setText("轻度");
                tvAirLevel.setBackgroundResource(R.drawable.weather_small_level_shape);
            } else if (airLevel > 150 && airLevel <= 200) {
                tvAirLevel.setBackgroundResource(R.drawable.weather_mid_level_shape);
                tvAirLevel.setText("中度");
            } else if (airLevel > 200 && airLevel <= 300) {
                tvAirLevel.setBackgroundResource(R.drawable.weather_big_level_shape);
                tvAirLevel.setText("重度");
            } else if (airLevel > 300) {
                tvAirLevel.setBackgroundResource(R.drawable.weather_poison_level_shape);
                tvAirLevel.setText("严重");
            }
        }
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
    }
}
