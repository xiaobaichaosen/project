package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.GalleryAdapter;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentContact;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.bean.StudentResume;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 我的----我的简历
 */
public class MySampleActivity extends BaseActivity {
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_headImage)
    CircleImageView ivHeadImage;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rv_resume)
    TextView rvResume;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.tv_per)
    TextView tvPer;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_personalInformation)
    TextView tvPersonalInformation;
    @BindView(R.id.rl_personalInformation)
    RelativeLayout rlPersonalInformation;
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_personalInformation)
    LinearLayout llPersonalInformation;
    @BindView(R.id.tv_contactWay)
    TextView tvContactWay;
    @BindView(R.id.rl_contactWay)
    RelativeLayout rlContactWay;
    @BindView(R.id.ll_contactWay)
    LinearLayout llContactWay;
    @BindView(R.id.tv_educationBackground)
    TextView tvEducationBackground;
    @BindView(R.id.rl_educationBackground)
    RelativeLayout rlEducationBackground;
    @BindView(R.id.ll_educationBackground)
    LinearLayout llEducationBackground;
    @BindView(R.id.tv_workExperience)
    TextView tvWorkExperience;
    @BindView(R.id.rl_workExperience)
    RelativeLayout rlWorkExperience;
    @BindView(R.id.ll_workExperience)
    LinearLayout llWorkExperience;
    @BindView(R.id.tv_relateIntention)
    TextView tvRelateIntention;
    @BindView(R.id.rl_relatedIntention)
    RelativeLayout rlRelatedIntention;
    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    @BindView(R.id.ll_relatedIntention)
    LinearLayout llRelatedIntention;
    @BindView(R.id.tv_selfAssessment)
    TextView tvSelfAssessment;
    @BindView(R.id.rl_selfAssessment)
    RelativeLayout rlSelfAssessment;
    @BindView(R.id.ll_selfAssessment)
    TextView llSelfAssessment;
    @BindView(R.id.tv_nb)
    TextView tvNb;
    @BindView(R.id.rl_nb)
    RelativeLayout rlNb;
    @BindView(R.id.tv_honoraryCertificate)
    TextView tvHonoraryCertificate;
    @BindView(R.id.rl_honoraryCcertificate)
    RelativeLayout rlHonoraryCcertificate;
    @BindView(R.id.ll_honoraryCcertificate)
    LinearLayout llHonoraryCcertificate;
    @BindView(R.id.ll_sample)
    LinearLayout llSample;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    @BindView(R.id.back)
    TextView back;
    private GalleryAdapter mAdapter;
    private StudentInfo studentInfo;
    private StudentContact studentContact;
    private StudentResume studentResume;
    private JSONArray studentEducationJson;
    private JSONArray studentWorkDueJson;
    private boolean sendCheck;
    private List<String> mDatas;
    private boolean isStart;
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
    private String headPic;
    private String userId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_mysample);
    }

    @Override
    public void init() {
        toolbarTitle.setText("我的简历");
        tvSetting.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("");
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(this, appbar, collapsingToolbar, toolbar, getResources().getColor(R.color.colorTheme));

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                rlRoot.setAlpha(1 - Math.abs(verticalOffset * 1.0f) / appBarLayout.getTotalScrollRange());
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                Intent intent = new Intent();
//                intent.setClass(MySampleActivity.this, MeActivity.class);
//                startActivity(intent);
            }
        });

    }

    private void initDatas() {

        //获取简历详情
        HttpUtils getinstance = HttpUtils.getinstance(this);
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
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject studentInfoJson = data.getJSONObject("studentInfo");
                    JSONObject studentContactJson = data.getJSONObject("studentContact");
                    JSONObject studentResumeJson = data.getJSONObject("studentResume");
                    String resumeIntegrity = data.getString("resumeIntegrity");
                    tvPer.setText("简历完整的:" + resumeIntegrity);
                    //教育背景
                    studentEducationJson = data.getJSONArray("studentEducation");
                    //工作经历
                    studentWorkDueJson = data.getJSONArray("studentWorkDue");

                    //个人信息

                    studentInfo = gson.fromJson(studentInfoJson.toString(), StudentInfo.class);
                    //联系人
                    studentContact = gson.fromJson(studentContactJson.toString(), StudentContact.class);
                    //简历信息
                    studentResume = gson.fromJson(studentResumeJson.toString(), StudentResume.class);
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
                        headPic = studentInfo.getHeadPic();
                        if (headPic==null||headPic.equals("")) {
                            ivHeadImage.setBackgroundResource(R.mipmap.head);

                        }else {

                            ImageLoaderUtil.getImageLoader(MySampleActivity.this).displayImage(Constant.infoUrl+userId+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
                        }
                        Integer status = studentResume.getStatus();
                        if (status == 0) {
                            tvStatus.setText("未发送审核");
                        } else if (status == 1) {
                            tvStatus.setText("待审核");
                        } else if (status == 2) {
                            tvStatus.setText("通过");
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

    @OnClick({R.id.back,R.id.rl_nb, R.id.iv_headImage, R.id.rl_personalInformation, R.id.rl_contactWay, R.id.rl_educationBackground, R.id.rl_relatedIntention, R.id.rl_selfAssessment, R.id.rl_honoraryCcertificate, R.id.rl_workExperience, R.id.rv_resume})
    public void Click(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_mySample:
                //我的简历
                intent.setClass(this, FeatureActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_collectPost:
                break;
            case R.id.rl_attentionKinder:
                break;
            case R.id.rl_footPrint:
                break;
            case R.id.rl_mySend:
                break;
//            case R.id.iv_settings:
//                intent.setClass(mActivity, SettingActivity.class);
//                startActivity(intent
//                );
//                break;

            case R.id.iv_headImage:
                uploadHeadImage();
                break;
            //特长爱好
            case R.id.rl_nb:
                if (null != studentResume) {
                    intent.putExtra("id", studentResume.getId());
                    intent.putExtra("nb", studentResume.getHobby());
                }
                intent.setClass(this, FeatureActivity.class);
                startActivity(intent);
                break;
            //个人信息
            case R.id.rl_personalInformation:
                if (null != studentInfo) {
                    mBundle.putSerializable("studentInfo", studentInfo);
                    intent.putExtras(mBundle);
                }
                intent.setClass(this, PersonalInformationActivity.class);
                startActivity(intent);
                break;
            //联系方式
            case R.id.rl_contactWay:
                if (null != studentContact) {
                    mBundle.putSerializable("studentContact", studentContact);
                    intent.putExtras(mBundle);
                }
                intent.setClass(this, ContactWayActivity.class);
                startActivity(intent);
                break;
            //教育背景
            case R.id.rl_educationBackground:
                if (studentEducationJson.length() > 0) {
                    intent.putExtra("studentEducationJson", studentEducationJson.toString());
                    intent.setClass(this, EducationListActivity.class);
                } else {
                    intent.setClass(this, EducationBackgroundActivity.class);
                }
                startActivity(intent);
                break;
            //相关意向
            case R.id.rl_relatedIntention:
                if (null != studentResume) {
                    mBundle.putSerializable("studentResume", studentResume);
                    intent.putExtras(mBundle);
                }
                intent.setClass(this, RelateIntentinActivty.class);
                startActivity(intent);
                break;
            //自我评价
            case R.id.rl_selfAssessment:
                if (null != studentResume) {
                    intent.putExtra("selfassess", studentResume.getSelfEvaluate());
                    intent.putExtra("id", studentResume.getId());
                }
                intent.setClass(this, SelfAssessmentActivity.class);
                startActivity(intent);
                break;
            //荣誉证书
            case R.id.rl_honoraryCcertificate:
                if (null != studentResume) {
                    intent.putExtra("honorary", studentResume.getCertificate());
                }
                intent.setClass(this, HonoraryCcertificateActivity.class);
                startActivity(intent);
                break;
            //工作经历
            case R.id.rl_workExperience:

                if (studentWorkDueJson.length() > 0) {
                    intent.putExtra("studentWorkDueJson", studentWorkDueJson.toString());
                    intent.setClass(MySampleActivity.this, WorkListActivity.class);
                } else {
                    intent.setClass(this, WorkExperienceActivity.class);
                }
                startActivity(intent);
                break;

            //发送简历
            case R.id.rv_resume:
                intent.setClass(this, ResumepreviewActivity.class);
                startActivity(intent);
                break;


        }
    }

    @Override
    protected void onResume() {
        userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
        initDatas();
        super.onResume();
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setClippingEnabled(true);
        popupWindow.update();
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
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
            Uri contentUri = FileProvider.getUriForFile(this, "yijie" + ".fileProvider", tempFile);
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
                    final String cropImagePath = FileUtil.getRealFilePathFromUri(this, uri);
                    //此处后面可以将bitMap转为二进制上传后台网络
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
                    HttpUtils.getinstance(MySampleActivity.this).uploadFiles("headPic",Constant.HEADPICUPLOAD, stringStringHashMap, files, new BaseCallback<String>() {
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
                                    ImageLoaderUtil.getImageLoader(MySampleActivity.this).displayImage(Constant.infoUrl+userId+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
                                }
                                ShowToastUtils.showToastMsg(MySampleActivity.this, jsonObject.getString("resMessage"));
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
        intent.setClass(this, ClipImageActivity.class);

        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
