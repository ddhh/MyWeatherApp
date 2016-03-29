package com.dh.myweatherapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.dh.myweatherapp.fragment.WeatherInfoFragment;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/29.
 */
public class WeatherFragmentAdapter extends FragmentStatePagerAdapter {


    public List<WeatherInfoFragment> getList() {
        return list;
    }

    public void setList(List<WeatherInfoFragment> list) {
        this.list = list;
    }

    private List<WeatherInfoFragment> list;

    public WeatherFragmentAdapter(FragmentManager fm,List<WeatherInfoFragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
