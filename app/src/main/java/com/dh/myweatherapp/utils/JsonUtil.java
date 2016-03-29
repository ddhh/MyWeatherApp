package com.dh.myweatherapp.utils;

import android.util.Log;

import com.dh.myweatherapp.bean.CityBean;
import com.dh.myweatherapp.bean.CityListBean;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 * 解析网络请求得到的Json字符串
 */
public class JsonUtil {

    public static CityListBean getCityList(String jsonString) {
//        Log.d("JsonUtil", jsonString);
        CityListBean clb = new CityListBean();
        Gson gson = new Gson();
        clb = gson.fromJson(jsonString, new TypeToken<CityListBean>(){}.getType());
  //      Log.d("JsonUtil", clb.toString());
        return clb;
    }


    public static RecentWeathersBean getRecentWeather(String jsonString){
        Log.d("JsonUtil", jsonString);
        RecentWeathersBean rwb = new RecentWeathersBean();
        Gson gson = new Gson();
        rwb = gson.fromJson(jsonString,new TypeToken<RecentWeathersBean>(){}.getType());
        Log.d("JsonUtil", rwb.toString());
        return rwb;
    }

}
