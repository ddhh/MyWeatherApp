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

        for(int i=0;i<Contacts.CHINA_PROVINCE.length;i++){
            String province = EncodeAndDecodeUtil.getURLEncode(Contacts.CHINA_PROVINCE[i]);
            String result = HttpUtil.request(HttpUtil.CITYLIST_URL, "cityname="+province);
            System.out.println(JsonUtil.getCityList(result).toString());
        }
    }
}