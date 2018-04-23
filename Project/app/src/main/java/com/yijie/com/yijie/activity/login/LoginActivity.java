package com.yijie.com.yijie.activity.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.modle.LoginCallBack;
import com.yijie.com.yijie.activity.login.modle.LoginModel;
import com.yijie.com.yijie.activity.registerd.RegisteredActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.AppInstallUtils;
import com.yijie.com.yijie.utils.KeyBoardHelper;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;

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

public class LoginActivity extends BaseActivity {

    LoginModel loginModel;
    @BindView(R.id.et_name)
    EditText etName;


    //    @BindView(R.id.loading)
//    LoadingLayout loading;


    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.iv_qq_login)
    ImageView ivQqLogin;
    @BindView(R.id.iv_wx_login)
    ImageView ivWxLogin;
    @BindView(R.id.tv_registered)
    TextView tvRegistered;
    @BindView(R.id.tv_fogetPassWord)
    TextView tvFogetPassWord;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_bottom)
    RelativeLayout llBottom;
    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;
    private KeyBoardHelper boardHelper;
    private int bottomHeight;

    Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


        }
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);

    }

    @Override
    public void init() {


        loginModel = new LoginModel();
        setColor(LoginActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(LoginActivity.this); // 改变状态栏变成透明
//        loading.showContent();



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
        if (boardHelper!=null) {
            boardHelper.onDestory();
        }
        super.onDestroy();
    }

    @OnClick({R.id.btnSubmit, R.id.iv_qq_login, R.id.iv_wx_login, R.id.tv_registered, R.id.et_passWord, R.id.et_name})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:
                loginModel.login(etName.getText().toString(), etPassWord.getText().toString(), new LoginCallBack() {
                    @Override
                    public void beforLogin() {
                        dialog.show();
                    }

                    @Override
                    public void onLoginSuccess(String success) {
                        LogUtil.e(success);
                        try {
                            JSONObject jsonObject=new JSONObject(success);
                            String status = jsonObject.getString("status");
                            if (status.equals("ok")){

                                ShowToastUtils.showToastMsg(LoginActivity.this, jsonObject.getString("message"));
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                ShowToastUtils.showToastMsg(LoginActivity.this, jsonObject.getString("message"));
                            }
                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onLoginFail(Exception error) {
                        dialog.dismiss();

                    }

                });
//
                break;
            //qq登陆
            case R.id.iv_qq_login:
                if (AppInstallUtils.isQQClientAvailable(LoginActivity.this)) {
                    authorization(SHARE_MEDIA.QQ);
                } else {
                    ShowToastUtils.showToastMsg(LoginActivity.this, "请先安装QQ");
                }


                break;
            //微信登陆
            case R.id.iv_wx_login:

                if (AppInstallUtils.isQQClientAvailable(LoginActivity.this)) {
                    authorization(SHARE_MEDIA.WEIXIN);
                } else {
                    ShowToastUtils.showToastMsg(LoginActivity.this, "请先安装微信");
                }


                break;
            case R.id.tv_registered:

                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_fogetPassWord:

                //忘记密码
                break;

            case R.id.et_name:
                boardHelper = new KeyBoardHelper(LoginActivity.this);
                boardHelper.onCreate();

                boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);


                break;

            case R.id.et_passWord:
                boardHelper = new KeyBoardHelper(LoginActivity.this);
                boardHelper.onCreate();
                boardHelper.setOnKeyBoardStatusChangeListener(onKeyBoardStatusChangeListener);
                break;


        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();


                //拿到信息去请求登录接口。。。
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                ShowToastUtils.showToastMsg(LoginActivity.this, "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                ShowToastUtils.showToastMsg(LoginActivity.this, "授权取消");
            }
        });
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
