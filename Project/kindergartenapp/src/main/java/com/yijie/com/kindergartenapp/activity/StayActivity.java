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
    private StayBean modiftyStayBean;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_stay);
    }

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
            if (! modiftyStayBean.getUpString().isEmpty()) {
                cbUp.setChecked(true);
            }
            if (! modiftyStayBean.getDownString().isEmpty()) {
                cbDown.setChecked(true);
            }
            if ( !modiftyStayBean.getOutString().isEmpty()) {
                cbOut.setChecked(true);
            }
            if (!modiftyStayBean.getFoureightString().isEmpty()) {
                cbFoureight.setChecked(true);
            }
            if (!modiftyStayBean.getEighttwelveString().isEmpty()) {
                cbEighttwelve.setChecked(true);
            }
            if (! modiftyStayBean.getTwoString().isEmpty()) {
                cbTwo.setChecked(true);
            }
            if (! modiftyStayBean.getThreeString().isEmpty()) {
                cbThree.setChecked(true);
            }

            if (! modiftyStayBean.getOtherString().isEmpty() ) {
                cbOther.setChecked(true);
                etContent.setText(modiftyStayBean.getOtherString());
            }

        }
    }

    @OnCheckedChanged({R.id.cb_other, R.id.cb_up, R.id.cb_down, R.id.cb_out,R.id.cb_foureight, R.id.cb_eighttwelve, R.id.cb_two, R.id.cb_three})
    public void OnCheckedChangeListener(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.cb_other:
                if (ischanged) {
                    etContent.setFocusable(true);
                    etContent.setFocusableInTouchMode(true);
                } else {
                    etContent.setFocusable(false);
                    etContent.setFocusableInTouchMode(false);
                    etContent.setText("");
                }
                break;
            case R.id.cb_up:
                if (ischanged) {
                    stayBean.setUpString("园内地上");
                } else {
                    stayBean.setUpString("");
                }
                break;
            case R.id.cb_down:
                if (ischanged) {
                    stayBean.setDownString("园内地下");
                } else {
                    stayBean.setDownString("");
                }
                break;
            case R.id.cb_out:
                if (ischanged) {
                    stayBean.setOutString("园外");
                } else {
                    stayBean.setOutString("");
                }
                break;
            case R.id.cb_foureight:
                if (ischanged) {
                    stayBean.setFoureightString("4-8人");
                } else {
                    stayBean.setFoureightString("");
                }
                break;
            case R.id.cb_eighttwelve:
                if (ischanged) {
                    stayBean.setEighttwelveString("8-12人");
                } else {
                    stayBean.setEighttwelveString("");
                }
                break;
            case R.id.cb_two:
                if (ischanged) {
                    stayBean.setTwoString("两居");
                } else {
                    stayBean.setTwoString("");
                }
                break;
            case R.id.cb_three:
                if (ischanged) {
                    stayBean.setThreeString("三居");
                } else {
                    stayBean.setThreeString("");
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
                stayBean.setOtherString(etContent.getText().toString().trim());
                EventBus.getDefault().post(stayBean);
                finish();
                break;
        }
    }
}
