package com.dh.myweatherapp.utils;

import android.provider.Settings;
import android.util.Log;

import com.dh.myweatherapp.bean.CityBean;
import com.dh.myweatherapp.bean.CityListBean;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static org.junit.Assert.*;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class HttpUtilTest {

    @Test
    public void testRequest() throws Exception {

        File file = new File("city.txt");
        if(!file.exists()){
            file.createNewFile();
        }

        OutputStream os = new FileOutputStream(file);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"utf-8"));

        for(int i=0;i<Contacts.CHINA_PROVINCE.length;i++){
            String province = EncodeAndDecodeUtil.getURLEncode(Contacts.CHINA_PROVINCE[i]);
            String result = HttpUtil.request(HttpUtil.CITYLIST_URL, "cityname="+province);
            CityListBean clb = JsonUtil.getCityList(result);
            for(CityBean cb:clb.getRetData()){
                String area_id = cb.getArea_id();
                String name_cn = cb.getName_cn();
                String district_cn = cb.getDistrict_cn();
                String province_cn = cb.getProvince_cn();
                String address = name_cn+","+district_cn+","+province_cn;
                String outString = "\""+area_id+"\",\""+name_cn+"\",\""+district_cn+"\",\""+province_cn+"\",\""+address+"\"";
     //           System.out.println(outString);
                bw.append(outString);
                bw.append("\r\n");
            }

        }

        bw.close();
        os.close();

        //TODO 用文件格式（或者数据库形式）保存所有城市信息以及天气代码，提高程序搜索城市效率，也可以提供模糊查询
        /**
         * 数据库weather_city.db中的city表中的字段 area_id(主键),name_cn,district_cn,province_cn
         *                                     create table city (area_id string primary key, )
         *
         *
         */
    }
}