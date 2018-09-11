package com.yijie.com.kindergartenapp.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.KenderDetailAcitivity;
import com.yijie.com.kindergartenapp.activity.SettingAcitivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;
    @BindView(R.id.rl_kendMessage)
    RelativeLayout rlKendMessage;
    @BindView(R.id.rl_requst)
    RelativeLayout rlRequst;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        super.onResume();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        title.setText("我的");
        back.setVisibility(View.GONE);
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.student_title_setting);

    }

    @Override
    protected void onInvisible() {

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

    @OnClick({R.id.rl_kendMessage, R.id.rl_requst, R.id.action_item})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {


            case R.id.rl_kendMessage:
                intent.setClass(mActivity, KenderDetailAcitivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.rl_requst:
                break;
            case R.id.action_item:

                intent.setClass(mActivity, SettingAcitivity.class);
                mActivity.startActivity(intent);
                break;

        }
    }


}
