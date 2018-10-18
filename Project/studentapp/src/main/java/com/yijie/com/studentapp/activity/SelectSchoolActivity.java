package com.yijie.com.studentapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.adapter.FilterAdapter;
import com.yijie.com.studentapp.adapter.LoadMorePopuAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.bean.SchoolPractice;
import com.yijie.com.studentapp.bean.Student;
import com.yijie.com.studentapp.utils.AnimationUtil;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

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
 * 选择学校
 */
public class SelectSchoolActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.et_school)
    AutoCompleteTextView etSchool;
    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.view_project)
    View viewProject;
    private String password;
    private String phonenumber;
    private  SchoolPractice schoolPractice;
    private List<Student> dataList = new ArrayList<>();
    private List<SchoolPractice> projectList = new ArrayList<>();
    private FilterAdapter mAdapter;
    private Student student;
    private String verifyCode;
    private LoadMorePopuAdapter loadMoreWrapperAdapter;
    private PopupWindow mPopupWindow;
    private int fromYDelta;
    private boolean isPopWindowShowing;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_selectschool);
    }

    @Override
    public void init() {
        password = getIntent().getStringExtra("password");
        phonenumber = getIntent().getStringExtra("phonenumber");
        verifyCode = getIntent().getStringExtra("verifyCode");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        getData();
        etSchool.setThreshold(1);  //设置输入一个字符 提示，默认为2
        etSchool.setOnItemClickListener(this);
    }

    /**
     * 获取学校
     */
    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("longitude", "");
        stringStringHashMap.put("latitude", "");
        getinstance.post(Constant.SCHOOLMAINRAGELIST, stringStringHashMap, new BaseCallback<String>() {
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
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray list = jsonObject.getJSONArray("data");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        Student student = new Student();
                        student.setId(jsonObject1.getInt("id") + "");
                        student.setName(jsonObject1.getString("name"));
                        dataList.add(student);
                    }
                    mAdapter = new FilterAdapter(dataList, SelectSchoolActivity.this);
                    etSchool.setAdapter(mAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });

    }

    /**
     * 获取学校下面项目列表
     */
    private void getProjectList(String schoolId) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId", schoolId);
        getinstance.post(Constant.SELECTSCHOOLPRACTICELIST, stringStringHashMap, new BaseCallback<String>() {
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
                LogUtil.e("学校下面项目列表=="+o);
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray list = jsonObject.getJSONArray("data");
                    Gson gson=new Gson();
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        SchoolPractice schoolPractice = gson.fromJson(jsonObject1.toString(), SchoolPractice.class);
                        projectList.add(schoolPractice);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    //注册
//    @OnClick(R.id.btn_two)
//    public void onViewClicked() {
//        if (null == student) {
//           ShowToastUtils.showToastMsg(this, "请选择学校");
//       }  else {
//            //请求网络
//            HttpUtils instance = HttpUtils.getinstance(this);
//            Map map = new HashMap();
//            Map mapDate = new HashMap();
//            mapDate.put("cellphone", phonenumber);
//            mapDate.put("password", password);
//            mapDate.put("schoolId", student.getId()+"");
//            mapDate.put("schoolPracticeId", schoolPractice.getId()+"");
//            mapDate.put("verifyCode", verifyCode);
//
//            map.put("requestData", mapDate.toString());
//            instance.post(Constant.REGISTURL, map, new BaseCallback<String>() {
//                @Override
//                public void onRequestBefore() {
//                    commonDialog.dismiss();
//                }
//
//                @Override
//                public void onFailure(Request request, Exception e) {
//                    commonDialog.dismiss();
//                }
//
//                @Override
//                public void onSuccess(Response response, String s) {
//                    commonDialog.dismiss();
//                    LogUtil.e(s);
//                    try {
//                        JSONObject jsonObject = new JSONObject(s);
//                        if (jsonObject.getString("rescode").equals("200")) {
//                            Intent intent = new Intent();
//                            intent.setClass(SelectSchoolActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            ShowToastUtils.showToastMsg(SelectSchoolActivity.this, jsonObject.getString("resMessage"));
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(Response response, int errorCode, Exception e) {
//                    commonDialog.dismiss();
//                }
//            });
//        }
//    }


    @OnClick(R.id.btn_two)
    public void onViewClicked(){
        Intent intent=new Intent();
        intent.putExtra("cellphone",phonenumber);
        intent.putExtra("password",password);
        intent.putExtra("schoolId",student.getId()+"");
        intent.putExtra("schoolPracticeId",schoolPractice.getId()+"");
        intent.putExtra("verifyCode",verifyCode);

        intent.setClass(SelectSchoolActivity.this,StudentNumActivity.class);
//        startActivity(intent);
////        if (null == student) {
////            ShowToastUtils.showToastMsg(this, "请选择学校");
////        } else {
////            //请求网络
////            HttpUtils instance = HttpUtils.getinstance(this);
////            Map map = new HashMap();
////            Map mapDate = new HashMap();
////            mapDate.put("cellphone", phonenumber);
////            mapDate.put("password", password);
////            mapDate.put("schoolId", student.getId());
////            mapDate.put("verifyCode", verifyCode);
////            map.put("requestData", mapDate.toString());
////            instance.post(Constant.REGISTURL, map, new BaseCallback<String>() {
////                @Override
////                public void onRequestBefore() {
////                    commonDialog.dismiss();
////                }
////
////                @Override
////                public void onFailure(Request request, Exception e) {
////                    commonDialog.dismiss();
////                }
////
////                @Override
////                public void onSuccess(Response response, String s) {
////                    commonDialog.dismiss();
////                    LogUtil.e(s);
////                    try {
////                        JSONObject jsonObject = new JSONObject(s);
////                        if (jsonObject.getString("rescode").equals("200")) {
////                            Intent intent = new Intent();
////                            intent.setClass(SelectSchoolActivity.this, LoginActivity.class);
////                            startActivity(intent);
////                            finish();
////                        } else {
////                            ShowToastUtils.showToastMsg(SelectSchoolActivity.this, jsonObject.getString("resMessage"));
////                        }
////
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                }
////
////                @Override
////                public void onError(Response response, int errorCode, Exception e) {
////                    commonDialog.dismiss();
////                }
//            });
//        }
    }

    private void showPopupWindow() {
        //andorid 7.0如果内容超过屏幕showasdropdown 将会失效需要适配
        final View contentView = LayoutInflater.from(this).inflate(R.layout.selectlist, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置适配器
        loadMoreWrapperAdapter = new LoadMorePopuAdapter(projectList, R.layout.adapter_popu_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);


        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
//        mPopupWindow.setOutsideTouchable(false);
//        mPopupWindow.setFocusable(false);
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        fromYDelta = -contentHeight - 50;
        mPopupWindow.showAsDropDown(viewProject, 0, 0);
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(this, fromYDelta));
//        tvDown.startAnimation(AnimationUtil.createRotateupAnimation());
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMorePopuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                schoolPractice = projectList.get(position);
                tvProject.setText(schoolPractice.getProjectName());
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopWindowShowing = false;
            }
        });
        isPopWindowShowing = true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        student = mAdapter.getmList().get(i);
        etSchool.setText(student.getName());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        getProjectList(student.getId());
    }

    @OnClick(R.id.tv_project)
    public void onViewClick() {
        if (null!=student){
            if (projectList.size()==0){
                ShowToastUtils.showToastMsg(this,"学校还没有实习项目");
            }else{
                if (!isPopWindowShowing){
                    showPopupWindow();
                }
            }
        }else {
            if (!TextUtils.isEmpty(etSchool.getText().toString())){
                ShowToastUtils.showToastMsg(this,"您的学校还没有注册");
            }else{
                ShowToastUtils.showToastMsg(this,"请先选择学校");
            }

        }

    }

}
