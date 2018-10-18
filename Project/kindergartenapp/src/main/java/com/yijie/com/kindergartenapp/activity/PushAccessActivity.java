package com.yijie.com.kindergartenapp.activity;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.ImagePickerAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;


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
 * 园所对学生的评价
 */
public class PushAccessActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
;
    @BindView(R.id.rb_crowdingDegree)
    MaterialRatingBar rbCrowdingDegree;
    @BindView(R.id.rb_hygieneStatus)
    MaterialRatingBar rbHygieneStatus;
    @BindView(R.id.rb_startEndWork)
    MaterialRatingBar rbStartEndWork;
    @BindView(R.id.et_content)
    EditText etContent;

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

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_pushaccess);
    }

    @Override
    public void init() {

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
            //网络来得图片
            if (slist.path.startsWith("http")) {
                String fileName = slist.path.substring(slist.path.lastIndexOf("/") + 1);
                sb.append(fileName + ";");
            } else {
                String path = slist.path;
                File file = new File(path);
                files.add(file);
            }
        }
        stringStringHashMap.put("studentUserId", userId);
        stringStringHashMap.put("kinderId", "36");

        //拥挤程度
        stringStringHashMap.put("crowdingDegree", rbCrowdingDegree.getRating()+"");
        //卫生状况
        stringStringHashMap.put("hygieneStatus", rbHygieneStatus.getRating()+"");
        //上下班便利程度
        stringStringHashMap.put("startEndWork", rbStartEndWork.getRating()+"");
        //整体评价计算
        stringStringHashMap.put("totalEvaluate", (rbCrowdingDegree.getRating()+rbHygieneStatus.getRating()+rbStartEndWork.getRating())/7+"");
        map.put("param", stringStringHashMap.toString());

        HttpUtils.getinstance(this).uploadFiles("evaluatePic", Constant.SELECTALREADYRESUMELIST, map, files, new BaseCallback<String>() {
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
