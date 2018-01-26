package com.yijie.com.yijie.activity.registerd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.verficationcode.codeinputlib.CodeInput;
import com.yijie.com.yijie.view.verficationcode.codeinputlib.callback.CodeInputCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class VerificationCodeActivity extends BaseActivity  implements CodeInputCallback<CodeInput> {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.tv_showPhoneNumber)
    TextView tvShowPhoneNumber;

    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_recode)
    TextView tvRecode;
    @BindView(R.id.tv_reVoiceCode)
    TextView tvReVoiceCode;
    @BindView(R.id.tv_or)
    TextView tvOr;
    @BindView(R.id.codeInput)
    CodeInput codeInput;
    private MyCountDownTimer myCountDownTimer;
    String registCode;
    private boolean isGetCode;
    String phoneNumber;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_verificationcode);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @Override
    public void init() {


        phoneNumber = getIntent().getStringExtra("phoneNumber");

        registCode = getIntent().getStringExtra("registCode");

        ShowToastUtils.showToastMsg(this,registCode);
        tvShowPhoneNumber.setText(" +86 " + phoneNumber.substring(0, 3) + "  " + phoneNumber.substring(3, 7) + "  " + phoneNumber.substring(7, 11));
        //new倒计时对象,总共的时间,每隔多少秒更新一次时间
        codeInput.setCodeInputListener(this);
        myCountDownTimer = new MyCountDownTimer(15000, 1000);
        myCountDownTimer.start();

    }

    @Override
    public void onInputFinish(CodeInput ci, String inputResult) {
        if (inputResult.equals(registCode)&&!isGetCode){

            Intent intent=new Intent();
            intent.setClass(VerificationCodeActivity.this,PassWordActivity.class);
            intent.putExtra("phoneNumber", phoneNumber);
            startActivity(intent);
        }else{
            ShowToastUtils.showToastMsg(VerificationCodeActivity.this,"验证码输入不正确!");
        }

    }

    @Override
    public void onInput(CodeInput ci, Character currentChar) {

    }

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tvRecode.setClickable(false);
            tvReVoiceCode.setClickable(false);
            tvRecode.setTextColor(Color.parseColor("#F38583"));
            tvReVoiceCode.setTextColor(Color.parseColor("#F38583"));
            tvOr.setTextColor(Color.parseColor("#F38583"));
            tvTime.setText(l / 1000 + "s 后");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            isGetCode=true;
            tvRecode.setClickable(true);
            tvReVoiceCode.setClickable(true);
            tvOr.setTextColor(Color.parseColor("#ffffff"));
            tvRecode.setTextColor(Color.parseColor("#ffffff"));
            tvReVoiceCode.setTextColor(Color.parseColor("#ffffff"));
            tvTime.setText(" ");
        }
    }

    @OnClick({R.id.tv_recode, R.id.tv_reVoiceCode,R.id.iv_back})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.tv_recode:
                ShowToastUtils.showToastMsg(VerificationCodeActivity.this, "重写获取验证码");
                myCountDownTimer.start();
                break;
            case R.id.tv_reVoiceCode:
                ShowToastUtils.showToastMsg(VerificationCodeActivity.this, "重写获取语音验证码");
                myCountDownTimer.start();
                break;

            case R.id.iv_back:
               finish();
                break;



        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
