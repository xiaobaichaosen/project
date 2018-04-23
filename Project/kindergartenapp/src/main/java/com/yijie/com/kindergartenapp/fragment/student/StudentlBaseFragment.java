package com.yijie.com.kindergartenapp.fragment.student;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class StudentlBaseFragment extends BaseFragment {


    Unbinder unbinder;
private String image;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_studentbase;
    }

    @Override
    protected void initData() {
        final StudentFragment studentFragment = new StudentFragment();
        final StudentMoreFragment schoolMoreFragment =  new StudentMoreFragment();


        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, studentFragment);
        fragmentTransaction.commit();
        studentFragment.setOnButtonClick(new StudentFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//进从右向左   出从左向右
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open, R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_frame_layout, schoolMoreFragment);
                fragmentTransaction.commit();
                SharedPreferencesUtils.setParam(mActivity,"isStart",true);
                EventBus.getDefault().post("unLoad");

            }
        });

    }


    @Override
    protected void lazyLoad() {
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