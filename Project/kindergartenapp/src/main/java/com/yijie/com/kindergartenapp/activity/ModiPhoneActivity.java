package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.KeyBoardHelper;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 修改绑定的手机号
 */
public class ModiPhoneActivity extends BaseActivity {
    @BindView(R.id.tv_currPhone)
    TextView tvCurrPhone;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_passWord)
    EditText etPassWord;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.ll_bottom)
    RelativeLayout llBottom;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_verviCode)
    TextView tvVerviCode;
    private int bottomHeight;
    private KeyBoardHelper boardHelper;
    private MyCountDownTimer myCountDownTimer;
    private  String phoneNumber;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_modiphone);
    }

    @Override
    public void init() {
        title.setText("修改手机号");
        setColor(ModiPhoneActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(ModiPhoneActivity.this); // 改变状态栏变成透明

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        tvCurrPhone.setText("当前手机号:"+phoneNumber);
        llBottom.post(new Runnable() {
            @Override
            public void run() {
                bottomHeight = llBottom.getHeight();
            }
        });

    }

    private KeyBoardHelper.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelper.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;

            if (bottomHeight > height) {
                llBottom.setVisibility(View.GONE);
            } else {

                int offset = bottomHeight - height;
                final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) llContent
                        .getLayoutParams();
                lp.topMargin = offset;
                llContent.setLayoutParams(lp);
            }

        }

        @Override
        public void OnKeyBoardClose(int oldKeyBoardheight) {
            if (View.VISIBLE != llBottom.getVisibility()) {
                llBottom.setVisibility(View.VISIBLE);
            }
            final ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) llContent
                    .getLayoutParams();
            if (lp.topMargin != 0) {
                lp.topMargin = 0;
                llContent.setLayoutParams(lp);
            }

        }
    };


    @Override
    protected void onDestroy() {
        if (boardHelper != null) {
            boardHelper.onDestory();
        }
        myCountDownTimer.onFinish();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_verviCode, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_verviCode:
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请输入手机号");
                } else if (etName.getText().toString().length() != 11) {
                    ShowToastUtils.showToastMsg(this, "手机号格式不正确");
                    return;
                } else {
                    HttpUtils getinstance = HttpUtils.getinstance(ModiPhoneActivity.this);
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("cellphone", etName.getText().toString());
                    getinstance.post(Constant.SENDSMSCODE, stringStringHashMap, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) {
                            commonDialog.dismiss();
                            LogUtil.e(s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getString("rescode").equals("200")) {
                                    String registCode = jsonObject.getString("data");
                                    etPassWord.setText(registCode);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {

                        }

                    });
                    myCountDownTimer = new MyCountDownTimer(15000, 1000);
                    myCountDownTimer.start();
                }

                break;
            case R.id.btnSubmit:
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请输入手机号");
                } else if (etName.getText().toString().length() != 11) {
                    ShowToastUtils.showToastMsg(this, "手机号格式不正确");
                    return;
                } else if (TextUtils.isEmpty(etPassWord.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请输入验证码");
                    return;
                } else {
                    HttpUtils getinstance = HttpUtils.getinstance(ModiPhoneActivity.this);
                    HashMap<String, String> stringStringHashMap = new HashMap<>();

                    stringStringHashMap.put("newPhone", etName.getText().toString());
                    stringStringHashMap.put("oldCellphone", phoneNumber);
                    stringStringHashMap.put("verifyCode", etPassWord.getText().toString());
                    getinstance.post(Constant.REPLACECELLPHONE, stringStringHashMap, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) {
                            commonDialog.dismiss();
                            LogUtil.e(s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);

                                if (jsonObject.getString("rescode").equals("200")) {
                                    Intent intent = new Intent();
                                    intent.setClass(ModiPhoneActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    ShowToastUtils.showToastMsg(ModiPhoneActivity.this,jsonObject.getString("resMessage"));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {

                        }

                    });
                    break;
                }
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
            if (null!=tvVerviCode){
                tvVerviCode.setClickable(false);
                tvVerviCode.setTextColor(Color.parseColor("#F38583"));
                tvVerviCode.setText(l / 1000 + "s 后");
            }

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (null!=tvVerviCode) {
                tvVerviCode.setClickable(true);
                tvVerviCode.setTextColor(Color.parseColor("#ffffff"));
                tvVerviCode.setText(" 获取验证码");
            }
        }
    }
}
