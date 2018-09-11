package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/6/6.
 * 园所级别
 */

public class KenderGradeActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rb_onetoone)
    RadioButton rbOnetoone;
    @BindView(R.id.rb_onetotwo)
    RadioButton rbOnetotwo;
    @BindView(R.id.rb_twotoone)
    RadioButton rbTwotoone;
    @BindView(R.id.rb_twototwo)
    RadioButton rbTwototwo;
    @BindView(R.id.rb_twotothree)
    RadioButton rbTwotothree;
    @BindView(R.id.cb_other)
    RadioButton cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rp_frist)
    RadioGroup rpFrist;
    @BindView(R.id.rp_second)
    RadioGroup rpSecond;
    @BindView(R.id.rp_third)
    RadioGroup rpThird;
    private CommonBean tempGradeCommonBean;
    CommonBean commonBean = new CommonBean();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_grade);
    }

    @Override
    public void init() {
        title.setText("园所级别");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempGradeCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempGradeCommonBean");
        //回显数据
        if (null != tempGradeCommonBean&&null!=tempGradeCommonBean.getRbString()) {

            if (tempGradeCommonBean.getRbString().equals("一级一类")){
                rbOnetoone.setChecked(true);
            }else if(tempGradeCommonBean.getRbString().equals("一级二类")){
                rbOnetotwo.setChecked(true);
            }else if(tempGradeCommonBean.getRbString().equals("二级一类")){
                rbTwotoone.setChecked(true);
            }else if(tempGradeCommonBean.getRbString().equals("二级二类")){
                rbTwototwo.setChecked(true);
            }else if(tempGradeCommonBean.getRbString().equals("二级三类")){
                rbTwotothree.setChecked(true);
            }else {
                cbOther.setChecked(true);
                etContent.setText(tempGradeCommonBean.getRbString());
            }



        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.rb_onetoone, R.id.rb_onetotwo, R.id.rb_twotoone, R.id.rb_twototwo, R.id.rb_twotothree, R.id.cb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {

        switch (view.getId()) {
            case R.id.cb_other:
                if (ischanged) {
                    rpFrist.clearCheck();
                    rpSecond.clearCheck();
                    rpThird.clearCheck();
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText(null);
                }
                break;
            case R.id.rb_onetoone:

                if (ischanged) {
                    cbOther.setChecked(false);
                    rpSecond.clearCheck();
                    rpThird.clearCheck();
                    commonBean.setRbString("一级一类");
                }
                break;
            case R.id.rb_onetotwo:
                if (ischanged) {
                    cbOther.setChecked(false);
                    rpSecond.clearCheck();
                    rpThird.clearCheck();
                    commonBean.setRbString("一级二类");
                }
                break;
            case R.id.rb_twotoone:
                if (ischanged) {
                    cbOther.setChecked(false);
                    rpFrist.clearCheck();
                    rpThird.clearCheck();
                    commonBean.setRbString("二级一类");
                }
                break;
            case R.id.rb_twototwo:
                if (ischanged) {
                    cbOther.setChecked(false);
                    rpFrist.clearCheck();
                    rpThird.clearCheck();
                    commonBean.setRbString("二级二类");
                }
                break;
            case R.id.rb_twotothree:
                if (ischanged) {
                    cbOther.setChecked(false);
                    rpFrist.clearCheck();
                    rpSecond.clearCheck();
                    commonBean.setRbString("二级三类");
                }
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
                if (!etContent.getText().toString().trim().equals("")){
                    commonBean.setRbString(etContent.getText().toString().trim());
                }
                commonBean.setType(1);
                EventBus.getDefault().post(commonBean);
                finish();
                break;
        }
    }
}

