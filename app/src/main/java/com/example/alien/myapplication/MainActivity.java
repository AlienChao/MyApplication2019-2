package com.example.alien.myapplication;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibrary.ConfigInit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConfigInit configInit =  new ConfigInit();
        configInit.init(this);


        list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list.add("-------item:-------"+i);
        }

//通过findViewById拿到RecyclerView实例
        mRecyclerView = findViewById(R.id.recyclerView);
//设置RecyclerView管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//初始化适配器
        mAdapter = new MyRecyclerViewAdapter(list);
//设置添加或删除item时的动画，这里使用默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//设置适配器
        mRecyclerView.setAdapter(mAdapter);

    }
}
