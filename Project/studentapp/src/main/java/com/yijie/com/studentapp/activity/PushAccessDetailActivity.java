package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.LoadMoreMe2KendAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentEvaluate;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 查看我对园所的评价
 */
public class PushAccessDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_addMore)
    TextView tvAddMore;

    private RecyclerView.LayoutManager mLayoutManager;
    private LoadMoreMe2KendAdapter mAdapter;

    private List<List<StudentEvaluate>> mList = new ArrayList<>();
    private int kinderId;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pushaccessdetail);
    }

    @Override
    public void init() {
        kinderId = getIntent().getIntExtra("kinderId",0);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("我对园所的评价");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("园评");
        actionItem.setVisibility(View.GONE);
        getAcessDetail();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LoadMoreMe2KendAdapter(mList, this, R.layout.adapter_kend2me_item);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 查询学生对园所评价的详情
     */
    public void getAcessDetail() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        final ArrayList<File> files = new ArrayList<File>();
        String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
        stringStringHashMap.put("studentUserId", userId);
        HttpUtils.getinstance(this).post(Constant.SELECTSTUDENTEVALUATE, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    Gson gson = new Gson();
                    String rescode = jsonObject.getString("rescode");
                    String resMessage = jsonObject.getString("resMessage");
                    JSONArray data = jsonObject.getJSONArray("data");
                    if (data.length() > 0) {
                        for (int i = 0; i < data.length(); i++) {
                            JSONArray jsonArray = data.getJSONArray(i);
                            ArrayList<StudentEvaluate> studentEvaluates = new ArrayList<>();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                StudentEvaluate studentEvaluate = gson.fromJson(jsonArray.getJSONObject(j).toString(), StudentEvaluate.class);
                                studentEvaluates.add(studentEvaluate);
                            }
                            mList.add(studentEvaluates);
                        }
                        mAdapter.notifyDataSetChanged();
                    }

//                    ShowToastUtils.showToastMsg(PushAccessActivity.this, resMessage);
//                    if (rescode.equals("200")) {
//                        finish();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

    @OnClick({R.id.back, R.id.tv_addMore, R.id.tv_recommend})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_addMore:
                //追加评论
                intent.putExtra("kinderId",kinderId);
                intent.putExtra("isAddAccess",true);
                intent.setClass(PushAccessDetailActivity.this, PushAccessActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_recommend:
                //园所对我的评价
                intent.setClass(PushAccessDetailActivity.this, Kend2MeAccActivity.class);
                startActivity(intent);
                break;

        }
    }
}
