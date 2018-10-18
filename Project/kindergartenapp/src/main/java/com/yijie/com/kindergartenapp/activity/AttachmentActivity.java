package com.yijie.com.kindergartenapp.activity;

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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.ImagePickerAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

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
 *  宿舍环境
 */
public class AttachmentActivity extends BaseActivity implements ImagePickerAdapter.OnRecyclerViewItemClickListener{
 
             public static final int IMAGE_ITEM_ADD = -1;
     public static final int REQUEST_CODE_SELECT = 100;
     public static final int REQUEST_CODE_PREVIEW = 101;
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
     CommonBean commonBean = new CommonBean();
             private ImagePickerAdapter adapter;
     private ArrayList<ImageItem> selImageList; //
     private int maxImgCount = 8;
     private CommonBean tempPictureCommonBean;
     private String userId;
    private String kinderId;

    @Override
     public void setContentView() {
                 setContentView(R.layout.activity_honorarycertificate);
             }
     @Override
     public void init() {
         userId = (String) SharedPreferencesUtils.getParam(AttachmentActivity.this, "cellphone", "");
         kinderId = (String) SharedPreferencesUtils.getParam(AttachmentActivity.this, "kinderId", "");
                 setColor(this, getResources().getColor(R.color.appBarColor)); // 鏀瑰彉鐘舵€佹爮鐨勯¢滆壊
                 setTranslucent(this); // 鏀瑰彉鐘舵€佹爮鍙樻垚閫忔槑
                 title.setText("宿舍环境");
                 tvRecommend.setVisibility(View.VISIBLE);
                 tvRecommend.setText("保存");
                 selImageList = new ArrayList<>();
                 adapter = new ImagePickerAdapter(AttachmentActivity.this, selImageList, maxImgCount);
                 adapter.setOnItemClickListener(AttachmentActivity.this);
                 recyclerView.setLayoutManager(new GridLayoutManager(AttachmentActivity.this, 4));
                 recyclerView.setHasFixedSize(true);
                 recyclerView.setAdapter(adapter);
                 getKenderDeail(userId);
         
                     }
     /**
       * 閫氳繃id鏌ヨ¯㈠洯鎵€璇︽儏
       */
             public void getKenderDeail(String kenderId) {
                 final HttpUtils instance = HttpUtils.getinstance(this);
                 Map map = new HashMap();
                 map.put("cellphone", kenderId);

                 instance.post(Constant.SELECTBYCELLPHONE, map, new BaseCallback<String>() {
 
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
                                                 KindergartenDetail modityKindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);
                                                 String attachment = modityKindergartenDetail.getAttachment();
                                                 //绗¬浜屾¬¤繘鏉ワ紝鏈夌綉缁滃浘鐗
                                                         if (!TextUtils.isEmpty(attachment)){
                                                         String[] split = attachment.split(";");
                                                         List<String> strings = Arrays.asList(split);
                                                         ArrayList<ImageItem> imageItems = new ArrayList<>();
                                                         for (int i = 0; i < strings.size(); i++) {
                                                                 ImageItem imageItem = new ImageItem();
                                                                 imageItem.path= Constant.certificateUrl+ kinderId +"/attachment/" +strings.get(i);
                                                                 imageItems.add(imageItem);
                                                             }
                                                         selImageList.addAll(imageItems);
                                                             adapter.setImages(selImageList);
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
 
             @OnClick({R.id.back,R.id.tv_recommend})
     public void onViewClicked(View view) {
                 switch (view.getId()) {
                         case R.id.back:
                                 finish();
                                 break;
                         case R.id.tv_recommend:
                                 //涓婁紶鍥剧墖
                                         uploadPicture();
                 
                                         break;
             
                             }
             }
 
             @Override
     public void onItemClick(View v, int position) {
                 switch (position) {
                         case IMAGE_ITEM_ADD:
                                 View view = LayoutInflater.from(AttachmentActivity.this).inflate(R.layout.layout_popupwindow, null);
                                 TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
                                 TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
                                 TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
                                 final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                 popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                                 popupWindow.setOutsideTouchable(true);
                                 View parent = LayoutInflater.from(AttachmentActivity.this).inflate(R.layout.activity_main, null);
                                 popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                                 popupWindow.setAnimationStyle(R.style.PopupAnimation);
                                 popupWindow.setClippingEnabled(true);
                                 popupWindow.update();
                                 //popupWindow鍦ㄥ脊绐楃殑鏃跺€欒儗鏅¯鍗婇€忔槑
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
                                                         Intent intent = new Intent(AttachmentActivity.this, ImageGridActivity.class);
                                                         intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 鏄¯鍚︽槸鐩存帴鎵撳紑鐩告満
                                                         startActivityForResult(intent, REQUEST_CODE_SELECT);
                                                         popupWindow.dismiss();
                                                     }
                 });
                                 btnPhoto.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                                                 //鎵撳紑閫夋嫨,鏈¬娆″厑璁搁€夋嫨鐨勬暟閲
                                                         ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                                 Intent intent1 = new Intent(AttachmentActivity.this, ImageGridActivity.class);
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
                                 //鎵撳紑棰勮§
                                         Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                                 intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                                 intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                                 intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                                 startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                                 break;
                     }
             }
     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                 super.onActivityResult(requestCode, resultCode, data);
                 if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                         //娣诲姞鍥剧墖杩斿洖
                                 if (data != null && requestCode == REQUEST_CODE_SELECT) {
                                 ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                                 if (images != null){
                                         selImageList.addAll(images);
                                         adapter.setImages(selImageList);
                                     }
                             }
                     } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
                         //棰勮§堝浘鐗囪繑鍥
                                 if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                                 ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                                 if (images != null){
                                         selImageList.clear();
                                         selImageList.addAll(images);
                                         adapter.setImages(selImageList);
                                     }
                             }
                     }
         
                     }
 
             /**
       * 涓婁紶鍥­鎵€鐜¯澧冨浘鐗
       *
       */
             public void uploadPicture(){
                 HashMap<String, String> stringStringHashMap = new HashMap<>();
                 final ArrayList<File> files = new ArrayList<File>();
                 //TODO 浠ュ墠鐨勫浘鐗囨€庝箞澶勭悊鍛¢锛
                         StringBuilder sb=new StringBuilder();
                 for (ImageItem slist:selImageList) {

                                 if (slist.path.startsWith("http")){
                                 String fileName = slist.path.substring(slist.path.lastIndexOf("/")+ 1);
                                 sb.append(fileName +";");
                             }else{
                                 String path = slist.path;
                                 File file = new File(path);
                                 files.add(file);
                             }
                     }
                 stringStringHashMap.put("cellphone",userId);
                 stringStringHashMap.put("imagePath",sb.toString());
                 HttpUtils.getinstance(this).uploadFiles("headload",Constant.ATTACHMENTUPLOAD,stringStringHashMap,files, new BaseCallback<String>() {
             @Override
             public void onRequestBefore() {
                                 commonDialog.show();
                             }
 
                     @Override
             public void onFailure(Request request, Exception e) {
                                 commonDialog.dismiss();
                             }
 
                     @Override
             public void onSuccess(Response response, String s) throws JSONException {
                                 LogUtil.e(s);
                                 JSONObject jsonObject = new JSONObject(s);
                                 String rescode = jsonObject.getString("rescode");
                                 String resMessage = jsonObject.getString("resMessage");
                                 ShowToastUtils.showToastMsg(AttachmentActivity.this,resMessage);
                                 if (rescode.equals("200")){
                                         CommonBean commonBean = new CommonBean();
                                         commonBean.setType(13);
                                         EventBus.getDefault().post(commonBean);
                                         finish();
                                     }
                                 commonDialog.dismiss();
                             }
 
                     @Override
             public void onError(Response response, int errorCode, Exception e) {
                                 commonDialog.dismiss();
                             }
         });
             }
 
         }
 
