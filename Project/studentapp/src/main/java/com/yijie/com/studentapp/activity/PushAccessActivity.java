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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.ImagePickerAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 发表评价
 */
public class PushAccessActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.rb_scale)
    MaterialRatingBar rbScale;
    @BindView(R.id.rb_position)
    MaterialRatingBar rbPosition;
    @BindView(R.id.rb_hardware)
    MaterialRatingBar rbHardware;
    @BindView(R.id.rb_traffic)
    MaterialRatingBar rbTraffic;
    @BindView(R.id.rb_crowdingDegree)
    MaterialRatingBar rbCrowdingDegree;
    @BindView(R.id.rb_hygieneStatus)
    MaterialRatingBar rbHygieneStatus;
    @BindView(R.id.rb_startEndWork)
    MaterialRatingBar rbStartEndWork;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.cb_isName)
    CheckBox cbIsName;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_kinderName)
    TextView tvKinderName;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.tv_detaliAdress)
    TextView tvDetaliAdress;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.iv_head)
    ImageView ivHead;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;

    RelativeLayout rlRegisteredPermanent;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private String userId;
    private int kinderId;
    boolean isAddAccess;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pushaccess);
    }

    @Override
    public void init() {
        kinderId = getIntent().getIntExtra("kinderId", 0);
        isAddAccess = getIntent().getBooleanExtra("isAddAccess", false);
        String kindeName = getIntent().getStringExtra("kindeName");
        String headPic = getIntent().getStringExtra("headPic");
        String kindAddress = getIntent().getStringExtra("kindAddress");
        String kindDetailAddress = getIntent().getStringExtra("kindDetailAddress");
        tvKinderName.setText(kindeName);
        tvAdress.setText(kindAddress);
        tvDetaliAdress.setText(kindDetailAddress);
        if (!TextUtils.isEmpty(headPic)) {
            ivHead.setBackgroundResource(R.mipmap.head);
        } else {
            ImageLoaderUtil.getImageLoader(this).displayImage(Constant.kinderUrl +kinderId + "/head_pic_/" + headPic, ivHead, ImageLoaderUtil.getPhotoImageOption());
        }

        if (isAddAccess) {
            llRoot.setVisibility(View.GONE);
        } else {
            llRoot.setVisibility(View.VISIBLE);
        }
        userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("发表评价");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("发布");
        selImageList = new ArrayList<>();
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new ImagePickerAdapter(PushAccessActivity.this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(PushAccessActivity.this);
        recyclerView.setLayoutManager(new GridLayoutManager(PushAccessActivity.this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.back, R.id.tv_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                //上传发布内容
                pushAccess();
                break;

        }
    }

    @Override
    public void onItemClick(View v, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                View view = LayoutInflater.from(PushAccessActivity.this).inflate(R.layout.layout_popupwindow, null);
                TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
                TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
                TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setOutsideTouchable(true);
                View parent = LayoutInflater.from(PushAccessActivity.this).inflate(R.layout.activity_main, null);
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
                        Intent intent = new Intent(PushAccessActivity.this, ImageGridActivity.class);
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
                        Intent intent1 = new Intent(PushAccessActivity.this, ImageGridActivity.class);
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
     * 发表评价
     */
    public void pushAccess() {
        Map map = new HashMap();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        final ArrayList<File> files = new ArrayList<File>();
        //TODO 以前的图片怎么处理呢？
        StringBuilder sb = new StringBuilder();
        for (ImageItem slist : selImageList) {
//            //网络来得图片
//            if (slist.path.startsWith("http")) {
//                String fileName = slist.path.substring(slist.path.lastIndexOf("/") + 1);
//                sb.append(fileName + ";");
//            } else {
            String path = slist.path;
            File file = new File(path);
            files.add(file);
//            }
        }
        stringStringHashMap.put("studentUserId", userId);
        stringStringHashMap.put("kinderId", kinderId + "");
        //园所规模
        stringStringHashMap.put("scale", rbScale.getRating() + "");
        //地理位置
        stringStringHashMap.put("position", rbPosition.getRating() + "");
        //硬件设施
        stringStringHashMap.put("hardware", rbHardware.getRating() + "");
        //交通便利程度
        stringStringHashMap.put("traffic", rbTraffic.getRating() + "");
        //拥挤程度
        stringStringHashMap.put("crowdingDegree", rbCrowdingDegree.getRating() + "");
        //卫生状况
        stringStringHashMap.put("hygieneStatus", rbHygieneStatus.getRating() + "");
        //上下班便利程度
        stringStringHashMap.put("startEndWork", rbStartEndWork.getRating() + "");
        //整体评价计算
        stringStringHashMap.put("totalEvaluate", (rbScale.getRating() + rbPosition.getRating() + rbHardware.getRating() + rbTraffic.getRating() + rbCrowdingDegree.getRating() + rbHygieneStatus.getRating() + rbStartEndWork.getRating()) / 7 + "");
        //是否匿名评价 1,匿名，2，不匿名
        if (cbIsName.isChecked()) {
            stringStringHashMap.put("isAnonymous", "1");
        } else {
            stringStringHashMap.put("isAnonymous", "2");
        }
        stringStringHashMap.put("evaluateContent", etContent.getText().toString().trim());
        map.put("param", stringStringHashMap.toString());
        HttpUtils.getinstance(this).uploadFiles("evaluatePic", Constant.SAVESTUDENTEVALUAGTE, map, files, new BaseCallback<String>() {
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
                    String rescode = jsonObject.getString("rescode");
                    String resMessage = jsonObject.getString("resMessage");
                    ShowToastUtils.showToastMsg(PushAccessActivity.this, resMessage);
                    if (rescode.equals("200")) {
                        finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
