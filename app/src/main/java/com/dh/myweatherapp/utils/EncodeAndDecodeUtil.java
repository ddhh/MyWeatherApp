package com.dh.myweatherapp.utils;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by 端辉 on 2016/3/24.
 * 解码编码的工具类
 */
public class EncodeAndDecodeUtil {

    public static String getURLEncode(String s) {
        String result = s;
        try {
            result = URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getURLDecode(String s) {
        String result = s;
        try {
            result = URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String decodeUnicode(String s){
        String result = "";
        for(int i=0;i<s.length();i+=4){
            for(int j=i;j<i+4;j++){
                result+=String.valueOf(s.charAt(j));
            }
            result+=String.valueOf((char)Integer.valueOf(result, 16).intValue());
        }
        return result;
    }

}
