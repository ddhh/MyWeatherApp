package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class HistoryWeatherBean {

    private List<WeatherBean> historytList;

    public List<WeatherBean> getHistorytList() {
        return historytList;
    }

    public void setHistorytList(List<WeatherBean> historytList) {
        this.historytList = historytList;
    }
}
