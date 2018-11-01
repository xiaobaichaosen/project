package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.base.JsonBean;
import com.yijie.com.kindergartenapp.base.JsonFileReader;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.view.OptionsPickerView;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 园所详细地址
 */
public class KinderDetailAdressActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_addAdress)
    TextView tvAddAdress;
    @BindView(R.id.tv_addDetailAdress)
    TextView tvAddDetailAdress;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private SchoolAdress tempSchoolAdress;
    private String kindName;

    @Override
    public void setContentView() {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_kinderdetailadress);
    }

    @Override
    public void init() {
        initJsonData();
        title.setText("园所地址");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        tempSchoolAdress = (SchoolAdress) getIntent().getExtras().getSerializable("tempSchoolAdress");
        kindName = getIntent().getStringExtra("kindName");
        if (null != tempSchoolAdress) {
            tvAddDetailAdress.setText(tempSchoolAdress.getDetailAdress());
            tvAddAdress.setText(tempSchoolAdress.getName());

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void bean(SchoolAdress schoolAdress) {
        tempSchoolAdress=schoolAdress;
        tvAddDetailAdress.setText(tempSchoolAdress.getDetailAdress());

    }
    @OnClick({R.id.back, R.id.tv_recommend, R.id.tv_addAdress,R.id.tv_addDetailAdress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                SchoolAdress schoolAdress = new SchoolAdress();
                if (TextUtils.isEmpty(tvAddAdress.getText().toString().trim())){
                    ShowToastUtils.showToastMsg(this,"请选择地址");
                    return;
                }else {
                    schoolAdress.setName(tvAddAdress.getText().toString().trim());
                }
                if (null==tempSchoolAdress){
                    ShowToastUtils.showToastMsg(this,"请选择详细地址");
                    return;
                }else {
                    schoolAdress.setDetailAdress(tempSchoolAdress.getDetailAdress());
                }

                schoolAdress.setLat(tempSchoolAdress.getLat());
                schoolAdress.setLon(tempSchoolAdress.getLon());
                EventBus.getDefault().post(schoolAdress);
                finish();
                break;
            case R.id.tv_addAdress:
                showOptions();
                break;
            case R.id.tv_addDetailAdress:
                Intent intent=new Intent();
                intent.putExtra("kindName",kindName);
                intent.setClass(KinderDetailAdressActivity.this,PoiSearchActivity.class);
                startActivity(intent);
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
                tvAddAdress.setText(tx);
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
