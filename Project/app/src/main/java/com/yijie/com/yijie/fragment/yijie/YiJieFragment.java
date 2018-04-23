package com.yijie.com.yijie.fragment.yijie;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.SchoolMoreActivity;
import com.yijie.com.yijie.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/4/19.
 */

public class YiJieFragment extends BaseFragment {
    @BindView(R.id.btn_school)
    Button btnSchool;
    @BindView(R.id.btn_kendergard)
    Button btnKendergard;
    Unbinder unbinder;
    @BindView(R.id.btn_student)
    Button btnStudent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yijie;
    }

    @Override
    protected void initData() {

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

    @OnClick({R.id.btn_school, R.id.btn_kendergard,R.id.btn_student})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_school:
                intent.setClass(mActivity, SchoolMoreActivity.class);
                intent.putExtra("page", 0);
                startActivity(intent);
                break;
            case R.id.btn_kendergard:
                intent.setClass(mActivity, SchoolMoreActivity.class);
                intent.putExtra("page", 1);
                startActivity(intent);
                break;

            case  R.id.btn_student:
                intent.setClass(mActivity, SchoolMoreActivity.class);
                intent.putExtra("page", 2);
                startActivity(intent);
                break;
        }
    }


}
