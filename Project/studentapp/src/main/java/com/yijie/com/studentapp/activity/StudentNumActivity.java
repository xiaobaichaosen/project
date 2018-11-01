package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;

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
 * 学号输入页面
 */
public class StudentNumActivity extends BaseActivity {

    @BindView(R.id.et_studentnum)
    EditText etStudentnum;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.et_studentName)
    EditText etStudentName;
    private String password;
    private String phonenumber;
    private String schoolId;
    private String schoolPracticeId;
    private String verifyCode;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_studentnum);
    }

    @Override
    public void init() {
        password = getIntent().getStringExtra("password");
        phonenumber = getIntent().getStringExtra("cellphone");
        schoolId = getIntent().getStringExtra("schoolId");
        schoolPracticeId = getIntent().getStringExtra("schoolPracticeId");
        verifyCode = getIntent().getStringExtra("verifyCode");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //注册
    @OnClick(R.id.btn_two)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etStudentnum.getText().toString())) {
            ShowToastUtils.showToastMsg(this, "请输入学生姓名");
        } else {
            //请求网络
            HttpUtils instance = HttpUtils.getinstance(this);
            Map map = new HashMap();
            Map mapDate = new HashMap();
            mapDate.put("cellphone", phonenumber);
            mapDate.put("password", password);
            mapDate.put("schoolId", schoolId);
            mapDate.put("schoolPracticeId", schoolPracticeId);
            mapDate.put("studentName", etStudentName.getText().toString());
            mapDate.put("stuNumber", etStudentnum.getText().toString());
            mapDate.put("verifyCode", verifyCode);
            map.put("requestData", mapDate.toString());
            instance.post(Constant.REGISTURL, map, new BaseCallback<String>() {
                @Override
                public void onRequestBefore() {
                    commonDialog.dismiss();
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    commonDialog.dismiss();
                }

                @Override
                public void onSuccess(Response response, String s) {
                    commonDialog.dismiss();
                    LogUtil.e(s);
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject.getString("rescode").equals("200")) {
                            Intent intent = new Intent();
                            intent.setClass(StudentNumActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ShowToastUtils.showToastMsg(StudentNumActivity.this, jsonObject.getString("resMessage"));
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
        }
    }


}

