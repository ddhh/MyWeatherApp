package com.dh.myweatherapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.adapter.WeatherFragmentAdapter;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.dh.myweatherapp.bean.WeatherBean;
import com.dh.myweatherapp.fragment.WeatherInfoFragment;
import com.dh.myweatherapp.utils.Contacts;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class WeatherInfoActivity extends AppCompatActivity{

    public static final String INTENT_CITY_ID = "INTENT_CITY_ID";
    public static final String INTENT_CITY_NAME = "INTENT_CITY_NAME";

    public String id;
    private String name;

    private Toolbar toolbar;
    private TextView tv_title;

    private SharedPreferences spf;

    private ViewPager viewPager;
    private WeatherFragmentAdapter wAdapter;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_info);
        id = getIntent().getStringExtra(INTENT_CITY_ID);
        name = getIntent().getStringExtra(INTENT_CITY_NAME);
        initView();
        spf = getSharedPreferences(Contacts.SHARED_XML_NAME,MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Editor edit = spf.edit();
//        if(spf.contains(id)){
//            edit.putString(id,name);
//        }
        edit.clear();
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //初始化布局
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = (TextView) findViewById(R.id.title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        tv_title.setText(name);

        viewPager = (ViewPager) findViewById(R.id.weather_pagers);
        mList.add(new WeatherInfoFragment());
        wAdapter = new WeatherFragmentAdapter(getSupportFragmentManager(),mList);
        viewPager.setAdapter(wAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_weather_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_location:
                Intent intent = new Intent(WeatherInfoActivity.this,ManageCityActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
