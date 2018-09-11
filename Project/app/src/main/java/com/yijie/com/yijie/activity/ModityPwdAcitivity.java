package com.yijie.com.yijie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.login.LoginActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.CheckUserUtils;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置密码
 */
public class ModityPwdAcitivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_oldpwd)
    EditText etOldpwd;
    @BindView(R.id.et_newpwd)
    EditText etNewpwd;
    @BindView(R.id.et_compwd)
    EditText etCompwd;
    @BindView(R.id.tv_forgetPwd)
    TextView tvForgetPwd;
   private int userId = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_moditypwd);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("设置密码");
        tvRecommend.setText("保存");
        String json = (String) SharedPreferencesUtils.getParam(this, "user", "");

        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_forgetPwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
                case R.id.tv_recommend:
                        //修改密码
                    String oldPwd = etOldpwd.getText().toString().trim();
                    String newPwd = etNewpwd.getText().toString().trim();
                    String comPwd = etCompwd.getText().toString().trim();
                    if (TextUtils.isEmpty(oldPwd)){
                        ShowToastUtils.showToastMsg(ModityPwdAcitivity.this,"旧密码不能为空");
                        return;
                    }else if(TextUtils.isEmpty(newPwd)){
                        ShowToastUtils.showToastMsg(ModityPwdAcitivity.this,"新密码不能为空");
                        return;
                    }else if(TextUtils.isEmpty(comPwd)){
                        ShowToastUtils.showToastMsg(ModityPwdAcitivity.this,"确认密码不能为空");
                        return;
                    }else if(!CheckUserUtils.checkString(newPwd)){
                        ShowToastUtils.showToastMsg(ModityPwdAcitivity.this,"密码格式不正确");
                        return;
                    }
                    else if(!comPwd.equals(newPwd)){
                        ShowToastUtils.showToastMsg(ModityPwdAcitivity.this,"两次密码输入不一样");
                        return;
                    }
                    final HttpUtils instance = HttpUtils.getinstance(ModityPwdAcitivity.this);
                        Map map = new HashMap();
                        map.put("userId", userId+"");
                        map.put("pwd", oldPwd);
                        map.put("newPwd", newPwd);
                        map.put("isPwd", comPwd);
                        instance.post(Constant.setPwd, map, new BaseCallback<String>() {
                            @Override
                            public void onRequestBefore() {
                                commonDialog.show();
                                commonDialog.setTitle("修改密码中...");
                            }
                            @Override
                            public void onFailure(Request request, Exception e) {
                                commonDialog.dismiss();
                            }

                            @Override
                            public void onSuccess(Response response, String s) throws JSONException {
                                LogUtil.e("===" + s);
                                JSONObject jsonObject = new JSONObject(s);
                                String resMessage = jsonObject.getString("resMessage");
                                String rescode = jsonObject.getString("rescode");
                                ShowToastUtils.showToastMsg(ModityPwdAcitivity.this, resMessage);
                                if (rescode.equals("200")){
                                    finish();
                                }
                                commonDialog.dismiss();
                            }

                            @Override
                            public void onError(Response response, int errorCode, Exception e) {
                                commonDialog.dismiss();
                            }
                        });

                break;
            case R.id.tv_forgetPwd:
                Intent intent = new Intent();
                intent.setClass(ModityPwdAcitivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
