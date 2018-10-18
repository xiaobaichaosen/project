package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 考勤日期
 */
public class SignDateActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.cb_one)
    CheckBox cbOne;
    @BindView(R.id.cb_two)
    CheckBox cbTwo;
    @BindView(R.id.cb_three)
    CheckBox cbThree;
    @BindView(R.id.cb_four)
    CheckBox cbFour;
    @BindView(R.id.cb_five)
    CheckBox cbFive;
    @BindView(R.id.cb_six)
    CheckBox cbSix;
    @BindView(R.id.cb_seven)
    CheckBox cbSeven;
    @BindView(R.id.swith_timeSet)
    SwitchButton swithTimeSet;
    @BindView(R.id.rl_signSet)
    RelativeLayout rlSignSet;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_signdate);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("考勤日期");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @OnCheckedChanged({R.id.cb_one,R.id.cb_two,R.id.cb_three,R.id.cb_four,R.id.cb_five,R.id.cb_six,R.id.cb_seven})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
        switch (buttonView.getId()) {
            case R.id.cb_one:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_two:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_three:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_four:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_five:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_six:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
            case R.id.cb_seven:
                if (isChecked){
                    ShowToastUtils.showToastMsg(this,"勾选");
                }else {
                    ShowToastUtils.showToastMsg(this,"未勾选");
                }
                break;
        }
    }
    @OnClick({R.id.back, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.tv_next:
                break;
        }
    }
}
