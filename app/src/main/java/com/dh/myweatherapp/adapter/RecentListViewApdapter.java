package com.dh.myweatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.bean.WeatherBean;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/31.
 */
public class RecentListViewApdapter extends BaseAdapter {

    private List<WeatherBean> list;

    public RecentListViewApdapter(List<WeatherBean> list) {
        this.list = list;
    }

    public List<WeatherBean> getList() {
        return list;
    }

    public void setList(List<WeatherBean> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecentViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rencent_weather_item, parent, false);
            holder = new RecentViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecentViewHolder) convertView.getTag();
        }
        WeatherBean wb = list.get(position);
        if (position == 0) {
            holder.tv_week.setText("昨天");
        } else if (position == 1) {
            holder.tv_week.setText("今天");
        } else if (position == 2) {
            holder.tv_week.setText("明天");
        }else{
            holder.tv_week.setText(wb.getWeek());
        }
        holder.tv_type.setText(wb.getType());
        holder.tv_temp.setText(wb.getHightemp() + "|" + wb.getLowtemp());
        holder.tv_fengli.setText(wb.getFengli());

        return convertView;
    }

    class RecentViewHolder {
        private TextView tv_week;
        private TextView tv_type;
        private TextView tv_temp;
        private TextView tv_fengli;

        public RecentViewHolder(View itemView) {
            tv_week = (TextView) itemView.findViewById(R.id.day);
            tv_type = (TextView) itemView.findViewById(R.id.type);
            tv_temp = (TextView) itemView.findViewById(R.id.temp);
            tv_fengli = (TextView) itemView.findViewById(R.id.feng);
        }
    }

}
