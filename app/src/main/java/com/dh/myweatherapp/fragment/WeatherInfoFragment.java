package com.dh.myweatherapp.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.adapter.IndexGridViewAdapter;
import com.dh.myweatherapp.bean.IndexBean;
import com.dh.myweatherapp.bean.RecentWeathersBean;
import com.dh.myweatherapp.bean.WeatherBean;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class WeatherInfoFragment extends Fragment {


    private SwipeRefreshLayout swipe;

    private TextView today_temp;
    private TextView today_type;
    private GridView index;
    private IndexGridViewAdapter gAdapter;

    private RelativeLayout rls_today,rls_yesterday,rls_tomorrow,rls_second,rls_thrid,rls_forth;

    private List<RelativeLayout> rlsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_info, container, false);
        initView(view);
        String id = "";//父activity给的城市天气代码id
        new GetWeatherInfoTask().execute(id);
        return view;
    }


    private void initView(View v) {
        today_temp = (TextView) v.findViewById(R.id.today_temp);
        today_type = (TextView) v.findViewById(R.id.today_type);
        index = (GridView) v.findViewById(R.id.index);
        gAdapter = new IndexGridViewAdapter(new ArrayList<IndexBean>());
        index.setAdapter(gAdapter);
        initRls(v);
    }

    private void initRls(View v){
        rlsList.add(rls_yesterday = (RelativeLayout) v.findViewById(R.id.yesterday));
        rlsList.add(rls_today = (RelativeLayout) v.findViewById(R.id.today));
        rlsList.add(rls_tomorrow = (RelativeLayout) v.findViewById(R.id.tomorrow));
        rlsList.add(rls_second = (RelativeLayout) v.findViewById(R.id.second));
        rlsList.add(rls_thrid = (RelativeLayout) v.findViewById(R.id.thrid));
        rlsList.add(rls_forth = (RelativeLayout) v.findViewById(R.id.forth));
    }

    private void rlsListDataSet(List<WeatherBean> lwb){
        for(int i=0;i<lwb.size();i++){
            ((TextView)rlsList.get(i).getChildAt(0)).setText(lwb.get(i).getDate());
            ((TextView)rlsList.get(i).getChildAt(1)).setText(lwb.get(i).getType());
            ((TextView)rlsList.get(i).getChildAt(2)).setText(lwb.get(i).getHightemp()+"|"+lwb.get(i).getLowtemp());
            ((TextView)rlsList.get(i).getChildAt(3)).setText(lwb.get(i).getFengli());
        }
    }

    class GetWeatherInfoTask extends AsyncTask<String,Void,RecentWeathersBean>{

        @Override
        protected RecentWeathersBean doInBackground(String... params) {
            return JsonUtil.getRecentWeather(HttpUtil.getRecentWeathersJson(params[0]));
        }

        @Override
        protected void onPostExecute(RecentWeathersBean recentWeathersBean) {
            super.onPostExecute(recentWeathersBean);
            today_temp.setText(recentWeathersBean.getRetData().getToday().getCurTemp());
            today_type.setText(recentWeathersBean.getRetData().getToday().getType());
            gAdapter.setList(recentWeathersBean.getRetData().getToday().getIndex());
            gAdapter.notifyDataSetChanged();
            //TODO 拼凑一个合适的List<WeatherBean>
            //histroy中的最后一个item
            //forecast中的所有
            List<WeatherBean> tempList = new ArrayList<>();
            tempList.add(recentWeathersBean.getRetData().getHistory().get(recentWeathersBean.getRetData().getHistory().size()-1));
            tempList.addAll(recentWeathersBean.getRetData().getForecast());
            rlsListDataSet(tempList);
        }
    }

}
