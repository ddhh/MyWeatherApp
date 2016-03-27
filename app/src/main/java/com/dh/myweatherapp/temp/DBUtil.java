package com.dh.myweatherapp.temp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class DBUtil {

    public static List<SearchCityResultBean> getCityListFromDB(String str) {
        List<SearchCityResultBean> list = new ArrayList<>();
        File file = new File("C:\\Android\\AndroidStudioWorkSpace\\MyWeatherApp\\app\\src\\main\\res\\assets\\weather.db");
        if (file.exists()) {
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file, null);
            Cursor cursor = database.query("city", new String[]{"area_id", "name_cn", "address"}, "name_cn like " + str + "%", null, null, null, "area_id asc");
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    SearchCityResultBean scrb = new SearchCityResultBean();
                    String area_id = cursor.getString(cursor.getColumnIndex("area_id"));
                    String name_cn = cursor.getString(cursor.getColumnIndex("name_cn"));
                    String address = cursor.getString(cursor.getColumnIndex("address"));
                    scrb.setArea_id(area_id);
                    scrb.setName_cn(name_cn);
                    scrb.setAddress(address);
                    list.add(scrb);
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

}
