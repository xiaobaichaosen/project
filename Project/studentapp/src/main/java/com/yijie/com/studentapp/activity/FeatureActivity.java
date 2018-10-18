package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.CourseBean;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

public class FeatureActivity extends BaseActivity {
    CourseBean courseBean = new CourseBean();
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.cb_dance)
    CheckBox cbDance;
    @BindView(R.id.cb_art)
    CheckBox cbArt;
    @BindView(R.id.cb_english)
    CheckBox cbEnglish;
    @BindView(R.id.cb_roll)
    CheckBox cbRoll;
    @BindView(R.id.cb_piano)
    CheckBox cbPiano;
    @BindView(R.id.cb_thought)
    CheckBox cbThought;
    @BindView(R.id.cb_other)
    CheckBox cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_confrim)
    TextView tvConfrim;
    private int id;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feature);
    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("特长爱好");
        Bundle extras = getIntent().getExtras();
        if (null!=extras) {
            String nb = extras.getString("nb");
            id = extras.getInt("id");
            if (!TextUtils.isEmpty(nb)){
                String[] split = nb.split("、");
                List<String> strings = Arrays.asList(split);
                for (String nbString:  strings) {
                    if (nbString.equals("舞蹈")){
                        cbDance.setChecked(true);
                        courseBean.setDanceString("舞蹈");
                    }
                    else if (nbString.equals("美术")){
                        cbArt.setChecked(true);
                        courseBean.setArtString("美术");
                    }
                    else if (nbString.equals("英语")){
                        cbEnglish.setChecked(true);
                        courseBean.setEnglishString("英语");
                    }
                    else  if (nbString.equals("轮滑")){
                        cbRoll.setChecked(true);
                        courseBean.setRollString("轮滑");
                    }
                    else if (nbString.equals("钢琴")){
                        cbPiano.setChecked(true);
                        courseBean.setPainoString("钢琴");
                    }
                    else if (nbString.equals("电子琴")){
                        cbThought.setChecked(true);
                        courseBean.setThoughtString("电子琴");
                    }else{
                        cbOther.setChecked(true);
                        courseBean.setOtherString(nbString);
                        etContent.setText(nbString);
                    }

                }
            }


        }


    }

    @OnCheckedChanged({R.id.cb_dance, R.id.cb_art, R.id.cb_english, R.id.cb_roll, R.id.cb_piano, R.id.cb_thought, R.id.cb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_other:
                if (ischanged) {
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText(null);
                }
                break;
            case R.id.cb_dance:
                if (ischanged) {
                    courseBean.setDanceString("舞蹈");
                } else {
                    courseBean.setDanceString(null);
                }
                break;
            case R.id.cb_art:
                if (ischanged) {
                    courseBean.setArtString("美术");
                } else {
                    courseBean.setArtString(null);
                }
                break;
            case R.id.cb_english:
                if (ischanged) {
                    courseBean.setEnglishString("英语");
                } else {
                    courseBean.setEnglishString(null);
                }
                break;
            case R.id.cb_roll:
                if (ischanged) {
                    courseBean.setRollString("轮滑");
                } else {
                    courseBean.setRollString(null);
                }
                break;
            case R.id.cb_piano:
                if (ischanged) {
                    courseBean.setPainoString("钢琴");
                } else {
                    courseBean.setPainoString(null);
                }
                break;
            case R.id.cb_thought:
                if (ischanged) {
                    courseBean.setThoughtString("电子琴");
                } else {
                    courseBean.setThoughtString(null);
                }
                break;


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,  R.id.tv_confrim})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_confrim:
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                HttpUtils getinstance = HttpUtils.getinstance(this);
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                HashMap<String, String> requestData = new HashMap<>();
                if (cbOther.isChecked()) {
                    courseBean.setOtherString(etContent.getText().toString().trim());
                }
                String nb = "";
                if (null!=courseBean){
                    if (null != courseBean.getDanceString()) {
                        nb += courseBean.getDanceString() + "、";
                    }
                    if (null != courseBean.getArtString()) {
                        nb += courseBean.getArtString() + "、";
                    }
                    if (null != courseBean.getEnglishString()) {
                        nb += courseBean.getEnglishString() + "、";
                    }
                    if (null != courseBean.getRollString()) {
                        nb += courseBean.getRollString() + "、";
                    }
                    if (null != courseBean.getPainoString()) {
                        nb += courseBean.getPainoString() + "、";
                    }
                    if (null != courseBean.getThoughtString()) {
                        nb += courseBean.getThoughtString() + "、";
                    } if (null != courseBean.getOtherString()) {
                        nb += courseBean.getOtherString() + "、";
                    }
                    //  TODO 去掉最后一个、
                    if (nb.length() > 0) {
                        nb = nb.substring(0, nb.length() - 1);
                    }

                }
                if (id!=0){
                    stringStringHashMap.put("id", id+"");
                }

                stringStringHashMap.put("hobby", nb);
                stringStringHashMap.put("studentUserId", userId);
                requestData.put("requestData",new JSONObject(stringStringHashMap).toString());
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
                            ShowToastUtils.showToastMsg(FeatureActivity.this, jsonObject.getString("resMessage"));
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
            case R.id.back:
                finish();
                break;

        }
    }


}
