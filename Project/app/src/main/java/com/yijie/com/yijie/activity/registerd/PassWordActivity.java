package com.yijie.com.yijie.activity.registerd;

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

import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

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

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_password);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @OnClick({R.id.tv_toHome,R.id.btn_next})
    public void Click(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_toHome:
                intent.setClass(PassWordActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_next:

                String passWord = etPassWord.getText().toString().trim();
                if (passWord.equals("")){
                    ShowToastUtils.showToastMsg(PassWordActivity.this,"请输入密码!");
                }else if (passWord.length()<6||passWord.length()>20){
                    ShowToastUtils.showToastMsg(PassWordActivity.this,"密码长度需6-20个字符!");
                }else{
                    intent.setClass(PassWordActivity.this, MainActivity.class);
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
