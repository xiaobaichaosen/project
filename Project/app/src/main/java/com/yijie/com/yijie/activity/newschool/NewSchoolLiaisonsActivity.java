package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.CommonBean;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.bean.school.SchoolTrainDetail;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建联系人
 */

public class NewSchoolLiaisonsActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    ContactBean modiftyContactBean = null;
    boolean modify;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.ll_date)
    RelativeLayout llDate;
    @BindView(R.id.tv_date)
    TextView tvDate;
    //用来关联学校联络人
    private int practiceId;
    private  int id;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_liaision);

    }

    @Override
    public void init() {

        modify = getIntent().getBooleanExtra("modify", false);
        practiceId = getIntent().getExtras().getInt("practiceId");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        title.setText("院校联络人");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        SchoolTrainDetail schoolTrainDetail= (SchoolTrainDetail) getIntent().getExtras().getSerializable("tempSchoolTrainDetail");
        //回显数据
        if (schoolTrainDetail!=null) {
            etName.setText(schoolTrainDetail.getSchoolContactName());
            etPhone.setText(schoolTrainDetail.getSchoolContactCelphone());
            tvDate.setText(schoolTrainDetail.getTrainTime());
            practiceId=schoolTrainDetail.getSchoolPracticeId();

            id = schoolTrainDetail.getId();
        }
    }

    @OnClick({R.id.back, R.id.tv_recommend,R.id.ll_date})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.ll_date:
                TimePickerView.Type yearType = TimePickerView.Type.ALL;
                String yearFormat = "yyyy-MM-dd HH:mm";
                ViewUtils.alertTimerPicker(this, yearType, yearFormat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvDate.setText(date);
                    }
                });
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                String name = etName.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();
                String date = tvDate.getText().toString().trim();

                if (name.equals("")) {
                    ShowToastUtils.showToastMsg(this, "姓名不能为空");
                } else if (!TelephoneRejestUtils.checkCellphone(phone)) {
                    ShowToastUtils.showToastMsg(this, "手机号不合法！");
                } else if (date.equals("")) {
                    ShowToastUtils.showToastMsg(this, "日期不能为空");
                } else {
                    SchoolTrainDetail schoolTrainDetail = new SchoolTrainDetail();
                    schoolTrainDetail.setSchoolContactName(name);
                    if (id!=0){
                        schoolTrainDetail.setId(id);
                    }
                    schoolTrainDetail.setSchoolContactCelphone(phone);
                    schoolTrainDetail.setTrainTime(date);
                    schoolTrainDetail.setSchoolPracticeId(practiceId);
                    addSchoolLiaison(schoolTrainDetail);

                }
                break;
        }
    }

    /**
     * 新建或修改院校联络人
     *
     * @param schoolTrainDetail
     */
    private void addSchoolLiaison(SchoolTrainDetail schoolTrainDetail) {
        HttpUtils getinstance = HttpUtils.getinstance(NewSchoolLiaisonsActivity.this);
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);

        getinstance.postJson(Constant.SCHOOLLIAISONS, schoolTrainDetail, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String schoolTrainDetail) {
                LogUtil.e(schoolTrainDetail);
                //将数据保存到上一个页面
                try {
                    JSONObject jsonObject = new JSONObject(schoolTrainDetail);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson=new Gson();
                    SchoolTrainDetail schoolTrainDetail1 = gson.fromJson(data.toString(), SchoolTrainDetail.class);
                    EventBus.getDefault().post(schoolTrainDetail1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_date)
    public void onViewClicked() {
    }
}
