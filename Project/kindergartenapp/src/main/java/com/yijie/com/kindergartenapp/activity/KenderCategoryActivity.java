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
 * 园所类别
 */

public class KenderCategoryActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.rb_private)
    RadioButton rbPrivate;
    @BindView(R.id.rb_public)
    RadioButton rbPublic;
    @BindView(R.id.rb_chain)
    RadioButton rbChain;
    @BindView(R.id.cb_other)
    RadioButton cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.rp_all)
    RadioGroup rpAll;
    private CommonBean tempCategoryCommonBean;
    //    CategoryBean categoryBean = new CategoryBean();
    CommonBean commonBean = new CommonBean();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_category);
    }

    @Override
    public void init() {
        title.setText("园所类别");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempCategoryCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempCategoryCommonBean");
        //回显数据
        if (null != tempCategoryCommonBean&&null!=tempCategoryCommonBean.getRbString()) {
            if (tempCategoryCommonBean.getRbString().equals("民办")) {
                rbPrivate.setChecked(true);
            } else if (tempCategoryCommonBean.getRbString().equals("公办")) {
                rbPublic.setChecked(true);
            } else if (tempCategoryCommonBean.getRbString().equals("连锁")) {
                rbChain.setChecked(true);
            } else {
                cbOther.setChecked(true);
                etContent.setText(tempCategoryCommonBean.getRbString());
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.rb_private, R.id.rb_public, R.id.rb_chain, R.id.cb_other})
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
            case R.id.rb_private:
                if (ischanged) {
                    cbOther.setChecked(false);
                    commonBean.setRbString("民办");
                }
                break;
            case R.id.rb_public:
                if (ischanged) {
                    cbOther.setChecked(false);
                    commonBean.setRbString("公办");
                }
                break;
            case R.id.rb_chain:
                if (ischanged) {
                    cbOther.setChecked(false);
                    commonBean.setRbString("连锁");
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
                commonBean.setType(0);
                EventBus.getDefault().post(commonBean);
                finish();
                break;
        }
    }
}

