package com.yijie.com.kindergartenapp.fragment.yijie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.BuildConfig;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.RequestDetailActivity;
import com.yijie.com.kindergartenapp.activity.ResumnStatusActivity;
import com.yijie.com.kindergartenapp.activity.WebViewActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.kindergartenapp.base.baseadapter.EndlessRecyclerOnScrollListener;
import com.yijie.com.kindergartenapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.utils.BannerImageLoader;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class YiJieFragment extends BaseFragment {


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    //    @BindView(R.id.loading)
//    LoadingLayout loading;

    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
//    @BindView(R.id.backdrop)
//    ImageView backdrop;
    @BindView(R.id.horsrollRecycle)
    RecyclerView horsrollRecycle;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.banner)
    Banner banner;
    private List<SchoolPractice> schoolPracticeList = new ArrayList<>();
    private List<KindergartenNeed> dataList = new ArrayList<>();
    private LoadMoreWrapper loadMoreWrapper;
    private int currentPage = 1;
    private int totalPage;
    //所有需求总数
    private Integer demandTotal;
    //所有接收总数
    private Integer countReceive;
    // 园所报名总数
    private Integer enrollTotal;
    private ArrayList<CommonBean> horList = new ArrayList<>();
    private StatusLayoutManager statusLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yijie;
    }

    @Override
    protected void initView() {
        statusLayoutManager = new StatusLayoutManager.Builder(rootLayout)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
                        getData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        dataList.clear();
                        currentPage = 1;
                        getData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        horList.add(new CommonBean(R.mipmap.head, "来学生啦"));
        horList.add(new CommonBean(R.mipmap.head, "在岗学生"));
        horList.add(new CommonBean(R.mipmap.head, "考勤"));
        horList.add(new CommonBean(R.mipmap.head, "问题提交"));
        horList.add(new CommonBean(R.mipmap.head, "评价"));
        horList.add(new CommonBean(R.mipmap.head, "学生分享"));
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
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


        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.swipRefrsh));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(mLayoutManager);
        //设置适配器
        LoadMoreYijieAdapter loadMoreWrapperAdapter = new LoadMoreYijieAdapter(schoolPracticeList, dataList, R.layout.yijie_adapter_item);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horsrollRecycle.setLayoutManager(linearLayoutManager);
        //设置适配器
        LoadMoreYijieHorAdapter loadMoreYijieHorAdapter = new LoadMoreYijieHorAdapter(horList, R.layout.yijie_horsrcoll_item);
        horsrollRecycle.setAdapter(loadMoreYijieHorAdapter);
        //提完需求就可以查看详细了
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreYijieAdapter.OnItemClickListener(
//                tempMealCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempMealCommonBean");
                                                      ) {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              if (null != dataList.get(position).getDemandNum()) {
                                                                  Intent intent = new Intent();
                                                                  intent.putExtra("id", dataList.get(position).getId());
                                                                  intent.putExtra("projectName", dataList.get(position).getProjectName());
                                                                  intent.putExtra("schoolName", dataList.get(position).getSchoolName());
                                                                  intent.putExtra("schoolId", dataList.get(position).getSchoolId());
                                                                  intent.putExtra("kinderId", dataList.get(position).getKinderId());
                                                                  intent.putExtra("projectId", dataList.get(position).getSchoolPracticeId());
                                                                  Bundle mBundle = new Bundle();
                                                                  mBundle.putSerializable("SchoolPractice", schoolPracticeList.get(position));
                                                                  intent.putExtras(mBundle);
                                                                  intent.setClass(mActivity, RequestDetailActivity.class);
                                                                  startActivity(intent);
                                                              }


//
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

        // 设置加载更多监听
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
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
        final List<SchoolAdress> images=new ArrayList<>();
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
                Intent intent=new Intent();
                intent.putExtra("url",images.get(position).getDetailAdress());
                intent.putExtra("user","新闻消息");
                intent.setClass(mActivity, WebViewActivity.class);
                startActivity(intent);
            }
        });
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        banner.start();


    }

    @Override
    public void onResume() {
        isPrepared = true;
        dataList.clear();
        currentPage = 1;
        initData();
        super.onResume();
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        getData();
        //开始轮播
        banner.startAutoPlay();

    }

    @Override
    protected void onInvisible() {
        //结束轮播
        if (null!=banner){
            banner.stopAutoPlay();
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_select:
                intent.putExtra("title", "已选");
                intent.setClass(mActivity, ResumnStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.action_noSelect:
                intent.putExtra("title", "待选");
                intent.setClass(mActivity, ResumnStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.action_noPass:
                intent.putExtra("title", "放弃");
                intent.setClass(mActivity, ResumnStatusActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取需求列表
     */
    private void getData() {
       String userId= (String) SharedPreferencesUtils.getParam(mActivity, "userId", "");
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("pageSize", 10 + "");
        stringStringHashMap.put("kinderId", userId);

        getinstance.post(Constant.SELECTDEMANDLIST, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                loading.showError();
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                currentPage++;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject data = jsonObject.getJSONObject("data");
                    int demandTotal = data.getInt("demandTotal");
                    int enrollTotal = data.getInt("enrollTotal");
                    int kinderChoiceNum = data.getInt("kinderChoiceNum");
                    totalPage = Integer.parseInt(data.getString("total"));
                    tvCount.setText("需求总和:" + demandTotal + " " + "已报:" + enrollTotal + " " + "接收:" + kinderChoiceNum);
                    JSONArray list = data.getJSONArray("list");
                    JSONArray proDetailList = data.getJSONArray("proDetailList");


                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);
                        KindergartenNeed schoolSimple = gson.fromJson(jsonObject1.toString(), KindergartenNeed.class);
                        dataList.add(schoolSimple);
                    }
                    for (int i = 0; i < proDetailList.length(); i++) {
                        JSONObject jsonObject1 = proDetailList.getJSONObject(i);
                        SchoolPractice schoolPractice = gson.fromJson(jsonObject1.toString(), SchoolPractice.class);
                        schoolPracticeList.add(schoolPractice);
                    }
                    if (dataList.size() < 10) {
                        // 显示加载到底的提示
                        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    loadMoreWrapper.notifyDataSetChanged();
//                    if (dataList.size() == 0) {
//                        statusLayoutManager.showEmptyLayout();
//                    } else {
//                        statusLayoutManager.showSuccessLayout();
//                    }
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

