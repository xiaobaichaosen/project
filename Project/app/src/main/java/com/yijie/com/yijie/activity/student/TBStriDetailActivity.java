package com.yijie.com.yijie.activity.student;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.student.adapter.GalleryAdapter;
import com.yijie.com.yijie.adapter.CardAdapter;
import com.yijie.com.yijie.adapter.HonorCardAdapter;
import com.yijie.com.yijie.adapter.InfoCardAdapter;
import com.yijie.com.yijie.adapter.LoadMoreEducationAdapter;
import com.yijie.com.yijie.adapter.LoadMoreWorkAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.bean.StudentContact;
import com.yijie.com.yijie.bean.StudentEducation;
import com.yijie.com.yijie.bean.StudentInfo;
import com.yijie.com.yijie.bean.StudentResume;
import com.yijie.com.yijie.bean.StudentResumeDetail;
import com.yijie.com.yijie.bean.StudentWorkDue;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.PhotoActivityForHor;
import com.yijie.com.yijie.view.ScrollChangedScrollView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 待分配学生详情
 */
public class TBStriDetailActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;

    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    @BindView(R.id.tab_ll_personalInformation)
    LinearLayout tabLlPersonalInformation;
    @BindView(R.id.tab_ll_contact)
    LinearLayout tabLlContact;
    @BindView(R.id.tab_ll_ducationBackground)
    LinearLayout tabLlDucationBackground;
    @BindView(R.id.tab_ll_workExprice)
    LinearLayout tabLlWorkExprice;

    @BindView(R.id.tab_ll_elfAssessment)
    LinearLayout tabLlElfAssessment;
    @BindView(R.id.tab_ll_honoraryCertificate)
    LinearLayout tabLlHonoraryCertificate;
    @BindView(R.id.anchor_bodyContainer)
    ScrollChangedScrollView anchorBodyContainer;
    @BindView(R.id.anchor_tagContainer)
    TabLayout anchorTagContainer;
    @BindView(R.id.iv_see)
    ImageView ivSee;

    @BindView(R.id.tv_stuName)
    TextView tvStuName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.tv_nation)
    TextView tvNation;
    @BindView(R.id.tv_place)
    TextView tvPlace;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_cellphone)
    TextView tvCellphone;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.education_recyclerView)
    RecyclerView educationRecyclerView;
    @BindView(R.id.work_recyclerView)
    RecyclerView workRecyclerView;
    @BindView(R.id.tv_expectWorkPlace)
    TextView tvExpectWorkPlace;
    @BindView(R.id.tab_ll_intent)
    LinearLayout tabLlIntent;
    @BindView(R.id.tv_selfEvaluate)
    TextView tvSelfEvaluate;
    @BindView(R.id.tv_selfNb)
    TextView tvSelfNb;
    @BindView(R.id.tab_ll_selfNb)
    LinearLayout tabLlSelfNb;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.tv_urgentContact)
    TextView tvUrgentContact;
    @BindView(R.id.tv_urgentCellphone)
    TextView tvUrgentCellphone;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.recycler_view_honor)
    RecyclerView recyclerViewHonor;

    private GalleryAdapter mAdapter;
    private List<String> mDatas;
    /**
     * 是否是ScrollView主动动作
     * false:是ScrollView主动动作
     * true:是TabLayout 主动动作
     */
    private boolean tagFlag = false;
    StudentResume studentResume;
    /**
     * 用于切换内容模块，相应的改变导航标签，表示当一个所处的位置
     */
    private int lastTagIndex = 0;
    /**
     * 用于在同一个内容模块内滑动，锁定导航标签，防止重复刷新标签
     * true: 锁定
     * false ; 没有锁定
     */
    private boolean content2NavigateFlagInnerLock = false;
    //学生id
    int studentUserId;
    // 头部导航标签
//    private String[] navigationTag = {"个人信息", "联系方式", "教育背景", "工作经历", "相关意向", "自我评价", "特长爱好", "荣誉证书"};
    private List<String> navigationTag=new ArrayList<>();

    private ArrayList<String> infoList = new ArrayList<>();
    private ArrayList<String> honorList = new ArrayList<>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tbstridetail);
    }

    @Override
    public void init() {
        studentUserId = getIntent().getIntExtra("studentUserId", 0);
        boolean isShowMove = getIntent().getBooleanExtra("isShowMove", false);
          if (isShowMove){
              title.setText("学生简历");
          }else{
              title.setText("待分配");
          }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        actionItem.setVisibility(View.INVISIBLE);
        getResumnDetail(studentUserId);

        initListener();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        LinearLayoutManager horlinearLayoutManager = new LinearLayoutManager(this);
        horlinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewHonor.setLayoutManager(horlinearLayoutManager);
        //设置适配器
//        mAdapter = new GalleryAdapter(this, mDatas);
//        mRecyclerView.setAdapter(mAdapter);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        final int width = outMetrics.widthPixels;
        final int height = outMetrics.heightPixels;

    }

    /**
     * 查询简历
     *
     * @param id
     */
    private void getResumnDetail(final  int id) {
        final HttpUtils instance = HttpUtils.getinstance(TBStriDetailActivity.this);

        Map map = new HashMap();
        map.put("studentUserId", id + "");

        instance.post(Constant.SELECTBYSTUDENTUSERID, map, new BaseCallback<String>() {

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
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    StudentResumeDetail studentResumeDetail = gson.fromJson(data.toString(), StudentResumeDetail.class);
                    //个人信息
                    StudentInfo studentInfo = studentResumeDetail.getStudentInfo();
                    if (null!=studentInfo){
                        tvStuName.setText(studentInfo.getStuName());
                        tvSex.setText(studentInfo.getSex());
                        tvHeight.setText(studentInfo.getHeight() + "");
                        tvWeight.setText(Math.round(studentInfo.getWeight()) + "");
                        tvNation.setText(studentInfo.getNation());
                        tvPlace.setText(studentInfo.getPlace());
                        tvAge.setText(studentInfo.getStuAge()+"");
                        tvBirth.setText(studentInfo.getBirth() + "");
                        tvAdress.setText(studentInfo.getAddress());
                        tvIdcard.setText(studentInfo.getIdCard());
                        navigationTag.add("个人信息");
                    }else {
                        tabLlPersonalInformation.setVisibility(View.GONE);
                    }
                    //联系方式
                    StudentContact studentContact = studentResumeDetail.getStudentContact();
                    if (null!=studentContact){
                        tvCellphone.setText(studentContact.getCellphone());
                        tvWechat.setText(studentContact.getWechat());
                        tvQq.setText(studentContact.getQq());
                        tvEmail.setText(studentContact.getEmail());
                        tvUrgentContact.setText(studentContact.getUrgentContact());
                        tvUrgentCellphone.setText(studentContact.getUrgentCellphone());
                        navigationTag.add("联系方式");
                    }else {
                        tabLlContact.setVisibility(View.GONE);
                    }

                    //教育经历
                    List<StudentEducation> studentEducation = studentResumeDetail.getStudentEducation();
                    if (studentEducation.size()>0){
                        LoadMoreEducationAdapter loadMoreEducationAdapter = new LoadMoreEducationAdapter(studentEducation);
                        educationRecyclerView.addItemDecoration(new DividerItemDecoration(TBStriDetailActivity.this, LinearLayoutManager.VERTICAL));
                        LinearLayoutManager educationManager = new LinearLayoutManager(TBStriDetailActivity.this);
                        educationRecyclerView.setLayoutManager(educationManager);
                        educationRecyclerView.setNestedScrollingEnabled(false);
                        educationRecyclerView.setAdapter(loadMoreEducationAdapter);
                        navigationTag.add("教育背景");
                    }else {
                        tabLlDucationBackground.setVisibility(View.GONE);
                    }

                    //工作经历
                    List<StudentWorkDue> studentWorkDue = studentResumeDetail.getStudentWorkDue();
                    if (studentWorkDue.size()>0){
                        LoadMoreWorkAdapter loadMoreWorkAdapter = new LoadMoreWorkAdapter(studentWorkDue);
                        workRecyclerView.addItemDecoration(new DividerItemDecoration(TBStriDetailActivity.this, LinearLayoutManager.VERTICAL));
                        LinearLayoutManager workManager = new LinearLayoutManager(TBStriDetailActivity.this);
                        workRecyclerView.setLayoutManager(workManager);
                        workRecyclerView.setNestedScrollingEnabled(false);
                        workRecyclerView.setAdapter(loadMoreWorkAdapter);
                        navigationTag.add("工作经历");
                    }else {
                        tabLlWorkExprice.setVisibility(View.GONE);
                    }
                    studentResume = studentResumeDetail.getStudentResume();
                    if (null!=studentResume){
                        //相关意向
                        tvExpectWorkPlace.setText(studentResume.getExpectWorkPlace());
                        String expectPartener = studentResume.getExpectPartener();
                        tvCompanionName.setText(expectPartener);
                        if (!TextUtils.isEmpty(studentResume.getExpectWorkPlace())||!TextUtils.isEmpty(expectPartener)){
                            navigationTag.add("相关意向");
                        }else {
                            tabLlIntent.setVisibility(View.GONE);
                        }
                        //自我评价
                        tvSelfEvaluate.setText(studentResume.getSelfEvaluate());
                        if (!TextUtils.isEmpty(studentResume.getSelfEvaluate())){
                            navigationTag.add("自我评价");
                        }else {

                            tabLlElfAssessment.setVisibility(View.GONE);
                        }
                        //特长爱好
                        tvSelfNb.setText(studentResume.getHobby());
                        if (!TextUtils.isEmpty(studentResume.getHobby())){
                            navigationTag.add("特长爱好");
                        }else {
                            tabLlSelfNb.setVisibility(View.GONE);
                        }
                        //荣誉证书
                        //TODO===============
                        String img = studentInfo.getPic();
                        if (!"".equals(img) && null != img) {
                            String[] split = img.split(";");
                            List<String> strings = Arrays.asList(split);
                            for (int i = 0; i < strings.size(); i++) {
                                infoList.add(strings.get(i));
                            }

                            mRecyclerView.setAdapter(new InfoCardAdapter(TBStriDetailActivity.this, infoList, id + "", "info", "info"));
                        }
                        if (!TextUtils.isEmpty(studentResume.getCertificate())) {
                            String img1 = studentResume.getCertificate();
                            if (!"".equals(img1) && null != img1) {
                                String[] split = img1.split(";");
                                List<String> strings = Arrays.asList(split);
                                for (int i = 0; i < strings.size(); i++) {
                                    honorList.add(strings.get(i));
                                }
                            }
                            recyclerViewHonor.setAdapter(new InfoCardAdapter(TBStriDetailActivity.this, honorList, id + "", "certificate", "certificate"));
                            navigationTag.add("荣誉证书");
                        }else {
                            tabLlHonoraryCertificate.setVisibility(View.GONE);
                        }
                    }
                    //添加页面到导航标签
                    for (String item : navigationTag) {
                        anchorTagContainer.addTab(anchorTagContainer.newTab().setText(item));

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

    //监听scrollview滚动事件
    private void initListener() {
        anchorBodyContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //表明当前的动作是由 ScrollView 触发和主导
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    tagFlag = true;
                }
                return false;
            }
        });

        anchorBodyContainer.setScrollViewListener(new ScrollChangedScrollView.ScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrollRefreshNavigationTag(scrollView);
            }

            @Override
            public void onScrollStop(boolean isStop) {
            }
        });
        anchorTagContainer.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //表明当前的动作是由 TabLayout 触发和主导
                tagFlag = false;
                // 根据点击的位置，使ScrollView 滑动到对应区域
                int position = tab.getPosition();
                // 计算点击的导航标签所对应内容区域的高度
                int targetY = 0;
                switch (position) {
                    //个人简历
                    case 0:
                        //
                        break;
                    //联系方式
                    case 1:
                        targetY = tabLlContact.getTop();
                        break;
                    //教育背景
                    case 2:
                        targetY = tabLlDucationBackground.getTop();
                        break;
                    //工作经历
                    case 3:
                        targetY = tabLlWorkExprice.getTop();
                        break;
                    //相关意向
                    case 4:
                        targetY = tabLlIntent.getTop();
                        break;
                    //自我评价
                    case 5:
                        targetY = tabLlElfAssessment.getTop();
                        break;
                    case 6:
                        targetY = tabLlSelfNb.getTop();
                        break;
                    //荣誉证书
                    case 7:
                        targetY = tabLlHonoraryCertificate.getTop();
                        break;

                    default:
                        break;
                }
                // 移动到对应的内容区域
                anchorBodyContainer.smoothScrollTo(0, targetY + 5);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //刷新标签位置
    private void scrollRefreshNavigationTag(ScrollView scrollView) {
        if (scrollView == null) {
            return;
        }
        // 获得ScrollView滑动距离
        int scrollY = scrollView.getScrollY();
        // 确定ScrollView当前展示的顶部内容属于哪个内容模块
        if (scrollY > tabLlHonoraryCertificate.getTop()) {
            refreshContent2NavigationFlag(7);

        } else if (scrollY > tabLlSelfNb.getTop()) {
            refreshContent2NavigationFlag(6);

        } else if (scrollY > tabLlElfAssessment.getTop()) {
            refreshContent2NavigationFlag(5);

        } else if (scrollY > tabLlIntent.getTop()) {
            refreshContent2NavigationFlag(4);

        } else if (scrollY > tabLlWorkExprice.getTop()) {
            refreshContent2NavigationFlag(3);

        } else if (scrollY > tabLlDucationBackground.getTop()) {
            refreshContent2NavigationFlag(2);

        } else if (scrollY > tabLlContact.getTop()) {
            refreshContent2NavigationFlag(1);

        } else {
            refreshContent2NavigationFlag(0);
        }
    }

    //标签移动到位置
    private void refreshContent2NavigationFlag(int currentTagIndex) {
        // 上一个位置与当前位置不一致是，解锁内部锁，是导航可以发生变化
        if (lastTagIndex != currentTagIndex) {
            content2NavigateFlagInnerLock = false;
        }
        if (!content2NavigateFlagInnerLock) {
            // 锁定内部锁
            content2NavigateFlagInnerLock = true;
            // 动作是由ScrollView触发主导的情况下，导航标签才可以滚动选中
            if (tagFlag) {
                anchorTagContainer.setScrollPosition(currentTagIndex, 0, true);
            }
        }
        lastTagIndex = currentTagIndex;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void click(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;


        }

    }
}

