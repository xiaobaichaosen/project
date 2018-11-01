package com.yijie.com.studentapp.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.MainActivity;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.SelectSchoolActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.CheckUserUtils;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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

public class PassWordActivity extends BaseActivity {

    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_passWord_comfirm)
    EditText etPassWordComfirm;
    @BindView(R.id.cb_isComVisiable)
    CheckBox cbIsComVisiable;
    private String phoneNumber;
    private String verifyCode;


    public EditText getEtPassWord() {
        return etPassWord;
    }

    public void setEtPassWord(EditText etPassWord) {
        this.etPassWord = etPassWord;
    }

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

    @OnClick({R.id.btn_next})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_next:
                String passWord = etPassWord.getText().toString().trim();
                if (etPassWord.getText().toString().isEmpty()) {
                    ShowToastUtils.showToastMsg(this, "请填写密码");
                    return;
                }
                if (etPassWordComfirm.getText().toString().isEmpty()) {
                    ShowToastUtils.showToastMsg(this, "请填写确定密码");
                    return;
                }
                if (!CheckUserUtils.checkString(etPassWord.getText().toString()) || !CheckUserUtils.checkString(etPassWordComfirm.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "必须6-20位数字和密码混合!");
                    return;
                }
                if (!etPassWordComfirm.getText().toString().equals(etPassWord.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "两次密码输入不一致");
                    return;

                } else {
                    HttpUtils getinstance = HttpUtils.getinstance(this);
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    stringStringHashMap.put("cellphone", phoneNumber);
                    stringStringHashMap.put("password", etPassWord.getText().toString());
                    getinstance.post(Constant.UPDATEBYCELLPHONE, stringStringHashMap, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String o) {
                            LogUtil.e(o);
                            try {
                                JSONObject jsonObject = new JSONObject(o)    ;
                                String rescode = jsonObject.getString("rescode");
                                if (rescode.equals("200")){
                                    ShowToastUtils.showToastMsg(PassWordActivity.this,jsonObject.getString("resMessage"));
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    SharedPreferencesUtils.setParam(PassWordActivity.this, "user", data.toString());
                                    SharedPreferencesUtils.setParam(PassWordActivity.this, "schoolId", data.getInt("schoolId")+"");
                                    SharedPreferencesUtils.setParam(PassWordActivity.this, "schoolPracticeId", data.getInt("schoolPracticeId")+"");
                                    SharedPreferencesUtils.setParam(PassWordActivity.this, "id", data.getInt("id")+"");
                                    Intent intent = new Intent();
                                    intent.setClass(PassWordActivity.this, MainActivity.class);
                                    ShowToastUtils.showToastMsg(PassWordActivity.this, "登录成功！");
                                    startActivity(intent);
                                    finish();

//                                    Set<String> tags = new HashSet<String>();
//                                    //清除tags
//                                    tags.add("");
//                                    JPushInterface.setAliasAndTags(LoginActivity.this, "", tags, new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int i, String s, Set<String> set) {
//                                        }
//                                    });
//
//                                    JPushInterface.setAliasAndTags(LoginActivity.this, userId + "", tags, new TagAliasCallback() {
//                                        @Override
//                                        public void gotResult(int code, String s, Set<String> set) {
//                                            switch (code) {
//                                                case 0:
//                                                    //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                                                    LogUtil.e("Set tag and alias success极光推送别名设置成功");
//                                                    Intent intent = new Intent();
//                                                    intent.setClass(LoginActivity.this, MainActivity.class);
//                                                    ShowToastUtils.showToastMsg(LoginActivity.this, "登录成功！");
//                                                    startActivity(intent);
//                                                    finish();
//                                                    commonDialog.dismiss();
//                                                    break;
//                                                case 6002:
//                                                    //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
//                                                    LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
//                                                    break;
//                                                default:
//                                                    LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
//                                                    break;
//                                            }
//
//                                        }
//                                    });
                                }else{
                                    ShowToastUtils.showToastMsg(PassWordActivity.this,jsonObject.getString("resMessage"));
                                }
                                commonDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
                        }
                    });

//                    loginModel.login(etName.getText().toString(), etPassWord.getText().toString(), new LoginCallBack() {
//                        @Override
//                        public void beforLogin() {
//                            commonDialog.show();
//                        }
//
//                        @Override
//                        public void onLoginSuccess(String success) {
//                            try {
//                                JSONObject jsonObject = new JSONObject(success);
//                                LogUtil.e("==="+jsonObject);
//                                String status = jsonObject.getString("status");
//                                if (status.equals("ok")) {
//                                    ShowToastUtils.showToastMsg(LoginActivity.this, jsonObject.getString("message"));
//                                    JSONObject jsonObject2 = jsonObject.getJSONObject("result");
//                                    String bytes = jsonObject2.getString("bytes");
//                                    String user_phone = jsonObject2.getString("user_phone");
//                                    Integer user_id = jsonObject2.getInt("id");
//                                    SharedPreferencesUtils.setParam(LoginActivity.this,"image",bytes);
//                                    SharedPreferencesUtils.setParam(LoginActivity.this,"user_phone",user_phone);
//                                    SharedPreferencesUtils.setParam(LoginActivity.this,"user_id",user_id);
//                                    Intent intent = new Intent();
//                                    intent.putExtra("image",bytes);
//                                    intent.setClass(LoginActivity.this, MainActivity.class);
//                                    startActivity(intent);
//                                    commonDialog.dismiss();
//                                    finish();
//                                } else {
//                                    ShowToastUtils.showToastMsg(LoginActivity.this, jsonObject.getString("message"));
//                                    commonDialog.dismiss();
//                                }
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onLoginFail(Exception error) {
//                            commonDialog.dismiss();
//
//                        }
//
//                    });
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

    @OnCheckedChanged({R.id.cb_isComVisiable})
    public void OnCheckedChangeListenerComfirm(CheckBox view, boolean ischanged) {
        if (ischanged) {
            etPassWordComfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassWordComfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassWordComfirm.setSelection(etPassWordComfirm.getText().length());//set cursor to the end
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
