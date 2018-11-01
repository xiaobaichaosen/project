package com.yijie.com.yijie.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.SchoolTrainDetail;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.BGAFlowLayout;
import com.yijie.com.yijie.view.LoadingLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/24.
 * 培训详情
 */

public class TrainDetailAcitity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_finishTime)
    TextView tvFinishTime;
    @BindView(R.id.rl_finishTime)
    RelativeLayout rlFinishTime;
    @BindView(R.id.cb_jieben)
    CheckBox cbJieben;
    @BindView(R.id.cb_teacher)
    CheckBox cbTeacher;
    @BindView(R.id.et_main_tag)
    EditText mTagEt;
    @BindView(R.id.flowlayout)
    BGAFlowLayout mFlowLayout;
    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.rb_height_yes)
    RadioButton rbHeightYes;
    @BindView(R.id.rb_hight_no)
    RadioButton rbHightNo;
    @BindView(R.id.view_gone)
    View viewGone;
    @BindView(R.id.loading)
    LoadingLayout loading;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.rb_me)
    RadioButton rbMe;
    @BindView(R.id.rb_together)
    RadioButton rbTogether;
    //拥于存用户选是否自动提示的文字
    private List<String> dataList = new ArrayList<>();
    //培训详情id
    int schoolTrainId;
    //实习项目id
    int practiceId;
    private int userId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_train);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        loading.showContent();
        title.setText("培训详情");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        Bundle extras = getIntent().getExtras();
        String json = (String) SharedPreferencesUtils.getParam(this, "user", "");
        SchoolTrainDetail schoolTrainDetail= (SchoolTrainDetail) getIntent().getExtras().getSerializable("tempSchoolTrainDetail");
        //回显数据
        if (null!=schoolTrainDetail) {
            tvTime.setText(schoolTrainDetail.getToBeijingTime());
            tvFinishTime.setText(schoolTrainDetail.getEndTime());
            etNumber.setText(schoolTrainDetail.getEnrollNum()+"");
            String toBeijingMethod = schoolTrainDetail.getToBeijingMethod();
            if (toBeijingMethod.equals("自行")){
                rbMe.setChecked(true);
            }else {
                rbTogether.setChecked(true);
            }
            String wipeUpFee = schoolTrainDetail.getWipeUpFee();
            if (wipeUpFee.equals("是")){
                rbYes.setChecked(true);
            }else {
                rbNo.setChecked(true);
            }
            String promote = schoolTrainDetail.getPromote();
            if (null!=promote){
                rbHeightYes.setChecked(true);
                llRoot.setVisibility(View.VISIBLE);
                viewGone.setVisibility(View.GONE);
                String[] split = promote.split(",");
                for (int i = 0; i < split.length; i++) {
                    if (split[i].equals("接本")){
                        cbJieben.setChecked(true);
                        cbJieben.setBackgroundResource(R.drawable.edittext_select_shap);
                        cbJieben.setTextColor(getResources().getColor(R.color.colorTheme));
                    }else if (split[i].equals("教师资格证考试")){
                        cbTeacher.setTextColor(getResources().getColor(R.color.colorTheme));
                        cbTeacher.setBackgroundResource(R.drawable.edittext_select_shap);
                    }else {
                        mFlowLayout.addView(getLabel(split[i]), mFlowLayout.getChildCount() - 1, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    }

                }


            }else {
                rbHightNo.setChecked(true);
            }

        }
        try {
            JSONObject jsonObject = new JSONObject(json);
            userId = Integer.parseInt(jsonObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        schoolTrainId = extras.getInt("schoolTrainId");
        practiceId = extras.getInt("practiceId");
        cbJieben.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbJieben.setBackgroundResource(R.drawable.edittext_select_shap);
                    cbJieben.setTextColor(getResources().getColor(R.color.colorTheme));
                    dataList.add("接本");
                } else {
                    cbJieben.setBackgroundResource(R.drawable.edittext_shap);
                    cbJieben.setTextColor(getResources().getColor(R.color.item_content));
                    Iterator<String> it = dataList.iterator();
                	   while (it.hasNext()) {
                      String value = it.next();
                       if ("接本".equals(value)) {
                      it.remove();
                       }
                   }
                }

            }
        });

        cbTeacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dataList.add("教师资格证考试");
                    cbTeacher.setTextColor(getResources().getColor(R.color.colorTheme));
                    cbTeacher.setBackgroundResource(R.drawable.edittext_select_shap);
                } else {
                    cbTeacher.setTextColor(getResources().getColor(R.color.item_content));
                    cbTeacher.setBackgroundResource(R.drawable.edittext_shap);
                    Iterator<String> it = dataList.iterator();
                    while (it.hasNext()) {
                        String value = it.next();
                        if ("接本".equals(value)) {
                            it.remove();
                        }
                    }
                }
            }
        });
        mTagEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    String tag = mTagEt.getText().toString().trim();
                    if (!TextUtils.isEmpty(tag)) {
                        mFlowLayout.addView(getLabel(tag), mFlowLayout.getChildCount() - 1, new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT));
                    }
                    mTagEt.setText("");
                }
                return true;
            }
        });
        rbHeightYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llRoot.setVisibility(View.VISIBLE);
                    viewGone.setVisibility(View.GONE);
                } else {
                    viewGone.setVisibility(View.VISIBLE);
                    llRoot.setVisibility(View.GONE);
                }
            }
        });
        rbHightNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    llRoot.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_time, R.id.rl_finishTime, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.back:
                finish();
                break;
            case R.id.rl_time:
                TimePickerView.Type typeTime = TimePickerView.Type.YEAR_MONTH_DAY;
                String formatDate = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(this, typeTime, formatDate, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {

                        if (TimeUtil.compare_date("yyyy-MM-dd",date,TimeUtil.getCurrentDate("yyyy-MM-dd"))==1){
                            tvTime.setText(date);
                        }else {
                            ShowToastUtils.showToastMsg(TrainDetailAcitity.this,"选择的当前日期之前的日期");
                        }
                    }
                });
                break;
            case R.id.rl_finishTime:
                TimePickerView.Type finishTime = TimePickerView.Type.YEAR_MONTH_DAY;
                String finishDate = "yyyy-MM-dd HH:mm";
                ViewUtils.alertTimerPicker(this, finishTime, finishDate, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        if (TimeUtil.compare_date("yyyy-MM-dd HH:mm",date,TimeUtil.getCurrentDate("yyyy-MM-dd HH:mm"))==1){
                            tvFinishTime.setText(date);
                        }else {
                            ShowToastUtils.showToastMsg(TrainDetailAcitity.this,"选择的当前日期之前的日期");
                        }

                    }
                });
                break;
            case R.id.tv_recommend:
                //提交数据
                String getBjTime = tvTime.getText().toString().trim();
                String shipFinishTime = tvFinishTime.getText().toString().trim();
                String arriveNumber = etNumber.getText().toString().trim();
                if (getBjTime.equals("")) {
                    ShowToastUtils.showToastMsg(this, "请填写到京时间");
                }  else if (!rbMe.isChecked()&&!rbTogether.isChecked()) {
                    ShowToastUtils.showToastMsg(this, "请选择到京方式");
                }  else if (arriveNumber.equals("")) {
                    ShowToastUtils.showToastMsg(this, "请填写报名人数");
                }else if (!rbYes.isChecked()&&!rbNo.isChecked()) {
                    ShowToastUtils.showToastMsg(this, "请选择报销路费");
                } else if (shipFinishTime.equals("")) {
                    ShowToastUtils.showToastMsg(this, "请填写结束时间");
                }else {
                    SchoolTrainDetail schoolTrainDetail = new SchoolTrainDetail();
                    schoolTrainDetail.setToBeijingTime(getBjTime);
                    if (rbMe.isChecked()){
                        schoolTrainDetail.setToBeijingMethod(rbMe.getText().toString());
                    }else {
                        schoolTrainDetail.setToBeijingMethod(rbTogether.getText().toString());
                    }
                    schoolTrainDetail.setEnrollNum(Integer.parseInt(arriveNumber));
                    if (rbYes.isChecked()){
                        schoolTrainDetail.setWipeUpFee(rbYes.getText().toString());
                    }else {
                        schoolTrainDetail.setWipeUpFee(rbNo.getText().toString());
                    }

//
                    schoolTrainDetail.setEndTime(shipFinishTime);
                    schoolTrainDetail.setId(schoolTrainId);
                    schoolTrainDetail.setSchoolPracticeId(practiceId);
                    schoolTrainDetail.setTeacherId(userId);
                    int childCount = mFlowLayout.getChildCount();
                    StringBuilder sb=new StringBuilder();
                    for (int i = 0; i < dataList.size(); i++) {
                        if (childCount>0){
                            sb.append(dataList.get(i)+",");
                        }else {
                            if (i==dataList.size()-1){
                                sb.append(dataList.get(i));
                            }else {
                                sb.append(dataList.get(i)+",");
                            }
                        }

                    }
                    if (childCount>0){
                        for (int i = 0; i <childCount; i++) {
                            TextView textView = (TextView) mFlowLayout.getChildAt(i);
                            String textString = textView.getText().toString();
                            if (i==childCount-1){
                                sb.append(textString);
                            }else {
                                sb.append(textString+",");
                            }
                        }
                    }
                    schoolTrainDetail.setPromote(sb.toString());
                    addSchoolLiaison(schoolTrainDetail);
                }
                break;


        }
    }

    private TextView getLabel(String text) {
        final TextView label = new TextView(this);
        label.setTextColor(getResources().getColor(R.color.colorTheme));
        label.setBackgroundResource(R.drawable.edittext_select_shap);
        label.setGravity(Gravity.CENTER);
        label.setSingleLine(true);
        label.setEllipsize(TextUtils.TruncateAt.END);
        int padding = BGAFlowLayout.dp2px(this, 5);
        label.setPadding(padding, padding, padding, padding);
        label.setText(text);
        label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlowLayout.removeView(label);
            }
        });
        return label;
    }

    /**
     * @param schoolTrainDetail
     */
    private void addSchoolLiaison(SchoolTrainDetail schoolTrainDetail) {
        HttpUtils getinstance = HttpUtils.getinstance(TrainDetailAcitity.this);
        getinstance.postJson(Constant.SCHOOLTRAINDETAIL, schoolTrainDetail, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                loading.showLoading();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                loading.showError();
            }

            @Override
            public void onSuccess(Response response, String schoolTrainDetail) {
                LogUtil.e(schoolTrainDetail);
                //将数据保存到上一个页面
                try {
                    JSONObject jsonObject = new JSONObject(schoolTrainDetail);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    SchoolTrainDetail schoolTrainDetail1 = gson.fromJson(data.toString(), SchoolTrainDetail.class);
                    EventBus.getDefault().post(schoolTrainDetail1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                loading.showError();
            }
        });
    }

}
