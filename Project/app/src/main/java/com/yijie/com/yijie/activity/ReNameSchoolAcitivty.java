package com.yijie.com.yijie.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewSchoolIntroduction;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 修改学校名称
 */
public class ReNameSchoolAcitivty extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_schoolName)
    EditText etSchoolName;
    private String schoolName;
    private String schoolId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_renameschool);
    }

    @Override
    public void init() {
        schoolName = getIntent().getStringExtra("schoolName");
        schoolId = getIntent().getStringExtra("schoolId");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tvRecommend.setText("保存");
        title.setText("名称编辑");
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
                SchoolMain schoolMain = new SchoolMain();
                schoolMain.setName(etSchoolName.getText().toString().trim());
                schoolMain.setId(Integer.parseInt(schoolId));
                updateSchoolSimple(schoolMain);
                break;
        }
    }

    /**
     * 修改学校名称
     *
     * @param schoolMain
     */
    public void updateSchoolSimple(SchoolMain schoolMain) {
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ArrayList<File> files = new ArrayList<File>();

        Gson gson = new Gson();
        stringStringHashMap.put("parm", gson.toJson(schoolMain).toString());
        HttpUtils.getinstance(ReNameSchoolAcitivty.this).uploadFiles(Constant.UPDATESCHOOLSIMPLE, stringStringHashMap, files, new BaseCallback<String>() {
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
                LogUtil.e(s);
                ShowToastUtils.showToastMsg(ReNameSchoolAcitivty.this, "修改成功");
                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }
}
