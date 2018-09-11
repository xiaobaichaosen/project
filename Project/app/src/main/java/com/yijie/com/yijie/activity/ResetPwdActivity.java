package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 重置密码
 */
public class ResetPwdActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_sendsms)
    TextView tvSendsms;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_showPhoneNumber)
    TextView tvShowPhoneNumber;
   private String cellphone;
    private MyCountDownTimer myCountDownTimer;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_resetpwd);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("重置密码");
        String smsCode = getIntent().getStringExtra("smsCode");
        cellphone = getIntent().getStringExtra("cellphone");
        etSmscode.setText(smsCode);
        String startCell = cellphone.substring(0, 3) + "****" + cellphone.substring(7, 11);
        tvShowPhoneNumber.setText("请输入"+startCell+"收到的短信验证码");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
    @OnCheckedChanged({R.id.cb_isVisiable})
    public void OnCheckedChangeListener(CheckBox view, boolean ischanged) {
        if (ischanged) {
            etPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPwd.setSelection(etPwd.getText().length());//set cursor to the end
    }
    @OnClick({R.id.back, R.id.title, R.id.tv_sendsms, R.id.tv_next})
    public void onViewClicked(View view) {
        final HttpUtils instance = HttpUtils.getinstance(ResetPwdActivity.this);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_sendsms:
                //发送验证码
                Map map = new HashMap();
                map.put("mobile", cellphone);
                instance.post(Constant.sendSmsCode, map, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {

                        commonDialog.show();
                        commonDialog.setTitle("获取验证码中...");
                    }
                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e("===" + s);
                        JSONObject jsonObject = new JSONObject(s);
                        String resMessage = jsonObject.getString("resMessage");
                        String rescode = jsonObject.getString("rescode");
                        ShowToastUtils.showToastMsg(ResetPwdActivity.this, resMessage);
                        if (rescode.equals("200")){
                            myCountDownTimer = new MyCountDownTimer(60000, 1000);
                            myCountDownTimer.start();
                            etSmscode.setText(jsonObject.getString("data"));
                        }
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }
                });
                break;

            case R.id.tv_next:
                String password = etPwd.getText().toString().trim();
                String smscode = etSmscode.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    ShowToastUtils.showToastMsg(ResetPwdActivity.this,"请输入新密码");
                    return;
                }else  if (TextUtils.isEmpty(smscode)){
                    ShowToastUtils.showToastMsg(ResetPwdActivity.this,"请输入验证码");
                }
                Map resetMap = new HashMap();
                resetMap.put("mobile", cellphone);
                resetMap.put("verifyCode", smscode);
                resetMap.put("password", password);
                instance.post(Constant.updatePassword, resetMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {

                        commonDialog.show();
                        commonDialog.setTitle("更新密码中...");
                    }
                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e("===" + s);
                        JSONObject jsonObject = new JSONObject(s);
                        String resMessage = jsonObject.getString("resMessage");
                        String rescode = jsonObject.getString("rescode");
                        ShowToastUtils.showToastMsg(ResetPwdActivity.this, resMessage);
                        if (rescode.equals("200")){
                            ShowToastUtils.showToastMsg(ResetPwdActivity.this,resMessage);
                             Intent intent=new Intent();
                            intent.setClass(ResetPwdActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (rescode.equals("500")){
                            ShowToastUtils.showToastMsg(ResetPwdActivity.this,resMessage);
                        }
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }
                });

                break;
        }
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
            if (tvSendsms != null) {
                tvSendsms.setClickable(false);
                tvSendsms.setTextColor(Color.parseColor("#F38583"));
                tvSendsms.setText(l / 1000 + "s 后");
            }
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (tvSendsms != null) {
                tvSendsms.setClickable(true);
                tvSendsms.setText(" 重发验证码");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (null!=myCountDownTimer){
            myCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
