package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class WeatherRetData {

    private String city;
    private String cityid;
    private TodayWeatherBean today;
    private List<WeatherBean> forecast;
    private List<WeatherBean> history;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public TodayWeatherBean getToday() {
        return today;
    }

    public void setToday(TodayWeatherBean today) {
        this.today = today;
    }

    public List<WeatherBean> getForecast() {
        return forecast;
    }

    public void setForecast(List<WeatherBean> forecast) {
        this.forecast = forecast;
    }

    public List<WeatherBean> getHistory() {
        return history;
    }

    public void setHistory(List<WeatherBean> history) {
        this.history = history;
    }


    @Override
    public String toString() {
        return "WeatherRetData{" +
                "city='" + city + '\'' +
                ", cityid='" + cityid + '\'' +
                ", today=" + today +
                ", forecast=" + forecast +
                ", history=" + history +
                '}';
    }
}
