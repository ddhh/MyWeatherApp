package com.dh.myweatherapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.dh.myweatherapp.utils.NetWorkUtil;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class NetWorkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
            if(!NetWorkUtil.isNetworkConnected(context)){
                Toast.makeText(context,"确定网络连接后重试！",Toast.LENGTH_LONG).show();
            }else{
                if(NetWorkUtil.isMobileConnected(context)||NetWorkUtil.isWifiConnected(context)){
                    Toast.makeText(context,"网络可用！",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
