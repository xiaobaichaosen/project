package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.PassWordActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.CheckUserUtils;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 注册
 */
public class RegistActivity extends BaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_verviCode)
    EditText etVerviCode;
    @BindView(R.id.tv_verviCode)
    TextView tvVerviCode;
    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;
    @BindView(R.id.et_passWord_comfirm)
    EditText etPassWordComfirm;
    @BindView(R.id.cb_isVisiable_comfirm)
    CheckBox cbIsVisiableComfirm;
    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    private MyCountDownTimer myCountDownTimer;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_regist);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @OnClick({R.id.tv_verviCode, R.id.btn_one,R.id.back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        final HttpUtils instance = HttpUtils.getinstance(this);
        final Map map = new HashMap();
        switch (view.getId()) {
            case R.id.tv_verviCode:
                //获取验证码
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                //获取验证码
                if (etName.getText().toString().trim().isEmpty()) {
                    ShowToastUtils.showToastMsg(RegistActivity.this, "请填写手机号");
                    return;
                }
                if (etName.getText().toString().length() != 11) {
                    ShowToastUtils.showToastMsg(RegistActivity.this, "手机号格式不正确");
                    return;
                } else {

                    map.put("cellphone",etName.getText().toString());
                    instance.post(Constant.SENDSMSCODE,map, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                            commonDialog.setTitle("验证码获取中...");
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) throws JSONException {
                            JSONObject jsonObject = new JSONObject(s);
                            String resMessage = jsonObject.getString("resMessage");
                            String rescode = jsonObject.getString("rescode");
                            ShowToastUtils.showToastMsg(RegistActivity.this, resMessage);
                            if (rescode.equals("200")) {
                                etVerviCode.setText(jsonObject.getString("data"));
                                myCountDownTimer = new MyCountDownTimer(60000, 1000);
                                myCountDownTimer.start();
                            }

                            LogUtil.e("===" + s);
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            commonDialog.dismiss();
                        }
                    });

                }
                break;
            case R.id.btn_one:


                    if (etName.getText().toString().trim().isEmpty()) {
                        ShowToastUtils.showToastMsg(this, "请填写手机号");
                        return;
                    }
                    if (etName.getText().toString().length() != 11) {
                        ShowToastUtils.showToastMsg(this, "手机号格式有误");
                        return;
                    }
                    if (etVerviCode.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(this, "请填写验证码");
                        return;
                    }

                    if (etPassWord.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(this, "请填写密码");
                        return;
                    }
                    if (etPassWordComfirm.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(this, "请填写确定密码");
                        return;
                    }
                    if (!CheckUserUtils.checkString(etPassWord.getText().toString()) || !CheckUserUtils.checkString(etPassWordComfirm.getText().toString())) {
                        ShowToastUtils.showToastMsg(this, "必须6-20位数字和密码混合!");
                        return;
                    }
                    if (!etPassWordComfirm.getText().toString().equals(etPassWord.getText().toString())) {
                        ShowToastUtils.showToastMsg(this, "两次密码输入不一致");
                        return;

                    }
                intent.putExtra("password",etPassWord.getText().toString());
                intent.putExtra("phonenumber",etName.getText().toString());
                intent.putExtra("verifyCode",etVerviCode.getText().toString());
                intent.setClass(this, SelectSchoolActivity.class);
                startActivity(intent);

                break;
            case R.id.back:
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

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(false);
                tvVerviCode.setTextColor(Color.parseColor("#F38583"));
                tvVerviCode.setText(l / 1000 + "s 后");
            }


        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(true);
                tvVerviCode.setTextColor(getResources().getColor(R.color.colorTheme));
                tvVerviCode.setText(" 获取验证码");
            }
        }
    }
}
