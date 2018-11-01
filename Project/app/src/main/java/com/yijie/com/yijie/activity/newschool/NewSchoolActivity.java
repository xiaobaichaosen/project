package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.imagepicker.bean.ImageItem;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.PoiSearchActivity;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.IntershipMoney;
import com.yijie.com.yijie.bean.SchoolAdress;
import com.yijie.com.yijie.bean.StudentSample;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolContact;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.bean.school.SchoolMemoInfo;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建学校
 */

public class NewSchoolActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_newEducation)
    EditText tvNewEducation;

    @BindView(R.id.tv_newSchoolAdress)
    TextView tvNewSchoolAdress;
    @BindView(R.id.to_newSchoolAdress)
    RelativeLayout toNewSchoolAdress;
    @BindView(R.id.et_detalAdress)
    TextView etDetalAdress;

    @BindView(R.id.tv_newType)
    TextView tvNewType;
    private String rescode="";

    @BindView(R.id.tv_newSchoolSample)
    TextView tvNewSchoolSample;


    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.to_newAppointmenttime)
    RelativeLayout toNewAppointmenttime;
    @BindView(R.id.tv_newNote)
    TextView tvNewNote;
    @BindView(R.id.to_newNodetal)
    TextView toNewNodetal;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.to_newSchoolSample)
    RelativeLayout toNewSchoolSample;
    @BindView(R.id.to_memorandum)
    RelativeLayout toMemorandum;
    @BindView(R.id.toGetAdress)
    RelativeLayout toGetAdress;
    @BindView(R.id.to_newContact)
    RelativeLayout toNewContact;
    @BindView(R.id.ll_click)
    LinearLayout llClick;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private MessageBean messageBean;
    private StudentSample studentSample;
    private IntershipMoney tempIntershipMoney;
    private SchoolAdress mSchoolAdress;
    private SchoolContact tempSchoolContact;
    private ArrayList<ImageItem> selImageList;
    //用来存放从备忘录过来的数据
    private School schoolMemory;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newschool);
        EventBus.getDefault().register(this);

    }
//
//    @OnTextChanged(value = R.id.tv_newEducation, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
//    void afterTextChanged(Editable s) {
//
//    }

    @Override
    public void init() {
        setColor(NewSchoolActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(NewSchoolActivity.this); // 改变状态栏变成透明
        title.setText("新建");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        initJsonData();

        //清空数据库
        DatabaseAdapter.getIntance(NewSchoolActivity.this).deleteAll();
        DatabaseAdapter.getIntance(NewSchoolActivity.this).deletePersonAll();

    }


    @OnClick({R.id.back, R.id.to_newSchoolAdress, R.id.to_newAppointmenttime, R.id.to_newContact, R.id.to_newSchoolSample, R.id.to_memorandum, R.id.toGetAdress, R.id.tv_recommend,R.id.ll_click})
    public void Click(View view) {
        final Bundle mBundle = new Bundle();
        final Intent intent = new Intent();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        String schoolName = tvNewEducation.getText().toString().trim();
        stringStringHashMap.put("name", schoolName);
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_newSchoolAdress:
                if (TextUtils.isEmpty(schoolName)){
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this,"请填写学校名称");
                    return;
                }
                HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e(s);

                        try {
                            JSONObject jsonArray = new JSONObject(s);
                            String resMessage = jsonArray.getString("resMessage");
                            rescode = jsonArray.getString("rescode");
                            commonDialog.dismiss();
                            if (rescode.equals("200")){
                                ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                            }else{
                                //先隐藏输入键盘
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                showOptions();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }

                });

                break;
            case R.id.to_memorandum:
                if (TextUtils.isEmpty(schoolName)){
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this,"请填写学校名称");
                    return;
                }
                HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e(s);

                        try {
                            JSONObject jsonArray = new JSONObject(s);
                            String resMessage = jsonArray.getString("resMessage");
                            rescode = jsonArray.getString("rescode");
                            commonDialog.dismiss();
                            if (rescode.equals("200")){
                                ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                            }else{
                                if (schoolMemory != null) {
                                    mBundle.putSerializable("schoolMemory", schoolMemory);
                                }
                                intent.putExtras(mBundle);
                                intent.setClass(NewSchoolActivity.this, MemorandumActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }

                });

                break;
            case R.id.to_newAppointmenttime:
                //实训详情
                intent.setClass(this, NewInternshipDetailActivity.class);
                if (tvNewAppointmenttime.getText().equals("已填写")) {
                    List<ContactBean> list = DatabaseAdapter.getIntance(this).queryAll();
                    for (int i = 0; i < list.size(); i++) {
                        ContactBean contactBean = list.get(i);
                        if (null == contactBean.getPhoneNumber()) {
                            mBundle.putSerializable("internshipModify", contactBean);
                            mBundle.putSerializable("internshipMoney", tempIntershipMoney);
                            intent.putExtras(mBundle);
                            break;
                        }
                    }
                }
                startActivity(intent);
                break;
            case  R.id.ll_click:


                break;
            case R.id.to_newContact:
                if (TextUtils.isEmpty(schoolName)){
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this,"请填写学校名称");
                    return;
                }
                HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e(s);

                        try {
                            JSONObject jsonArray = new JSONObject(s);
                            String resMessage = jsonArray.getString("resMessage");
                            rescode = jsonArray.getString("rescode");
                            commonDialog.dismiss();
                            if (rescode.equals("200")){
                                ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                            }else{
                                if (tempSchoolContact != null) {
                                    mBundle.putSerializable("tempSchoolContact", tempSchoolContact);
                                }
                                intent.putExtras(mBundle);
                                intent.setClass(NewSchoolActivity.this, NewContactActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }

                });

                break;
            case R.id.toGetAdress:
                if (TextUtils.isEmpty(schoolName)){
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this,"请填写学校名称");
                    return;
                }
                HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e(s);

                        try {
                            JSONObject jsonArray = new JSONObject(s);
                            String resMessage = jsonArray.getString("resMessage");
                            rescode = jsonArray.getString("rescode");
                            commonDialog.dismiss();
                            if (rescode.equals("200")){
                                ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                            }else{
                                intent.putExtra("schoolName",tvNewEducation.getText().toString().trim());
                                intent.setClass(NewSchoolActivity.this, PoiSearchActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }

                });



                break;
            case R.id.to_newSchoolSample:
                if (TextUtils.isEmpty(schoolName)){
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this,"请填写学校名称");
                    return;
                }
                HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        commonDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        commonDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
                        LogUtil.e(s);

                        try {
                            JSONObject jsonArray = new JSONObject(s);
                            String resMessage = jsonArray.getString("resMessage");
                            rescode = jsonArray.getString("rescode");
                            commonDialog.dismiss();
                            if (rescode.equals("200")){
                                ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                            }else{
                                intent.setClass(NewSchoolActivity.this, NewSchoolIntroduction.class);
                                mBundle.putSerializable("sampleModify", messageBean);
                                intent.putExtras(mBundle);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }

                });

                break;
            case R.id.tv_recommend:
                String educationString = tvNewEducation.getText().toString();
                String adressString = tvNewSchoolAdress.getText().toString();
                String detailString = etDetalAdress.getText().toString();
                if (educationString.equals("")) {
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this, "请填写学校名称");
                    return;
                } else if (adressString.equals("")) {
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this, "请填写学校地址");
                    return;
                } else if (detailString.equals("")) {
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this, "请填写学校详细地址");
                    return;
                }
                String json = (String) SharedPreferencesUtils.getParam(NewSchoolActivity.this, "user", "");
                int userId = 0;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    userId = Integer.parseInt(jsonObject.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //学校基本信息
                SchoolMain schoolMain = new SchoolMain();
                schoolMain.setName(educationString);
                schoolMain.setLocation(adressString);
                schoolMain.setDetailAddress(detailString);
                schoolMain.setLongitude(mSchoolAdress.getLon());
                schoolMain.setLatitude(mSchoolAdress.getLat());
//                //0是开发状态
//                schoolMain.setState("0");
                //TODO 登陆的时候返回待完成 ower是项目转移给谁
//                schoolMain.setCreateBy(userId);
                schoolMain.setCreateBy(userId);
                //messageBean，为学校简历
                ArrayList<File> files = new ArrayList<File>();
                if (messageBean != null) {
                    schoolMain.setContent(messageBean.getContent());
                    //TODO 上传图片待完成
                    selImageList = messageBean.getSelImageList();
                    for (ImageItem slist : selImageList) {
                        String path = slist.path;
                        File file = new File(path);
                        files.add(file);
                    }
                }

                //实习详情
//                SchoolPractice schoolPractice = new SchoolPractice();
                //学校联系人集合
                ArrayList<SchoolContact> schoolContacts = new ArrayList<>();
                schoolContacts.add(tempSchoolContact);
//                if (tvNewAppointmenttime.getText().equals("已填写")) {
//                    List<ContactBean> list = DatabaseAdapter.getIntance(this).queryAll();
//                    for (int i = 0; i < list.size(); i++) {
//                        ContactBean contactBean = list.get(i);
//                        if (null == contactBean.getPhoneNumber()) {
//                            schoolPractice.setUserId(userId);
//                            schoolPractice.setEducation(contactBean.getSchoolEduction());
//                            schoolPractice.setPracticeMonth(contactBean.getSchoolMonth());
//                            schoolPractice.setStatus(0);
//                            //年份
//                            schoolPractice.setYear(contactBean.getSchoolSample());
//                            schoolPractice.setPracticeType(contactBean.getSchoolType());
//                            schoolPractice.setManageModel(contactBean.getSchoolMode());
//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//                            try {
//                                Date date = sdf.parse(contactBean.getSchoolTime());
//                                schoolPractice.setOrderTime(date);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            break;
//                        }else {
//                            SchoolContact schoolContact = new SchoolContact();
//                            schoolContact.setUserName(contactBean.getName());
//                            schoolContact.setCellphone(contactBean.getPhoneNumber());
//                            schoolContact.setTelephone(contactBean.getZjNubmer());
//                            schoolContact.setWechat(contactBean.getWxNubmer());
//                            schoolContact.setQq(contactBean.getQqNubmer());
//                            schoolContacts.add(schoolContact);
//                        }
//                    }
//                }
                School school = new School();
//                ArrayList<SchoolPractice> schoolPractices = new ArrayList<>();
//                ArrayList<SchoolSalaryInfo> schoolSalaryInfos = new ArrayList<>();

//                schoolPractices.add(schoolPractice);
//                SchoolSalaryInfo schoolSalaryInfo = new SchoolSalaryInfo();
//                    说明实习薪资已经填写
//                if (tempIntershipMoney!=null){
//                schoolSalaryInfo.setPeriod(tempIntershipMoney.getStartDate()+"-"+tempIntershipMoney.getEndDate());
//                schoolSalaryInfo.setSalary(BigDecimal.valueOf(Integer.parseInt(tempIntershipMoney.getPay())));
//                schoolSalaryInfo.setManageFee(BigDecimal.valueOf(Integer.parseInt(tempIntershipMoney.getCountMoney())));
//                SchoolOtherFree schoolOtherFree = new SchoolOtherFree();
//                schoolOtherFree.setSchoolFree(tempIntershipMoney.getSchoolMoney());
//                schoolOtherFree.setYijieFree(tempIntershipMoney.getYijieMoney());
//                schoolOtherFree.setCommonBeans(tempIntershipMoney.getCommonBeans());
//                schoolSalaryInfo.setOtherFee(new Gson().toJson(schoolOtherFree).toString());
//                }

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//                try {
//                    Date date = sdf.parse("2018-7-8");
//                    schoolMemoInfo.setCreateTime(date.toString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                if (schoolContacts.size() == 0) {
                    ShowToastUtils.showToastMsg(NewSchoolActivity.this, "请填写联系人");
                    return;
                }
                school.setSchoolMain(schoolMain);
//                school.setSchoolPractice(schoolPractices);
                school.setSchoolContact(schoolContacts);
//                schoolSalaryInfos.add(schoolSalaryInfo);
//                school.setSchoolSalaryInfo(schoolSalaryInfos);
                if (null != schoolMemory) {
                    List<SchoolMemoInfo> schoolMemoInfo = schoolMemory.getSchoolMemoInfo();
                    ArrayList<SchoolMemoInfo> schoolMemoInfos = new ArrayList<>();
                    //为了去除创建时间----解析异常
                    for (int i = 0; i < schoolMemoInfo.size(); i++) {
                        SchoolMemoInfo schoolMemoInfo1 = new SchoolMemoInfo();
                        schoolMemoInfo1.setMemoContent(schoolMemoInfo.get(i).getMemoContent());
                        schoolMemoInfo1.setSendType(schoolMemoInfo.get(i).getSendType());
                        schoolMemoInfos.add(schoolMemoInfo1);
                    }
                    school.setSchoolMemoInfo(schoolMemoInfos);
                }

                final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);
                HashMap<String, String> stringStringHa = new HashMap<>();

                Gson gson = new Gson();
                stringStringHa.put("data", gson.toJson(school).toString());
                HttpUtils.getinstance(NewSchoolActivity.this).uploadFiles(Constant.NEWSCHOOL, stringStringHa, files, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                        progressDialog.show();
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(Response response, String s) throws JSONException {
//                        ShowToastUtils.showToastMsg(NewSchoolActivity.this,"新建学校成功");
                        LogUtil.e(s);
                        finish();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        progressDialog.dismiss();
                    }
                });


                break;

        }

    }

    private void selectSchoolCreate(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("name", tvNewEducation.getText().toString().trim());
        HttpUtils.getinstance(NewSchoolActivity.this).post(Constant.SELECTSCHOOLBYNAME, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) throws JSONException {
                LogUtil.e(s);

                try {
                    JSONObject jsonArray = new JSONObject(s);
                    String resMessage = jsonArray.getString("resMessage");
                    rescode = jsonArray.getString("rescode");
                    commonDialog.dismiss();
                    if (rescode.equals("200")){
                        ShowToastUtils.showToastMsg(NewSchoolActivity.this, resMessage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }

        });

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(String message) {
//        if (message.equals("schoolContact")) {
//            final List<ContactBean> list = DatabaseAdapter.getIntance(NewSchoolActivity.this).queryAll();
//            final List<ContactBean> listContact = new ArrayList<ContactBean>();
//            for (int i = 0; i < list.size(); i++) {
//                ContactBean contactBean = list.get(i);
//                if (null != contactBean.getPhoneNumber()) {
//                    listContact.add(contactBean);
//                }
//            }
//            recyclerView.setVisibility(View.VISIBLE);
//            if (listContact.size() > 0) {
//                toNewContact.setImageResource(R.mipmap.add_contact);
//            } else {
//                toNewContact.setImageResource(R.mipmap.goright);
//            }
//            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            ContactAdapter contactAdapter = null;
//            if (contactAdapter == null) {
//                contactAdapter = new ContactAdapter(this, listContact);
//                recyclerView.setAdapter(contactAdapter);
//            } else {
//                contactAdapter.notifyDataSetChanged();
//            }
//            contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(View v, int position) {
//                    Intent intent = new Intent();
//                    intent.setClass(NewSchoolActivity.this, NewContactActivity.class);
//                    Bundle mBundle = new Bundle();
//                    mBundle.putSerializable("contactModify", listContact.get(position));
//                    intent.putExtras(mBundle);
//                    startActivity(intent);
//                }
//            });
//        }
        if (message.equals("schoolIntroduction")) {


        } else if (message.equals("schoolInternshipDetail")) {
            tvNewAppointmenttime.setText("已填写");
        } else if (message.equals("schooldetailadress")) {
            etDetalAdress.setText("已填写");
        }


    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(MessageBean event) {
        if (event != null) {
            tvNewSchoolSample.setText("已填写");
            messageBean = event;
            LogUtil.e("content==" + event.getContent());
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void school(School school) {
        if (school != null) {
            schoolMemory = school;
            tvNewNote.setText("已填写");
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void school(SchoolContact schoolContact) {
        if (schoolContact != null) {
            tempSchoolContact = schoolContact;
            tvNewType.setText("已填写");
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread1(IntershipMoney intershipMoney) {
        if (intershipMoney != null) {
            tempIntershipMoney = intershipMoney;
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleSomethingElse(SchoolAdress schoolAdress) {
        int type = schoolAdress.getType();
        if (type == 2) {
            etDetalAdress.setText(schoolAdress.getDetailAdress());
            mSchoolAdress = schoolAdress;
        }

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void showOptions() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        // 初始化三个列表数据
//        DataModel.initData(options1Items, options2Items, options3Items);


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
//        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setTextSize(18);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvNewSchoolAdress.setText(tx);
//                vMasker.setVisibility(View.GONE);
            }
        });
        pvOptions.show();

    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
