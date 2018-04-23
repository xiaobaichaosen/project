package com.yijie.com.kindergartenapp.activity.regist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.GrademAdressActivity;
import com.yijie.com.kindergartenapp.activity.MealsActivity;
import com.yijie.com.kindergartenapp.activity.StayActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/3/29.
 */

public class RegistDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_gradStay)
    RelativeLayout rlGradStay;
    @BindView(R.id.iv_goright)
    ImageView ivGoright;
    @BindView(R.id.rl_meal)
    RelativeLayout rlMeal;
    @BindView(R.id.rl_gradAdress)
    RelativeLayout rlGradAdress;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rgistdetail);
    }

    @Override
    public void init() {
        title.setText("注册信息填写");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_gradStay, R.id.iv_goright, R.id.rl_meal,R.id.rl_gradAdress})
    public void onViewClicked(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_gradStay:
                //跳转到住宿

                intent.setClass(RegistDetailActivity.this, StayActivity.class);
                startActivity(intent);

                break;
            case R.id.iv_goright:
                break;
            case R.id.rl_meal:

                intent.setClass(RegistDetailActivity.this, MealsActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_gradAdress:

                intent.setClass(RegistDetailActivity.this,  GrademAdressActivity.class);
                startActivity(intent);

                break;

        }
    }
}
