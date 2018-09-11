package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by 奕杰平台 on 2018/6/7.
 * 工服押金
 */

public class UniformFreeActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.rb_yes)
    RadioButton rbYes;
    @BindView(R.id.et_yesContent)
    EditText etYesContent;
    @BindView(R.id.rb_no)
    RadioButton rbNo;
    @BindView(R.id.rb_other)
    RadioButton rbOther;
    @BindView(R.id.et_otherContent)
    EditText etOtherContent;
    @BindView(R.id.rp_all)
    RadioGroup rpAll;
    private CommonBean tempUniformCommonBean;
    CommonBean commonBean = new CommonBean();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_uniformfree);
    }

    @Override
    public void init() {
        title.setText("工服押金");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempUniformCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempUniformCommonBean");
        //回显数据
        if (null != tempUniformCommonBean) {
            if (null != tempUniformCommonBean.getRbString() && !tempUniformCommonBean.getRbString().equals("")&&!tempUniformCommonBean.getRbString().equals("无")) {
                rbYes.setChecked(true);
                etYesContent.setText(tempUniformCommonBean.getRbString());
            }
            if (null != tempUniformCommonBean.getRbString() && !tempUniformCommonBean.getRbString().equals("")&&tempUniformCommonBean.getRbString().equals("无")) {
                rbNo.setChecked(true);
            }
            if (null != tempUniformCommonBean.getCbString() && !tempUniformCommonBean.getCbString().equals("")) {
                rbOther.setChecked(true);
                etOtherContent.setText(tempUniformCommonBean.getCbString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.rb_yes, R.id.rb_no, R.id.rb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.rb_other:
                if (ischanged) {
                    rbNo.setChecked(false);
                    rbYes.setChecked(false);
                    etOtherContent.setFocusable(true);
                    etOtherContent.setFocusableInTouchMode(true);
                } else {
                    etOtherContent.setFocusable(false);
                    etOtherContent.setFocusableInTouchMode(false);
                    etOtherContent.setText("");
                }
                break;
            case R.id.rb_yes:
                if (ischanged) {
                    rbNo.setChecked(false);
                    rbOther.setChecked(false);
                    etYesContent.setFocusable(true);
                    etYesContent.setFocusableInTouchMode(true);
                } else {
                    etYesContent.setFocusable(false);
                    etYesContent.setFocusableInTouchMode(false);
                    etYesContent.setText("");
                }
                break;
            case R.id.rb_no:
                if (ischanged) {
                    rpAll.clearCheck();
                    rbYes.setChecked(false);
                    rbOther.setChecked(false);
                    rbNo.setChecked(true);

//                    etYesContent.setFocusable(true);
//                    etYesContent.setFocusableInTouchMode(true);
                } else {

                    rpAll.clearCheck();
//                    etYesContent.setFocusable(false);
//                    etYesContent.setFocusableInTouchMode(false);
//                    etYesContent.setText("");
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
                if (!etYesContent.getText().toString().trim().equals("")) {
                    commonBean.setRbString(etYesContent.getText().toString().trim());
                }
                if (!etOtherContent.getText().toString().trim().equals("")) {
                    commonBean.setCbString(etOtherContent.getText().toString().trim());
                }if (rbNo.isChecked()) {
                commonBean.setRbString("无");
            }
                commonBean.setType(3);
                EventBus.getDefault().post(commonBean);
                finish();
                break;

        }
    }

}
