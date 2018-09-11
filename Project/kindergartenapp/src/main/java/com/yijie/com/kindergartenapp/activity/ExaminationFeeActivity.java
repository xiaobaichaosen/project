package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
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
 * 首年体检费
 */

public class ExaminationFeeActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.rb_own)
    RadioButton rbOwn;
    @BindView(R.id.rb_free)
    RadioButton rbFree;
    @BindView(R.id.rp_all)
    RadioGroup rpAll;
    @BindView(R.id.cb_other)
    RadioButton cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    private CommonBean tempExaminationCommonBean;
    //    CategoryBean categoryBean = new CategoryBean();
    CommonBean commonBean = new CommonBean();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_examination);
    }

    @Override
    public void init() {
        title.setText("班级配置");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempExaminationCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempExaminationCommonBean");
        //回显数据
        if (null != tempExaminationCommonBean&&null != tempExaminationCommonBean.getRbString()) {
            if (tempExaminationCommonBean.getRbString().equals("自理")) {
                rbOwn.setChecked(true);
            } else if (tempExaminationCommonBean.getRbString().equals("报销")) {
                rbFree.setChecked(true);
            } else {
                cbOther.setChecked(true);
                etContent.setText(tempExaminationCommonBean.getRbString());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.rb_own, R.id.rb_free, R.id.cb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_other:
                if (ischanged) {
                    rpAll.clearCheck();
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText("");
                }
                break;
            case R.id.rb_own:
                if (ischanged) {
                    cbOther.setChecked(false);
                    commonBean.setRbString("自理");
                }
                break;
            case R.id.rb_free:
                if (ischanged) {
                    cbOther.setChecked(false);
                    commonBean.setRbString("报销");
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
                if (!etContent.getText().toString().trim().equals("")) {
                    commonBean.setRbString(etContent.getText().toString().trim());
                }
                commonBean.setType(9);
                EventBus.getDefault().post(commonBean);
                finish();
                break;
        }
    }
}


