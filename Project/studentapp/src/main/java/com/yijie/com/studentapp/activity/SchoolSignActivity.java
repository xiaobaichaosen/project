package com.yijie.com.studentapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.fragment.FootprintFragment;
import com.yijie.com.studentapp.fragment.SchoolSignFragment;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 学校打卡
 */
public class SchoolSignActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.iv_headImage)
    CircleImageView ivHeadImage;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;

    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;


    @BindView(R.id.radio_punchCard)
    RadioButton radioPunchCard;
    @BindView(R.id.radio_applyFor)
    RadioButton radioApplyFor;

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;

    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;
    @BindView(R.id.tv_kinderName)
    TextView tvKinderName;
    @BindView(R.id.tv_currentData)
    TextView tvCurrentData;
    private  String userIdString;
    private ArrayList<Fragment> fragmentList;
    private SchoolSignFragment schoolSignFragment;
    private FootprintFragment footprintFragment;
    public String kindName;
    public String headPic;
    public String stuName;
    public   boolean signInFlag;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_schoolsign);
    }


    @Override
    public void init() {


        userIdString = (String) SharedPreferencesUtils.getParam(this, "id", "");
        if (getIntent().getBooleanExtra("signInFlag", false)) signInFlag = true;
        else signInFlag = false;
        getData(userIdString);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_punchCard);
        kindName = getIntent().getStringExtra("kindeName");
        title.setText("学校安全签到打卡");
        initSchoolSignFragment();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 获取打卡初始化数据
     */
    private void getData(String userId) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        getinstance.post(Constant.SELECTSTUDENTSIGNIN, stringStringHashMap, new BaseCallback<String>() {
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
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        String id = data.getString("id");
                        String signinDate = data.getString("signinDate");
                        kindName = data.getString("kindName");

                        headPic = data.getString("headPic");
                        stuName = data.getString("stuName");
                        tvStuName.setText(stuName);
                        tvCurrentData.setText(signinDate);
                        tvKinderName.setText("当前所在的园所:"+kindName);
                        if (headPic==null||headPic.equals("")) {
                            ivHeadImage.setBackgroundResource(R.mipmap.head);

                        }else {
                            ImageLoaderUtil.getImageLoader(SchoolSignActivity.this).displayImage(Constant.infoUrl+userIdString+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
                        }

                    } else {
                        ShowToastUtils.showToastMsg(SchoolSignActivity.this, "考勤初始化数据失败");
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
    }

    //初始化学校签到碎片
    private void initSchoolSignFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    if (schoolSignFragment == null) {
        schoolSignFragment = new SchoolSignFragment();
//    }
        transaction.replace(R.id.main_frame_layout, schoolSignFragment);
        //提交事务
        transaction.commit();
    }

    //初始化足迹碎片
    private void initFootprintFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        if (footprintFragment == null) {
        footprintFragment = new FootprintFragment();
//        }
        transaction.replace(R.id.main_frame_layout, footprintFragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        //获取当前被选中的RadioButton的ID，用于改变ViewPager的当前页

        switch (CheckedId) {
            case R.id.radio_punchCard:
                title.setText("学校安全签到打卡");
                initSchoolSignFragment();
                break;
            case R.id.radio_applyFor:
                title.setText("足迹");
                initFootprintFragment();
                break;


        }

    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                break;
        }
    }
}
