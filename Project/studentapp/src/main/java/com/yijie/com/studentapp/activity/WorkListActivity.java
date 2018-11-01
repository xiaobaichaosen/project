package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentWorkDue;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.slipdelect.LoadMoreWorkDueAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 多个工作经历
 */
public class WorkListActivity extends BaseActivity implements LoadMoreWorkDueAdapter.IonSlidingViewClickListener {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<StudentWorkDue> dataList = new ArrayList<>();
    private  LoadMoreWorkDueAdapter loadMoreWrapperAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_educationlist);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (null!=extras) {
            String studentWorkDueJson = extras.getString("studentWorkDueJson");
            try {
                JSONArray jsonArray = new JSONArray(studentWorkDueJson);
                Gson gson=new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    StudentWorkDue studentWorkDue = gson.fromJson(jsonArray.getJSONObject(i).toString(), StudentWorkDue.class);
                    dataList.add(studentWorkDue);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//

        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("工作经历");
        tvAdd.setText("添加工作经历");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器

        loadMoreWrapperAdapter = new LoadMoreWorkDueAdapter(dataList, R.layout.education_adapter_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_add:
                Intent intent = new Intent();
                intent.setClass(this, WorkExperienceActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("studentWorkDue", dataList.get(position));
        intent.putExtras(mBundle);
        intent.setClass(WorkListActivity.this,WorkExperienceActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeleteBtnCilck(View view, final  int position) {
        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",dataList.get(position).getId().toString());
        getinstance.post(Constant.DELETWORKDUEBYID, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
                commonDialog.setTitle("正在删除");
            }
            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                commonDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    ShowToastUtils.showToastMsg(WorkListActivity.this, jsonObject.getString("resMessage"));
                    if (jsonObject.getString("rescode").equals("200")) {
                        loadMoreWrapperAdapter.removeData(position);
                    }

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
}
