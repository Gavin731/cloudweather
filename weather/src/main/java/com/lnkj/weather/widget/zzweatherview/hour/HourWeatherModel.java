package com.lnkj.weather.widget.zzweatherview.hour;

/**
 * Model of weather
 *
 * @author Zz
 * 2016/12/8 9:42
 */
public class HourWeatherModel {

    /**
     * 小时温度
     */
    private int hourTemp;
    /**
     * 天气名称
     */
    private String hourWeather;
    /**
     * 24小时时间
     */
    private String time;
    /**
     * 小时天气图标
     */
    private int hourPic;
    /**
     * 空气质量
     */
    private int airLevel;
    /**
     * 风速
     */
    private String wind;
    /**
     * 风速方向
     */
    private String direction;

    private boolean isCurrent;

    public HourWeatherModel() {
    }

    public HourWeatherModel(int hourTemp, String hourWeather, String time, int hourPic,
                            int airLevel, boolean isCurrent, String wind, String direction) {
        this.hourTemp = hourTemp;
        this.hourWeather = hourWeather;
        this.time = time;
        this.hourPic = hourPic;
        this.airLevel = airLevel;
        this.isCurrent = isCurrent;
        this.wind = wind;
        this.direction = direction;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public int getHourTemp() {
        return hourTemp;
    }

    public void setHourTemp(int hourTemp) {
        this.hourTemp = hourTemp;
    }

    public String getHourWeather() {
        return hourWeather;
    }

    public void setHourWeather(String hourWeather) {
        this.hourWeather = hourWeather;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getHourPic() {
        return hourPic;
    }

    public void setHourPic(int hourPic) {
        this.hourPic = hourPic;
    }

    public int getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(int airLevel) {
        this.airLevel = airLevel;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
