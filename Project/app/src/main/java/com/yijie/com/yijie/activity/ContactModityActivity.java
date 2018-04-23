package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewContactActivity;
import com.yijie.com.yijie.adapter.ContactRecycleViewModityAapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.db.ContactBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/2/23.
 */

public class ContactModityActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_addContact)
    TextView tvAddContact;
    public static Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
    @BindView(R.id.tv_delect)
    TextView tvDelect;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    List list;
    ContactRecycleViewModityAapter myAdapterRecyclerView;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contact_modity);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("联系方式");

        list = new ArrayList<ContactBean>();

        for (int i = 0; i < 10; i++) {
            ContactBean contactBean = new ContactBean();
            contactBean.setName("王院长" + i);
            contactBean.setPhoneNumber("1867777" + i);
            contactBean.setZjNubmer("99999" + i);
            contactBean.setWxNubmer("8888" + i);
            contactBean.setQqNubmer("6666" + i);
            list.add(contactBean);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        // Create Instance of SchoolAdapterRecyclerView
        myAdapterRecyclerView = new ContactRecycleViewModityAapter(this, list, R.layout.contact_modity_item);
        recyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_addContact,R.id.tv_delect, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_addContact:
                Intent intent = new Intent();
                intent.setClass(this, NewContactActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_delect:
                List llist=new ArrayList();
                for (Integer i: map.keySet() ) {
                  if (map.get(i)){
                      llist.add(list.get(i));
                  }

                }
                list.removeAll(llist);
                myAdapterRecyclerView.notifyDataSetChanged();
                map.clear();
                llist.clear();
                break;
            case R.id.tv_confim:
                break;
        }
    }


}
