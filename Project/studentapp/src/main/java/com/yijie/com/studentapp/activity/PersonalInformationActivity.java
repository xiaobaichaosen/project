package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.login.LoginActivity;
import com.yijie.com.studentapp.adapter.ImagePickerAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/31.
 * 新建个人信息
 */

public class PersonalInformationActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.ll_name)
    RelativeLayout llName;
    @BindView(R.id.ll_gender)
    RelativeLayout llGender;
    @BindView(R.id.rl_height)
    RelativeLayout rlHeight;

    @BindView(R.id.line_or_v)
    View lineOrV;
    @BindView(R.id.rl_weight)
    RelativeLayout rlWeight;
    @BindView(R.id.tv_national)
    TextView tvNational;
    @BindView(R.id.rl_nativePlace)
    RelativeLayout rlNativePlace;
    @BindView(R.id.rl_cardnumber)
    RelativeLayout rlCardnumber;
    @BindView(R.id.rl_registeredPermanent)
    RelativeLayout rlRegisteredPermanent;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recyclerView_addPhoto)
    RecyclerView recyclerViewAddPhoto;
    @BindView(R.id.tv_gender)
    TextView tvGender;
    @BindView(R.id.tv_weight)
    EditText etWeight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_height)
    EditText etHeight;
    @BindView(R.id.et_cardnumber)
    EditText etCardnumber;
    @BindView(R.id.et_nativePlace)
    EditText etNativePlace;
    @BindView(R.id.et_registeredPermanent)
    EditText etRegisteredPermanent;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<String> genderList = new ArrayList<String>();
    private ArrayList<String> nativeList = new ArrayList<String>();
    private StudentInfo studentInfo;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personalinformation);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        selImageList = new ArrayList<>();
        if (null!=extras){
            studentInfo = (StudentInfo) extras.getSerializable("studentInfo");
            etName.setText(studentInfo.getStuName());
            tvGender.setText(studentInfo.getSex());
            etHeight.setText(studentInfo.getHeight()+"");
            etWeight.setText(studentInfo.getWeight()+"");
            tvNational.setText(studentInfo.getNation());
            etNativePlace.setText(studentInfo.getPlace());
            etRegisteredPermanent.setText(studentInfo.getAddress());
            etCardnumber.setText(studentInfo.getIdCard()) ;
            String img = studentInfo.getPic();
            if (!"".equals(img)&&null!=img){
                String[] split = img.split(";");
                List<String> strings = Arrays.asList(split);
                ArrayList<ImageItem> imageItems = new ArrayList<>();
                for (int i = 0; i < strings.size(); i++) {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path=Constant.infoUrl+studentInfo.getStudentUserId()+"/info/"+strings.get(i);
                    imageItems.add(imageItem);
                }
                selImageList.addAll(imageItems);
            }

        }

        genderList.add("男");
        genderList.add("女");
        nativeList.add("汉");
        nativeList.add("蒙族");
        nativeList.add("回族");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("个人信息");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");

        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        recyclerViewAddPhoto.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewAddPhoto.setHasFixedSize(true);
        recyclerViewAddPhoto.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.ll_name, R.id.ll_gender, R.id.rl_height, R.id.rl_weight, R.id.rl_national, R.id.rl_cardnumber, R.id.rl_registeredPermanent, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.ll_name:
                break;
            case R.id.ll_gender:
                ViewUtils.alertBottomWheelOption(this, genderList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvGender.setText(genderList.get(postion));
                    }

                });
                break;
            case R.id.rl_height:
                break;
            case R.id.rl_weight:
                break;
            case R.id.rl_national:
                ViewUtils.alertBottomWheelOption(this, nativeList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvNational.setText(nativeList.get(postion));
                    }

                });
                break;
            case R.id.rl_nativePlace:
                break;
            case R.id.rl_cardnumber:
                break;
            case R.id.rl_registeredPermanent:
                break;
            case R.id.tv_recommend:

                EventBus.getDefault().post("personInformation");
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                //请求网络
                HttpUtils instance = HttpUtils.getinstance(this);
                String name = etName.getText().toString();
                String gender = tvGender.getText().toString();
                String height = etHeight.getText().toString();
                String weight = etWeight.getText().toString();
                String cardnumber = etCardnumber.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ShowToastUtils.showToastMsg(this, "请输入姓名");
                    return;
                } else if (TextUtils.isEmpty(gender)) {
                    ShowToastUtils.showToastMsg(this, "请选择性别");
                    return;
                } else if (TextUtils.isEmpty(height)) {
                    ShowToastUtils.showToastMsg(this, "请输入身高");
                    return;
                } else if (TextUtils.isEmpty(weight)) {
                    ShowToastUtils.showToastMsg(this, "请输入体重");
                    return;
                } else if (TextUtils.isEmpty(cardnumber)) {
                    ShowToastUtils.showToastMsg(this, "请输入身份证号");
                    return;
                }

                Map map = new HashMap();
                Map mapDate = new HashMap();
                if (null!=studentInfo){
                    mapDate.put("id", studentInfo.getId()+"");
                }
                mapDate.put("studentUserId", userId+"");
                mapDate.put("resumeId", userId+"");
                mapDate.put("stuName", name);
                mapDate.put("sex", gender);
                mapDate.put("height", height+"");
                mapDate.put("weight", weight+"");
                mapDate.put("nation", tvNational.getText().toString());
                mapDate.put("place",etNativePlace.getText().toString() );
                mapDate.put("address",etRegisteredPermanent.getText().toString());
                mapDate.put("idCard",cardnumber+"");

                String  birth = cardnumber.substring(6, 10) + "-" + cardnumber.substring(10, 12) + "-" + cardnumber.substring(12, 14);
                mapDate.put("birth",birth);

                final ArrayList<File> files = new ArrayList<File>();

                //TODO 以前的图片怎么处理呢？
                String sb="";
                for (ImageItem slist:selImageList) {
                    //网络来得图片
                    if (slist.path.startsWith("http")){
                        String fileName = slist.path.substring(slist.path.lastIndexOf("/")+1);
                        sb+=fileName+";";
                    }else{
                        String path = slist.path;
                        File file = new File(path);
                        files.add(file);
                    }
                }

                map.put("requestData", mapDate.toString()+"#"+sb);
                instance.uploadFiles("studentPic",Constant.STUDENTINFO, map, files,new BaseCallback<String>() {
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
                        commonDialog.dismiss();
                        LogUtil.e(s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ShowToastUtils.showToastMsg(PersonalInformationActivity.this, jsonObject.getString("resMessage"));
                            if (jsonObject.getString("rescode").equals("200")) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response response, int errorCode, Exception e) {
                        commonDialog.dismiss();
                    }
                });


//                finish();
                break;

        }
    }

    @Override
    public void onItemClick(View V, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                View view = LayoutInflater.from(PersonalInformationActivity.this).inflate(R.layout.layout_popupwindow, null);
                TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
                TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
                TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setOutsideTouchable(true);
                View parent = LayoutInflater.from(PersonalInformationActivity.this).inflate(R.layout.activity_main, null);
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
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        popupWindow.dismiss();
                    }
                });
                btnPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开选择,本次允许选择的数量
                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                        Intent intent1 = new Intent(PersonalInformationActivity.this, ImageGridActivity.class);
                        startActivityForResult(intent1, REQUEST_CODE_SELECT);
                        popupWindow.dismiss();
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
}
