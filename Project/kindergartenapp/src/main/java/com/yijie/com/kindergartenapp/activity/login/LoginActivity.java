package com.yijie.com.kindergartenapp.activity.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
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

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;

import com.yijie.com.kindergartenapp.activity.regist.RegistActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistDetailActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.PermissionsActivity;
import com.yijie.com.kindergartenapp.base.PermissionsChecker;
import com.yijie.com.kindergartenapp.utils.AppInstallUtils;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.KeyBoardHelper;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.StutasToolUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

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
    @BindView(R.id.tv_regist)
    TextView tvRegist;

    private KeyBoardHelper boardHelper;
    private int bottomHeight;
    private static final int REQUEST_CODE = 0; // 请求码
    //    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    public void setContentView() {
                PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, 0, PERMISSIONS);
        }
        setContentView(R.layout.activity_login);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {

        }
    }
    @Override
    public void init() {

        setColor(LoginActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(LoginActivity.this); // 改变状态栏变成透明

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


    @OnClick({R.id.btnSubmit, R.id.iv_qq_login, R.id.iv_wx_login, R.id.et_passWord,R.id.tv_regist})
    public void Click(View view) {

        switch (view.getId()) {
            case R.id.btnSubmit:
                final HttpUtils instance = HttpUtils.getinstance(LoginActivity.this);
//                final ProgressDialog progressDialog = ViewUtils.getProgressDialog(LoginActivity.this);
                Map map = new HashMap();

                String phoneOremail = etName.getText().toString().trim();

                    if (StutasToolUtils.checkEmail(phoneOremail)){
                        map.put("eamil", phoneOremail);
                    }else {
                        map.put("cellphone",phoneOremail);
                    }
                    map.put("password", etPassWord.getText().toString());
                    if (TextUtils.isEmpty(phoneOremail)){
                        ShowToastUtils.showToastMsg(LoginActivity.this,"请填写手机号");
                        return;
                   }else if (TextUtils.isEmpty(etPassWord.getText().toString().trim())){
                        ShowToastUtils.showToastMsg(LoginActivity.this,"请填写密码");
                        return;
                    }
                    instance.post(Constant.LOGINURL, map, new BaseCallback<String>() {

                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                            commonDialog.setTitle("登录中...");
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) {
                            LogUtil.e(s);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String resode = jsonObject.getString("rescode");
                                boolean success = jsonObject.getBoolean("success");
                                final String resMessage = jsonObject.getString("resMessage");

                                if (success){
                                    int userId = jsonObject.getJSONObject("data").getInt("id");
                                    int kinderId = jsonObject.getJSONObject("data").getInt("kinderId");
                                    String cellphone = jsonObject.getJSONObject("data").getString("cellphone");
                                    SharedPreferencesUtils.setParam(LoginActivity.this, "userId",userId+"");
                                    SharedPreferencesUtils.setParam(LoginActivity.this, "kinderId",kinderId+"");
                                    SharedPreferencesUtils.setParam(LoginActivity.this, "cellphone",cellphone);
                                    ShowToastUtils.showToastMsg(LoginActivity.this,resMessage);
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    commonDialog.dismiss();

//                                    Set<String> tags = new HashSet<String>();
//                                    JPushInterface.setAliasAndTags(LoginActivity.this, "",tags, new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int i, String s, Set<String> set) {
//                                        }
//                                    });
//                                    //重新设置推送tags
//                                    tags.clear();
//                                    JPushInterface.setAliasAndTags(LoginActivity.this, userId+"",tags, new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int code, String s, Set<String> set) {
//                                            switch (code) {
//                                                case 0:
//                                                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                                                    LogUtil.e("Set tag and alias success极光推送别名设置成功");
//                                                    ShowToastUtils.showToastMsg(LoginActivity.this,resMessage);
//                                                    Intent intent = new Intent();
//                                                    intent.setClass(LoginActivity.this, MainActivity.class);
//                                                    startActivity(intent);
//                                                    finish();
//                                                    commonDialog.dismiss();
//                                                    break;
//                                                case 6002:
//                                                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
//                                                    LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
//                                                    commonDialog.dismiss();
//                                                    break;
//                                                default:
//                                                    LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
//                                                    commonDialog.dismiss();
//                                                    break;
//                                            }
//
//                                        }
//                                    });

                                }else{
                                    ShowToastUtils.showToastMsg(LoginActivity.this,resMessage);
                                    commonDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            commonDialog.dismiss();
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

            case R.id.tv_fogetPassWord:

                //忘记密码
                break;

            case R.id.tv_regist:
                //注册
                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this, RegistActivity.class);
                intent.setClass(LoginActivity.this, RegistKindActivity.class);
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
