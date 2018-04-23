package com.yijie.com.kindergartenapp.activity.regist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.activity.login.PassWordActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.AppInstallUtils;
import com.yijie.com.kindergartenapp.utils.KeyBoardHelper;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by 奕杰平台 on 2018/1/12.
 * 登陆
 */

public class RegistActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;


    //    @BindView(R.id.loading)
//    LoadingLayout loading;


    @BindView(R.id.loading)
    RelativeLayout loading;



    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.ll_content)
    LinearLayout llContent;

    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;


    @BindView(R.id.tv_verviCode)
    TextView tvVerviCode;
    private KeyBoardHelper boardHelper;
    private int bottomHeight;

    private MyCountDownTimer myCountDownTimer;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_rgist);

    }

    @Override
    public void init() {



        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明





    }

    private KeyBoardHelper.OnKeyBoardStatusChangeListener onKeyBoardStatusChangeListener = new KeyBoardHelper.OnKeyBoardStatusChangeListener() {

        @Override
        public void OnKeyBoardPop(int keyBoardheight) {

            final int height = keyBoardheight;

            if (bottomHeight > height) {

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
        super.onDestroy();
    }





    @OnClick({R.id.btnSubmit, R.id.et_passWord, R.id.et_name,R.id.tv_verviCode})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:
                //跳转到注册详情
                Intent intent = new Intent();
                intent.setClass(RegistActivity.this, RegistDetailActivity.class);
                startActivity(intent);

                break;

            case R.id.tv_fogetPassWord:

                //忘记密码
                break;



            case  R.id.tv_verviCode:
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间

                myCountDownTimer = new MyCountDownTimer(15000, 1000);
                myCountDownTimer.start();
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
    @OnTextChanged(value = {R.id.et_name, R.id.et_passWord}, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            btnSubmit.setBackgroundResource(R.drawable.bg_round_lightyellow);
        } else {
            btnSubmit.setBackgroundResource(R.drawable.bg_round_ligtred);
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
            tvVerviCode.setClickable(false);
            tvVerviCode.setTextColor(Color.parseColor("#F38583"));
            tvVerviCode.setText(l / 1000 + "s 后");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            tvVerviCode.setClickable(true);
            tvVerviCode.setTextColor(Color.parseColor("#ffffff"));
            tvVerviCode.setText(" 获取验证码");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
