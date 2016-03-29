package com.dh.myweatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.bean.IndexBean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/29.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {


    private List<IndexBean> list;

    public List<IndexBean> getList() {
        return list;
    }

    public void setList(List<IndexBean> list) {
        this.list = list;
    }

    public ExpandableListViewAdapter(List<IndexBean> list){
        this.list = list;
    }


    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getDetails();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_expand_group_item,parent,false);
            holder = new GroupViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.expand_title);
            convertView.setTag(holder);
        }else{
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(list.get(groupPosition).getName());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if(convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.index_expand_child_item,parent,false);
            holder = new ChildViewHolder();
            holder.tv_detail = (TextView) convertView.findViewById(R.id.detail);
            convertView.setTag(holder);
        }else{
            holder = (ChildViewHolder) convertView.getTag();
        }
        holder.tv_detail.setText("\u3000\u3000"+list.get(groupPosition).getDetails());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView tv_title;
    }

    class ChildViewHolder {
        TextView tv_detail;
    }

}
