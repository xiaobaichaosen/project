package com.yijie.com.yijie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.fragment.SchoolDetailFragment;
import com.yijie.com.yijie.fragment.SchoolMemeryFragment;
import com.yijie.com.yijie.fragment.school.ProjectListFragment;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.view.CircleImageView;
import com.yijie.com.yijie.view.CommomDialog;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/5/16.
 * 学校详情
 */

public class TempActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.collapse_toolbar)
    CollapsingToolbarLayout collapseToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    private int maxImgCount = 1;               //允许选择图片最大数
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;
    @BindView(R.id.iv_schoolPic)
    CircleImageView ivSchoolPic;
    @BindView(R.id.tv_schoolName)
    TextView tvSchoolName;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_schoolName_edit)
    TextView tvSchoolNameEdit;
    //学校详情碎片
    private SchoolDetailFragment schoolDetailFragment;
    //备忘录碎片
    private SchoolMemeryFragment memeryFragment;
    //项目列表
    private ProjectListFragment projectListFragment;
    //用来判断   actionItem是哪个图片，来触发点击事件 1==项目，2==备忘录
    private int current;
    public String schoolId;
    //是编辑还是完成
    public boolean changeText;
    //学校详情中联系人的编辑按钮
    TextView tvContactEdit;
    public String schoolName;
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_temp);
    }

    @Override
    public void init() {
        schoolName = getIntent().getStringExtra("schoolName");
        schoolId = getIntent().getStringExtra("schoolId");
        String schoolContact = getIntent().getStringExtra("schoolContact");
        String schoolContactPhone = getIntent().getStringExtra("schoolContactPhone");
        String logoPath = getIntent().getStringExtra("logo");
        String telephone = getIntent().getStringExtra("zjNumber");
        title.setText(schoolName);
        tvSchoolName.setText(schoolName);
        if (TextUtils.isEmpty(schoolContactPhone)) {
            tvContact.setText(schoolContact + " " + telephone);
        } else {
            tvContact.setText(schoolContact + " " + schoolContactPhone);
        }

        if (null == logoPath || "" == logoPath) {
            ivSchoolPic.setBackgroundResource(R.mipmap.logo);
        } else {
            //加载网络图片
            String[] split = logoPath.split(";");
            ImageLoaderUtil.getImageLoader(this).displayImage(Constant.getheadUrl + schoolId + "/head_pic_/" + split[0], ivSchoolPic, ImageLoaderUtil.getPhotoImageOption());
        }
        collapseToolbar.setTitleEnabled(false);

        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tvRecommend.setText("编辑");
        selImageList = new ArrayList<>();
        //添加标签
        tabLayout.addTab(tabLayout.newTab().setText("学校信息"));
        tabLayout.addTab(tabLayout.newTab().setText("项目列表"));
        tabLayout.addTab(tabLayout.newTab().setText("备忘录"));
        int page = getIntent().getExtras().getInt("page");
        if (page == 3) {
            //从极光跳转过来得数据，默认选择备忘录
            tabLayout.getTabAt(2).select();
            Bundle extras = getIntent().getExtras();
            String schoolId = extras.getString("schoolId");
            String schoolName1 = extras.getString("schoolName");
            this.schoolId = schoolId;
            title.setText(schoolName1);
            initMemeryFragment();
        } else {
            tabLayout.getTabAt(0).select();
            initSchoolDetailFragmentFragment();
        }
        //绑定tab点击事件
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    tvRecommend.setVisibility(View.VISIBLE);
                    actionItem.setVisibility(View.GONE);
                    initSchoolDetailFragmentFragment();
                    tvRecommend.setText("编辑");
                } else if (position == 1) {
                    initProjectListFragment();
                    tvRecommend.setVisibility(View.GONE);
                    actionItem.setVisibility(View.VISIBLE);
                    actionItem.setBackgroundResource(R.mipmap.new_school);
                    current = 1;
                } else if (position == 2) {
                    initMemeryFragment();
                    appBarLayout.setExpanded(false);
                    tvRecommend.setVisibility(View.GONE);
                    actionItem.setVisibility(View.VISIBLE);
                    actionItem.setBackgroundResource(R.mipmap.no_vode);
                    current = 2;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initSchoolDetailFragmentFragment() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (schoolDetailFragment == null) {
            schoolDetailFragment = new SchoolDetailFragment();
        }
        transaction.replace(R.id.main_frame_layout, schoolDetailFragment);
        //提交事务
        transaction.commit();
    }


    private void initProjectListFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (projectListFragment == null) {
            projectListFragment = new ProjectListFragment();
        }
        transaction.replace(R.id.main_frame_layout, projectListFragment);
        transaction.commit();
    }

    //
    private void initMemeryFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (memeryFragment == null) {
            memeryFragment = new SchoolMemeryFragment();
        }
        transaction.replace(R.id.main_frame_layout, memeryFragment);
        transaction.commit();
    }

    /**
     * 设置自定义的tabitem
     *
     * @param name
     * @param iconID
     * @return
     */
    private View tab_icon(String name, int iconID) {
        View newtab = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView textTitle = (TextView) newtab.findViewById(R.id.tv_title);
        textTitle.setText(name);
        TextView textIcon = (TextView) newtab.findViewById(R.id.tv_icon);
        textIcon.setBackgroundResource(iconID);
        return newtab;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.action_item, R.id.tv_schoolName_edit, R.id.tv_contact, R.id.iv_schoolPic})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_schoolPic:
                if (tvRecommend.getText().equals("完成")) {
                    View vPopu = LayoutInflater.from(TempActivity.this).inflate(R.layout.layout_popupwindow, null);
                    TextView btnCarema = (TextView) vPopu.findViewById(R.id.btn_camera);
                    TextView btnPhoto = (TextView) vPopu.findViewById(R.id.btn_photo);
                    TextView btnCancel = (TextView) vPopu.findViewById(R.id.btn_cancel);
                    final PopupWindow popupWindow = new PopupWindow(vPopu, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                    popupWindow.setOutsideTouchable(true);
                    View parent = LayoutInflater.from(TempActivity.this).inflate(R.layout.activity_main, null);
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
                            Intent intent = new Intent(TempActivity.this, ImageGridActivity.class);
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
                            Intent intent1 = new Intent(TempActivity.this, ImageGridActivity.class);
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
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_schoolName_edit:
                //修改学校名称
                intent.putExtra("schoolId", schoolId);
                intent.putExtra("schoolName", schoolName);
                intent.setClass(TempActivity.this, ReNameSchoolAcitivty.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_contact:
                tvContact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new CommomDialog(TempActivity.this, R.style.dialog, "您确定拨打电话么？", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm, String string) {
                                if (confirm) {
                                    call("15865125759");
                                    dialog.dismiss();
                                }

                            }
                        })
                                .setTitle("提示").show();
                    }
                });
                break;
            case R.id.tv_recommend:
                TextView tvContactEdit = schoolDetailFragment.getView().findViewById(R.id.tv_contact_edit);
                TextView tvSimpleEdit = schoolDetailFragment.getView().findViewById(R.id.tv_simple_edit);
                if (changeText) {
                    changeText = !changeText;
                    tvRecommend.setText("编辑");
                    tvContactEdit.setVisibility(View.GONE);
                    tvSimpleEdit.setVisibility(View.GONE);
                    tvSchoolNameEdit.setVisibility(View.GONE);
                } else {
                    changeText = !changeText;
                    tvRecommend.setText("完成");
                    tvContactEdit.setVisibility(View.VISIBLE);
                    tvSimpleEdit.setVisibility(View.VISIBLE);
                    tvSchoolNameEdit.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.action_item:

                if (current == 1) {
                    intent.putExtra("schoolId", schoolId);
                    intent.setClass(TempActivity.this, ProjectActivity.class);
                    startActivity(intent);
                } else {
                    ShowToastUtils.showToastMsg(TempActivity.this, "备忘录");
                }
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
                }
                LogUtil.e("selImageList==" + selImageList.get(0).path);
                upLoadHead(schoolId);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                }
                LogUtil.e("selImageList==" + selImageList.get(0).path);
                upLoadHead(schoolId);
            }
        }
    }

    /**
     * 调用拨号界面
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    /**
     * 上传头像
     *
     * @param
     */
    public void upLoadHead(String schoolId) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        ArrayList<File> files = new ArrayList<File>();
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
        stringStringHashMap.put("schoolId", schoolId);
        stringStringHashMap.put("imagePath", sb.toString());
        HttpUtils.getinstance(TempActivity.this).uploadFiles(Constant.headUrl, stringStringHashMap, files, new BaseCallback<String>() {
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
                ShowToastUtils.showToastMsg(TempActivity.this, "修改成功");
                finish();
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });
    }

}
