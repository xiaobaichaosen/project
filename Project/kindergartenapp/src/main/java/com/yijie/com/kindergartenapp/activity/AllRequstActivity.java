package com.yijie.com.kindergartenapp.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 招聘需求记录
 */
public class AllRequstActivity extends BaseActivity {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private StatusLayoutManager statusLayoutManager;
    private List<KindergartenNeed> dataList = new ArrayList<>();
    private int currentPage=1;
    private int totalPage;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_allrequst);
    }

    @Override
    public void init() {
        statusLayoutManager = new StatusLayoutManager.Builder(swipeRefreshLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
//                        getData();

                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
//                        getData();

                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
    }

    /**
     * 获取需求记录
     */
//    private void getData() {
//        String userId = (String) SharedPreferencesUtils.getParam(this, "userId", "");
//        HttpUtils getinstance = HttpUtils.getinstance(this);
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("pageStart", currentPage + "");
//        stringStringHashMap.put("pageSize", 10 + "");
//        stringStringHashMap.put("kinderId", userId);
//
//        getinstance.post(Constant.SELECTDEMANDLIST, stringStringHashMap, new BaseCallback<String>() {
//            @Override
//            public void onRequestBefore() {
//                commonDialog.show();
//            }
//
//            @Override
//            public void onFailure(Request request, Exception e) {
////                loading.showError();
//                commonDialog.dismiss();
////                statusLayoutManager.showErrorLayout();
//            }
//
//            @Override
//            public void onSuccess(Response response, String o) {
//                LogUtil.e(o);
//                currentPage++;
//                Gson gson = new Gson();
//                try {
//                    JSONObject jsonObject = new JSONObject(o);
//                    JSONObject data = jsonObject.getJSONObject("data");
//                    int demandTotal = data.getInt("demandTotal");
//                    int enrollTotal = data.getInt("enrollTotal");
//                    int kinderChoiceNum = data.getInt("kinderChoiceNum");
//                    totalPage = Integer.parseInt(data.getString("total"));
//                    tvCount.setText("需求总和:" + demandTotal + " " + "已报:" + enrollTotal + " " + "接收:" + kinderChoiceNum);
//                    JSONArray list = data.getJSONArray("list");
//                    JSONArray proDetailList = data.getJSONArray("proDetailList");
//
//
//                    for (int i = 0; i < list.length(); i++) {
//                        JSONObject jsonObject1 = list.getJSONObject(i);
//                        KindergartenNeed schoolSimple = gson.fromJson(jsonObject1.toString(), KindergartenNeed.class);
//                        dataList.add(schoolSimple);
//                    }
//                    for (int i = 0; i < proDetailList.length(); i++) {
//                        JSONObject jsonObject1 = proDetailList.getJSONObject(i);
//                        SchoolPractice schoolPractice = gson.fromJson(jsonObject1.toString(), SchoolPractice.class);
//                        schoolPracticeList.add(schoolPractice);
//                    }
//                    if (dataList.size() < 10) {
//                        // 显示加载到底的提示
//                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
//                    }
//                    loadMoreWrapper.notifyDataSetChanged();
////                    if (dataList.size() == 0) {
////                        statusLayoutManager.showEmptyLayout();
////                    } else {
////                        statusLayoutManager.showSuccessLayout();
////                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                commonDialog.dismiss();
//
//            }
//
//            @Override
//            public void onError(Response response, int errorCode, Exception e) {
//                commonDialog.dismiss();
////                statusLayoutManager.showErrorLayout();
//            }
//        });
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
