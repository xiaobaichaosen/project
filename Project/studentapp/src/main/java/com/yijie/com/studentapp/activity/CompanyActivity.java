package com.yijie.com.studentapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.MainActivity;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.slidecontact.ContactAdapter;
import com.yijie.com.studentapp.view.slidecontact.HanziToPinyin;
import com.yijie.com.studentapp.view.slidecontact.bean.Contact;
import com.yijie.com.studentapp.view.slidecontact.widget.CustomEditText;
import com.yijie.com.studentapp.view.slidecontact.widget.SideBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 选同伴
 */
public class CompanyActivity extends BaseActivity implements SideBar
        .OnTouchingLetterChangedListener, TextWatcher {

    @BindView(R.id.school_friend_member_search_input)
    CustomEditText schoolFriendMemberSearchInput;
    @BindView(R.id.school_friend_member)
    ListView schoolFriendMember;
    @BindView(R.id.school_friend_dialog)
    TextView schoolFriendDialog;
    @BindView(R.id.school_friend_sidrbar)
    SideBar schoolFriendSidrbar;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    private ArrayList<Contact> datas = new ArrayList<>();
    private ContactAdapter mAdapter;
    private TextView mFooterView;

    /**
     * 获取学校联系人
     */
    private void parser() {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        String schoolPracticeId = (String) SharedPreferencesUtils.getParam(this, "schoolPracticeId", "");
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolPracticeId",schoolPracticeId);
        getinstance.post(Constant.SELECTBYSCHOOLID, stringStringHashMap, new BaseCallback<String>() {
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
                try {
                    JSONObject jsonObject = new JSONObject(o)    ;
                    String rescode = jsonObject.getString("rescode");
                    if (rescode.equals("200")){
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject jsonObject1 = data.getJSONObject(i);
                            Contact contact = new Contact();
                            contact.setName(jsonObject1.getString("studentName"));
                            contact.setId(jsonObject1.getInt("id"));
                            contact.setUrl(jsonObject1.getString("headPic"));
                            contact.setPinyin(HanziToPinyin.getPinYin(contact.getName()));
                            datas.add(contact);
                        }

                        mFooterView.setText(datas.size() + "位联系人");
                        mAdapter = new ContactAdapter(schoolFriendMember, datas);
                        schoolFriendMember.setAdapter(mAdapter);
                    }else{
                        ShowToastUtils.showToastMsg(CompanyActivity.this,jsonObject.getString("resMessage"));
                    }

                    commonDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        int position = 0;
        // 该字母首次出现的位置
        if (mAdapter != null) {
            position = mAdapter.getPositionForSection(s.charAt(0));
        }
        if (position != -1) {
            schoolFriendMember.setSelection(position);
        } else if (s.contains("#")) {
            schoolFriendMember.setSelection(0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        ArrayList<Contact> temp = new ArrayList<>();
        for (Contact data : datas) {
            if (data.getName().contains(s) || data.getPinyin().contains(s)) {
                temp.add(data);
            } else {

            }
        }
        if (mAdapter != null) {
            mAdapter.refresh(temp);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_company);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明bb
        title.setText("联系人");
        schoolFriendSidrbar.setTextView(schoolFriendDialog);
        schoolFriendSidrbar.setOnTouchingLetterChangedListener(this);
        schoolFriendMemberSearchInput.addTextChangedListener(this);

        // 给listView设置adapter
        mFooterView = (TextView) View.inflate(this, R.layout.item_list_contact_count, null);
        schoolFriendMember.addFooterView(mFooterView);
        parser();
        schoolFriendMember.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(datas.get(i));
                finish();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
