package com.yijie.com.yijie.activity.kendergard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ProjectActivity;
import com.yijie.com.yijie.activity.ReNameSchoolAcitivty;
import com.yijie.com.yijie.activity.TempActivity;
import com.yijie.com.yijie.activity.school.SchoolActivity;
import com.yijie.com.yijie.activity.school.StudentBean;
import com.yijie.com.yijie.activity.school.adapter.SchoolContactAdapterRecyclerView;
import com.yijie.com.yijie.activity.school.adapter.SchoolMemeryAdapterRecyclerView;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.base.baseadapter.LoadMoreWrapper;
import com.yijie.com.yijie.bean.bean.KindergartenNeed;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.fragment.CompayKinderFragment;
import com.yijie.com.yijie.fragment.CompayedKinderFragment;
import com.yijie.com.yijie.fragment.KinderNeedFragment;
import com.yijie.com.yijie.fragment.NewKinderFragment;
import com.yijie.com.yijie.fragment.SchoolDetailFragment;
import com.yijie.com.yijie.fragment.SchoolMemeryFragment;
import com.yijie.com.yijie.fragment.kndergaren.KndergartenFragment;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreCompanyKndergrtenAdapter;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreKndergrtenAdapter;
import com.yijie.com.yijie.fragment.kndergaren.LoadMoreLoadingKndergrtenAdapter;
import com.yijie.com.yijie.fragment.school.ProjectListFragment;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.CircleImageView;
import com.yijie.com.yijie.view.CommomDialog;
import com.yijie.com.yijie.view.LoadingLayout;

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
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

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

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private int maxImgCount = 1;               //允许选择图片最大数
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.iv_schoolPic)
    CircleImageView ivSchoolPic;
    @BindView(R.id.tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_schoolName_edit)
    TextView tvSchoolNameEdit;



    //用来判断   actionItem是哪个图片，来触发点击事件 1==项目，2==备忘录
    private int current;
    public String schoolId;
    //是编辑还是完成
    public boolean changeText;
    //学校详情中联系人的编辑按钮
    TextView tvContactEdit;
    public String schoolName;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private NewKinderFragment newKinderFragment;
    private KinderNeedFragment kinderNeedFragment;
    private CompayedKinderFragment compayedKinderFragment;
    private CompayKinderFragment compayKinderFragment;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_temp);
    }

    @Override
    public void init() {

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tvRecommend.setText("编辑");
        selImageList = new ArrayList<>();
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("园所需求"));
        tabLayout.addTab(tabLayout.newTab().setText("新园所"));
        tabLayout.addTab(tabLayout.newTab().setText("合作园所"));
        tabLayout.addTab(tabLayout.newTab().setText("往期合作园所"));
        //绑定tab点击事件
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    initKinderNeedFragment();
                } else if (position == 1) {
                    initNewKinderFragment();
                } else if (position == 2) {
                    initCompayFragment();

                } else if (position == 2) {
                initCompayedFragment();

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
            kinderNeedFragment= new KinderNeedFragment();
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



}
