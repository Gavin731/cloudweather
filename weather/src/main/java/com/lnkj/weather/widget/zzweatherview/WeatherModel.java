package com.lnkj.weather.widget.zzweatherview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model of weather
 * @author Zz
 * 2016/12/8 9:42
 */
public class WeatherModel implements Parcelable {

    private int dayTemp; //temperature of day
    private int nightTemp; //temperature of night
    private String dayWeather; //weather of day
    private String nightWeather; // weather of night
    private String date; //date
    private String week; //week
    private int dayPic; //image res id of day
    private int nightPic; // image res id if night
    private String windOrientation; //orientation of wind
    private String windLevel; //level of wind
    private int airLevel; // level of air quality

    public WeatherModel() {
    }

    public WeatherModel(int dayTemp, int nightTemp, String dayWeather, String nightWeather, String date, String week, int dayPic, int nightPic, String windOrientation, String windLevel, int airLevel) {
        this.dayTemp = dayTemp;
        this.nightTemp = nightTemp;
        this.dayWeather = dayWeather;
        this.nightWeather = nightWeather;
        this.date = date;
        this.week = week;
        this.dayPic = dayPic;
        this.nightPic = nightPic;
        this.windOrientation = windOrientation;
        this.windLevel = windLevel;
        this.airLevel = airLevel;
    }

    protected WeatherModel(Parcel in) {
        dayTemp = in.readInt();
        nightTemp = in.readInt();
        dayWeather = in.readString();
        nightWeather = in.readString();
        date = in.readString();
        week = in.readString();
        dayPic = in.readInt();
        nightPic = in.readInt();
        windOrientation = in.readString();
        windLevel = in.readString();
        airLevel = in.readInt();
    }

    public static final Creator<WeatherModel> CREATOR = new Creator<WeatherModel>() {
        @Override
        public WeatherModel createFromParcel(Parcel in) {
            return new WeatherModel(in);
        }

        @Override
        public WeatherModel[] newArray(int size) {
            return new WeatherModel[size];
        }
    };

    public int getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(int dayTemp) {
        this.dayTemp = dayTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(int nightTemp) {
        this.nightTemp = nightTemp;
    }

    public String getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(String dayWeather) {
        this.dayWeather = dayWeather;
    }

    public String getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(String nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getDayPic() {
        return dayPic;
    }

    public void setDayPic(int dayPic) {
        this.dayPic = dayPic;
    }

    public int getNightPic() {
        return nightPic;
    }

    public void setNightPic(int nightPic) {
        this.nightPic = nightPic;
    }

    public String getWindOrientation() {
        return windOrientation;
    }

    public void setWindOrientation(String windOrientation) {
        this.windOrientation = windOrientation;
    }

    public String getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(String windLevel) {
        this.windLevel = windLevel;
    }

    public int getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(int airLevel) {
        this.airLevel = airLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dayTemp);
        dest.writeInt(nightTemp);
        dest.writeString(dayWeather);
        dest.writeString(nightWeather);
        dest.writeString(date);
        dest.writeString(week);
        dest.writeInt(dayPic);
        dest.writeInt(nightPic);
        dest.writeString(windOrientation);
        dest.writeString(windLevel);
        dest.writeInt(airLevel);
    }
}
