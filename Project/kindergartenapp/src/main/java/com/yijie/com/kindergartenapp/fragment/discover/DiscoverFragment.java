package com.yijie.com.kindergartenapp.fragment.discover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initData() {
        title.setText("发现");
        back.setVisibility(View.GONE);
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
