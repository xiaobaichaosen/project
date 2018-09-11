package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolContact;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.CheckUserUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TelephoneRejestUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.utils.ViewUtils;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jiguang.net.HttpUtils;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

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
    boolean modify;
    private String schoolId;
    SchoolContact modiftyContactBean;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contact);

    }

    @Override
    public void init() {
        schoolId = getIntent().getExtras().getString("schoolId");
        modify = getIntent().getBooleanExtra("modify", false);

        modiftyContactBean = (SchoolContact) getIntent().getExtras().getSerializable("tempSchoolContact");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明

        title.setText("联系人");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        if (modiftyContactBean!=null){
            etName.setText(modiftyContactBean.getUserName());
            etPhone.setText(modiftyContactBean.getCellphone());
            etTelephone.setText(modiftyContactBean.getTelephone());
            etWx.setText(modiftyContactBean.getWechat());
            etQq.setText(modiftyContactBean.getQq());
        }
//        modiftyContactBean = (ContactBean) getIntent().getSerializableExtra("contactModify");
//        if (modiftyContactBean!=null){
//            etName.setText(modiftyContactBean.getName());
//            etPhone.setText(modiftyContactBean.getPhoneNumber());
//            etTelephone.setText(modiftyContactBean.getZjNubmer());
//            etWx.setText(modiftyContactBean.getWxNubmer());
//            etQq.setText(modiftyContactBean.getQqNubmer());
//        }
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
//                contactBean.setName(name);
//                contactBean.setPhoneNumber(phone);
//                contactBean.setZjNubmer(etTelephone.getText().toString().trim());
//                contactBean.setWxNubmer(etWx.getText().toString().trim());
//                contactBean.setQqNubmer(etQq.getText().toString().trim());
                 if (name.equals("")){
                    ShowToastUtils.showToastMsg(this,"姓名不能为空");
                    return;
                }
                  if (!TextUtils.isEmpty(phone)){
                    if (phone.length()<11){
                        ShowToastUtils.showToastMsg(this,"手机号不合法");
                        return;
                    }
                    else if (!CheckUserUtils.isNumeric(phone)){
                        ShowToastUtils.showToastMsg(this,"手机号不合法");
                        return;
                    }
                }


                     SchoolContact schoolContact = new SchoolContact();
                     schoolContact.setCellphone(phone);
                     schoolContact.setTelephone(etTelephone.getText().toString().trim());
                     schoolContact.setUserName(name);
                     schoolContact.setWechat(etWx.getText().toString().trim());
                     schoolContact.setQq(etQq.getText().toString().trim());
                     if (null!=modiftyContactBean){
                         schoolContact.setId(modiftyContactBean.getId());
                     }
                    if (schoolId!=null){
                        schoolContact.setSchoolId(Integer.parseInt(schoolId));
                        School school = new School();
                        ArrayList<SchoolContact> schoolContacts = new ArrayList<>();
                        schoolContacts.add(schoolContact);
                        school.setSchoolContact(schoolContacts);
                        addContact(school);
                    }else {
                        EventBus.getDefault().post(schoolContact);

                    }
                     finish();

//                String name = etName.getText().toString().trim();
//                String phone = etPhone.getText().toString().trim();
//                ContactBean contactBean = new ContactBean();
//                contactBean.setName(name);
//                contactBean.setPhoneNumber(phone);
//                contactBean.setZjNubmer(etTelephone.getText().toString().trim());
//                contactBean.setWxNubmer(etWx.getText().toString().trim());
//                contactBean.setQqNubmer(etQq.getText().toString().trim());

//                if (name.equals("")){
//                    ShowToastUtils.showToastMsg(this,"姓名不能为空");
//                }else if (phone.equals("")){
//                    ShowToastUtils.showToastMsg(this,"手机不能为空");
//                }else if (!TelephoneRejestUtils.checkCellphone(phone)){
//                    ShowToastUtils.showToastMsg(this,"手机号不合法");
//                }
//                else{
//                    if (modify){
//                    //后台数据库已经有数据了，是从学校详情过来的数据
//                        SchoolContact schoolContact = new SchoolContact();
////                       String schoolId = getIntent().getStringExtra("schoolId");
//                        //TODO 通过上一个页面传入
////                        schoolContact.setSchoolId(Integer.parseInt(schoolId));
//                        schoolContact.setSchoolId(94);
//                        schoolContact.setUserName(name);
//                        schoolContact.setCellphone(phone);
//                        schoolContact.setTelephone(etTelephone.getText().toString().trim());
//                        schoolContact.setWechat(etWx.getText().toString().trim());
//                        schoolContact.setQq(etQq.getText().toString().trim());
//                        School school = new School();
//                        ArrayList<SchoolContact> schoolContacts = new ArrayList<>();
//                        schoolContacts.add(schoolContact);
//                        school.setSchoolContact(schoolContacts);
//                        addContact(school);
//                    }else{
//                    if (modiftyContactBean!=null){
//                        contactBean.setId(modiftyContactBean.getId());
//                        DatabaseAdapter.getIntance(NewContactActivity.this).update(contactBean);
//                    }else {
//                        DatabaseAdapter.getIntance(NewContactActivity.this).inserInfo(contactBean);
//                    }
//                    finish();
//                    EventBus.getDefault().post("schoolContact");
//                }

                break;
        }
    }

    /**
     * 新建或修改联系人
     * @param school
     */
    private void addContact(School school){
        com.yijie.com.yijie.utils.HttpUtils getinstance = com.yijie.com.yijie.utils.HttpUtils.getinstance(NewContactActivity.this);

        getinstance.postJson(Constant.MODECONTACT, school, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                finish();
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
