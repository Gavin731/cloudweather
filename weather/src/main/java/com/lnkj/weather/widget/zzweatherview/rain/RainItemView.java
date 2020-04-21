package com.lnkj.weather.widget.zzweatherview.rain;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lnkj.weather.R;


/**
 * WeatherItemView
 *
 * @author Zz
 * 2016/12/8 10:32
 */
public class RainItemView extends LinearLayout {

    private View rootView;
    private TextView tvTime;
    private RainView ttvTemp;

    public RainItemView(Context context) {
        this(context, null);
    }

    public RainItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RainItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rootView = LayoutInflater.from(context).inflate(R.layout.weather_rain_view, null);
        tvTime = rootView.findViewById(R.id.tv_time);
        ttvTemp = rootView.findViewById(R.id.ttv);
        rootView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
    }

    public void setTime(String time) {
        if (tvTime != null)
            tvTime.setText(time);
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

    public void setTemp(int temp) {
        if (ttvTemp != null)
            ttvTemp.setTemperature(temp);
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
