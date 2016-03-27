package com.dh.myweatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.bean.IndexBean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class IndexGridViewAdapter extends BaseAdapter {

    private List<IndexBean> list;
    public IndexGridViewAdapter(List<IndexBean> list){
        this.list = list;
    }

    public List<IndexBean> getList() {
        return list;
    }

    public void setList(List<IndexBean> list) {
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
        IndexViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_grid_view_item,parent,false);
            holder = new IndexViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (IndexViewHolder) convertView.getTag();
        }
        holder.tv_index.setText(list.get(position).getDetails());
        return convertView;
    }

    class IndexViewHolder {
        TextView tv_index;
        public IndexViewHolder(View v){
            tv_index = (TextView) v.findViewById(R.id.tv_index);
        }
    }

}
