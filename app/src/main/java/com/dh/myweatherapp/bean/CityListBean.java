package com.dh.myweatherapp.bean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class CityListBean {

    private int errNum;
    private String errMsg;
    private List<CityBean> retData;

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

    public List<CityBean> getRetData() {
        return retData;
    }

    public void setRetData(List<CityBean> retData) {
        this.retData = retData;
    }


    @Override
    public String toString() {
        return "CityListBean{" +
                "errNum=" + errNum +
                ", errMsg='" + errMsg + '\'' +
                ", retData=" + retData +
                '}';
    }
}
