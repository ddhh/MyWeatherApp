package com.dh.myweatherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dh.myweatherapp.R;

import java.util.List;

/**
 * Created by 端辉 on 2016/3/27.
 */
public class ManageCityListAdapter extends RecyclerView.Adapter {

    public List<String[]> list;

    private OnItemClickListener listener;

    public ManageCityListAdapter(List<String[]> list) {
        this.list = list;
    }

    public boolean isEdit = false;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_city_item, parent, false);
        TypedValue typedValue = new TypedValue();
        parent.getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);
        if(!isEdit){
            view.setClickable(true);
        }else{
            view.setClickable(false);
        }
        return new ManageCityItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ManageCityItemHolder mHolder = (ManageCityItemHolder) holder;
        mHolder.position = position;
        mHolder.tv_location.setText(list.get(position)[1]);
        mHolder.id = list.get(position)[0];
        if (isEdit) {
            mHolder.ib_delete.setVisibility(View.VISIBLE);
        } else {
            mHolder.ib_delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ManageCityItemHolder extends RecyclerView.ViewHolder {

        TextView tv_location;
        ImageButton ib_delete;
        int position;
        String id;

        public ManageCityItemHolder(View itemView) {
            super(itemView);
            tv_location = (TextView) itemView.findViewById(R.id.city_location);
            ib_delete = (ImageButton) itemView.findViewById(R.id.delete);
            ib_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 删除选中的城市
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick();
                }
            });
        }
    }

    public interface OnItemClickListener {
        public void itemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
