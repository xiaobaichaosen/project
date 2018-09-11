package com.yijie.com.yijie.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewContactActivity;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.school.adapter.SchoolContactAdapterRecyclerView;
import com.yijie.com.yijie.activity.school.adapter.SchoolMemeryAdapterRecyclerView;
import com.yijie.com.yijie.adapter.ContactRecycleViewModityAapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.ContactReceiveBean;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolContact;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/23.
 */

public class ContactModityActivity extends BaseActivity  {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_addContact)
    TextView tvAddContact;
    public static Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
    @BindView(R.id.tv_delect)
    TextView tvDelect;
    @BindView(R.id.tv_confim)
    TextView tvConfim;
    List <SchoolContact>list;
    ContactRecycleViewModityAapter myAdapterRecyclerView;
    //获取学校id
    String schoolId;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_contact_modity);
    }

    @Override
    public void init() {
        list=new ArrayList<>();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("联系方式");
        schoolId = getIntent().getStringExtra("schoolId");
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView


    }

    @Override
    protected void onResume() {
        list.clear();
        getContactList();
        super.onResume();
    }

    /**
     * 查询学校联系人列表
     */
    private void getContactList() {
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(ContactModityActivity.this);
        HttpUtils getinstance = HttpUtils.getinstance(ContactModityActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("schoolId", schoolId);
        stringStringHashMap.put("schoolId", "94");
        getinstance.post(Constant.SELECTCONTACT, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }
            @Override
            public void onSuccess(Response response, String o) {
                    LogUtil.e(o);
                try {
                    //学校联系人
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray schoolContact = jsonObject.getJSONArray("data");
                    for (int i = 0; i < schoolContact.length(); i++) {
                        JSONObject jsonObject1 = schoolContact.getJSONObject(i);
                        SchoolContact schoolContact1 = new SchoolContact();
                        schoolContact1.setId(Integer.parseInt(jsonObject1.getString("id")));
                        schoolContact1.setSchoolId(Integer.parseInt(jsonObject1.getString("schoolId")));
                        schoolContact1.setUserName(jsonObject1.getString("userName"));
                        schoolContact1.setCellphone(jsonObject1.getString("cellphone"));
                        schoolContact1.setTelephone(jsonObject1.getString("telephone"));
                        schoolContact1.setWechat(jsonObject1.getString("wechat"));
                        schoolContact1.setQq(jsonObject1.getString("qq"));
                        list.add(schoolContact1);
                    }
                    myAdapterRecyclerView = new ContactRecycleViewModityAapter(ContactModityActivity.this, list, R.layout.contact_modity_item);
                    recyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_addContact,R.id.tv_delect, R.id.tv_confim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_addContact:
                Intent intent = new Intent();
                intent.setClass(this, NewContactActivity.class);
                //如果是修改进去的执行联网操作，
                intent.putExtra("modify",true);
                intent.putExtra("schoolId",schoolId);
                startActivity(intent);
                break;
            case R.id.tv_delect:
                StringBuilder stringBuilder=new StringBuilder();
                ArrayList<String> strings = new ArrayList<>();
                for (Integer i: map.keySet() ) {
                  if (map.get(i)){
                      strings.add(list.get(i).getId().toString());
                  }
                }
                for (int i = 0; i < strings.size(); i++) {
                    if (i==strings.size()-1){
                        stringBuilder.append(strings.get(i));
                    }else{
                        stringBuilder.append(strings.get(i)+",");
                    }
                }
                deleteContactById(stringBuilder.toString());

                map.clear();
                break;
            case R.id.tv_confim:
                //批量修改联系人
                School school = new School();
                ArrayList<SchoolContact> schoolContacts = new ArrayList<>();
                List<SchoolContact> modiftyList = myAdapterRecyclerView.getModiftyList();
                for (Integer i: map.keySet() ) {
                    if (map.get(i)){
                     SchoolContact schoolContact = modiftyList.get(i);
                     schoolContacts.add(schoolContact);
                    }
                }
                school.setSchoolContact(schoolContacts);
                UpdateSchoolContact(school);
                map.clear();

                break;
        }
    }

    /**
     * 批量修改联系人
     * @param school
     */
    public void UpdateSchoolContact(School school){
        com.yijie.com.yijie.utils.HttpUtils getinstance = com.yijie.com.yijie.utils.HttpUtils.getinstance(ContactModityActivity.this);
        final  ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);

        getinstance.postJson(Constant.MODECONTACT, school, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                finish();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }
    /**
     * 通过id删除联系人
     */
    public void deleteContactById(String  ids) {
        HttpUtils getinstance = HttpUtils.getinstance(ContactModityActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("ids",ids);
        stringStringHashMap.put("schoolId","94");

        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);
        getinstance.post(Constant.DELETECONTACT, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                list.clear();
                getContactList();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }



}
