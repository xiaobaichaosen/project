package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.OptionsPickerView;
import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.JsonBean;
import com.yijie.com.studentapp.bean.JsonFileReader;
import com.yijie.com.studentapp.bean.StudentContact;
import com.yijie.com.studentapp.bean.StudentResume;
import com.yijie.com.studentapp.utils.BaseCallback;
import com.yijie.com.studentapp.utils.HttpUtils;
import com.yijie.com.studentapp.utils.LogUtil;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;
import com.yijie.com.studentapp.utils.ShowToastUtils;
import com.yijie.com.studentapp.view.slidecontact.bean.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/1.
 */

public class RelateIntentinActivty extends BaseActivity

{
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;


    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.rl_adress)
    RelativeLayout rlAdress;
    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private Contact mContact;
    private StudentResume studentResume;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_relateintentn);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void init() {
        Bundle extras = getIntent().getExtras();
        if (null!=extras){
            studentResume = (StudentResume) extras.getSerializable("studentResume");
            tvCompanionName.setText(studentResume.getExpectPartener());
            tvAdress.setText(studentResume.getExpectWorkPlace());
        }
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        initJsonData();
        title.setText("相关意向");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.rl_compay, R.id.rl_adress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_adress:
                showOptions();
                break;
            case R.id.tv_recommend:
                String userId = (String) SharedPreferencesUtils.getParam(this, "id", "");
                HttpUtils getinstance = HttpUtils.getinstance(this);
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                HashMap<String, String> requestData = new HashMap<>();
                if (null!=studentResume){
                    stringStringHashMap.put("id", studentResume.getId()+"");
                }
                stringStringHashMap.put("studentUserId", userId);
                stringStringHashMap.put("expectWorkPlace", tvAdress.getText().toString());
                stringStringHashMap.put("expectWorkPlace", tvAdress.getText().toString());
                stringStringHashMap.put("expectPartener", tvCompanionName.getText().toString());
                if (null==mContact){
                    stringStringHashMap.put("companionId", Integer.MAX_VALUE+"");
                }else {
                    stringStringHashMap.put("companionId", mContact.getId()+"");
                }

                requestData.put("requestData",stringStringHashMap.toString());
                getinstance.post(Constant.STUDENTRESUME, requestData, new BaseCallback<String>() {
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
                        commonDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            ShowToastUtils.showToastMsg(RelateIntentinActivty.this, jsonObject.getString("resMessage"));
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
//                statusLayoutManager.showErrorLayout();
                    }
                });
                break;

            case R.id.rl_compay:
                Intent intent = new Intent();
                intent.setClass(this, CompanyActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleSomethingElse(Contact contact) {
        mContact = contact;
        tvCompanionName.setText(contact.getName());
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
                JsonBean

                    entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);


                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
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
                if (options1Items.get(options1).getPickerViewText().equals("不限")&&options2Items.get(options1).get(option2).equals("不限")&&options3Items.get(options1).get(option2).get(options3).equals("不限")){
                    String tx = options1Items.get(options1).getPickerViewText();
                    tvAdress.setText(tx);
                }else if (options1Items.get(options1).getPickerViewText().equals("北京市")&&options2Items.get(options1).get(option2).equals("北京市")){
                    String tx =options3Items.get(options1).get(option2).get(options3);
                    tvAdress.setText(tx);
                }else {
                    String tx = options1Items.get(options1).getPickerViewText()
                            + options2Items.get(options1).get(option2)
                            + options3Items.get(options1).get(option2).get(options3);
                    tvAdress.setText(tx);
                }

            }
        });
        pvOptions.show();

    }
}

