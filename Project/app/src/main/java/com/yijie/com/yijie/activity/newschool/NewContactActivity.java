package com.yijie.com.yijie.activity.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建联系人
 */

public class NewContactActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_telephone)
    EditText etTelephone;
    @BindView(R.id.et_wx)
    EditText etWx;
    @BindView(R.id.et_qq)
    EditText etQq;
    ContactBean modiftyContactBean=null;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contact);

    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        title.setText("联系人");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");

        modiftyContactBean = (ContactBean) getIntent().getSerializableExtra("contactModify");
        if (modiftyContactBean!=null){
            etName.setText(modiftyContactBean.getName());
            etPhone.setText(modiftyContactBean.getPhoneNumber());
            etTelephone.setText(modiftyContactBean.getZjNubmer());
            etWx.setText(modiftyContactBean.getWxNubmer());
            etQq.setText(modiftyContactBean.getQqNubmer());
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
                String phone = etPhone.getText().toString().trim();
                ContactBean contactBean = new ContactBean();
                contactBean.setName(name);
                contactBean.setPhoneNumber(phone);
                contactBean.setZjNubmer(etTelephone.getText().toString().trim());
                contactBean.setWxNubmer(etWx.getText().toString().trim());
                contactBean.setQqNubmer(etQq.getText().toString().trim());

                if (name.equals("")){
                    ShowToastUtils.showToastMsg(this,"姓名不能为空");
                }else if (phone.equals("")){
                    ShowToastUtils.showToastMsg(this,"手机不能为空");
                }else if (!TelephoneRejestUtils.checkCellphone(phone)){
                    ShowToastUtils.showToastMsg(this,"手机号不合法");
                }
                else{
                    if (modiftyContactBean!=null){
                        contactBean.setId(modiftyContactBean.getId());
                        DatabaseAdapter.getIntance(NewContactActivity.this).update(contactBean);
                    }else {
                        DatabaseAdapter.getIntance(NewContactActivity.this).inserInfo(contactBean);
                    }
                    finish();
                    EventBus.getDefault().post("schoolContact");
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
