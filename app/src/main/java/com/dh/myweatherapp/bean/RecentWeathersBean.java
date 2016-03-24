package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class RecentWeathersBean {

    private String city; //城市名
    private String cityid;//城市ID
    private TodayWeatherBean today;//今天的天气
    private ForecastWeatherBean forecast;//未来四天的天气
    private HistoryWeatherBean history;  //历史七天的天气

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

    public ForecastWeatherBean getForecast() {
        return forecast;
    }

    public void setForecast(ForecastWeatherBean forecast) {
        this.forecast = forecast;
    }

    public HistoryWeatherBean getHistory() {
        return history;
    }

    public void setHistory(HistoryWeatherBean history) {
        this.history = history;
    }
}
