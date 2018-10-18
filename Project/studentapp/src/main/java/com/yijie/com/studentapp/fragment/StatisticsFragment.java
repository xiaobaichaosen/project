package com.yijie.com.studentapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.photo.CameraActivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 统计
 */
public class StatisticsFragment extends BaseFragment {
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    Unbinder unbinder;
    @BindView(R.id.tv_calender)
    TextView tvCalender;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_statisitce;
    }

    @Override
    protected void initView() {

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

    @OnClick({R.id.tv_date,R.id.tv_calender})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                TimePickerView.Type yearType = TimePickerView.Type.YEAR_MONTH_DAY;
                String yearFormat = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(mActivity, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvDate.setText(date);
                    }
                });
                break;
            case R.id.tv_calender:
                Intent intent = new Intent();
//                intent.setClass(mActivity, CalenderActivity.class);
                startActivity(intent);
                break;

        }
    }

    @OnClick()
    public void onViewClicked() {
    }
}
