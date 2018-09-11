package com.yijie.com.yijie.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.MapLocationActivity;
import com.yijie.com.yijie.activity.SettingAcitivity;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.activity.student.StudentActivity;
import com.yijie.com.yijie.activity.student.StudentSpeedCheckActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/4/20.
 */

public class MeFragment extends BaseFragment  {
    @BindView(R.id.back)
    TextView back;

    Unbinder unbinder;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        super.onResume();
    }

    @Override
    protected void initView() {
        title.setText("我的");
        back.setVisibility(View.GONE);
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.setting);

    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.title, R.id.action_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title:
//                Intent intent1=new Intent();
//                intent1.setClass(mActivity,MapLocationActivity.class);
//                startActivity(intent1);

                break;
            case R.id.action_item:
                Intent intent = new Intent();
                intent.setClass(mActivity, SettingAcitivity.class);
                startActivity(intent);
                break;
        }
    }




}
