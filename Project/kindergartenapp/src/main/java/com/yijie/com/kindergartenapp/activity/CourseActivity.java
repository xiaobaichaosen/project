package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CourseBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/3/29.
 * 特色课程可以多选
 */

public class CourseActivity extends BaseActivity {


    CourseBean courseBean = new CourseBean();
    ;
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
    private CourseBean modiftyCourseBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_course);
    }

    @Override
    public void init() {
        title.setText("特色课程");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        modiftyCourseBean = (CourseBean) getIntent().getExtras().getSerializable("tempCourseBean");
        //回显数据
        if (null != modiftyCourseBean) {
            if (null != modiftyCourseBean.getDanceString()) {
                cbDance.setChecked(true);
            }
            if (null != modiftyCourseBean.getArtString()) {
                cbArt.setChecked(true);
            }
            if (null != modiftyCourseBean.getEnglishString()) {
                cbEnglish.setChecked(true);
            }
            if (null != modiftyCourseBean.getRollString()) {
                cbRoll.setChecked(true);
            }
            if (null != modiftyCourseBean.getPainoString()) {
                cbPiano.setChecked(true);
            }
            if (null != modiftyCourseBean.getThoughtString()) {
                cbThought.setChecked(true);
            }
            if (null != modiftyCourseBean.getOtherString() && !modiftyCourseBean.getOtherString().equals("")) {
                cbOther.setChecked(true);
                etContent.setText(modiftyCourseBean.getOtherString());
            }

        }
    }

    @OnCheckedChanged({R.id.cb_dance, R.id.cb_art, R.id.cb_english, R.id.cb_roll,R.id.cb_piano, R.id.cb_thought, R.id.cb_other})
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
                    courseBean.setThoughtString("思维课");
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

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                courseBean.setOtherString(etContent.getText().toString().trim());
                EventBus.getDefault().post(courseBean);
                finish();
                break;
        }
    }
}
