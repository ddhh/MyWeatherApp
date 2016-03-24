package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class ForecastWeatherBean {

    private List<WeatherBean> forecastList;


    public List<WeatherBean> getForecastList() {
        return forecastList;
    }

    public void setForecastList(List<WeatherBean> forecastList) {
        this.forecastList = forecastList;
    }
}
