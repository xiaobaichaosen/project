package com.yijie.com.yijie.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 设置最多招聘人数
 */
public class SettingNumActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_num)
    EditText tvNum;
    private String schoolId;
    private String practiceId;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_setting_num);

    }

    @Override
    public void init() {
        schoolId = getIntent().getStringExtra("schoolId");
        practiceId = getIntent().getStringExtra("practiceId");
        String kindpeoNumSet = getIntent().getStringExtra("kindpeoNumSet");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("设置");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        tvNum.setText(kindpeoNumSet);
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
                SchoolPractice schoolPractice = new SchoolPractice();
                schoolPractice.setKindpeoNumSet(Integer.parseInt(tvNum.getText().toString().trim()));
                schoolPractice.setId(Integer.parseInt(practiceId));
                schoolPractice.setSchoolId(Integer.parseInt(schoolId));
                updateShipDetail(schoolPractice);
                break;
        }
    }

    //更新实习详情
    private void updateShipDetail(SchoolPractice schoolPractice) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        getinstance.postJson(Constant.NEWSHIPDETAIL, schoolPractice, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
                commonDialog.setTitle("数据提交中...");
            }
            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String shipDetail) {
                LogUtil.e(shipDetail);
                //将数据保存到上一个页面
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(shipDetail);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")||rescode.equals("300")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        Gson gson=new Gson();
                        SchoolPractice schoolPractice1= gson.fromJson(data.toString(), SchoolPractice.class);
                        ShowToastUtils.showToastMsg(SettingNumActivity.this,jsonObject.getString("resMessage"));
                        EventBus.getDefault().post(schoolPractice1);
                        finish();
                    }else if(rescode.equals("400")){
                        ShowToastUtils.showToastMsg(SettingNumActivity.this,jsonObject.getString("resMessage"));
                    }
                    commonDialog.dismiss();

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
