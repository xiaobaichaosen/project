package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
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
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 忘记密码
 */
public class ForgetPwdActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.et_cellphone)
    EditText etCellphone;
   private String cellphone;
    int isFromForget;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_forgetpwd);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        isFromForget = getIntent().getIntExtra("isFromForget", 0);
        if (isFromForget==1){
            title.setText("重置登录密码");
        }else if (isFromForget==2){
            title.setText("输入手机号");
            tvNext.setText("获取验证码");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_next:
                //发送验证码
                cellphone = etCellphone.getText().toString().trim();
                final HttpUtils instance = HttpUtils.getinstance(ForgetPwdActivity.this);
                if (cellphone.isEmpty()) {
                    ShowToastUtils.showToastMsg(ForgetPwdActivity.this, "请输入手机号");
                    return;
                } else if (cellphone.length() < 11) {
                    ShowToastUtils.showToastMsg(ForgetPwdActivity.this, "请输入正确的手机号");
                    return;
                }
                Map map = new HashMap();
                map.put("mobile", cellphone);
                instance.post(Constant.sendSmsCode, map, new BaseCallback<String>() {
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
                        LogUtil.e("===" + s);
                        JSONObject jsonObject = new JSONObject(s);
                        String resMessage = jsonObject.getString("resMessage");
                        String rescode = jsonObject.getString("rescode");
                        ShowToastUtils.showToastMsg(ForgetPwdActivity.this, resMessage);
                        Intent intent=new Intent();
                        if (rescode.equals("200")){
                            if (isFromForget==1){
                                intent.setClass(ForgetPwdActivity.this,ResetPwdActivity.class);
                                intent.putExtra("cellphone",cellphone);
                                intent.putExtra("smsCode",jsonObject.getString("data"));
                                startActivity(intent);
                            }else if (isFromForget==2){
                                intent.setClass(ForgetPwdActivity.this,SmsLoginAcitivity.class);
                                intent.putExtra("cellphone",cellphone);
                                intent.putExtra("smsCode",jsonObject.getString("data"));
                                startActivity(intent);
                            }

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
}
