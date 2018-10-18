package com.yijie.com.kindergartenapp.fragment.me;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
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
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.KenderDetailAcitivity;
import com.yijie.com.kindergartenapp.activity.ModiKenderDetailActivity;
import com.yijie.com.kindergartenapp.activity.SettingAcitivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.headportrait.ClipImageActivity;
import com.yijie.com.kindergartenapp.headportrait.util.FileUtil;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.ImageLoaderUtil;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    Unbinder unbinder;
    @BindView(R.id.rl_kendMessage)
    RelativeLayout rlKendMessage;
    @BindView(R.id.rl_requst)
    RelativeLayout rlRequst;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_kendMessage)
    TextView tvKendMessage;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_contactName)
    TextView tvContactName;

    File tempFile;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    @BindView(R.id.iv_headImage)
    ImageView ivHeadImage;
    @BindView(R.id.tv_warn)
    TextView tvWarn;
    private String cropImagePath;
    private String headPic;

    @Override
    public void onResume() {
        isPrepared = true;
        initData();
        super.onResume();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        title.setText("我的");
        back.setVisibility(View.GONE);
        actionItem.setVisibility(View.VISIBLE);
        actionItem.setBackgroundResource(R.mipmap.student_title_setting);
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }
        String userId = (String) SharedPreferencesUtils.getParam(mActivity, "cellphone", "");
        getKenderDeail(userId);

    }

    @Override
    protected void onInvisible() {

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

    @OnClick({R.id.rl_kendMessage, R.id.rl_requst, R.id.action_item, R.id.iv_headImage,R.id.tv_warn})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {


            case R.id.rl_kendMessage:
                intent.setClass(mActivity, KenderDetailAcitivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.rl_requst:
                break;

            case R.id.tv_warn:
                new CommomDialog(mActivity, R.style.dialog, "园所信息填写的选项及字段越全,完整度就会越高,从而获得实习生简历投递的几率就会越高。", new CommomDialog.OnCloseListener() {


                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String sContent) {
                        if (confirm){
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onContentClick() {

                    }
                })     .setNegativeButtonInV(false)
                        .setPositiveButton("我知道了")
                        .setTitle("提示").show();
                break;
            case R.id.action_item:

                intent.setClass(mActivity, SettingAcitivity.class);
                mActivity.startActivity(intent);
                break;
            case R.id.iv_headImage:
                uploadHeadImage();
                break;
        }
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

                    if (null == headPic) {

                    } else {
                        stringStringHashMap.put("imagePath", headPic);
                    }
                    LogUtil.e("=====" + headPic);

//                    stringStringHashMap.put("studentUserId", userId);
//                    HttpUtils.getinstance(mActivity).uploadFiles("headPic",Constant.HEADPICUPLOAD, stringStringHashMap, files, new BaseCallback<String>() {
//                        @Override
//                        public void onRequestBefore() {
//
//                        }
//
//                        @Override
//                        public void onFailure(Request request, Exception e) {
//                            LogUtil.e("======"+e);
//                        }
//
//                        @Override
//                        public void onSuccess(Response response, String o) {
//                            LogUtil.e("======"+o);
//                            try {
//                                JSONObject jsonObject = new JSONObject(o);
//                                String rescode = jsonObject.getString("rescode");
//                                if (rescode.equals("200")){
//                                    JSONObject data1 = jsonObject.getJSONObject("data");
//                                    String headPic = data1.getString("headPic");
//                                    LogUtil.e("url==="+Constant.certificateUrl+kindergartenDetail.getId()+"/head_pic_/"+headPic);
//                                    ImageLoaderUtil.getImageLoader(mActivity).displayImage(Constant.certificateUrl+kindergartenDetail.getId()+"/head_pic_/"+headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
//                                }
//                                ShowToastUtils.showToastMsg(mActivity, jsonObject.getString("resMessage"));
////                                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
////                                ivHeadImage.setImageBitmap(bitMap);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onError(Response response, int errorCode, Exception e) {
//                            LogUtil.e("======"+e);
//                        }
//                    });

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

    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
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
                    KindergartenDetail kindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);
                    String kinderIntegrity = kindergartenDetail.getKinderIntegrity();
                    String replace = kinderIntegrity.replace("%", "");
                    long round = Math.round(Double.parseDouble(replace));
                    tvKendMessage.setText(round+"%");
                    Integer auditStatus = kindergartenDetail.getAuditStatus();
                    if (auditStatus == 1) {
                        tvStatus.setText(kindergartenDetail.getKindName());
                    } else {
                        tvStatus.setText("审核中");
                    }
                    tvContactName.setText(kindergartenDetail.getKindContact());

                    headPic = kindergartenDetail.getHeadPic();
                    if (headPic == null || headPic.equals("")) {
                        ivHeadImage.setBackgroundResource(R.mipmap.head);

                    } else {

                        ImageLoaderUtil.getImageLoader(mActivity).displayImage(Constant.certificateUrl + kindergartenDetail.getId() + "/head_pic_/" + headPic, ivHeadImage, ImageLoaderUtil.getPhotoImageOption());
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

}
