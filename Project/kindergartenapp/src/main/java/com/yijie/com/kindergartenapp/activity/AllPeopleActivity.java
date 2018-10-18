package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.AllPeopleAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 参与人员
 */
public class AllPeopleActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.cb_all)
    CheckBox cbAll;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    private LinearLayoutManager linearLayoutManager;
    private AllPeopleAdapter myAdapter;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_allpeople);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setImageResource(R.mipmap.search);
        title.setText("参与人员");
        //在加载数据之前配置
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //创建一个适配器
        myAdapter = new AllPeopleAdapter();
        recyclerView.setAdapter(myAdapter);
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleSomethingElse(String update) {
        HashMap<Integer, Boolean> map = myAdapter.map;
        int i=0;
        for (Integer key :map.keySet()){
            if (map.get(key)){
                i++;
            }
        }
        tvNumber.setText("确定("+i+"/500)");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.cb_all})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        if (ischanged) {
            myAdapter.selectAll();
            tvNumber.setText("确定("+myAdapter.list.size()+"/500)");
        } else {
            myAdapter.neverAll();
            tvNumber.setText("确定(0/500)");
        }

    }

    @OnClick({R.id.back, R.id.action_item,R.id.tv_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.action_item:
                ShowToastUtils.showToastMsg(AllPeopleActivity.this, "搜索");
                break;

            case R.id.tv_number:
                finish();
                break;
        }
    }
}
