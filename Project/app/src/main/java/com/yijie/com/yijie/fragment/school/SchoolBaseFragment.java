package com.yijie.com.yijie.fragment.school;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.Item;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.view.LoadingLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/29.
 */

public class SchoolBaseFragment extends BaseFragment {


    Unbinder unbinder;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_schoolbase;
    }

    @Override
    protected void initData() {
        final   SchoolFragment schoolFragment = new SchoolFragment();
       final SchoolMoreFragment schoolMoreFragment = new SchoolMoreFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout,schoolFragment);
        fragmentTransaction.commit();
        schoolFragment.setOnButtonClick(new SchoolFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//进从右向左   出从左向右
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_frame_layout,schoolMoreFragment);
                fragmentTransaction.commit();
            }
        });
        schoolMoreFragment.setOnButtonClick(new SchoolFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_reopen,R.anim.on_activity_close);
                fragmentTransaction.replace(R.id.main_frame_layout,schoolFragment);
                fragmentTransaction.commit();
            }
        });

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
}

