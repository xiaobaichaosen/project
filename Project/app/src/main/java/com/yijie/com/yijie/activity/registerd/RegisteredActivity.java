package com.yijie.com.yijie.activity.registerd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/12.
 */

public class RegisteredActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;


    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.et_phone)
    EditText etPhone;
    LoadingDailog dialog;
    @Override
    public void setContentView() {

        setContentView(R.layout.activity_regisited);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(this)
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);

        dialog = loadBuilder.create();

    }

    @OnClick({R.id.btnSubmit, R.id.iv_back})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:

                String phoneNumber = etPhone.getText().toString();
                if (phoneNumber.equals("")){
                    ShowToastUtils.showToastMsg(RegisteredActivity.this,"请输入手机号!");
                }else if (!TelephoneRejestUtils.checkCellphone(phoneNumber)){
                    ShowToastUtils.showToastMsg(RegisteredActivity.this,"手机号不存在");
                }else{
                    //请求网络

                    HttpUtils getinstance = HttpUtils.getinstance();

                    getinstance.get(Constant.getRegistCodeUrl+"?phone="+phoneNumber, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            dialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            dialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) {
                            dialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                if (jsonObject.getString("status").equals("ok")) {
                                    if (jsonObject.getString("result").equals("用户已经注册")) {
                                        ShowToastUtils.showToastMsg(RegisteredActivity.this,jsonObject.getString("result"));
                                    } else {

                                        Intent intent = new Intent();
                                        intent.setClass(RegisteredActivity.this, VerificationCodeActivity.class);
                                        intent.putExtra("phoneNumber", etPhone.getText().toString());
                                        intent.putExtra("registCode", jsonObject.getString("result"));
                                        startActivity(intent);
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            dialog.dismiss();
                        }



                        });

                }


                break;

            case R.id.iv_back:
                finish();
                break;
        }

    }
//    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.BEFORE_TEXT_CHANGED)
//    void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }

    @OnTextChanged(value = R.id.et_phone, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btnSubmit.setBackgroundResource(R.drawable.bg_round_lightyellow);
        } else {
            btnSubmit.setBackgroundResource(R.drawable.bg_round_ligtred);
        }
    }

//
//    @OnTextChanged(value = R.id.clearEditText, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    void afterTextChanged(Editable s) {
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
