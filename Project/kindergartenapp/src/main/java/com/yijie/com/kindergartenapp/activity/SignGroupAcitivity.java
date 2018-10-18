package com.yijie.com.kindergartenapp.activity;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

/**
 * 考勤组管理
 */
public class SignGroupAcitivity extends BaseActivity{
    @Override
    public void setContentView() {
        setContentView(R.layout.fragment_setting);
    }
    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }
}
