package com.dh.myweatherapp.utils;

import android.provider.Settings;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class HttpUtilTest {

    @Test
    public void testRequest() throws Exception {
        String result = HttpUtil.request(HttpUtil.CITYLIST_URL, "cityname=%E6%9C%9D%E9%98%B3");
        System.out.print(result);
    }
}