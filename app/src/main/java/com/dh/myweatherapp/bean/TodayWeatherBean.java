package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class TodayWeatherBean {

    private WeatherBean tody;
    private List<IndexBean> index;
    public List<IndexBean> getIndex() {
        return index;
    }

    public WeatherBean getTody() {
        return tody;
    }

    public void setTody(WeatherBean tody) {
        this.tody = tody;
    }

    public void setIndex(List<IndexBean> index) {
        this.index = index;
    }
}
