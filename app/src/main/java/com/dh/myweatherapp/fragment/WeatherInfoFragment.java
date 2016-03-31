package com.dh.myweatherapp.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.activity.WeatherInfoActivity;
import com.dh.myweatherapp.adapter.ExpandableListViewAdapter;
import com.dh.myweatherapp.adapter.RecentListViewApdapter;
import com.dh.myweatherapp.bean.IndexBean;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.dh.myweatherapp.bean.WeatherBean;
import com.dh.myweatherapp.db.DBHelper;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;
import com.dh.myweatherapp.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class WeatherInfoFragment extends Fragment {


    private SwipeRefreshLayout swipe;
    private SwipeRefreshLayout.OnRefreshListener listener;

    private ScrollView scrollView;

    private TextView today_temp;
    private TextView today_type;

    private MyListView myListView;
    private RecentListViewApdapter mAdapter;

    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter eAdapter;

    public String weatherId;//城市天气代码id
    public String city;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public boolean isExit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_info, container, false);
        initView(view);
        checkDB(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkDB(View v) {
        dbHelper = ((WeatherInfoActivity) getActivity()).dbHelper;
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("weatherInfo", new String[]{"name_cn", "json_str,last_time"}, "area_id=?", new String[]{weatherId}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String name_cn = cursor.getString(cursor.getColumnIndex("name_cn"));
            String jsonStr = cursor.getString(cursor.getColumnIndex("json_str"));
            long lastTime = cursor.getLong(cursor.getColumnIndex("last_time"));
            city = name_cn;
            postData(JsonUtil.getRecentWeather(jsonStr));
            if (System.currentTimeMillis() - lastTime > 1000 * 60 * 60) {
                swipe.post(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(true);
                    }
                });
                listener.onRefresh();
            }
        } else {
            swipe.post(new Runnable() {
                @Override
                public void run() {
                    swipe.setRefreshing(true);
                }
            });
            listener.onRefresh();
        }
    }

    private void updateDB(String jsonStr, long lastTime) {
        ContentValues values = new ContentValues();
        values.put("json_str", jsonStr);
        values.put("last_time", lastTime);
        db.update("weatherInfo", values, "area_id=?", new String[]{weatherId});
    }

    private void insertDB(String s1, String s2, String s3, String s4) {
        ContentValues values = new ContentValues();
        values.put("area_id", s1);
        values.put("name_cn", s2);
        values.put("json_str", s3);
        values.put("last_time", s4);
        city = s2;
        db.insert("weatherInfo", null, values);
    }

    private void initRefresh(View v) {
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.weather_swipe);
        swipe.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetWeatherInfoTask().execute(weatherId);
            }
        };
        swipe.setOnRefreshListener(listener);
    }

    private void initView(View v) {

        scrollView = (ScrollView) v.findViewById(R.id.weather_info_scrollview);

        today_temp = (TextView) v.findViewById(R.id.today_temp);
        today_type = (TextView) v.findViewById(R.id.today_type);
        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        eAdapter = new ExpandableListViewAdapter(new ArrayList<IndexBean>());
        expandableListView.setAdapter(eAdapter);

        myListView = (MyListView) v.findViewById(R.id.recent_list);
        mAdapter = new RecentListViewApdapter(new ArrayList<WeatherBean>());
        myListView.setAdapter(mAdapter);

        initRefresh(v);
    }

    class GetWeatherInfoTask extends AsyncTask<String, Void, RecentWeathersBean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe.setRefreshing(true);
        }

        @Override
        protected RecentWeathersBean doInBackground(String... params) {
            String json = HttpUtil.getRecentWeathersJson(params[0]);//json_str
            RecentWeathersBean rwb = JsonUtil.getRecentWeather(json);
            long last_time = System.currentTimeMillis();//last_time
            String name = rwb.getRetData().getCity();//name_cn
            String areaId = rwb.getRetData().getCityid();//area_id
            if (isExit) {
                updateDB(json, last_time);
            } else {
                insertDB(weatherId, name, json, last_time + "");
                isExit = true;
            }
            return rwb;
        }

        @Override
        protected void onPostExecute(RecentWeathersBean recentWeathersBean) {
            super.onPostExecute(recentWeathersBean);
            postData(recentWeathersBean);
        }
    }

    private void postData(RecentWeathersBean recentWeathersBean) {
        today_temp.setText(recentWeathersBean.getRetData().getToday().getCurTemp());
        today_type.setText(recentWeathersBean.getRetData().getToday().getType());

        List<WeatherBean> tempList = new ArrayList<>();
        tempList.add(recentWeathersBean.getRetData().getHistory().get(recentWeathersBean.getRetData().getHistory().size() - 1));
        WeatherBean todayInfo = new WeatherBean();
        todayInfo.setType(recentWeathersBean.getRetData().getToday().getType());
        todayInfo.setFengli(recentWeathersBean.getRetData().getToday().getFengli());
        todayInfo.setHightemp(recentWeathersBean.getRetData().getToday().getHightemp());
        todayInfo.setLowtemp(recentWeathersBean.getRetData().getToday().getLowtemp());
        tempList.add(todayInfo);
        tempList.addAll(recentWeathersBean.getRetData().getForecast());
        mAdapter.setList(tempList);
        mAdapter.notifyDataSetChanged();

        eAdapter.setList(recentWeathersBean.getRetData().getToday().getIndex());
        eAdapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
        scrollView.smoothScrollTo(0,0);
    }

}
