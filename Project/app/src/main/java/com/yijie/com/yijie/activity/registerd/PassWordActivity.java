package com.yijie.com.yijie.activity.registerd;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/15.
 * <p>
 * 设置密码
 */

public class PassWordActivity extends BaseActivity {

    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;

    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.tv_toHome)
    TextView tvToHome;
    @BindView(R.id.btn_next)
    Button btnNext;
    private String phoneNumber;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_password);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        phoneNumber = getIntent().getStringExtra("phoneNumber");

    }

    @OnClick({R.id.tv_toHome,R.id.btn_next})
    public void Click(View view) {


        switch (view.getId()) {
            case R.id.tv_toHome:
//              dialog.show();
                regist(phoneNumber,"");
//                dialog.dismiss();
                break;

            case R.id.btn_next:

                String passWord = etPassWord.getText().toString().trim();
                if (passWord.equals("")){
                    ShowToastUtils.showToastMsg(PassWordActivity.this,"请输入密码!");
                }else if (passWord.length()<6||passWord.length()>20){
                    ShowToastUtils.showToastMsg(PassWordActivity.this,"密码长度需6-20个字符!");
                }else{
//                    dialog.show();
                    regist(phoneNumber,passWord);
//                    dialog.dismiss();
                }

                break;



        }

    }
    //保存注册信息到数据库
    public void regist(String phone,String password){
        //请求网络

        HttpUtils instance = HttpUtils.getinstance(PassWordActivity.this);
        Map map=new HashMap();
        map.put("phone",phone);
        map.put("password",password);
        instance.post(Constant.registUrl, map, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                dialog.dismiss();
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
                    if (jsonObject.getString("status").equals("ok")){
                        dialog.dismiss();
                        ShowToastUtils.showToastMsg(PassWordActivity.this,jsonObject.getString("message"));
                        Intent intent = new Intent();
                        intent.setClass(PassWordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
    @OnCheckedChanged({R.id.cb_isVisiable})
    public void OnCheckedChangeListener(CheckBox view, boolean ischanged) {
        if (ischanged) {

            etPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);


        } else {

            etPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        etPassWord.setSelection(etPassWord.getText().length());//set cursor to the end
    }

    /**
     * 监听输入框变化
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged(value = R.id.et_passWord, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btnNext.setBackgroundResource(R.drawable.bg_round_lightyellow);
        } else {
            btnNext.setBackgroundResource(R.drawable.bg_round_ligtred);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
