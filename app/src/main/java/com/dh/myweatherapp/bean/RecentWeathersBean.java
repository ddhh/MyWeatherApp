package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class RecentWeathersBean {

    private int errNum;
    private String errMsg;
    private WeatherRetData retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public WeatherRetData getRetData() {
        return retData;
    }

    public void setRetData(WeatherRetData retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "RecentWeathersBean{" +
                "errNum=" + errNum +
                ", errMsg='" + errMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
