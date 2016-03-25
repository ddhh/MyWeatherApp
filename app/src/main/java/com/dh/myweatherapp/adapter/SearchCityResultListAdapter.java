package com.dh.myweatherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.bean.CityBean;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class SearchCityResultListAdapter extends RecyclerView.Adapter {

    private List<CityBean> list;

    public SearchCityResultListAdapter(List<CityBean> list) {
        this.list = list;
    }

    private OnItemSelectListener listener;

    public List<CityBean> getList() {
        return list;
    }

    public void setList(List<CityBean> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_city_result_item,parent,false);
        TypedValue typedValue = new TypedValue();
        parent.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultViewHolder mHolder = (ResultViewHolder)holder;
        CityBean  cb = list.get(position);
        String province = cb.getProvince_cn();
        String district = cb.getDistrict_cn();
        String name = cb.getName_cn();
        mHolder.tv_search_result.setText(name+"--"+district+"--"+province);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{

        TextView tv_search_result;

        public ResultViewHolder(View itemView) {
            super(itemView);
            tv_search_result = (TextView) itemView.findViewById(R.id.tv_search_result);
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemSelect();
                }
            });
        }
    }

    public interface OnItemSelectListener{
        public void onItemSelect();
    }

    public void setOnItemSelectListener(OnItemSelectListener listener){
        this.listener = listener;
    }

}
