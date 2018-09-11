package com.yijie.com.studentapp.fragment.student;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.GalleryAdapter;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.AttendanceActivity;
import com.yijie.com.studentapp.activity.ContactWayActivity;
import com.yijie.com.studentapp.activity.EducationBackgroundActivity;
import com.yijie.com.studentapp.activity.HonoraryCcertificateActivity;
import com.yijie.com.studentapp.activity.MeActivity;
import com.yijie.com.studentapp.activity.PersonalInformationActivity;
import com.yijie.com.studentapp.activity.PhotoActivityForHor;
import com.yijie.com.studentapp.activity.RelateIntentinActivty;
import com.yijie.com.studentapp.activity.ResumepreviewActivity;
import com.yijie.com.studentapp.activity.SelfAssessmentActivity;
import com.yijie.com.studentapp.activity.SettingActivity;
import com.yijie.com.studentapp.activity.WorkExperienceActivity;
import com.yijie.com.studentapp.base.BaseFragment;
import com.yijie.com.studentapp.headportrait.ClipImageActivity;
import com.yijie.com.studentapp.headportrait.util.FileUtil;
import com.yijie.com.studentapp.niorgai.StatusBarCompat;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.CircleImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class StudentMoreFragment extends BaseFragment {
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    Unbinder unbinder;
    @BindView(R.id.iv_settings)
    ImageView ivSettings;
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

    @BindView(R.id.tv_attendance)
    TextView tvAttendance;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
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
    private List<String> mDatas;
    private boolean isStart;
    private String[] mUrls = new String[]{
            "http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
    };
    private GalleryAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_student_more;
    }

    public static StudentMoreFragment newInstance(String image) {
        StudentMoreFragment fragment = new StudentMoreFragment();
        Bundle args = new Bundle();
        args.putString("image", image);
        fragment.setArguments(args);
        return fragment;
    }

    public void setHeadImage() {
        String user_phone = (String) SharedPreferencesUtils.getParam(mActivity, "user_phone", "");
        String image = (String) SharedPreferencesUtils.getParam(mActivity, "image", "");
        tvPhone.setText(user_phone);
        if (image.equals("null") || image.equals("")) {
            ivHeadImage.setBackgroundResource(R.mipmap.head);

        } else {
            byte[] bitmapArray = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            ivHeadImage.setImageBitmap(bitmap);
        }


    }

    @Override
    protected void initData() {
//        SharedPreferencesUtils.setParam(mActivity,"isStart",true);

        isStart = (boolean) SharedPreferencesUtils.getParam(mActivity, "isStart", false);
        if (isStart){
            llStart.setVisibility(View.GONE);
        }
        //初始化控件
        isPrepared = true;
//        lazyLoad();
        setHeadImage();

        ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        initDatas();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(mActivity, mDatas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, PhotoActivityForHor.class);
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                intent.putExtra("imgUrl", mUrls[position]);
                intent.putExtra("startBounds", rect);

                startActivity(intent);
                mActivity.overridePendingTransition(0, 0);

            }
        });

    }

    @OnClick({R.id.iv_settings, R.id.iv_headImage, R.id.rl_personalInformation, R.id.rl_contactWay, R.id.rl_educationBackground, R.id.rl_relatedIntention, R.id.rl_selfAssessment, R.id.rl_honoraryCcertificate, R.id.rl_workExperience, R.id.rv_resume, R.id.tv_attendance, R.id.btn_start})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.iv_settings:
                intent.setClass(mActivity, SettingActivity.class);
                startActivity(intent
                );
                break;

            case R.id.iv_headImage:
                uploadHeadImage();
                break;
            //个人信息
            case R.id.rl_personalInformation:
                intent.setClass(mActivity, PersonalInformationActivity.class);
                startActivity(intent);
                break;
            //联系方式
            case R.id.rl_contactWay:
                intent.setClass(mActivity, ContactWayActivity.class);
                startActivity(intent);
                break;
            //教育背景
            case R.id.rl_educationBackground:
                intent.setClass(mActivity, EducationBackgroundActivity.class);
                startActivity(intent);
                break;
            //相关意向
            case R.id.rl_relatedIntention:
                intent.setClass(mActivity, RelateIntentinActivty.class);
                startActivity(intent);
                break;
            //自我评价
            case R.id.rl_selfAssessment:
                intent.setClass(mActivity, SelfAssessmentActivity.class);
                startActivity(intent);
                break;
            //荣誉证书
            case R.id.rl_honoraryCcertificate:
                intent.setClass(mActivity, HonoraryCcertificateActivity.class);
                startActivity(intent);
                break;
            //工作经历
            case R.id.rl_workExperience:
                intent.setClass(mActivity, WorkExperienceActivity.class);
                startActivity(intent);
                break;

            //发送简历
            case R.id.rv_resume:
                intent.setClass(mActivity, ResumepreviewActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_attendance:
                //考勤AttendanceActivity
                intent.setClass(mActivity, AttendanceActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_start:
                PropertyValuesHolder pvhScaleA = PropertyValuesHolder.ofFloat("alpha", 1.0f,0f);
                PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 0f);
                PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
                ValueAnimator   objAnimator = ObjectAnimator.ofPropertyValuesHolder(llStart, /*pvhTransX,*/ pvhScaleX, pvhScaleY, pvhScaleA);
                objAnimator.setDuration(500);
                objAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                     if ((float)animation.getAnimatedValue()==0f){
                        llStart.setVisibility(View.GONE);
                     }
                    }
                });
                objAnimator.start();
                SharedPreferencesUtils.setParam(mActivity,"isStart",true);
                isStart=true;
                break;
        }
    }


    @Override
    public void onResume() {
        if (isStart){
            ShowToastUtils.showToastMsg(mActivity, "提示信息");
        }
        super.onResume();
    }

    @Override
    protected void lazyLoad() {
        //在调用了onCreateView后并且fragment的UI是可见的就填充数据
        //不加判断会引起context空
        if (!isPrepared || !isVisible)
            return;
        //填充各控件的数据
        if (isStart){
            ShowToastUtils.showToastMsg(mActivity, "提示信息");
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(String message) {
        if (message.equals("personInformation")) {
            llPersonalInformation.setVisibility(View.VISIBLE);
            tvPersonalInformation.setCompoundDrawables(null, null, null, null);
            tvPersonalInformation.setText("编辑");
            tvPersonalInformation.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("contactway")) {
            llContactWay.setVisibility(View.VISIBLE);
            llPersonalInformation.setVisibility(View.VISIBLE);
            tvContactWay.setCompoundDrawables(null, null, null, null);
            tvContactWay.setText("编辑");
            tvContactWay.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("educationbackground")) {
            llEducationBackground.setVisibility(View.VISIBLE);
            tvEducationBackground.setCompoundDrawables(null, null, null, null);
            tvEducationBackground.setText("编辑");
            tvEducationBackground.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("relateIntentin")) {
            llRelatedIntention.setVisibility(View.VISIBLE);
            tvRelateIntention.setCompoundDrawables(null, null, null, null);
            tvRelateIntention.setText("编辑");
            tvRelateIntention.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("selfassessment")) {
            llSelfAssessment.setVisibility(View.VISIBLE);
            tvSelfAssessment.setCompoundDrawables(null, null, null, null);
            tvSelfAssessment.setText("编辑");
            tvSelfAssessment.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("honoraryccertificate")) {
            //获取证书图片
            HttpUtils instance = HttpUtils.getinstance();
            Map map = new HashMap();
            Integer id = (Integer) SharedPreferencesUtils.getParam(mActivity, "user_id", 0);
            map.put("id", String.valueOf(id));
            instance.post(Constant.honoraryCcertificateGetUrl, map, new BaseCallback<String>() {
                @Override
                public void onRequestBefore() {
                    dialog.dismiss();
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    dialog.dismiss();
                }

                @Override
                public void onSuccess(Response response, String s) {
                    dialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        LogUtil.e("--------"+jsonObject);
//                        if (jsonObject.getString("status").equals("ok")) {
//
//                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Response response, int errorCode, Exception e) {
                    dialog.dismiss();
                }
            });

//            llHonoraryCcertificate.setVisibility(View.VISIBLE);
//            tvHonoraryCertificate.setCompoundDrawables(null, null, null, null);
            tvHonoraryCertificate.setText("编辑");
            tvHonoraryCertificate.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("workexperience")) {
            llWorkExperience.setVisibility(View.VISIBLE);
            tvWorkExperience.setCompoundDrawables(null, null, null, null);
            tvWorkExperience.setText("编辑");
            tvWorkExperience.setTextColor(getResources().getColor(R.color.colorTheme));
        } else if (message.equals("sendSample")) {
            ivStutus.setBackgroundResource(R.mipmap.student_unchecked);
            tvPersonalInformation.setVisibility(View.GONE);
        }


    }

    private void initDatas() {
        mDatas = Arrays.asList(mUrls);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                    final String cropImagePath = FileUtil.getRealFilePathFromUri(mActivity, uri);


                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    List<File> files = new ArrayList<>();
                    File file = new File(cropImagePath);
                    files.add(file);
                    Map<String, String> parames = new HashMap<>();

                    String user_phone = (String) SharedPreferencesUtils.getParam(mActivity, "user_phone", "");
                    parames.put("phone", user_phone);
                    HttpUtils.getinstance().uploadFiles(Constant.headUploadUrl, parames, files, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {

                        }

                        @Override
                        public void onFailure(Request request, Exception e) {

                        }

                        @Override
                        public void onSuccess(Response response, String o) {
                            ShowToastUtils.showToastMsg(mActivity, "上传图像成功");
                            Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                            ivHeadImage.setImageBitmap(bitMap);
                            //更新本地存储
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] datas = baos.toByteArray();
                            String decode = Base64.encodeToString(datas, Base64.DEFAULT);
                            SharedPreferencesUtils.setParam(mActivity, "image", decode);
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {

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



}