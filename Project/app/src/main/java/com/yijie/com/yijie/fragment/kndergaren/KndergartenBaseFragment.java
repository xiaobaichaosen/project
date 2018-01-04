package com.yijie.com.yijie.fragment.kndergaren;

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

public class KndergartenBaseFragment extends BaseFragment {


    Unbinder unbinder;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kndergardbase;
    }

    @Override
    protected void initData() {
        final KndergartenFragment kndergartenFragment = new KndergartenFragment();
       final KndergartenMoreFragment kndergartenMoreFragment = new KndergartenMoreFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_kndergard_layout,kndergartenFragment);
        fragmentTransaction.commit();
        kndergartenFragment.setOnButtonClick(new KndergartenFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//进从右向左   出从左向右
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout,kndergartenMoreFragment);
                fragmentTransaction.commit();
            }
        });
        kndergartenMoreFragment.setOnButtonClick(new KndergartenMoreFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_reopen,R.anim.on_activity_close);
                fragmentTransaction.replace(R.id.main_kndergard_layout,kndergartenFragment);
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

