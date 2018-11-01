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
 * Created by 奕杰平台 on 2018/3/29.
 * 三餐安排
 */

public class MealsActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.rb_threemeals)
    RadioButton rbThreemeals;
    @BindView(R.id.rb_twomeals)
    RadioButton rbTwomeals;
    @BindView(R.id.rb_allmeal)
    RadioButton rbAllmeal;
    @BindView(R.id.cb_other)
    RadioButton cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    CommonBean commonBean = new CommonBean();
    @BindView(R.id.rp_all)
    RadioGroup rpAll;
    @BindView(R.id.cb_wu)
    RadioButton cbWu;
    private CommonBean tempMealCommonBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_meals);
    }

    @Override
    public void init() {
        title.setText("三餐安排");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempMealCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempMealCommonBean");
        //回显数据
        if (null != tempMealCommonBean) {
            if ("园内三餐".equals(tempMealCommonBean.getRbString())) {
                rbThreemeals.setChecked(true);
            } else if ("两餐一补".equals(tempMealCommonBean.getRbString())) {
                rbTwomeals.setChecked(true);
            } else if ("全补".equals(tempMealCommonBean.getRbString())) {
                rbAllmeal.setChecked(true);
            }else if ("无".equals(tempMealCommonBean.getRbString())) {
                cbWu.setChecked(true);
            } else {
                cbOther.setChecked(true);
                etContent.setText(tempMealCommonBean.getRbString());
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnCheckedChanged({R.id.cb_wu, R.id.rb_threemeals, R.id.rb_twomeals, R.id.rb_allmeal, R.id.cb_other})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {

            case R.id.cb_wu:
                if (ischanged) {
                    rpAll.clearCheck();
                    cbOther.setChecked(false);
                    commonBean.setRbString("无");
                }
                break;
            case R.id.cb_other:
                if (ischanged) {
                    rpAll.clearCheck();
                    commonBean.setRbString(null);
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText("");
                }
                break;
            case R.id.rb_threemeals:
                if (ischanged) {
                    cbOther.setChecked(false);
                    cbWu.setChecked(false);
                    commonBean.setRbString("园内三餐");
                }
                break;
            case R.id.rb_twomeals:
                if (ischanged) {
                    cbOther.setChecked(false);
                    cbWu.setChecked(false);
                    commonBean.setRbString("两餐一补");
                }
                break;
            case R.id.rb_allmeal:
                if (ischanged) {
                    cbOther.setChecked(false);
                    cbWu.setChecked(false);
                    commonBean.setRbString("全补");
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
                commonBean.setType(4);
                EventBus.getDefault().post(commonBean);

                finish();
                break;
        }
    }
}
