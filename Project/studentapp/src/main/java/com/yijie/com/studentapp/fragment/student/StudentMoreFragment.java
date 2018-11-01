package com.yijie.com.studentapp.fragment.student;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.TimeZoneFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.GalleryAdapter;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.ContactWayActivity;
import com.yijie.com.studentapp.activity.EducationBackgroundActivity;
import com.yijie.com.studentapp.activity.EducationListActivity;
import com.yijie.com.studentapp.activity.FeatureActivity;
import com.yijie.com.studentapp.activity.FootBrowActivity;
import com.yijie.com.studentapp.activity.HonoraryCcertificateActivity;
import com.yijie.com.studentapp.activity.MeActivity;
import com.yijie.com.studentapp.activity.MySampleActivity;
import com.yijie.com.studentapp.activity.MySendActivity;
import com.yijie.com.studentapp.activity.PersonalInformationActivity;
import com.yijie.com.studentapp.activity.PostCollectionActivity;
import com.yijie.com.studentapp.activity.RelateIntentinActivty;
import com.yijie.com.studentapp.activity.ResumepreviewActivity;
import com.yijie.com.studentapp.activity.SelfAssessmentActivity;
import com.yijie.com.studentapp.activity.SettingActivity;
import com.yijie.com.studentapp.activity.WorkExperienceActivity;
import com.yijie.com.studentapp.activity.WorkListActivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.bean.StudentContact;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.bean.StudentResume;
import com.yijie.com.studentapp.bean.StudentResumeDetail;
import com.yijie.com.studentapp.bean.StudentWorkDue;
import com.yijie.com.studentapp.headportrait.ClipImageActivity;
import com.yijie.com.studentapp.headportrait.util.FileUtil;
import com.yijie.com.studentapp.niorgai.StatusBarCompat;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CircleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class StudentMoreFragment extends BaseFragment {
    @BindView(R.id.backdrop)
    ImageView backdrop;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;

    @BindView(R.id.iv_headImage)
    CircleImageView ivHeadImage;
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    File tempFile;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    @BindView(R.id.rl_personalInformation)
    RelativeLayout rlPersonalInformation;
    @BindView(R.id.rl_contactWay)
    RelativeLayout rlContactWay;
    @BindView(R.id.rl_educationBackground)
    RelativeLayout rlEducationBackground;
    @BindView(R.id.rl_relatedIntention)
    RelativeLayout rlRelatedIntention;
    @BindView(R.id.rl_selfAssessment)
    RelativeLayout rlSelfAssessment;
    @BindView(R.id.rl_honoraryCcertificate)
    RelativeLayout rlHonoraryCcertificate;
    @BindView(R.id.rl_workExperience)
    RelativeLayout rlWorkExperience;
    @BindView(R.id.ll_personalInformation)
    LinearLayout llPersonalInformation;
    @BindView(R.id.ll_contactWay)
    LinearLayout llContactWay;
    @BindView(R.id.ll_educationBackground)
    LinearLayout llEducationBackground;
    @BindView(R.id.ll_workExperience)
    LinearLayout llWorkExperience;
    @BindView(R.id.ll_relatedIntention)
    LinearLayout llRelatedIntention;
    @BindView(R.id.ll_selfAssessment)
    TextView llSelfAssessment;
    @BindView(R.id.ll_honoraryCcertificate)
    LinearLayout llHonoraryCcertificate;
    @BindView(R.id.rv_resume)
    TextView rvResume;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;


    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_personalInformation)
    TextView tvPersonalInformation;
    @BindView(R.id.tv_contactWay)
    TextView tvContactWay;
    @BindView(R.id.tv_educationBackground)
    TextView tvEducationBackground;
    @BindView(R.id.tv_workExperience)
    TextView tvWorkExperience;
    @BindView(R.id.tv_relateIntention)
    TextView tvRelateIntention;
    @BindView(R.id.tv_selfAssessment)
    TextView tvSelfAssessment;
    @BindView(R.id.tv_honoraryCertificate)
    TextView tvHonoraryCertificate;
    @BindView(R.id.ll_start)
    LinearLayout llStart;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_per)
    TextView tvPer;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_nb)
    TextView tvNb;
    @BindView(R.id.rl_nb)
    RelativeLayout rlNb;

    @BindView(R.id.tv_kendSimple)
    TextView tvKendSimple;

    @BindView(R.id.tv_kendTrouble)
    TextView tvKendTrouble;

    @BindView(R.id.tv_kendAttendance)
    TextView tvKendAttendance;

    @BindView(R.id.ll_sendCheck)
    LinearLayout llSendCheck;
    @BindView(R.id.ll_sample)
    LinearLayout llSample;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.rl_mySample)
    RelativeLayout rlMySample;
    @BindView(R.id.rl_collectPost)
    RelativeLayout rlCollectPost;
    @BindView(R.id.rl_attentionKinder)
    RelativeLayout rlAttentionKinder;
    @BindView(R.id.rl_footPrint)
    RelativeLayout rlFootPrint;
    @BindView(R.id.rl_mySend)
    RelativeLayout rlMySend;
    private List<String> mDatas;
    private boolean isStart;

    private GalleryAdapter mAdapter;
    private StudentInfo studentInfo;
    private StudentContact studentContact;
    private StudentResume studentResume;
    private JSONArray studentEducationJson;
    private JSONArray studentWorkDueJson;
    private boolean sendCheck;
    private String headPic;
    private   String cropImagePath;
    private   String userId;

    @Override
    protected int getLayoutId() {
//        EventBus.getDefault().register(this);
        return R.layout.fragment_student_more;
    }

    public static StudentMoreFragment newInstance(String image) {
        StudentMoreFragment fragment = new StudentMoreFragment();
        Bundle args = new Bundle();
        args.putString("image", image);
        fragment.setArguments(args);
        return fragment;
    }

//    public void setHeadImage() {
//        String user_phone = (String) SharedPreferencesUtils.getParam(mActivity, "user_phone", "");
//        String image = (String) SharedPreferencesUtils.getParam(mActivity, "image", "");
//        tvPhone.setText(user_phone);
//        if (image.equals("null") || image.equals("")) {
//            ivHeadImage.setBackgroundResource(R.mipmap.head);
//
//        } else {
//            byte[] bitmapArray = Base64.decode(image, Base64.DEFAULT);
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//            ivHeadImage.setImageBitmap(bitmap);
//        }
//
//
//    }

    @Override
    protected void initView() {
        isStart = (boolean) SharedPreferencesUtils.getParam(mActivity, "isStart", false);

        if (isStart) {
            llStart.setVisibility(View.GONE);
        }


//        lazyLoad();
//        setHeadImage();

        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(getActivity(), appbar, collapsingToolbar, toolbar, getResources().getColor(R.color.colorTheme));

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                rlRoot.setAlpha(1 - Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(mActivity, MeActivity.class);
                startActivity(intent);
            }
        });


//        //设置布局管理器
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        //设置适配器
//        mAdapter = new GalleryAdapter(mActivity, mDatas);
//        recyclerView.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(mActivity, PhotoActivityForHor.class);
//                Rect rect = new Rect();
//                view.getGlobalVisibleRect(rect);
//
//                intent.putExtra("imgUrl", mUrls[position]);
//                intent.putExtra("startBounds", rect);
//
//                startActivity(intent);
//                mActivity.overridePendingTransition(0, 0);
//
//            }
//        });

    }

    @Override
    protected void initData() {
        // TODO 正常应该是!isVisble,待解决
        LogUtil.e("initData=" + isVisible);
        if (!isPrepared || !isVisible) {
            return;
        }
        initDatas();

    }


    @OnClick({R.id.tv_setting,R.id.rl_mySample, R.id.rl_collectPost, R.id.rl_attentionKinder, R.id.rl_footPrint, R.id.rl_mySend,R.id.rl_nb, R.id.iv_headImage, R.id.rl_personalInformation, R.id.rl_contactWay, R.id.rl_educationBackground, R.id.rl_relatedIntention, R.id.rl_selfAssessment, R.id.rl_honoraryCcertificate, R.id.rl_workExperience, R.id.rv_resume, R.id.btn_start})
    public void Click(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_setting:
                intent.setClass(mActivity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mySample:
                //我的简历
                intent.setClass(mActivity, MySampleActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_collectPost:
                //职位收藏
                intent.setClass(mActivity, PostCollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_attentionKinder:
                break;
            case R.id.rl_footPrint:
                //浏览足迹
                intent.setClass(mActivity, FootBrowActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_mySend:
                intent.setClass(mActivity, MySendActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_headImage:
                uploadHeadImage();
                break;
            //特长爱好
            case R.id.rl_nb:
                if (null != studentResume) {
                    intent.putExtra("id", studentResume.getId());
                    intent.putExtra("nb", studentResume.getHobby());
                }
                intent.setClass(mActivity, FeatureActivity.class);
                startActivity(intent);
                break;
            //个人信息
            case R.id.rl_personalInformation:
                if (null != studentInfo) {
                    mBundle.putSerializable("studentInfo", studentInfo);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, PersonalInformationActivity.class);
                startActivity(intent);
                break;
            //联系方式
            case R.id.rl_contactWay:
                if (null != studentContact) {
                    mBundle.putSerializable("studentContact", studentContact);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, ContactWayActivity.class);
                startActivity(intent);
                break;
            //教育背景
            case R.id.rl_educationBackground:
                if (studentEducationJson.length() > 0) {
                    intent.putExtra("studentEducationJson", studentEducationJson.toString());
                    intent.setClass(mActivity, EducationListActivity.class);
                } else {
                    intent.setClass(mActivity, EducationBackgroundActivity.class);
                }
                startActivity(intent);
                break;
            //相关意向
            case R.id.rl_relatedIntention:
                if (null != studentResume) {
                    mBundle.putSerializable("studentResume", studentResume);
                    intent.putExtras(mBundle);
                }
                intent.setClass(mActivity, RelateIntentinActivty.class);
                startActivity(intent);
                break;
            //自我评价
            case R.id.rl_selfAssessment:
                if (null != studentResume) {
                    intent.putExtra("selfassess", studentResume.getSelfEvaluate());
                    intent.putExtra("id", studentResume.getId());
                }
                intent.setClass(mActivity, SelfAssessmentActivity.class);
                startActivity(intent);
                break;
            //荣誉证书
            case R.id.rl_honoraryCcertificate:
                if (null != studentResume) {
                    intent.putExtra("honorary", studentResume.getCertificate());
                }
                intent.setClass(mActivity, HonoraryCcertificateActivity.class);
                startActivity(intent);
                break;
            //工作经历
            case R.id.rl_workExperience:

                if (studentWorkDueJson.length() > 0) {
                    intent.putExtra("studentWorkDueJson", studentWorkDueJson.toString());
                    intent.setClass(mActivity, WorkListActivity.class);
                } else {
                    intent.setClass(mActivity, WorkExperienceActivity.class);
                }
                startActivity(intent);
                break;

            //发送简历
            case R.id.rv_resume:
                intent.setClass(mActivity, ResumepreviewActivity.class);
                startActivity(intent);
                break;

//            case R.id.tv_attendance:
//                //考勤AttendanceActivity
//                intent.setClass(mActivity, AttendanceActivity.class);
//                startActivity(intent);
//                break;

            case R.id.btn_start:
                PropertyValuesHolder pvhScaleA = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
                PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
                PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
                ValueAnimator objAnimator = ObjectAnimator.ofPropertyValuesHolder(llStart, /*pvhTransX,*/ pvhScaleX, pvhScaleY, pvhScaleA);
                objAnimator.setDuration(500);
                objAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if ((float) animation.getAnimatedValue() == 0f) {
                            llStart.setVisibility(View.GONE);
                        }
                    }
                });
                objAnimator.start();
                SharedPreferencesUtils.setParam(mActivity, "isStart", true);
                isStart = true;
                break;
        }
    }


    @Override
    public void onResume() {
        userId = (String) SharedPreferencesUtils.getParam(mActivity, "id", "");
        isPrepared = true;
        initData();
        super.onResume();
    }


    public class DateAdapter implements JsonDeserializer<Date> {
        private final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        public Date deserialize(JsonElement arg0, Type arg1,
                                JsonDeserializationContext arg2) throws JsonParseException {
            try {
                return df.parse(arg0.getAsString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void initDatas() {

        //获取简历详情
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("studentUserId", userId);
        getinstance.post(Constant.STUDENTRESUMEDETAIL, stringStringHashMap, new BaseCallback<String>() {
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
                commonDialog.dismiss();
//                Gson gson = new Gson();

                Gson gson = null;
                GsonBuilder builder = new GsonBuilder();
                builder.registerTypeAdapter(Date.class, new DateAdapter());
                builder.setDateFormat("yyyy-MM-dd HH:mm");
                gson = builder.create();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    StudentResumeDetail studentResumeDetail = gson.fromJson(data.toString(), StudentResumeDetail.class);
//                    JSONObject studentInfoJson = data.getJSONObject("studentInfo");
//                    JSONObject studentContactJson = data.getJSONObject("studentContact");
//                    JSONObject studentResumeJson = data.getJSONObject("studentResume");
                    String resumeIntegrity = data.getString("resumeIntegrity");
                    tvPer.setText("简历完整的:" + resumeIntegrity);
                    //教育背景
                    studentEducationJson = data.getJSONArray("studentEducation");
                    //工作经历
                    studentWorkDueJson = data.getJSONArray("studentWorkDue");

                    //个人信息

//                    studentInfo = gson.fromJson(studentInfoJson.toString(), StudentInfo.class);
                    studentInfo = studentResumeDetail.getStudentInfo();
                    //联系人
//                    studentContact = gson.fromJson(studentContactJson.toString(), StudentContact.class);
                    studentContact = studentResumeDetail.getStudentContact();
                    //简历信息
//                    studentResume = gson.fromJson(studentResumeJson.toString(), StudentResume.class);
                    studentResume = studentResumeDetail.getStudentResume();
                    if (studentEducationJson.length() > 0) {
                        for (int i = 0; i < studentEducationJson.length(); i++) {
                            StudentEducation studentEducation = gson.fromJson(studentEducationJson.getJSONObject(i).toString(), StudentEducation.class);
                            if (!TextUtils.isEmpty(studentEducation.getCollege())
                                    && !TextUtils.isEmpty(studentEducation.getMajor())
                                    && !TextUtils.isEmpty(studentEducation.getEducation())
                                    && !TextUtils.isEmpty(studentEducation.getStartTime())
                                    && !TextUtils.isEmpty(studentEducation.getGraduateTime())
                                    ) {
                                tvEducationBackground.setText("完善");
                                break;
                            } else {
                                tvEducationBackground.setText("待完善");
                            }
                        }
                        tvEducationBackground.setTextColor(getResources().getColor(R.color.colorTheme));
                    } else {
                        tvEducationBackground.setText("待完善");
                        tvEducationBackground.setTextColor(getResources().getColor(R.color.colorTheme));
                    }
                    if (studentWorkDueJson.length() > 0) {
                        for (int i = 0; i < studentWorkDueJson.length(); i++) {
                            StudentWorkDue studentWorkDue = gson.fromJson(studentWorkDueJson.getJSONObject(i).toString(), StudentWorkDue.class);
                            if (!TextUtils.isEmpty(studentWorkDue.getCompanyName())
                                    && !TextUtils.isEmpty(studentWorkDue.getWorkDue())
                                    && !TextUtils.isEmpty(studentWorkDue.getPost())
                                    && !TextUtils.isEmpty(studentWorkDue.getDescription())
                                    ) {
                                tvWorkExperience.setText("完善");
                                break;
                            } else {
                                tvWorkExperience.setText("待完善");
                            }
                        }
                        tvWorkExperience.setTextColor(getResources().getColor(R.color.colorTheme));
                    } else {
                        tvWorkExperience.setText("待完善");
                        tvWorkExperience.setTextColor(getResources().getColor(R.color.colorTheme));
                    }
                    if (null != studentInfo) {
//                        llPersonalInformation.setVisibility(View.VISIBLE);
//                        tvPersonalInformation.setCompoundDrawables(null, null, null, null);
                        headPic = studentInfo.getHeadPic();
                        if (headPic==null||headPic.equals("")) {
                            ivHeadImage.setBackgroundResource(R.mipmap.head);
                        }else {
                            ImageLoaderUtil.getImageLoader(mActivity).displayImage(Constant.infoUrl+userId+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
                        }
                        tvStuName.setText(studentInfo.getStuName());
                        tvStuName.setText(studentInfo.getStuName());
                        if (!TextUtils.isEmpty(studentInfo.getStuName())
                                && !TextUtils.isEmpty(studentInfo.getSex())
                                && !TextUtils.isEmpty(studentInfo.getHeight() + "")
                                && !TextUtils.isEmpty(studentInfo.getWeight() + "")
                                && !TextUtils.isEmpty(studentInfo.getNation())
                                && !TextUtils.isEmpty(studentInfo.getPlace())
                                && !TextUtils.isEmpty(studentInfo.getAddress())
                                && !TextUtils.isEmpty(studentInfo.getIdCard())
                                ) {

                            tvPersonalInformation.setText("完善");
                        } else {
                            tvPersonalInformation.setText("待完善");
                        }
                        tvPersonalInformation.setTextColor(getResources().getColor(R.color.colorTheme));
                    } else {
                        tvPersonalInformation.setText("待完善");
                        tvPersonalInformation.setTextColor(getResources().getColor(R.color.colorTheme));
                    }
                    if (null != studentContact) {
                        if (!TextUtils.isEmpty(studentContact.getCellphone())
                                && !TextUtils.isEmpty(studentContact.getWechat())
                                && !TextUtils.isEmpty(studentContact.getQq())
                                && !TextUtils.isEmpty(studentContact.getEmail())
                                && !TextUtils.isEmpty(studentContact.getUrgentContact())
                                && !TextUtils.isEmpty(studentContact.getUrgentCellphone())
                                ) {

                            tvContactWay.setText("完善");
                        } else {
                            tvContactWay.setText("待完善");
                        }
//                        llContactWay.setVisibility(View.VISIBLE);
//                        tvContactWay.setCompoundDrawables(null, null, null, null);

                        tvContactWay.setTextColor(getResources().getColor(R.color.colorTheme));
                    } else {
                        tvContactWay.setText("待完善");
                        tvContactWay.setTextColor(getResources().getColor(R.color.colorTheme));
                    }
                    if (null != studentResume) {
                        Integer status = studentResume.getStatus();
                        if (status == 0) {
                            tvStatus.setText("未发送审核");
                        } else if (status == 1) {
                            tvStatus.setText("待审核");
                        } else if (status == 2) {
                            tvStatus.setText("通过");
                        }
                        if (status == 0) {
                            toolbarTitle.setText("我的简历");
                            llSendCheck.setVisibility(View.GONE);
                            llSample.setVisibility(View.VISIBLE);
                            tvSetting.setVisibility(View.VISIBLE);
                        } else {
                            llSample.setVisibility(View.GONE);
                            llSendCheck.setVisibility(View.VISIBLE);
                            toolbarTitle.setText("我的");
                            rvResume.setVisibility(View.GONE);
                            tvSetting.setVisibility(View.VISIBLE);

                        }

                        if (!TextUtils.isEmpty(studentResume.getCertificate())) {
                            tvHonoraryCertificate.setText("完善");
                            tvHonoraryCertificate.setTextColor(getResources().getColor(R.color.colorTheme));
                        } else {
                            tvHonoraryCertificate.setText("待完善");
                            tvHonoraryCertificate.setTextColor(getResources().getColor(R.color.colorTheme));
                        }
                        if (!TextUtils.isEmpty(studentResume.getSelfEvaluate())) {
                            tvSelfAssessment.setText("完善");
                            tvSelfAssessment.setTextColor(getResources().getColor(R.color.colorTheme));
                        } else {
                            tvSelfAssessment.setText("待完善");
                            tvHonoraryCertificate.setTextColor(getResources().getColor(R.color.colorTheme));
                        }
                        if (!TextUtils.isEmpty(studentResume.getExpectWorkPlace()) && !TextUtils.isEmpty(studentResume.getExpectPartener())) {
                            tvRelateIntention.setText("完善");
                            tvRelateIntention.setTextColor(getResources().getColor(R.color.colorTheme));
                        } else {
                            tvRelateIntention.setText("待完善");
                            tvRelateIntention.setTextColor(getResources().getColor(R.color.colorTheme));
                        }
                        if (!TextUtils.isEmpty(studentResume.getHobby())) {
                            tvNb.setText("完善");
                        } else {
                            tvNb.setText("待完善");
                        }
                        tvNb.setTextColor(getResources().getColor(R.color.colorTheme));
                    }else {
                        toolbarTitle.setText("我的简历");
                        llSendCheck.setVisibility(View.GONE);
                        llSample.setVisibility(View.VISIBLE);
                        tvSetting.setVisibility(View.VISIBLE);
                        tvRelateIntention.setText("待完善");
                        tvRelateIntention.setTextColor(getResources().getColor(R.color.colorTheme));
                        tvSelfAssessment.setText("待完善");
                        tvSelfAssessment.setTextColor(getResources().getColor(R.color.colorTheme));
                        tvHonoraryCertificate.setText("待完善");
                        tvHonoraryCertificate.setTextColor(getResources().getColor(R.color.colorTheme));
                        tvNb.setText("待完善");
                        tvNb.setTextColor(getResources().getColor(R.color.colorTheme));

                        String json = (String) SharedPreferencesUtils.getParam(mActivity, "user", "");
                        try {
                            JSONObject jsonObject1 = new JSONObject(json);
                            String stuName = jsonObject1.getString("studentName");
                            tvStuName.setText(stuName);
                            tvStatus.setText("简历待完善");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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



    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(mActivity).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setClippingEnabled(true);
        popupWindow.update();
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = 0.5f;
        mActivity.getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                mActivity.getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
//                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                } else {
                //跳转到调用系统相机
                gotoCamera();
//                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
//                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请READ_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
//                } else {
                //跳转到相册
                gotoPhoto();
//                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 外部存储权限申请返回
     */


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件

        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mActivity, "yijie" + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == -1) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == -1) {
                    Uri uri = data.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == -1) {
                    final Uri uri = data.getData();
                    if (uri == null) {
                        return;
                    }

                    cropImagePath = FileUtil.getRealFilePathFromUri(mActivity, uri);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    HashMap<String, String> stringStringHashMap = new HashMap<>();
                    List<File> files = new ArrayList<>();
                    File file = new File(cropImagePath);
                    files.add(file);

                    if (null==headPic) {

                    }else {
                        stringStringHashMap.put("imagePath",headPic);
                    }
                    LogUtil.e("====="+headPic);

                    stringStringHashMap.put("studentUserId", userId);
                    HttpUtils.getinstance(mActivity).uploadFiles("headPic",Constant.HEADPICUPLOAD, stringStringHashMap, files, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {

                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            LogUtil.e("======"+e);
                        }

                        @Override
                        public void onSuccess(Response response, String o) {
                            LogUtil.e("======"+o);
                            try {
                                JSONObject jsonObject = new JSONObject(o);
                                String rescode = jsonObject.getString("rescode");
                                if (rescode.equals("200")){
                                    JSONObject data1 = jsonObject.getJSONObject("data");
                                    String headPic = data1.getString("headPic");
                                    LogUtil.e("url==="+Constant.infoUrl+userId+"/head_pic_/"+headPic);
                                    ImageLoaderUtil.getImageLoader(mActivity).displayImage(Constant.infoUrl+userId+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
                                }
                                ShowToastUtils.showToastMsg(mActivity, jsonObject.getString("resMessage"));
//                                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
//                                ivHeadImage.setImageBitmap(bitMap);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            LogUtil.e("======"+e);
                        }
                    });

                }
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mActivity, ClipImageActivity.class);

        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
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