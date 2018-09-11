package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.adapter.CardAdapter;
import com.yijie.com.yijie.adapter.ImagePickerAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.school.School;
import com.yijie.com.yijie.bean.school.SchoolMain;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/1/22.
 * 学校简介
 */

public class NewSchoolIntroduction extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    //要去提交数据
    boolean isFromNet;
    //从网络来的数据
   private SchoolMain fromNetSchoolMain;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newschoolintroduction);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("学校简介");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");    selImageList = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            fromNetSchoolMain = (SchoolMain)extras.getSerializable("schoolMain");
            MessageBean messageBean= (MessageBean) extras.getSerializable("sampleModify");
            isFromNet = extras.getBoolean("isFromNet", false);
            //添加时修改回显数据
            if (null!=messageBean){
                etContent.setText(messageBean.getContent());
                selImageList.addAll(messageBean.getSelImageList());
            }
            //网络过来回显数据
            if (null!=fromNetSchoolMain){
                etContent.setText(fromNetSchoolMain.getContent());
                //TODO 图片待处理
                String img = fromNetSchoolMain.getImg();
                if (!"".equals(img)&&null!=img){
                    String[] split = img.split(";");
                    List<String> strings = Arrays.asList(split);
                    ArrayList<ImageItem> imageItems = new ArrayList<>();
                    for (int i = 0; i < strings.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path=Constant.getheadUrl+fromNetSchoolMain.getId()+"/summary/"+strings.get(i);
                        imageItems.add(imageItem);
                    }
                    selImageList.addAll(imageItems);
                }
            }

        }


        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    //监听文本变化
    @OnTextChanged(value = R.id.et_content, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        tvNum.setText((2000 - s.length()) + "/2000");
    }

    @OnClick({R.id.tv_recommend, R.id.back})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_recommend:
                if (isFromNet){
                    SchoolMain schoolMain = new SchoolMain();
                    schoolMain.setContent(etContent.getText().toString().trim());
                    schoolMain.setId(fromNetSchoolMain.getId());
                    schoolMain.setCreateBy(fromNetSchoolMain.getCreateBy());
                    updateSchoolSimple(schoolMain);
                }else{
                    EventBus.getDefault().post(new MessageBean( etContent.getText().toString().trim(),selImageList));
                    finish();
                }
                break;
            case R.id.back:
                finish();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                View view = LayoutInflater.from(NewSchoolIntroduction.this).inflate(R.layout.layout_popupwindow, null);
                TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
                TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
                TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setOutsideTouchable(true);
                View parent = LayoutInflater.from(NewSchoolIntroduction.this).inflate(R.layout.activity_main, null);
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
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
                        Intent intent = new Intent(NewSchoolIntroduction.this, ImageGridActivity.class);
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
                        Intent intent1 = new Intent(NewSchoolIntroduction.this, ImageGridActivity.class);
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

    /**
     * 修改学校简介
     * @param schoolMain
     */
    public void updateSchoolSimple(SchoolMain schoolMain){
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ArrayList<File> files = new ArrayList<File>();
        //TODO 以前的图片怎么处理呢？
        StringBuilder sb=new StringBuilder();
         for (ImageItem slist:selImageList) {
             //网络来得图片
             if (slist.path.startsWith("http")){
                 String fileName = slist.path.substring(slist.path.lastIndexOf("/")+1);
                sb.append(fileName+";");
             }else{
                 String path = slist.path;
                 File file = new File(path);
                 files.add(file);
             }
        }
        schoolMain.setImg(sb.toString());
        Gson gson=new Gson();
        stringStringHashMap.put("parm",gson.toJson(schoolMain).toString());
//                HttpUtils.getinstance(NewSchoolIntroduction.this).post(Constant.UPDATESCHOOLSIMPLE,stringStringHashMap, new BaseCallback<String>() {
        HttpUtils.getinstance(NewSchoolIntroduction.this).uploadFiles(Constant.UPDATESCHOOLSIMPLE,stringStringHashMap,files, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String s) throws JSONException {
                LogUtil.e("============"+s);
                JSONObject jsonObject = new JSONObject(s);
                progressDialog.dismiss();
                String resMessage = jsonObject.getString("resMessage");
                ShowToastUtils.showToastMsg(NewSchoolIntroduction.this,resMessage);
                String rescode = jsonObject.getString("rescode");
                if (rescode.equals("200")){
                    finish();
                }
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }

        });

    }

}
