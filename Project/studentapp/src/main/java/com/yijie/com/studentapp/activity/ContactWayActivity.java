package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentContact;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 联系方式
 */

public class ContactWayActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_cellphone)
    EditText tvCellphone;
    @BindView(R.id.et_wxnumber)
    EditText etWxnumber;
    @BindView(R.id.et_qqnumber)
    EditText etQqnumber;
    @BindView(R.id.et_mailnumber)
    EditText etMailnumber;
    @BindView(R.id.et_urgentContact)
    EditText etUrgentContact;
    @BindView(R.id.et_urgentCellphone)
    EditText etUrgentCellphone;
    private StudentContact studentContact;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contactway);
    }

    @Override
    public void init() {

        Bundle extras = getIntent().getExtras();
        if (null!=extras){
            studentContact = (StudentContact) extras.getSerializable("studentContact");
            tvCellphone.setText(studentContact.getCellphone());
            etWxnumber.setText(studentContact.getWechat());
            etQqnumber.setText(studentContact.getQq());
            etMailnumber.setText(studentContact.getEmail());
            etUrgentContact.setText(studentContact.getUrgentContact());
            etUrgentCellphone.setText(studentContact.getUrgentCellphone());
        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("联系方式");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        String json = (String) SharedPreferencesUtils.getParam(this, "user", "");
        try {
            JSONObject jsonObject = new JSONObject(json);
          String  cellphone = jsonObject.getString("cellphone");
          tvCellphone.setText(cellphone);
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

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_recommend:

                EventBus.getDefault().post("contactway");
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                //请求网络
                HttpUtils instance = HttpUtils.getinstance(this);
                String cellphone = tvCellphone.getText().toString();
                String wxnumber = etWxnumber.getText().toString();
                String qqnumber = etQqnumber.getText().toString();
                String mailnumber = etMailnumber.getText().toString();
                String urgentContact = etUrgentContact.getText().toString();
                String urgentCellphone = etUrgentCellphone.getText().toString();
                if (TextUtils.isEmpty(cellphone)) {
                    ShowToastUtils.showToastMsg(this, "请输入手机号");
                    return;
                } else if (TextUtils.isEmpty(urgentContact)) {
                    ShowToastUtils.showToastMsg(this, "请输入紧急联系人");
                    return;
                } else if (TextUtils.isEmpty(urgentCellphone)) {
                    ShowToastUtils.showToastMsg(this, "请输入联系人电话");
                    return;
                }

                Map map = new HashMap();
                Map mapDate = new HashMap();
                if (null!=studentContact){
                    mapDate.put("id", studentContact.getId());
                }
                mapDate.put("studentUserId", userId);
                mapDate.put("resumeId", userId);
                mapDate.put("cellphone", cellphone);
                if (!TextUtils.isEmpty(wxnumber)){
                    mapDate.put("wechat", wxnumber);
                }
                if (!TextUtils.isEmpty(qqnumber)){
                    mapDate.put("qq", qqnumber);
                }
                if (!TextUtils.isEmpty(mailnumber)){
                    mapDate.put("email", mailnumber);
                }

                mapDate.put("urgentContact", urgentContact);
                mapDate.put("urgentCellphone",urgentCellphone);
                map.put("requestData", mapDate.toString());
                instance.post(Constant.STUDENTCONTACT, map,new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
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
                            ShowToastUtils.showToastMsg(ContactWayActivity.this, jsonObject.getString("resMessage"));
                            if (jsonObject.getString("rescode").equals("200")) {
                                finish();
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
                break;

        }
    }
}

