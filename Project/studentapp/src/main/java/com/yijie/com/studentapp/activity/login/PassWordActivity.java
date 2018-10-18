package com.yijie.com.studentapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.MainActivity;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.SelectSchoolActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.CheckUserUtils;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class PassWordActivity  extends BaseActivity {

    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;

    @BindView(R.id.btn_next)
    Button btnNext;
    private String phoneNumber;
    private String verifyCode;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_password);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        verifyCode = getIntent().getStringExtra("verifyCode");

    }

    @OnClick({ R.id.btn_next})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_next:
                String passWord = etPassWord.getText().toString().trim();
                if (passWord.equals("")) {
                    ShowToastUtils.showToastMsg(PassWordActivity.this, "请输入密码!");
                    return;
                } else if (!CheckUserUtils.checkString(passWord)) {
                    ShowToastUtils.showToastMsg(PassWordActivity.this, "密码长度需8-20个字符!");
                    return;
                } else {
                    intent.putExtra("password",passWord);
                    intent.putExtra("phonenumber",phoneNumber);
                    intent.putExtra("verifyCode",verifyCode);
                    intent.setClass(PassWordActivity.this, SelectSchoolActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
        }

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
     *
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
