package com.yijie.com.yijie.fragment.school;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseFragment;

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
    public void onResume() {
        isPrepared=true;
        initData();
        super.onResume();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        // TODO 正常应该是!isVisble,待解决
        if(!isPrepared || !isVisible) {
            return;
        }
        final ProjectListFragment schoolFragment = new ProjectListFragment();
       final SchoolMoreFragment schoolMoreFragment = new SchoolMoreFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout,schoolFragment);
        fragmentTransaction.commit();


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

