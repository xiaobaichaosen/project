package com.yijie.com.yijie.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.newschool.NewSchoolActivity;
import com.yijie.com.yijie.adapter.TransferSelectAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.bean.User;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.LoadingLayout;

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
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/4.
 * <p>
 * 授权转移
 */

public class TransferActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    List<User> dataList = new ArrayList<>();
    private TransferSelectAdapter mAdapter;
    private String schoolId;
    private String practiceId;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_project_list);
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        getUserListByRoleName();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        getUserListByRoleName();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("授权转移");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        schoolId = getIntent().getStringExtra("schoolId");
        practiceId = getIntent().getStringExtra("practiceId");
        getUserListByRoleName();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器 为线性布局
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));//设置分割线
        mAdapter = new TransferSelectAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setDataSource(dataList);


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
                int selectedPos = mAdapter.getSelectedPos();
                String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
                if(roleName.contains("开发老师")){
                    if (selectedPos==-1){
                        ShowToastUtils.showToastMsg(TransferActivity.this,"请勾选转移人员");
                        return;
                    }else {
                        //转移整个学校
                        Integer id = dataList.get(selectedPos).getId();
                        updateSchoolMain(schoolId,id+"");
                    }

                }else {
                    if (selectedPos==-1){
                        ShowToastUtils.showToastMsg(TransferActivity.this,"请勾选转移人员");
                        return;
                    }else {
                        //转移学校下的项目
                        Integer id = dataList.get(selectedPos).getId();
                        updatePracticeAcceptId(schoolId,practiceId,id+"");
                    }

                    }

                break;
        }
    }


    /**
     * 通过当前用户的角色名称查询对应的角色下用户列表
     */
    private void getUserListByRoleName() {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
        String pramRoleName = null;
        if (roleName.contains("开发老师")) {
            pramRoleName = "开发老师";
        } else if (roleName.contains("培训老师")) {
            pramRoleName = "培训老师";
        }
        stringStringHashMap.put("roleName",pramRoleName);
        getinstance.post(Constant.GETUSERLISTBYROLENAME, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
              commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);

                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        User user = gson.fromJson(jsonObject1.toString(), User.class);
                        dataList.add(user);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                if (dataList.size() == 0) {
               statusLayoutManager.showEmptyLayout();
                } else {
                   statusLayoutManager.showSuccessLayout();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
            statusLayoutManager.showErrorLayout();
            commonDialog.dismiss();

            }
        });

    }
    /**
     * 更新school的创建人
     */
    private void updateSchoolMain(String schoolId,String createBy) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId",schoolId);
        stringStringHashMap.put("createBy",createBy);
        ArrayList<File> files = new ArrayList<>();
        getinstance.post(Constant.UPDATECREATEBY, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
              commonDialog.dismiss();
                statusLayoutManager.showSuccessLayout();
                ShowToastUtils.showToastMsg(TransferActivity.this,"转移成功");
                finish();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                statusLayoutManager.showErrorLayout();
            commonDialog.dismiss();

            }
        });

    }
    /**
     * 更新项目接收人
     */
    private void updatePracticeAcceptId(String schoolId,String practiceId,String acceptId) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("schoolId",schoolId);
        stringStringHashMap.put("practiceId",practiceId);
        stringStringHashMap.put("acceptId",acceptId);
        ArrayList<File> files = new ArrayList<>();
        getinstance.post(Constant.UPDATEPRACTICEACCEPTID, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
               commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
               commonDialog.dismiss();
               statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                commonDialog.dismiss();
                statusLayoutManager.showSuccessLayout();
                ShowToastUtils.showToastMsg(TransferActivity.this,"转移成功");
                finish();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
                statusLayoutManager.showErrorLayout();

            }
        });

    }
}
