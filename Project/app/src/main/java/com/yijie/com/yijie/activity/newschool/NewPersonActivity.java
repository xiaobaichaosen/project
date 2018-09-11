package com.yijie.com.yijie.activity.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.CommonBean;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建个人，填写名称和金额
 */

public class NewPersonActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;

    CommonBean modiftyCommonBean = null;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_name)
    EditText etName;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_person);

    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        title.setText("联系人");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");

        modiftyCommonBean = (CommonBean) getIntent().getSerializableExtra("commenModify");
        if (modiftyCommonBean != null) {
            etMoney.setText(modiftyCommonBean.getMoney());
            etName.setText(modiftyCommonBean.getName());
        }
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                String name = etName.getText().toString().trim();
                String money = etMoney.getText().toString().trim();

                if (name.equals("")) {
                    ShowToastUtils.showToastMsg(this, "姓名不能为空");
                } else if (money.equals("")) {
                    ShowToastUtils.showToastMsg(this, "金额不能为空");
                }else{
                    CommonBean commonBean = new CommonBean();
                    commonBean.setName(name);
                    commonBean.setMoney(money);
                    if (modiftyCommonBean!=null){
                        commonBean.setId(modiftyCommonBean.getId());
                        DatabaseAdapter.getIntance(NewPersonActivity.this).updatePerson(commonBean);
                    }else {
                        DatabaseAdapter.getIntance(NewPersonActivity.this).inserPersonInfo(commonBean);
                    }
                    finish();
                    EventBus.getDefault().post(commonBean);
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
}
