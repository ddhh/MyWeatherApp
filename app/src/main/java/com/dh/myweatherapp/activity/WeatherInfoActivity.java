package com.dh.myweatherapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.adapter.WeatherFragmentAdapter;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.dh.myweatherapp.bean.WeatherBean;
import com.dh.myweatherapp.db.DBHelper;
import com.dh.myweatherapp.fragment.WeatherInfoFragment;
import com.dh.myweatherapp.utils.Contacts;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class WeatherInfoActivity extends AppCompatActivity {

    public static final String INTENT_CITY_ID = "INTENT_CITY_ID";
    public static final String INTENT_CITY_NAME = "INTENT_CITY_NAME";

    public DBHelper dbHelper;
    public SQLiteDatabase db;

    public String id;
    private String name;

    private Toolbar toolbar;
    private TextView tv_title;

//    private SharedPreferences spf;

    private ViewPager viewPager;
    private WeatherFragmentAdapter wAdapter;
    private List<WeatherInfoFragment> mList = new ArrayList<>();

    private FragmentManager fm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        initView();
        initDB();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1||resultCode==2) {
            id = data.getStringExtra(INTENT_CITY_ID);
            name = data.getStringExtra(INTENT_CITY_NAME);
            if (!TextUtils.isEmpty(id)) {
                for (WeatherInfoFragment wf : mList) {
                    if (id.equals(wf.weatherId)) {
                        return;
                    }
                }
                WeatherInfoFragment f = new WeatherInfoFragment();
                f.weatherId = id;
                f.city = name;
                mList.add(f);
                wAdapter.setList(mList);
                wAdapter.notifyDataSetChanged();
//                wAdapter.setFragmentList(mList);
                viewPager.setCurrentItem(mList.size()-1);
                tv_title.setText(mList.get(mList.size()-1).city);
            }
        }else if(resultCode==3){
            initDB();
        }
    }

    private void initDB() {
        dbHelper = DBHelper.getInstance(this);
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("weatherInfo", new String[]{"count(*)"}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getInt(cursor.getColumnIndex("count(*)")) == 0) {
                cursor.close();
                Intent intent = new Intent(WeatherInfoActivity.this, SearchCityActivity.class);
                startActivityForResult(intent,1);
                return;
            }
        }
        cursor = db.query("weatherInfo", new String[]{"area_id","name_cn"}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            mList.clear();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("area_id"));
                String name_cn = cursor.getString(cursor.getColumnIndex("name_cn"));
                WeatherInfoFragment f = new WeatherInfoFragment();
                f.weatherId = id;
                f.city = name_cn;
                f.isExit = true;
                mList.add(f);
            }
            wAdapter.setList(mList);
            wAdapter.notifyDataSetChanged();
//            wAdapter.setFragmentList(mList);
            tv_title.setText(mList.get(0).city);
        }
        cursor.close();
    }

    //初始化布局
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        viewPager = (ViewPager) findViewById(R.id.weather_pagers);
        viewPager.setOffscreenPageLimit(0);
        wAdapter = new WeatherFragmentAdapter(fm = getSupportFragmentManager(), mList);
        viewPager.setAdapter(wAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(mList.get(position).city);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_weather_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                Intent intent = new Intent(WeatherInfoActivity.this, ManageCityActivity.class);
                startActivityForResult(intent,2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
