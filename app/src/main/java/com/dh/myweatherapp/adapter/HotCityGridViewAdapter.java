package com.dh.myweatherapp.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dh.myweatherapp.R;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class HotCityGridViewAdapter extends BaseAdapter {

    String[] hotCitys = null;

    private OnClickListener listener;

    public HotCityGridViewAdapter(String[] hotCitys){
        this.hotCitys = hotCitys;
    }

    @Override
    public int getCount() {
        return hotCitys.length;
    }

    @Override
    public Object getItem(int position) {
        return hotCitys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_city_item,null);
//            TypedValue typedValue = new TypedValue();
//            parent.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
//            convertView.setBackgroundResource(typedValue.resourceId);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_hot_city.setText(hotCitys[position]);
        holder.position = position;
        return convertView;
    }

    class ViewHolder {
        TextView tv_hot_city;
        int position;
        public ViewHolder(View view){
            tv_hot_city = (TextView) view.findViewById(R.id.tv_hot_city);
            tv_hot_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(position);
                }
            });
        }
    }

    public interface OnClickListener{
        public void onClick(int position);
    }
    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

}
