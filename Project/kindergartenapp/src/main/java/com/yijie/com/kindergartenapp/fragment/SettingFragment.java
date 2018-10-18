package com.yijie.com.kindergartenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.CertificateActivity;
import com.yijie.com.kindergartenapp.activity.ModiKenderDetailActivity;
import com.yijie.com.kindergartenapp.activity.NewSignGroupaActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 设置
 */
public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_newGroup)
    TextView tvNewGroup;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
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

    @OnClick(R.id.tv_newGroup)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(mActivity, NewSignGroupaActivity.class);
        startActivity(intent);
    }
}
