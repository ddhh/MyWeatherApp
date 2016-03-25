package com.dh.myweatherapp.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 端辉 on 2016/3/24.
 *
 * 进去网络请求与响应出来的工具类
 * 进行对天气预报接口操作
 *
 */
public class HttpUtil {

    public static final String API_KET = "cebaf187fb0948f21045aee7ff53aa28";
    public static final String CITYLIST_URL = "http://apis.baidu.com/apistore/weatherservice/citylist";
    public static final String CITYINFO_URL = "http://apis.baidu.com/apistore/weatherservice/cityinfo";
    public static final String RECENT_WEATHERS_URL = "http://apis.baidu.com/apistore/weatherservice/recentweathers";


    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",API_KET);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getCitylistJson(String cityStr){
        String httpArg = "cityname="+EncodeAndDecodeUtil.getURLEncode(cityStr);
        return request(CITYLIST_URL,httpArg);
    }

    public static String getCityinfoJson(String httpArg){
        return request(CITYINFO_URL,httpArg);
    }

    public static String getRecentWeathersJson(String httpArg) {
        return request(RECENT_WEATHERS_URL,httpArg);
    }
}
