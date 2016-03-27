package com.dh.myweatherapp.temp;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dh.myweatherapp.R;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/25.
 */
public class SearchCityResultListAdapter_02 extends RecyclerView.Adapter {

    private List<SearchCityResultBean> list;

    public SearchCityResultListAdapter_02(List<SearchCityResultBean> list) {
        this.list = list;
    }

    private OnItemSelectListener listener;

    public List<SearchCityResultBean> getList() {
        return list;
    }

    public void setList(List<SearchCityResultBean> list) {
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
        SearchCityResultBean  scrb = list.get(position);
        String area_id = scrb.getArea_id();
        String name_cn = scrb.getName_cn();
        String address = scrb.getAddress();
        mHolder.tv_search_result.setText(name_cn+" - "+address);
        mHolder.area_id = area_id;
        mHolder.position = position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResultViewHolder extends RecyclerView.ViewHolder{
        TextView tv_search_result;
        String area_id;
        int position;

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
