package com.dh.myweatherapp.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.activity.WeatherInfoActivity;
import com.dh.myweatherapp.adapter.ExpandableListViewAdapter;
import com.dh.myweatherapp.adapter.IndexGridViewAdapter;
import com.dh.myweatherapp.adapter.WeatherFragmentAdapter;
import com.dh.myweatherapp.bean.IndexBean;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.dh.myweatherapp.bean.WeatherBean;
import com.dh.myweatherapp.db.DBHelper;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class WeatherInfoFragment extends Fragment {


    private SwipeRefreshLayout swipe;
    private SwipeRefreshLayout.OnRefreshListener listener;

    private TextView today_temp;
    private TextView today_type;
//    private GridView index;
//    private IndexGridViewAdapter gAdapter;

    private List<RelativeLayout> rlsList = new ArrayList<>();

    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter eAdapter;

    public String weatherId;//城市天气代码id
    public String city;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public boolean isExit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if(b!=null){
            b.putString("weatherID",weatherId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_info, container, false);
        Bundle b = getArguments();
        if (b != null) {
            weatherId = b.getString("weatherID");
        }
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
        today_temp = (TextView) v.findViewById(R.id.today_temp);
        today_type = (TextView) v.findViewById(R.id.today_type);
//        index = (GridView) v.findViewById(R.id.index);
//        gAdapter = new IndexGridViewAdapter(new ArrayList<IndexBean>());
//        index.setAdapter(gAdapter);
        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        eAdapter = new ExpandableListViewAdapter(new ArrayList<IndexBean>());
        expandableListView.setAdapter(eAdapter);
        initRls(v);
        initRefresh(v);
    }

    private void initRls(View v) {
        rlsList.add((RelativeLayout) v.findViewById(R.id.yesterday));
        rlsList.add((RelativeLayout) v.findViewById(R.id.today));
        rlsList.add((RelativeLayout) v.findViewById(R.id.tomorrow));
        rlsList.add((RelativeLayout) v.findViewById(R.id.second));
        rlsList.add((RelativeLayout) v.findViewById(R.id.thrid));
        rlsList.add((RelativeLayout) v.findViewById(R.id.forth));
    }

    private void rlsListDataSet(List<WeatherBean> lwb) {
        for (int i = 0; i < lwb.size(); i++) {
            Log.d("WeatherInfoFragment", "i:" + i);
            rlsList.get(i).setVisibility(View.VISIBLE);
            if (i == 0) {
                ((TextView) rlsList.get(i).getChildAt(0)).setText("昨天");
            } else if (i == 1) {
                ((TextView) rlsList.get(i).getChildAt(0)).setText("今天");
            } else if (i == 2) {
                ((TextView) rlsList.get(i).getChildAt(0)).setText("明天");
            } else {
                ((TextView) rlsList.get(i).getChildAt(0)).setText(lwb.get(i).getWeek());
            }
            ((TextView) rlsList.get(i).getChildAt(1)).setText(lwb.get(i).getType());
            ((TextView) rlsList.get(i).getChildAt(2)).setText(lwb.get(i).getHightemp() + "|" + lwb.get(i).getLowtemp());
            ((TextView) rlsList.get(i).getChildAt(3)).setText(lwb.get(i).getFengli());
        }
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
//        gAdapter.setList(recentWeathersBean.getRetData().getToday().getIndex());
//        gAdapter.notifyDataSetChanged();

        List<WeatherBean> tempList = new ArrayList<>();
        tempList.add(recentWeathersBean.getRetData().getHistory().get(recentWeathersBean.getRetData().getHistory().size() - 1));
        WeatherBean todayInfo = new WeatherBean();
        todayInfo.setType(recentWeathersBean.getRetData().getToday().getType());
        todayInfo.setFengli(recentWeathersBean.getRetData().getToday().getFengli());
        todayInfo.setHightemp(recentWeathersBean.getRetData().getToday().getHightemp());
        todayInfo.setLowtemp(recentWeathersBean.getRetData().getToday().getLowtemp());
        tempList.add(todayInfo);
        tempList.addAll(recentWeathersBean.getRetData().getForecast());
        rlsListDataSet(tempList);
        eAdapter.setList(recentWeathersBean.getRetData().getToday().getIndex());
        eAdapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }

}
