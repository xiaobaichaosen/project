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

import com.google.gson.Gson;
import com.lvfq.pickerview.OptionsPickerView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.ImagePickerAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.JsonBean;
import com.yijie.com.studentapp.bean.JsonFileReader;
import com.yijie.com.studentapp.bean.StudentInfo;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.utils.ViewUtils;

import org.json.JSONArray;
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
    TextView etWeight;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_height)
    TextView etHeight;
    @BindView(R.id.et_cardnumber)
    EditText etCardnumber;
    @BindView(R.id.et_nativePlace)
    TextView etNativePlace;
    @BindView(R.id.et_registeredPermanent)
    TextView etRegisteredPermanent;
    @BindView(R.id.et_studentnum)
    EditText etStudentnum;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ArrayList<String> genderList = new ArrayList<String>();
    private ArrayList<String> nativeList = new ArrayList<String>();
    private ArrayList<String> heightList = new ArrayList<String>();
    private ArrayList<String> weightList = new ArrayList<String>();
    //籍贯
    private ArrayList<String> registList = new ArrayList<String>();

    private StudentInfo studentInfo;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private String array[] = {"汉族", "蒙古族", "回族", "藏族", "维吾尔族", "苗族", "彝族", "壮族", "布依族", "朝鲜族", "满族", "侗族", "瑶族", "白族", "土家族",
            "哈尼族", "哈萨克族", "傣族", "黎族", "傈僳族", "佤族", "畲族", "高山族", "拉祜族", "水族", "东乡族", "纳西族", "景颇族", "柯尔克孜族",
            "土族", "达斡尔族", "仫佬族", "羌族", "布朗族", "撒拉族", "毛南族", "仡佬族", "锡伯族", "阿昌族", "普米族", "塔吉克族", "怒族", "乌孜别克族",
            "俄罗斯族", "鄂温克族", "德昂族", "保安族", "裕固族", "京族", "塔塔尔族", "独龙族", "鄂伦春族", "赫哲族", "门巴族", "珞巴族", "基诺族"};

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personalinformation);
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        selImageList = new ArrayList<>();
        if (null != extras) {
            studentInfo = (StudentInfo) extras.getSerializable("studentInfo");
            if (null!=studentInfo){
                etName.setText(studentInfo.getStuName());
                tvGender.setText(studentInfo.getSex());
                if (0!=studentInfo.getHeight()){
                    etHeight.setText(Math.round(studentInfo.getHeight()) + "");
                }
                if (null!=studentInfo.getWeight()){
                    etWeight.setText(Math.round(studentInfo.getWeight()) + "");
                }
                tvNational.setText(studentInfo.getNation());
                etNativePlace.setText(studentInfo.getPlace());
                etRegisteredPermanent.setText(studentInfo.getAddress());
                etCardnumber.setText(studentInfo.getIdCard());
                String img = studentInfo.getPic();
                if (!"".equals(img) && null != img) {
                    String[] split = img.split(";");
                    List<String> strings = Arrays.asList(split);
                    ArrayList<ImageItem> imageItems = new ArrayList<>();
                    for (int i = 0; i < strings.size(); i++) {
                        ImageItem imageItem = new ImageItem();
                        imageItem.path = Constant.infoUrl + studentInfo.getStudentUserId() + "/info/" + strings.get(i);
                        imageItems.add(imageItem);
                    }
                    selImageList.addAll(imageItems);
                }
            }

        }
        initJsonData();
        genderList.add("男");
        genderList.add("女");
        //56个民族
        List<String> strings = Arrays.asList(array);
        nativeList.addAll(strings);

        for (int i = 140; i <= 200; i++) {
            heightList.add(i + "cm");
        }
        for (int i = 30; i <= 80; i++) {
            weightList.add(i + "kg");
        }
        for (int i = 0; i < options1Items.size(); i++) {
            registList.add(options1Items.get(i).getName());
        }
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
        String json = (String) SharedPreferencesUtils.getParam(PersonalInformationActivity.this, "user", "");
        try {
            JSONObject jsonObject = new JSONObject(json);
            String stuNumber = jsonObject.getString("stuNumber");
            String stuName = jsonObject.getString("studentName");
            etName.setText(stuName);
            etStudentnum.setText(stuNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.ll_name, R.id.ll_gender, R.id.rl_height, R.id.rl_weight, R.id.rl_national, R.id.rl_cardnumber, R.id.rl_registeredPermanent, R.id.tv_recommend, R.id.rl_nativePlace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.ll_name:
                break;
            case R.id.ll_gender:
                ViewUtils.hideSoftInputMethod(this);
                ViewUtils.alertBottomWheelOption(this, genderList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvGender.setText(genderList.get(postion));
                    }

                });
                break;
            case R.id.rl_height:
                ViewUtils.hideSoftInputMethod(this);
                ViewUtils.alertBottomWheelOption(this, heightList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        etHeight.setText(heightList.get(postion));
                    }

                });
                break;
            case R.id.rl_weight:
                ViewUtils.hideSoftInputMethod(this);
                ViewUtils.alertBottomWheelOption(this, weightList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        etWeight.setText(weightList.get(postion));
                    }

                });
                break;
            case R.id.rl_national:
                ViewUtils.hideSoftInputMethod(this);
                ViewUtils.alertBottomWheelOption(this, nativeList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvNational.setText(nativeList.get(postion));
                    }

                });
                break;
            //籍贯
            case R.id.rl_nativePlace:
                ViewUtils.hideSoftInputMethod(this);
                ViewUtils.alertBottomWheelOption(this, registList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        etNativePlace.setText(registList.get(postion));
                    }

                });
                break;
            case R.id.rl_cardnumber:
                break;
            case R.id.rl_registeredPermanent:
                ViewUtils.hideSoftInputMethod(this);
                showOptions();
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
                }   if (TextUtils.isEmpty(etStudentnum.getText().toString())) {
                        ShowToastUtils.showToastMsg(this, "请输入学号");
                        return;
                    }
                else if (TextUtils.isEmpty(height)) {
                    ShowToastUtils.showToastMsg(this, "请输入身高");
                    return;
                } else if (TextUtils.isEmpty(weight)) {
                    ShowToastUtils.showToastMsg(this, "请输入体重");
                    return;
                } else if (TextUtils.isEmpty(tvNational.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请选择民族");
                    return;
                } else if (TextUtils.isEmpty(etNativePlace.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请选择籍贯");
                    return;
                } else if (TextUtils.isEmpty(etRegisteredPermanent.getText().toString())) {
                    ShowToastUtils.showToastMsg(this, "请选择户口所在地");
                    return;
                } else if (TextUtils.isEmpty(cardnumber)) {
                    ShowToastUtils.showToastMsg(this, "请输入身份证号");
                    return;
                }
                    else if (cardnumber.length()!=18) {
                        ShowToastUtils.showToastMsg(this, "身份证号不合法");
                        return;
                    }
                Map map = new HashMap();
                Map mapDate = new HashMap();
                if (null != studentInfo&&null!=studentInfo.getIdCard()) {
                    mapDate.put("id", studentInfo.getId() + "");
                }
                mapDate.put("studentUserId", userId + "");
                mapDate.put("resumeId", userId + "");
                mapDate.put("stuName", name);
                mapDate.put("sex", gender);
                if (height.contains("cm")) {
                    mapDate.put("height", height.substring(0, height.length() - 2));
                } else {
                    mapDate.put("height", height);
                }
                if (weight.contains("kg")) {
                    mapDate.put("weight", weight.substring(0, weight.length() - 2));
                } else {
                    mapDate.put("weight", weight);
                }
                mapDate.put("nation", tvNational.getText().toString());
                mapDate.put("place", etNativePlace.getText().toString());
                mapDate.put("address", etRegisteredPermanent.getText().toString());
                mapDate.put("idCard", cardnumber + "");
                mapDate.put("stuNumber",etStudentnum.getText().toString());
                String birth = cardnumber.substring(6, 10) + "-" + cardnumber.substring(10, 12) + "-" + cardnumber.substring(12, 14);
                mapDate.put("birth", birth);

                final ArrayList<File> files = new ArrayList<File>();

                //TODO 以前的图片怎么处理呢？
                String sb = "";
                for (ImageItem slist : selImageList) {
                    //网络来得图片
                    if (slist.path.startsWith("http")) {
                        String fileName = slist.path.substring(slist.path.lastIndexOf("/") + 1);
                        sb += fileName + ";";
                    } else {
                        String path = slist.path;
                        File file = new File(path);
                        files.add(file);
                    }
                }

                map.put("requestData", mapDate.toString() + "#" + sb);
                instance.uploadFiles("studentPic", Constant.STUDENTINFO, map, files, new BaseCallback<String>() {
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

    private void showOptions() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        // 初始化三个列表数据
//        DataModel.initData(options1Items, options2Items, options3Items);


        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        //设置选择的三级单位
//        pvOptions.setLabels("省", "市", "区");
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, false, false);
        //设置默认选中的三级项目
        //监听确定选择按钮
//        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setTextSize(18);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                etRegisteredPermanent.setText(tx);
//                vMasker.setVisibility(View.GONE);
            }
        });
        pvOptions.show();

    }

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}
