package com.yijie.com.kindergartenapp.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.StayBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/3/29.
 * 住宿可以多选
 */

public class StayActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;

    @BindView(R.id.cb_up)
    CheckBox cbUp;
    @BindView(R.id.cb_down)
    CheckBox cbDown;
    @BindView(R.id.cb_out)
    CheckBox cbOut;
    @BindView(R.id.cb_other)
    CheckBox cbOther;
    @BindView(R.id.et_content)
    EditText etContent;
    StayBean stayBean = new StayBean();
    ;
    @BindView(R.id.cb_foureight)
    CheckBox cbFoureight;
    @BindView(R.id.cb_eighttwelve)
    CheckBox cbEighttwelve;
    @BindView(R.id.cb_two)
    CheckBox cbTwo;
    @BindView(R.id.cb_three)
    CheckBox cbThree;
    @BindView(R.id.cb_wu)
    CheckBox cbWu;
    @BindView(R.id.tv_warn)
    TextView tvWarn;
    private StayBean modiftyStayBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_stay);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        title.setText("住宿安排");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        modiftyStayBean = (StayBean) getIntent().getExtras().getSerializable("tempStayBean");
        //回显数据null != tempGradeCommonBean&&null!=tempGradeCommonBean.getRbString()
        if (null != modiftyStayBean) {
            if (!modiftyStayBean.getUpString().isEmpty()) {
                cbUp.setChecked(true);
            }
            if (!modiftyStayBean.getDownString().isEmpty()) {
                cbDown.setChecked(true);
            }
            if (!modiftyStayBean.getOutString().isEmpty()) {
                cbOut.setChecked(true);
            }
            if (!modiftyStayBean.getFoureightString().isEmpty()) {
                cbFoureight.setChecked(true);
            }
            if (!modiftyStayBean.getEighttwelveString().isEmpty()) {
                cbEighttwelve.setChecked(true);
            }
            if (!modiftyStayBean.getTwoString().isEmpty()) {
                cbTwo.setChecked(true);
            }
            if (!modiftyStayBean.getThreeString().isEmpty()) {
                cbThree.setChecked(true);
            }if (!modiftyStayBean.getOtherString().isEmpty()) {
                cbOther.setChecked(true);
            }
            if (!modiftyStayBean.getWuString().isEmpty()) {
                cbWu.setChecked(true);
                setCheckboxSelect(cbUp,true);
                setCheckboxSelect(cbDown,true);
                setCheckboxSelect(cbThree,true);
                setCheckboxSelect(cbTwo,true);
                setCheckboxSelect(cbEighttwelve,true);
                setCheckboxSelect(cbFoureight,true);
                setCheckboxSelect(cbOther,true);
                setCheckboxSelect(cbOut,true);
                tvWarn.setTextColor(getResources().getColorStateList(R.color.app_background));
            }
            if (!modiftyStayBean.getOtherString().isEmpty()) {
                cbOther.setChecked(true);
                etContent.setText(modiftyStayBean.getOtherString());
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnCheckedChanged({R.id.cb_wu, R.id.cb_other, R.id.cb_up, R.id.cb_down, R.id.cb_out, R.id.cb_foureight, R.id.cb_eighttwelve, R.id.cb_two, R.id.cb_three})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_wu:
                if (ischanged) {
                    setCheckboxSelect(cbUp,true);
                    setCheckboxSelect(cbDown,true);
                    setCheckboxSelect(cbThree,true);
                    setCheckboxSelect(cbTwo,true);
                    setCheckboxSelect(cbEighttwelve,true);
                    setCheckboxSelect(cbFoureight,true);
                    setCheckboxSelect(cbOther,true);
                    setCheckboxSelect(cbOut,true);
                    stayBean.setWuString("无");


                } else {
                    setCheckboxSelect(cbUp,false);
                    setCheckboxSelect(cbDown,false);
                    setCheckboxSelect(cbThree,false);
                    setCheckboxSelect(cbTwo,false);
                    setCheckboxSelect(cbEighttwelve,false);
                    setCheckboxSelect(cbFoureight,false);
                    setCheckboxSelect(cbOther,false);
                    setCheckboxSelect(cbOut,false);
                    stayBean.setWuString("");

                }
                break;
            case R.id.cb_other:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        etContent.setFocusable(true);
                        etContent.setFocusableInTouchMode(true);
                    } else {
                        etContent.setFocusable(false);
                        etContent.setFocusableInTouchMode(false);
                        etContent.setText("");
                    }
                }

                break;
            case R.id.cb_up:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setUpString("园内地上");
                    } else {
                        stayBean.setUpString("");
                    }
                }
                break;
            case R.id.cb_down:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setDownString("园内地下");
                    } else {
                        stayBean.setDownString("");
                    }
                }
                break;
            case R.id.cb_out:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setOutString("园外");
                    } else {
                        stayBean.setOutString("");
                    }
                }
                break;
            case R.id.cb_foureight:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setFoureightString("4-8人");
                    } else {
                        stayBean.setFoureightString("");
                    }
                }
                break;
            case R.id.cb_eighttwelve:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setEighttwelveString("8-12人");
                    } else {
                        stayBean.setEighttwelveString("");
                    }
                }
                break;
            case R.id.cb_two:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setTwoString("两居");
                    } else {
                        stayBean.setTwoString("");
                    }
                }
                break;
            case R.id.cb_three:
                if (!cbWu.isChecked()) {
                    if (ischanged) {
                        stayBean.setThreeString("三居");
                    } else {
                        stayBean.setThreeString("");
                    }
                }
                break;


        }
    }
    //当无是否勾选的时候 flag=true 勾选
    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setCheckboxSelect(CheckBox cb,boolean flag){
        if (flag){
            cb.setButtonTintList(getResources().getColorStateList(R.color.app_background));
            cb.setTextColor(getResources().getColorStateList(R.color.app_background));
            tvWarn.setTextColor(getResources().getColorStateList(R.color.app_background));
            cb.setClickable(false);
            cb.setChecked(false);
            cb.setFocusable(false);
            //重新赋值个空的staybean
            stayBean=new StayBean();
        }else {
            cb.setButtonTintList(getResources().getColorStateList(R.drawable.checkbox_shap));
            cb.setTextColor(getResources().getColorStateList(R.color.cb_select));
            tvWarn.setTextColor(getResources().getColorStateList(R.color.cb_select));
            cb.setClickable(true);
            cb.setFocusable(true);
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
                stayBean.setOtherString(etContent.getText().toString().trim());
                EventBus.getDefault().post(stayBean);
                finish();
                break;
        }
    }
}
