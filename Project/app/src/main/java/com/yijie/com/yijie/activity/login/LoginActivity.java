package com.yijie.com.yijie.activity.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ForgetPwdActivity;
import com.yijie.com.yijie.activity.login.modle.LoginModel;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.KeyBoardHelper;
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
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Request;
import okhttp3.Response;

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
    @BindView(R.id.tv_smsLogin)
    TextView tvSmsLogin;
    private KeyBoardHelper boardHelper;
    private int bottomHeight;
    private int userId;


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
        if (boardHelper != null) {
            boardHelper.onDestory();
        }
        super.onDestroy();
    }

    @OnClick({R.id.btnSubmit, R.id.et_passWord, R.id.et_name, R.id.tv_fogetPassWord,R.id.tv_smsLogin})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.btnSubmit:
                //请求网络
                final HttpUtils instance = HttpUtils.getinstance(LoginActivity.this);
                Map map = new HashMap();
                map.put("username", etName.getText().toString());
                map.put("password", etPassWord.getText().toString());
                final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);
                instance.post(Constant.LOGIN, map, new BaseCallback<String>() {

                    @Override
                    public void onRequestBefore() {
                        progressDialog.show();
                    }
                    @Override
                    public void onFailure(Request request, Exception e) {
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        JSONObject jsonObject = new JSONObject(s);
                        LogUtil.e(s);
                        if (jsonObject.getString("rescode").equals("200")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            SharedPreferencesUtils.setParam(LoginActivity.this, "cookie", data.getString("cookieString"));
                            SharedPreferencesUtils.setParam(LoginActivity.this, "user", data.toString());
                            SharedPreferencesUtils.setParam(LoginActivity.this, "isLogin", "登录成功");
                            SharedPreferencesUtils.setParam(LoginActivity.this, "roleName", data.getString("roleName"));
                            try {
                                JSONObject json = new JSONObject(data.toString());
                                userId = Integer.parseInt(json.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Set<String> tags = new HashSet<String>();
                            //清除tags
                            tags.add("");
                            JPushInterface.setAliasAndTags(LoginActivity.this, "", tags, new TagAliasCallback() {
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
                            JPushInterface.setAliasAndTags(LoginActivity.this, userId + "", tags, new TagAliasCallback() {
                                @Override
                                public void gotResult(int code, String s, Set<String> set) {
                                    switch (code) {
                                        case 0:
                                            //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                            LogUtil.e("Set tag and alias success极光推送别名设置成功");
                                            Intent intent = new Intent();
                                            intent.setClass(LoginActivity.this, MainActivity.class);
                                            ShowToastUtils.showToastMsg(LoginActivity.this, "登录成功！");
                                            startActivity(intent);
                                            finish();
                                            progressDialog.dismiss();
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
                            ShowToastUtils.showToastMsg(LoginActivity.this, "登录失败！");
                            progressDialog.dismiss();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        progressDialog.dismiss();
                    }
                });

                break;


            case R.id.tv_fogetPassWord:
                //忘记密码
                intent.setClass(LoginActivity.this, ForgetPwdActivity.class);
                intent.putExtra("isFromForget",1);
                startActivity(intent);
                break;
            case R.id.tv_smsLogin:
                //验证码登录
                intent.setClass(LoginActivity.this, ForgetPwdActivity.class);
                intent.putExtra("isFromForget",2);
                startActivity(intent);
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
