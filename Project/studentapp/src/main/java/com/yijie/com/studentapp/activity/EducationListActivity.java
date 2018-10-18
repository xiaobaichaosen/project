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
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.slipdelect.LoadMoreEducationAdapter;

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
 * 多个教育背景
 */
public class EducationListActivity extends BaseActivity implements LoadMoreEducationAdapter.IonSlidingViewClickListener {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<StudentEducation> dataList = new ArrayList<>();
    LoadMoreEducationAdapter loadMoreWrapperAdapter;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_educationlist);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (null!=extras) {
            String studentEducationJson = extras.getString("studentEducationJson");
            try {
                JSONArray jsonArray = new JSONArray(studentEducationJson);
                Gson gson=new Gson();
                for (int i = 0; i < jsonArray.length(); i++) {
                    StudentEducation studentEducation = gson.fromJson(jsonArray.getJSONObject(i).toString(), StudentEducation.class);
                    dataList.add(studentEducation);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(studentEducationJson);

            } catch (JSONException e) {
                e.printStackTrace();
            }

//

        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("教育背景");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器

        loadMoreWrapperAdapter = new LoadMoreEducationAdapter(dataList, R.layout.education_adapter_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);

//        mAdapter = new LoadMoreEducationAdapter(this);
//        recyclerView.setAdapter(mAdapter);
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
                intent.setClass(this, EducationBackgroundActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }



    @Override
    public void onItemClick(View view, int position) {
        Intent intent=new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("studentEducation", dataList.get(position));
        intent.putExtras(mBundle);
        intent.setClass(EducationListActivity.this,EducationBackgroundActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeleteBtnCilck(View view, final int position) {

        HttpUtils getinstance = HttpUtils.getinstance(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id",dataList.get(position).getId().toString());
        getinstance.post(Constant.DELETEEDUCATIONBYID, stringStringHashMap, new BaseCallback<String>() {
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
                    ShowToastUtils.showToastMsg(EducationListActivity.this, jsonObject.getString("resMessage"));
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
            }
        });
    }
}
