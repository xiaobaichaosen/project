package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 自我评价
 */

public class SelfAssessmentActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    private int id;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_selfassessment);
    }

    @Override
    public void init() {

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            String selfassess = extras.getString("selfassess");

            id = extras.getInt("id");
            etContent.setText(selfassess);
        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("自我评价");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //监听文本变化
    @OnTextChanged(value = R.id.et_content, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        tvNum.setText((2000 - s.length()) + "/2000");
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.tv_recommend:
                String content = etContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ShowToastUtils.showToastMsg(this, "请填写自我评价");
                    return;
                }
                HttpUtils getinstance = HttpUtils.getinstance(this);
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                HashMap<String, String> requestData = new HashMap<>();
                if (id != 0) {
                    stringStringHashMap.put("id", id + "");
                }
                stringStringHashMap.put("selfEvaluate", content);
                stringStringHashMap.put("studentUserId", userId);
                requestData.put("requestData", new JSONObject(stringStringHashMap).toString());
                getinstance.post(Constant.STUDENTRESUME, requestData, new BaseCallback<String>() {
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
                        LogUtil.e(s);
                        commonDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ShowToastUtils.showToastMsg(SelfAssessmentActivity.this, jsonObject.getString("resMessage"));
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
//                statusLayoutManager.showErrorLayout();
                    }
                });
                break;

        }
    }


}

