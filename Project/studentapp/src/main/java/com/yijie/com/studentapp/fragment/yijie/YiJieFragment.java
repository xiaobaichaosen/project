package com.yijie.com.studentapp.fragment.yijie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.WebViewActivity;
import com.yijie.com.studentapp.activity.kndergard.KndergardLoadingAcitivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.bean.KindergartenNeed;
import com.yijie.com.studentapp.bean.SchoolAdress;
import com.yijie.com.studentapp.utils.BannerImageLoader;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

/**
 * 奕杰
 */
public class YiJieFragment extends BaseFragment {


    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.yijieRecycle)
    RecyclerView yijieRecycle;

    private List<KindergartenNeed> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;
    private Unbinder unbinder;
    private int currentPage = 1;
    private int totalPage;
    private  String userId;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_school;
    }

    @Override
    protected void initView() {

        userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        // 设置刷新控件颜色
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorTheme));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
//        recyclerView.setNestedScrollingEnabled(false);

        //设置布局样式
        recyclerView.setLayoutManager(mLayoutManager);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置

        setHasOptionsMenu(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle("奕杰阳光幼教中心");
                } else {
                    collapsingToolbarLayout.setTitle(" ");
                }
            }
        });
        //设置适配器
        LoadMoreYjAdapter loadMoreWrapperAdapter = new LoadMoreYjAdapter(dataList, R.layout.school_adapter_item_content);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        final List<SchoolAdress> images = new ArrayList<>();
        SchoolAdress schoolAdress1 = new SchoolAdress();
        schoolAdress1.setName("http://pic9.photophoto.cn/20081128/0033033999061521_b.jpg");
        schoolAdress1.setDetailAdress("http://sh.qihoo.com/9e397f1e2780666e2?refer_scene=0&scene=1&sign=360_46b9a0ef&uid=7fe554c4667eb2e598f5b3fd82b95830");
        images.add(schoolAdress1);
        SchoolAdress schoolAdress2 = new SchoolAdress();
        schoolAdress2.setName("http://pic37.nipic.com/20140106/7487939_080505278000_2.jpg");
        schoolAdress2.setDetailAdress("http://sh.qihoo.com/979ab50009232a686?refer_scene=0&scene=1&sign=360_46b9a0ef&uid=7fe554c4667eb2e598f5b3fd82b95830");
        images.add(schoolAdress2);
        SchoolAdress schoolAdress3 = new SchoolAdress();
        schoolAdress3.setName("http://img1.3lian.com/2015/a1/32/d/1.jpg");
        schoolAdress3.setDetailAdress("http://sh.qihoo.com/93c664e772934f449?refer_scene=0&scene=1&sign=360_46b9a0ef&uid=7fe554c4667eb2e598f5b3fd82b95830");
        images.add(schoolAdress3);
        SchoolAdress schoolAdress4 = new SchoolAdress();
        schoolAdress4.setName("http://pic1.win4000.com/wallpaper/a/548e4bb13ef96.jpg");
        schoolAdress4.setDetailAdress("http://sh.qihoo.com/9bed477c59d9c61d7?refer_scene=0&scene=1&sign=360_46b9a0ef&uid=7fe554c4667eb2e598f5b3fd82b95830");
        images.add(schoolAdress4);
        SchoolAdress schoolAdress5 = new SchoolAdress();
        schoolAdress5.setName("http://img1.3lian.com/2015/a1/143/d/150.jpg");
        schoolAdress5.setDetailAdress("http://a1.att.hudong.com/45/78/300245751203132333787346944_950.jpg");
        images.add(schoolAdress5);

        //设置图片加载器
        banner.setImageLoader(new BannerImageLoader());  //设置图片集合
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("url", images.get(position).getDetailAdress());
                intent.putExtra("user", "新闻消息");
                intent.setClass(mActivity, WebViewActivity.class);
                startActivity(intent);
            }
        });
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.start();

        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreYjAdapter.OnItemClickListener(

                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
          //添加到浏览足迹
          addFootBrow(dataList.get(position).getId()+"",userId,dataList.get(position).getKinderId()+"");
          Intent intent = new Intent();
          intent.putExtra("kinderId",dataList.get(position).getKinderId()+"");
          intent.putExtra("kinderNeedId",dataList.get(position).getId()+"");
          intent.putExtra("status",dataList.get(position).getStatus()+"");
          intent.setClass(mActivity, KndergardLoadingAcitivity.class);
          startActivity(intent);

               }
                               }
        );


        // 设置下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新数据
                dataList.clear();
                currentPage = 1;
                getData();


                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
                // 延时1s关闭下拉刷新
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 1000);
            }
        });

//加载更多监听
        loadMoreWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);

                if (dataList.size() < totalPage) {
                    // 模拟获取网络数据，延时1s
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                                }
                            });
                        }
                    }, 1000);
                } else {
                    // 显示加载到底的提示
                    loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                }
            }
        });
    }

    @Override
    public void onResume() {
        isPrepared = true;

        initData();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        dataList.clear();
        currentPage = 1;
        getData();
    }


    /**
     * 获取正在招聘的园所列表
     */
    public void getData() {
        String schoolPracticeId = (String) SharedPreferencesUtils.getParam(mActivity, "schoolPracticeId", "");
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
        Map map = new HashMap();
        map.put("schoolPracticeId", schoolPracticeId);
        map.put("studentUserId", userId);
        map.put("pageStart", currentPage + "");
        instance.post(Constant.SELECTBEINGRECRUITLIST, map, new BaseCallback<String>() {

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
                currentPage++;
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (data != null) {
                        Gson gson = new Gson();
                        totalPage = data.getInt("total");
                        for (int i = 0; i < data.getJSONArray("list").length(); i++) {
                            KindergartenNeed kindergartenNeed = gson.fromJson(data.getJSONArray("list").getJSONObject(i).toString(), KindergartenNeed.class);
                            dataList.add(kindergartenNeed);
                        }
                    }

                    if (dataList.size() == totalPage) {
                        // 显示加载到底的提示
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    loadMoreWrapper.notifyDataSetChanged();

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


    /**
     * 添加浏览足迹
     */
    public void addFootBrow(String kinderNeedId,String studentUserId,String kinderId) {
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
        Map map = new HashMap();
        map.put("kinderNeedId", kinderNeedId);
        map.put("studentUserId", studentUserId );
        map.put("kinderId", kinderId);

        instance.post(Constant.STUDENTBROWSEFOOTMARK, map, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {

            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onSuccess(Response response, String s) {
                LogUtil.e(s);

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
