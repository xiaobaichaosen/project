package com.yijie.com.yijie.activity.kendergard;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.imagepicker.bean.ImageItem;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.adapter.LoadMorePopuAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.fragment.CompayKinderFragment;
import com.yijie.com.yijie.fragment.CompayedKinderFragment;
import com.yijie.com.yijie.fragment.KinderNeedFragment;
import com.yijie.com.yijie.fragment.NewKinderFragment;
import com.yijie.com.yijie.utils.AnimationUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by 奕杰平台 on 2018/6/28.
 * 园所需求，正在招聘，合作，往期合作的园所页面
 */

public class KendergardStatusActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;


    public String schoolName;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private NewKinderFragment newKinderFragment;
    private KinderNeedFragment kinderNeedFragment;
    private CompayedKinderFragment compayedKinderFragment;
    private CompayKinderFragment compayKinderFragment;
    private ArrayList<String > statusList=new ArrayList<>();
    private LoadMorePopuAdapter loadMoreWrapperAdapter;
    private int fromYDelta;
    private   PopupWindow mPopupWindow;
    private boolean isPopWindowShowing=false;
    private   TextView textView;
    private int current;
    private TextView tvName;
    public String textString;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kendstatus);

    }


    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setVisibility(View.GONE);
        tvRecommend.setVisibility(View.GONE);
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setImageResource(R.mipmap.search);
        selImageList = new ArrayList<>();
        //添加状态
        statusList.add("全部");
        statusList.add("待审核");
        statusList.add("已通过");
        statusList.add("未通过");
        statusList.add("注册中");
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("园所需求"));
        View view = tab_icon("新园所", R.mipmap.arrow_down);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view));
        tabLayout.addTab(tabLayout.newTab().setText("合作园所"));
        tabLayout.addTab(tabLayout.newTab().setText("往期园所"));
        TabLayout.Tab tabAt = tabLayout.getTabAt(1);
        textString="新园所";
        textView = view.findViewById(R.id.tv_icon);
        tvName = view.findViewById(R.id.tv_title);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current==1){
                    if (!isPopWindowShowing){
                        showPopupWindow();
                    }else {
                        mPopupWindow.dismiss();
                    }
                }

            }
        });
        initKinderNeedFragment();
        //绑定tab点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    if (isPopWindowShowing){
                        mPopupWindow.dismiss();
                    }
                    initKinderNeedFragment();
                    tvName.setTextColor(Color.parseColor("#737373"));
                    current=0;
                } else if (position == 1) {
                    initNewKinderFragment();
                    tvName.setTextColor(getResources().getColor(R.color.black));
                    current=1;
                } else if (position == 2) {
                    current=2;
                    initCompayFragment();
                    if (isPopWindowShowing){
                        mPopupWindow.dismiss();
                    }
                    tvName.setTextColor(Color.parseColor("#737373"));
                } else if (position == 3) {
                    current=3;
                    initCompayedFragment();
                    if (isPopWindowShowing){
                        mPopupWindow.dismiss();
                    }
                    tvName.setTextColor(Color.parseColor("#737373"));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //园所需求
    private void initKinderNeedFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (kinderNeedFragment == null) {
            kinderNeedFragment = new KinderNeedFragment();
        }
        transaction.replace(R.id.main_frame_layout, kinderNeedFragment);
        //提交事务
        transaction.commit();
    }

    //新园所
    private void initNewKinderFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (newKinderFragment == null) {
            newKinderFragment = new NewKinderFragment();
        }
        transaction.replace(R.id.main_frame_layout, newKinderFragment);
        transaction.commit();
    }

    //正在合作园所
    private void initCompayFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (compayKinderFragment == null) {
            compayKinderFragment = new CompayKinderFragment();
        }
        transaction.replace(R.id.main_frame_layout, compayKinderFragment);
        transaction.commit();
    }

    //往期合作园所
    private void initCompayedFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (compayedKinderFragment == null) {
            compayedKinderFragment = new CompayedKinderFragment();
        }
        transaction.replace(R.id.main_frame_layout, compayedKinderFragment);
        transaction.commit();
    }

    /**
     * 设置自定义的tabitem
     *
     * @param name
     * @param iconID
     * @return
     */
    private View tab_icon(String name, int iconID) {
        View newtab = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView textTitle = (TextView) newtab.findViewById(R.id.tv_title);
        textTitle.setText(name);
        TextView textIcon = (TextView) newtab.findViewById(R.id.tv_icon);
        textIcon.setBackgroundResource(iconID);
        return newtab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }




    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

        }
    }

    private void showPopupWindow() {
        //andorid 7.0如果内容超过屏幕showasdropdown 将会失效需要适配
        final View contentView = LayoutInflater.from(this).inflate(R.layout.selectlist2, null);
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,5);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        loadMoreWrapperAdapter = new LoadMorePopuAdapter(statusList, R.layout.adapter_popu_item);
        recyclerView.setAdapter(loadMoreWrapperAdapter);

        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
//        mPopupWindow.setOutsideTouchable(false);
//        mPopupWindow.setFocusable(false);
        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        fromYDelta = -contentHeight - 50;
        ViewUtils.showAsDropDown(mPopupWindow,textView,0,0);
//        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(this, fromYDelta));
//        tvDown.startAnimation(AnimationUtil.createRotateupAnimation());
        loadMoreWrapperAdapter.setOnItemClickListener(new LoadMorePopuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String statusString = statusList.get(position);
                TabLayout.Tab tabAt = tabLayout.getTabAt(1);
               TextView textView= tabAt.getCustomView().findViewById(R.id.tv_title);
               if (statusString.equals("全部")){
                   textView.setText("新园所");
               }else{
                   textView.setText(statusString);
               }
               textString=statusString;
                EventBus.getDefault().post(statusString);
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
}
