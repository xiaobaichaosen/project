package com.yijie.com.kindergartenapp.activity.regist;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.MealsActivity;
import com.yijie.com.kindergartenapp.activity.StayActivity;
import com.yijie.com.kindergartenapp.activity.sendlocation.SendLocationActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.JsonBean;
import com.yijie.com.kindergartenapp.base.JsonFileReader;
import com.yijie.com.kindergartenapp.base.PermissionsActivity;
import com.yijie.com.kindergartenapp.base.PermissionsChecker;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.bean.StayBean;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.StutasToolUtils;
import com.yijie.com.kindergartenapp.view.OptionsPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/3/29.
 */

public class RegistDetailActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.kenderName)
    EditText kenderName;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.rl_gradAdress)
    RelativeLayout rlGradAdress;
    @BindView(R.id.et_detailAdress)
    TextView etDetailAdress;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_wachat)
    EditText etWachat;
    @BindView(R.id.et_qq)
    EditText etQq;
    @BindView(R.id.et_area)
    EditText etArea;
    @BindView(R.id.tv_stay)
    TextView tvStay;
    @BindView(R.id.rl_gradStay)
    RelativeLayout rlGradStay;
    @BindView(R.id.tv_meal)
    TextView tvMeal;
    @BindView(R.id.rl_meal)
    RelativeLayout rlMeal;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.rl_detailAdress)
    RelativeLayout rlDetailAdress;
    private StayBean tempStayBean;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private CommonBean tempMealCommonBean;
    String username;
    String password;
    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,
    };

    @Override
    public void setContentView() {
        PermissionsChecker mPermissionsChecker = new PermissionsChecker(this);
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            PermissionsActivity.startActivityForResult(this, 0, PERMISSIONS);
        }
        setContentView(R.layout.activity_rgistdetail);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {

        }
    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }

    @Override
    public void init() {
        title.setText("注册信息填写");
        back.setVisibility(View.GONE);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        initJsonData();
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleSomethingElse(SchoolAdress schoolAdress) {
        etDetailAdress.setText(schoolAdress.getName());
    }

    @OnClick({R.id.rl_detailAdress, R.id.rl_gradStay, R.id.rl_meal, R.id.rl_gradAdress, R.id.btn_regist})
    public void onViewClicked(View view) {
        final Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_detailAdress:
//              intent.setClass(RegistDetailActivity.this, PoiSearchActivity.class);
                intent.setClass(RegistDetailActivity.this, SendLocationActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_gradStay:
                //跳转到住宿
                if (tempStayBean != null) {
                    mBundle.putSerializable("tempStayBean", tempStayBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(RegistDetailActivity.this, StayActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_goright:
                break;
            case R.id.rl_meal:
                //跳转三餐
                if (null != tempMealCommonBean) {
                    mBundle.putSerializable("tempMealCommonBean", tempMealCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(RegistDetailActivity.this, MealsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_gradAdress:
                showOptions();
                break;
            case R.id.btn_regist:
                //提交注册信息
                final String kendergradName = kenderName.getText().toString().trim();
                final String kenderAdress = tvAdress.getText().toString().trim();
                final String kenderContact = etContact.getText().toString().trim();
                final String kenderPhone = etPhone.getText().toString().trim();
                final String detailAdress = etDetailAdress.getText().toString().trim();

                if (TextUtils.isEmpty(kendergradName)) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写园所全称");
                    return;
                } else if (TextUtils.isEmpty(kenderAdress)) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写园所地址");
                    return;
                } else if (TextUtils.isEmpty(kenderContact)) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写联系人");
                    return;
                } else if (TextUtils.isEmpty(kenderPhone)) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写手机号码");
                    return;
                } else if (null == tempStayBean) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写住宿安排");
                    return;
                } else if (null == tempMealCommonBean) {
                    ShowToastUtils.showToastMsg(RegistDetailActivity.this, "请填写三餐安排");
                    return;
                }
                //调用注册接口
                Map map = new HashMap();
                if (StutasToolUtils.checkEmail(username)) {
                    map.put("eamil", username);
                } else {
                    map.put("cellphone", username);
                }
                map.put("password", password);
                final HttpUtils instance = HttpUtils.getinstance(RegistDetailActivity.this);
                instance.post(Constant.REGISTURL, map, new BaseCallback<String>() {
                    @Override
                    public void onRequestBefore() {
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                    }

                    @Override
                    public void onSuccess(Response response, String s) {
                        LogUtil.e(s);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String rescode = jsonObject.getString("rescode");
                            int id = jsonObject.getInt("data");
                            SharedPreferencesUtils.setParam(RegistDetailActivity.this, "userId", id);
                            SharedPreferencesUtils.setParam(RegistDetailActivity.this, "isLogin", "登录成功");
                            if (rescode.equals("200")) {
                                KindergartenDetail kindergartenDetail = new KindergartenDetail();
                                kindergartenDetail.setKindName(kendergradName);
                                kindergartenDetail.setKindAddress(kenderAdress);
                                kindergartenDetail.setKindContact(kenderContact);
                                kindergartenDetail.setCellphone(kenderPhone);
                                kindergartenDetail.setEmail(etEmail.getText().toString().trim());
                                kindergartenDetail.setWechart(etWachat.getText().toString().trim());
                                kindergartenDetail.setQq(etQq.getText().toString().trim());
                                kindergartenDetail.setArea(etArea.getText().toString().trim());
                                kindergartenDetail.setKindDetailAddress(detailAdress);
                                ArrayList<String> strings = new ArrayList<>();
                                strings.clear();
                                StringBuilder stringBuilder = new StringBuilder();
                                if (null != tempStayBean) {
                                    String otherString = tempStayBean.getOtherString();
                                    String downString = tempStayBean.getDownString();
                                    String outString = tempStayBean.getOutString();
                                    String upString = tempStayBean.getUpString();
                                    if ("" != otherString && !otherString.equals("")) {
                                        strings.add(otherString);
                                    }
                                    if (null != downString) {
                                        strings.add(downString);
                                    }
                                    if (null != outString) {
                                        strings.add(outString);
                                    }
                                    if (null != upString) {
                                        strings.add(upString);
                                    }
                                    for (int i = 0; i < strings.size(); i++) {
                                        if (i == strings.size() - 1) {
                                            stringBuilder.append(strings.get(i));
                                        } else {
                                            stringBuilder.append(strings.get(i) + "、");
                                        }
                                    }
                                    kindergartenDetail.setStay(stringBuilder.toString());
                                }
                                if (null != tempMealCommonBean) {
                                    kindergartenDetail.setEat(tempMealCommonBean.getRbString());
                                }

                                kindergartenDetail.setCreateBy(id);
                                instance.postJson(Constant.KINDERGARTENDETAIL, kindergartenDetail, new BaseCallback<String>() {

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
                                            String rescode1 = jsonObject.getString("rescode");
                                            if (rescode1.equals("200")){
                                                intent.setClass(RegistDetailActivity.this, PerfectInformationActivity.class);
                                                startActivity(intent);
                                            }

//                                            new CommomDialog(RegistDetailActivity.this, R.style.dialog, "是否继续完善园所信息", new CommomDialog.OnCloseListener() {
//                                                @Override
//                                                public void onClick(Dialog dialog, boolean confirm) {
//                                                    if (confirm) {
//                                                        intent.setClass(RegistDetailActivity.this, PerfectInformationActivity.class);
//                                                        dialog.dismiss();
//                                                    } else {
//                                                        intent.setClass(RegistDetailActivity.this, MainActivity.class);
//                                                    }
//                                                    startActivity(intent);
//                                                }
//
//                                                @Override
//                                                public void onContentClick() {
//
//                                                }
//                                            })
//                                                    .setTitle("提示").show();
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

                break;
        }
    }

    private void showOptions() {
        //选项选择器
        pvOptions = new OptionsPickerView(this);
        //三级联动效果
        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
        pvOptions.setTitle("选择城市");
        pvOptions.setCyclic(false, false, false);
        pvOptions.setTextSize(18);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3);
                tvAdress.setText(tx);
            }
        });
        pvOptions.show();

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void stay(StayBean commonBean) {
        tvStay.setText("已填写");
        tempStayBean = commonBean;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void meal(CommonBean commonBean) {
        tvMeal.setText("已填写");
        tempMealCommonBean = commonBean;
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
