package com.dh.myweatherapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.dh.myweatherapp.R;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class SearchCityActivity extends AppCompatActivity{

    private TextView tv_location;
    private Button btn;

    private LocationClient mLocationClient = null;
    private LocationClientOption mLocationClientOption = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        initView();
    }

    private void initView(){
        tv_location = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocation();
            }
        });
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
                    tv_location.setText(location.getDistrict());
                }
            }
        });
        mLocationClient.start();
    }

}
