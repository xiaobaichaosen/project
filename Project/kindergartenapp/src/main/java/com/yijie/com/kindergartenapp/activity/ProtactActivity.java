package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CourseBean;
import com.yijie.com.kindergartenapp.bean.ProtectBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/3/29.
 * 其他福利可以多选
 */

public class ProtactActivity extends BaseActivity {


    ProtectBean courseBean = new ProtectBean();
    ;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;


    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.cb_fiveInsurance)
    CheckBox cbFiveInsurance;
    @BindView(R.id.cb_providentFund)
    CheckBox cbProvidentFund;
    @BindView(R.id.cb_SuppMedInsurance)
    CheckBox cbSuppMedInsurance;
    @BindView(R.id.cb_other)
    CheckBox cbOther;
    private ProtectBean modiftyProtectBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_protact);
    }

    @Override
    public void init() {
        title.setText("其他福利");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        modiftyProtectBean = (ProtectBean) getIntent().getExtras().getSerializable("tempProtectBean");
        //回显数据
        if (null != modiftyProtectBean) {
            if (null != modiftyProtectBean.getFiveInsurance()) {
               cbFiveInsurance .setChecked(true);
            }
            if (null != modiftyProtectBean.getProvidentFund()) {
                cbProvidentFund.setChecked(true);
            }
            if (null != modiftyProtectBean.getSuppMedInsurance()) {
                cbSuppMedInsurance.setChecked(true);
            }
            if (null != modiftyProtectBean.getOther()) {
                cbOther.setChecked(true);
            }

            if (null != modiftyProtectBean.getOther() && !modiftyProtectBean.getOther().equals("")) {
                cbOther.setChecked(true);
                etContent.setText(modiftyProtectBean.getOther());
            }

        }
    }

    @OnCheckedChanged({R.id.cb_fiveInsurance, R.id.cb_providentFund, R.id.cb_SuppMedInsurance, R.id.cb_other})
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
            case R.id.cb_fiveInsurance:
                if (ischanged) {
                    courseBean.setFiveInsurance("五险");
                } else {
                    courseBean.setFiveInsurance(null);
                }
                break;
            case R.id.cb_providentFund:
                if (ischanged) {
                    courseBean.setProvidentFund("公积金");
                } else {
                    courseBean.setProvidentFund(null);
                }
                break;
            case R.id.cb_SuppMedInsurance:
                if (ischanged) {
                    courseBean.setSuppMedInsurance("补充医疗");
                } else {
                    courseBean.setSuppMedInsurance(null);
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
                courseBean.setOther(etContent.getText().toString().trim());
                EventBus.getDefault().post(courseBean);
                finish();
                break;
        }
    }
}
