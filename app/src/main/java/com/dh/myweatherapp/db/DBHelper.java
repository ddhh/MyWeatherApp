package com.dh.myweatherapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 端辉 on 2016/3/24.
 * 数据库用于数据的缓存
 * 进行数据库的管理，新建数据库，新建表，插入数据，删除数据
 *
 *
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE = "weather.db";
    public static final int VERSION = 1;
    public static final String TABLE = "create table weatherInfo(area_id text primary key,name_cn text not null,json_str text not null,last_time text not null)";

    private static DBHelper dbHelper;
    public static DBHelper getInstance(Context context){
        if(dbHelper==null){
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    public DBHelper(Context context){
        this(context,DATABASE,null,VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE);
        Log.d("DBHelper", "创建表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
