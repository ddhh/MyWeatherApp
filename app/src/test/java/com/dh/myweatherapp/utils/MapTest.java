package com.dh.myweatherapp.utils;

import com.dh.myweatherapp.bean.CityBean;
import com.dh.myweatherapp.bean.CityListBean;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class MapTest {

    @Test
    public void testMap() throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("1233444","北京");
        map.put("1211111","邢台");
        map.put("1222222","上海");
        map.put("1444444","唐山");
        map.put("1555555","沙河");
        map.put("1666666","天津");

        Iterator iter1 = (Iterator)map.values().iterator();
        Iterator iter2 = (Iterator)map.keySet().iterator();
        while(iter1.hasNext()&&iter2.hasNext()){
            System.out.println(iter1.next().toString()+":"+iter2.next().toString());
        }
    }
}