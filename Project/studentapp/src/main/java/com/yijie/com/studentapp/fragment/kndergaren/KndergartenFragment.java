package com.yijie.com.studentapp.fragment.kndergaren;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.PushAccessActivity;
import com.yijie.com.studentapp.activity.PushAccessDetailActivity;
import com.yijie.com.studentapp.activity.SchoolSignActivity;
import com.yijie.com.studentapp.activity.kndergard.KndergardAcitivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.base.baseadapter.DividerItemDecoration;
import com.yijie.com.studentapp.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.studentapp.bean.SchoolAdress;
import com.yijie.com.studentapp.bean.StudentuserKinderneed;
import com.yijie.com.studentapp.fragment.yijie.StudentBean;
import com.yijie.com.studentapp.utils.BannerImageLoader;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class KndergartenFragment extends BaseFragment {


    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.head_layout)
    RelativeLayout headLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.horsrollRecycle)
    RecyclerView horsrollRecycle;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.root_layout)
    CoordinatorLayout rootLayout;
    @BindView(R.id.loading)
    LinearLayout loading;
    @BindView(R.id.tv_kindName)
    TextView tvKindName;
    @BindView(R.id.ll_first)
    LinearLayout llFirst;
    @BindView(R.id.ll_second)
    LinearLayout llSecond;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_kindNameSplsh)
    TextView tvKindNameSplsh;
    @BindView(R.id.tv_splshcontent)
    TextView tvSplshcontent;
    @BindView(R.id.tv_access)
    TextView tvAccess;
    @BindView(R.id.tv_kindDetail)
    TextView tvKindDetail;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.view_first)
    View viewFirst;
    @BindView(R.id.ll_access)
    LinearLayout llAccess;

    //往前合作
    private List<StudentuserKinderneed> dataList = new ArrayList<>();
    private ArrayList<StudentBean> horList = new ArrayList<>();
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private LoadMoreWrapper loadMoreWrapper;
    private LoadMoreWrapper loadMoreLoadingWrapper;
    private String userId;
    private boolean signInFlag;
    private boolean evaluateFlag;
    private int kinderId;
    private int currentPage = 1;
    private String kindName;
    private int totalPage;
    private String kindAddress;
    private String kindDetailAddress;
    private String headPic;

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

    @OnClick(R.id.tv_access)
    public void onViewClicked() {
        Intent intent = new Intent();
        //评价
        intent.putExtra("kinderId", kinderId);
        intent.putExtra("kindeName",kindName);
        intent.putExtra("headPic",headPic);
        intent.putExtra("kindAddress",kindAddress);
        intent.putExtra("kindDetailAddress",kindDetailAddress);
        intent.setClass(mActivity, PushAccessActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_more)
    public void onClicked() {
        Intent intent = new Intent();
        intent.putExtra("kinderId",kinderId);
        intent.setClass(mActivity, KndergardAcitivity.class);
        startActivity(intent);
    }

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(View view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kendergard;
    }

    @Override
    protected void initView() {
        userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.swipRefrsh));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(mLayoutManager);

        //往前合作的园所
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);////设置展开后标题的位置

        setHasOptionsMenu(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -headLayout.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle(kindName);
                } else {
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });


        //横向设置适配器
        LoadMoreYijieHorAdapter loadMoreYijieHorAdapter = new LoadMoreYijieHorAdapter(horList, R.layout.yijie_horsrcoll_item);
        horsrollRecycle.setAdapter(loadMoreYijieHorAdapter);
        loadMoreYijieHorAdapter.setOnItemClickListener(new LoadMoreYijieHorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                if (position == 0) {
                    intent.putExtra("signInFlag", signInFlag);
                    intent.putExtra("kindeName",kindName);
                    intent.setClass(mActivity, SchoolSignActivity.class);
                    mActivity.startActivity(intent);
                } else if (position == 1) {
                    //评价
                    intent.putExtra("kinderId", kinderId);
                    intent.putExtra("kindeName",kindName);
                    intent.putExtra("headPic",headPic);
                    intent.putExtra("kindAddress",kindAddress);
                    intent.putExtra("kindDetailAddress",kindDetailAddress);
                    if (evaluateFlag) {
                        intent.setClass(mActivity, PushAccessDetailActivity.class);
                    } else {
                        intent.setClass(mActivity, PushAccessActivity.class);
                    }

//                    intent.setClass(mActivity, CalenderActivity.class);
                    mActivity.startActivity(intent);
                }
            }
        });

        LoadMoreKndergrtenAdapter loadMoreWrapperAdapter = new LoadMoreKndergrtenAdapter(dataList, mActivity, R.layout.kendergard_adapter_item);
        loadMoreWrapper = new LoadMoreWrapper(loadMoreWrapperAdapter);
        recyclerView.setAdapter(loadMoreWrapper);
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_ARROW);
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMoreKndergrtenAdapter.OnItemClickListener() {
                                                          @Override
                                                          public void onItemClick(View view, int position) {
                                                              Intent intent = new Intent();
                                                              intent.putExtra("kinderId", dataList.get(position).getKinderId());
                                                              intent.setClass(mActivity, KndergardAcitivity.class);
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


//往期合作园所加载更多监听
        loadMoreWrapper.setOnClickListener(new LoadMoreWrapper.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        horList.clear();
        dataList.clear();
        currentPage = 1;
        showDiff();
    }

    /**
     * 获取是否匹配成功展示不同页面
     */
    private void showDiff() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        final ArrayList<File> files = new ArrayList<File>();
        String userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        stringStringHashMap.put("studentUserId", userId);
        HttpUtils.getinstance(mActivity).post(Constant.QUERYISMATCHKINDER, stringStringHashMap, new BaseCallback<String>() {
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
                    boolean data = jsonObject.getBoolean("data");
                    if (data) {
                        llFirst.setVisibility(View.VISIBLE);
                        llSecond.setVisibility(View.GONE);
                        matchSuccess();
                        horList.add(new StudentBean(R.mipmap.head, "考勤签到"));
                        horList.add(new StudentBean(R.mipmap.head, "评价"));
                        horList.add(new StudentBean(R.mipmap.head, "通知"));
                        horList.add(new StudentBean(R.mipmap.head, "文档"));
                        horList.add(new StudentBean(R.mipmap.head, "问题反馈"));
                        horList.add(new StudentBean(R.mipmap.head, "分享"));
                        horList.add(new StudentBean(R.mipmap.head, "实习回访"));
                        horList.add(new StudentBean(R.mipmap.head, "更多"));

                        GridLayoutManager linearLayoutManager = new GridLayoutManager(mActivity, 4);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        horsrollRecycle.setLayoutManager(linearLayoutManager);

                    } else {
                        back.setVisibility(View.GONE);
                        title.setText("往期合作园所");
                        llSecond.setVisibility(View.VISIBLE);
                        llFirst.setVisibility(View.GONE);
                        // 获取合作园所数据
                        getData();
                    }

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
     * 获取正在合作园所的列表
     */
    private void getData() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        String userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        stringStringHashMap.put("pageStart", currentPage + "");
        stringStringHashMap.put("studentUserId", userId);
        HttpUtils.getinstance(mActivity).post(Constant.SELECTCOOPERATEKINDLIST, stringStringHashMap, new BaseCallback<String>() {
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
                LogUtil.e("-----------" + s);
                currentPage++;
                JSONObject jsonObject = null;
                try {
                    Gson gson = new Gson();
                    jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    totalPage = data.getInt("total");
                    JSONArray list = data.getJSONArray("list");
                    if (list.length() > 0) {
                        for (int i = 0; i < list.length(); i++) {
                            StudentuserKinderneed studentuserKinderneed = gson.fromJson(list.getJSONObject(i).toString(), StudentuserKinderneed.class);
                            dataList.add(studentuserKinderneed);
                        }
                        if (dataList.size() == totalPage) {
                            // 显示加载到底的提示
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                        }
                        loadMoreWrapper.notifyDataSetChanged();
                    }

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
     * 匹配成功的数据
     */
    private void matchSuccess() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        HttpUtils.getinstance(mActivity).post(Constant.GETKINDERMATCHHOMEPAGE, stringStringHashMap, new BaseCallback<String>() {
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
                    JSONObject data = jsonObject.getJSONObject("data");
                    kindName = data.getJSONObject("studentKinderMatchSuccess").getString("kindName");
                    kinderId = data.getJSONObject("studentKinderMatchSuccess").getInt("kinderId");
                    kindAddress=data.getJSONObject("studentKinderMatchSuccess").getString("kindAddress");
                    kindDetailAddress=data.getJSONObject("studentKinderMatchSuccess").getString("kindDetailAddress");
                    headPic=data.getJSONObject("studentKinderMatchSuccess").getString("headPic");
                    signInFlag = data.getBoolean("signInFlag");
                    evaluateFlag = data.getBoolean("evaluateFlag");
                    String kinderSample = data.getString("kinderSample");
                    tvKindDetail.setText(kinderSample);
                    tvKindName.setText(kindName);
                    tvKindNameSplsh.setText(kindName + "欢迎您");
                    tvSplshcontent.setText("您已抵达" + kindName + ",");

                    String environment = data.getString("environment");
                    List<SchoolAdress> images = new ArrayList<>();
                    if (!TextUtils.isEmpty(environment)){
                        //加载网络图片
                        for (int i = 0; i <environment.split(";").length ; i++) {
                            SchoolAdress schoolAdress=new SchoolAdress();
                            //TODO 36要改成kinderId,替换掉createby
                            String picUrl=  Constant.KINDERPICUIL+kinderId+"/environment/"+environment.split(";")[i];
                            schoolAdress.setName(picUrl);
                            images.add(schoolAdress);
                        }
                    }
                    //设置图片加载器
                    banner.setImageLoader(new BannerImageLoader());  //设置图片集合
                    banner.setImages(images);
                    banner.setIndicatorGravity(BannerConfig.RIGHT);
                    banner.start();




                    if (evaluateFlag) {
                        llAccess.setVisibility(View.GONE);
                        viewFirst.setVisibility(View.GONE);
                    } else {
                        llAccess.setVisibility(View.VISIBLE);
                        viewFirst.setVisibility(View.VISIBLE);
                    }
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

}
