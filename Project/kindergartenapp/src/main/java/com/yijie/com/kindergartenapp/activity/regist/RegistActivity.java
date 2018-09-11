package com.yijie.com.kindergartenapp.activity.regist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import com.umeng.socialize.UMShareAPI;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.WebViewActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.KeyBoardHelper;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.StutasToolUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

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
 * Created by 奕杰平台 on 2018/1/12.
 * 登陆
 */

public class RegistActivity extends BaseActivity {


    @BindView(R.id.et_name)
    EditText etName;

    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;
    @BindView(R.id.tv_verviCode)
    TextView tvVerviCode;
    @BindView(R.id.et_verviCode)
    EditText etVerviCode;
    @BindView(R.id.cb_isGreen)
    CheckBox cbIsGreen;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.loading)
    RelativeLayout loading;
    @BindView(R.id.tv_user_yes)
    TextView tvUserYes;
    @BindView(R.id.tv_sm_yes)
    TextView tvSmYes;
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
        if (null!=myCountDownTimer){
            myCountDownTimer.cancel();
        }
        super.onDestroy();
    }


    @OnClick({R.id.btnSubmit, R.id.et_passWord, R.id.et_name, R.id.tv_verviCode, R.id.back,R.id.tv_user_yes, R.id.tv_sm_yes})
    public void Click(View view) {
        final HttpUtils instance = HttpUtils.getinstance(RegistActivity.this);
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(RegistActivity.this);
        Map map = new HashMap();
        final String phoneOremail = etName.getText().toString().trim();
        Intent intent=new Intent();
        switch (view.getId()) {

            case R.id.tv_user_yes:
                intent.setClass(RegistActivity.this, WebViewActivity.class);
                intent.putExtra("user","用户服务协议");
                startActivity(intent);
                break;
            case R.id.tv_sm_yes:
                intent.setClass(RegistActivity.this, WebViewActivity.class);
                intent.putExtra("user","奕杰隐私条款");
                startActivity(intent);
                break;

            case R.id.back:
                finish();
                break;
            case R.id.btnSubmit:
                if (cbIsGreen.isChecked()) {
                    //跳转到注册详情
                    if (phoneOremail.equals("")) {
                        ShowToastUtils.showToastMsg(RegistActivity.this, "请填写邮箱/手机号");
                        return;
                    } else if (etVerviCode.getText().toString().equals("")) {
                        ShowToastUtils.showToastMsg(RegistActivity.this, "请填写验证码");
                        return;
                    }

                    if (etPassWord.getText().toString().equals("")) {
                        ShowToastUtils.showToastMsg(RegistActivity.this, "请填写密码");
                        return;
                    }
                    if (StutasToolUtils.checkEmail(phoneOremail)) {
                        map.put("eamil", phoneOremail);
                    } else {
                        map.put("cellphone", phoneOremail);
                    }
                    map.put("password", etPassWord.getText().toString());
                    map.put("verifyCode", etVerviCode.getText().toString());
                    instance.post(Constant.CHECKKINDERUSER, map, new BaseCallback<String>() {

                        @Override
                        public void onRequestBefore() {
                            progressDialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) {
                            LogUtil.e(s);
                            String resMessage = null;
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String rescode = jsonObject.getString("rescode");

                                resMessage = jsonObject.getString("resMessage");
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Intent intent = new Intent();
                                    intent.putExtra("username", phoneOremail);
                                    intent.putExtra("password", etPassWord.getText().toString());
                                    intent.setClass(RegistActivity.this, RegistDetailActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ShowToastUtils.showToastMsg(RegistActivity.this, resMessage);
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    ShowToastUtils.showToastMsg(RegistActivity.this, "请先同意服务协议");
                }

                break;

            case R.id.tv_fogetPassWord:

                //忘记密码
                break;

            case R.id.tv_verviCode:
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                //获取验证码
                if (phoneOremail.equals("")) {
                    ShowToastUtils.showToastMsg(RegistActivity.this, "请填写手机号/邮箱");
                    return;
                } else {
                    String url;
                    if (StutasToolUtils.checkEmail(phoneOremail)) {
                        url = Constant.SENDEAMIL;
                    } else {
                        url = Constant.SENDSMSCODE;
                    }
                    map.put("cellphone", etName.getText().toString());
                    instance.post(url, map, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
//                            progressDialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
//                            progressDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) throws JSONException {
                            JSONObject jsonObject = new JSONObject(s);
                            String resMessage = jsonObject.getString("resMessage");
                            ShowToastUtils.showToastMsg(RegistActivity.this, resMessage);
                            etVerviCode.setText(jsonObject.getString("data"));
                            LogUtil.e("===" + s);
//                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
//                            progressDialog.dismiss();
                        }
                    });
                    myCountDownTimer = new MyCountDownTimer(60000, 1000);
                    myCountDownTimer.start();
                }

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
    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(false);
                tvVerviCode.setTextColor(Color.parseColor("#F38583"));
                tvVerviCode.setText(l / 1000 + "s 后");
            }


        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(true);
                tvVerviCode.setTextColor(Color.parseColor("#ffffff"));
                tvVerviCode.setText(" 获取验证码");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
