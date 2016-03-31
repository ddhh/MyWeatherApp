package com.dh.myweatherapp.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.dh.myweatherapp.R;
import com.dh.myweatherapp.adapter.HotCityGridViewAdapter;
import com.dh.myweatherapp.adapter.SearchCityResultListAdapter;
import com.dh.myweatherapp.temp.SearchCityResultListAdapter_02;
import com.dh.myweatherapp.bean.CityBean;
import com.dh.myweatherapp.bean.CityListBean;
import com.dh.myweatherapp.temp.SearchCityResultBean;
import com.dh.myweatherapp.utils.Contacts;
import com.dh.myweatherapp.temp.DBUtil;
import com.dh.myweatherapp.utils.HttpUtil;
import com.dh.myweatherapp.utils.JsonUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class SearchCityActivity extends AppCompatActivity{

    public static final String INTENT_FROM_MANAGER = "INTENT_FROM_MANAGER";

    private Toolbar toolbar;
    private SearchView searchView;

    private RelativeLayout rl;
    private GridView gridView;
    private HotCityGridViewAdapter hAapter;

    private RecyclerView recyclerView;
    private SearchCityResultListAdapter mAdapter;

    private LocationClient mLocationClient = null;
    private LocationClientOption mLocationClientOption = null;
    private BDLocation bdLocation = null;

    private boolean isOp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        initView();
        initLocation();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setFocusable(false);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    rl.setVisibility(View.VISIBLE);
                    mAdapter.setList(new ArrayList<CityBean>());
                    mAdapter.notifyDataSetChanged();
                    //TODO 关闭输入法
                }else{
                    rl.setVisibility(View.GONE);
                    new SearchCityTask().execute(newText);
                }
                return true;
            }
        });

        rl = (RelativeLayout) findViewById(R.id.hot_city_layout);
        gridView = (GridView) findViewById(R.id.gridView);
        hAapter = new HotCityGridViewAdapter(Contacts.HOT_CITYS);
        hAapter.setOnClickListener(new HotCityGridViewAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                if(position==0){
                    //TODO 定位获取位置
                    String result = bdLocation.getDistrict();
                    result = result.substring(0,result.length()-1);
                    searchView.setQuery(result,true);
                    return;
                }
                //TODO 获取相应城市信息
                searchView.setQuery(Contacts.HOT_CITYS[position],true);
            }
        });
        gridView.setAdapter(hAapter);


        recyclerView = (RecyclerView) findViewById(R.id.search_city_reuslt_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration
                        .Builder(this)
                        .marginResId(R.dimen.activity_horizontal_margin, R.dimen.activity_horizontal_margin)
                        .build());
        mAdapter = new SearchCityResultListAdapter(new ArrayList<CityBean>());
        mAdapter.setOnItemSelectListener(new SearchCityResultListAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelect(String id,String name) {
                //TODO 查询结果的点击事件，跳转到天气信息界面,再异步加载该城市天气数据
                Intent intent = new Intent();
                intent.putExtra(WeatherInfoActivity.INTENT_CITY_ID,id);
                intent.putExtra(WeatherInfoActivity.INTENT_CITY_NAME,name);
                if(!TextUtils.isEmpty(getIntent().getStringExtra(INTENT_FROM_MANAGER))){
                    setResult(2,intent);
                }else {
                    setResult(1, intent);
                }
                finish();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    private void initLocation(){
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClientOption = new LocationClientOption();
        mLocationClientOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//低功率模式
        mLocationClientOption.setIsNeedAddress(true);//需要地址信息
        mLocationClient.setLocOption(mLocationClientOption);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                    bdLocation = location;
                }
            }
        });
        mLocationClient.start();
    }

    class SearchCityTask extends AsyncTask<String,Void,CityListBean>{

        @Override
        protected CityListBean doInBackground(String... params) {
            //TODO 异步访问网络获取数据
            return JsonUtil.getCityList(HttpUtil.getCitylistJson(params[0]));
        }

        @Override
        protected void onPostExecute(CityListBean clb) {
            super.onPostExecute(clb);
            //TODO 刷新结果列表
            mAdapter.setList(clb.getRetData());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            setResult(4);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
