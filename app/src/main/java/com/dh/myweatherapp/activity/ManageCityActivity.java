package com.dh.myweatherapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dh.myweatherapp.R;
import com.dh.myweatherapp.adapter.ManageCityListAdapter;
import com.dh.myweatherapp.db.DBHelper;
import com.dh.myweatherapp.utils.Contacts;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 端辉 on 2016/3/24.
 */
public class ManageCityActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private FloatingActionButton fab;

    private RecyclerView recyclerView;
    private ManageCityListAdapter mAdapter;
    private List<String[]> mList = new ArrayList<>();

    private SQLiteDatabase db;

    private boolean isDelete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_city);
        initView();
    }

    //初始化布局
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("城市管理");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.action_add);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.manage_city_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration
                        .Builder(this)
                        .marginResId(R.dimen.activity_horizontal_margin, R.dimen.activity_horizontal_margin)
                        .build());
        mAdapter = new ManageCityListAdapter(getSharedDate());
        mAdapter.setOnItemClickListener(new ManageCityListAdapter.OnItemClickListener() {
            @Override
            public void itemClick() {

            }
        });
        mAdapter.setOnDeleteListener(new ManageCityListAdapter.OnDeleteListener() {
            @Override
            public void itemDelete(int position) {
                if(deleteCity(mAdapter.list.get(position)[0])) {
                    mAdapter.list.remove(position);
                    mAdapter.notifyDataSetChanged();
                    isDelete = true;
                }
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    private boolean deleteCity(String id){
        db = DBHelper.getInstance(this).getWritableDatabase();
        if(db.delete("weatherInfo","area_id=?",new String[]{id})>0){
            return true;
        }
        return false;
    }

    private List<String[]> getSharedDate() {
        List<String[]> list = new ArrayList<>();
        db = DBHelper.getInstance(this).getWritableDatabase();
        Cursor cursor = db.query("weatherInfo",new String[]{"area_id,name_cn"},null,null,null,null,null);
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("area_id"));
                String name = cursor.getString(cursor.getColumnIndex("name_cn"));
                list.add(new String[]{id, name});
            }
        }
        mList = list;
        cursor.close();
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isDelete){
                    setResult(3);
                }
                finish();
                return true;
            case R.id.action_edit:
                if (mAdapter.isEdit){
                    mAdapter.isEdit = false;
                    mAdapter.notifyDataSetChanged();
                    item.setIcon(R.drawable.ic_edit_white_36dp);
                    getSupportActionBar().setTitle("城市管理");
                }else{
                    mAdapter.isEdit = true;
                    mAdapter.notifyDataSetChanged();
                    item.setIcon(R.drawable.ic_clear_white_36dp);
                    getSupportActionBar().setTitle("编辑城市");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAdapter.isEdit) {
                mAdapter.isEdit = false;
                mAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_add) {
            Intent intent = new Intent(ManageCityActivity.this, SearchCityActivity.class);
            intent.putExtra(SearchCityActivity.INTENT_FROM_MANAGER,"s");
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2){
            setResult(2,data);
            finish();
        }
    }
}
