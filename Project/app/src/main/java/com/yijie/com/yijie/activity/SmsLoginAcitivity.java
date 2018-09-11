package com.yijie.com.yijie.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 验证码登录
 */
public class SmsLoginAcitivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_showPhoneNumber)
    TextView tvShowPhoneNumber;
    @BindView(R.id.resms)
    TextView resms;
    private String cellphone;
    private MyCountDownTimer myCountDownTimer;
    private int userId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_smslogin);
    }

    @Override
    protected void onDestroy() {
        if (null!=myCountDownTimer){
            myCountDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        String smsCode = getIntent().getStringExtra("smsCode");
        cellphone = getIntent().getStringExtra("cellphone");
        etCellphone.setText(smsCode);
        title.setText("输入验证码");
//        String startCell = cellphone.substring(0, 3) + "****" + cellphone.substring(7, 11);
        tvShowPhoneNumber.setText("验证码已发送到手机:"+cellphone);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_next,R.id.resms})
    public void onViewClicked(View view) {
        final HttpUtils instance = HttpUtils.getinstance(SmsLoginAcitivity.this);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_next:
                //验证码登录
                Map smsMap = new HashMap();
                String verifyCode = etCellphone.getText().toString().trim();
                if (TextUtils.isEmpty(verifyCode)){
                    ShowToastUtils.showToastMsg(SmsLoginAcitivity.this,"请输入收到的验证码");
                    return;
                }
                smsMap.put("mobile", cellphone);
                smsMap.put("verifyCode",verifyCode );
                instance.post(Constant.verfyCodeLogin, smsMap, new BaseCallback<String>() {

                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        JSONObject jsonObject = new JSONObject(s);
                        LogUtil.e(s);
                        if (jsonObject.getString("rescode").equals("200")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            SharedPreferencesUtils.setParam(SmsLoginAcitivity.this, "cookie", data.getString("cookieString"));
                            SharedPreferencesUtils.setParam(SmsLoginAcitivity.this, "user", data.toString());
                            SharedPreferencesUtils.setParam(SmsLoginAcitivity.this, "isLogin", "登录成功");
                            SharedPreferencesUtils.setParam(SmsLoginAcitivity.this, "roleName", data.getString("roleName"));
                            try {
                                JSONObject json = new JSONObject(data.toString());
                                userId = Integer.parseInt(json.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Set<String> tags = new HashSet<String>();
                            //清除tags
                            tags.add("");
                            JPushInterface.setAliasAndTags(SmsLoginAcitivity.this, "", tags, new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                }
                            });
                            //重新设置推送tags
                            tags.clear();
//                    tags.add(data.getString("roleName"));
                            String roleName = data.getString("roleName");
                            String[] split = roleName.split(",");
                            for (int i = 0; i < split.length; i++) {
                                tags.add(split[i]);
                            }
                            JPushInterface.setAliasAndTags(SmsLoginAcitivity.this, userId + "", tags, new TagAliasCallback() {
                                @Override
                                public void gotResult(int code, String s, Set<String> set) {
                                    switch (code) {
                                        case 0:
                                            //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                            LogUtil.e("Set tag and alias success极光推送别名设置成功");
                                            Intent intent = new Intent();
                                            intent.setClass(SmsLoginAcitivity.this, MainActivity.class);
                                            ShowToastUtils.showToastMsg(SmsLoginAcitivity.this, "登录成功！");
                                            startActivity(intent);
                                            finish();
                                            commonDialog.dismiss();
                                            break;
                                        case 6002:
                                            //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                                            LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
                                            break;
                                        default:
                                            LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
                                            break;
                                    }

                                }
                            });
                        } else {
                            ShowToastUtils.showToastMsg(SmsLoginAcitivity.this, "登录失败！");
                            commonDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }
                });
                break;
            case  R.id.resms:
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
                        ShowToastUtils.showToastMsg(SmsLoginAcitivity.this, resMessage);
                        if (rescode.equals("200")){
                            myCountDownTimer = new MyCountDownTimer(60000, 1000);
                            myCountDownTimer.start();
                            etCellphone.setText(jsonObject.getString("data"));
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
            if (resms != null) {
                resms.setClickable(false);
                resms.setTextColor(Color.parseColor("#F38583"));
                resms.setText(l / 1000 + "s 后");
            }
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (resms != null) {
                resms.setClickable(true);
                resms.setText(" 重发获取验证码");
            }
        }
    }

}
