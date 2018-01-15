package com.yijie.com.yijie.activity.newschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.db.ContactBean;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.view.DataModel;
import com.yijie.com.yijie.view.OptionsPickerView;
import com.yijie.com.yijie.view.ProvinceBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by 奕杰平台 on 2018/1/2.
 * 新建学校
 */

public class NewSchoolActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_newEducation)
    EditText tvNewEducation;

    @BindView(R.id.tv_newSchoolAdress)
    TextView tvNewSchoolAdress;
    @BindView(R.id.to_newSchoolAdress)
    TextView toNewSchoolAdress;
    @BindView(R.id.et_detalAdress)
    EditText etDetalAdress;

    @BindView(R.id.tv_newType)
    TextView tvNewType;
    @BindView(R.id.to_newContact)
    ImageView toNewContact;
    @BindView(R.id.tv_line)
    TextView tvLine;
    @BindView(R.id.tv_newSchoolSample)
    TextView tvNewSchoolSample;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.to_newSchoolPicture)
    TextView toNewSchoolPicture;
    @BindView(R.id.tv_newAppointmenttime)
    TextView tvNewAppointmenttime;
    @BindView(R.id.to_newAppointmenttime)
    TextView toNewAppointmenttime;
    @BindView(R.id.tv_newNote)
    TextView tvNewNote;
    @BindView(R.id.to_newNodetal)
    TextView toNewNodetal;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.line_or_v)
    View lineOrV;
    private OptionsPickerView pvOptions;
    private ArrayList<JsonBean> options1Items = new ArrayList<JsonBean>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newschool);
        EventBus.getDefault().register(this);

    }

    @Override
    public void init() {
        setColor(NewSchoolActivity.this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(NewSchoolActivity.this); // 改变状态栏变成透明
        title.setText("新建");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        initJsonData();

        //清空数据库
        DatabaseAdapter.getIntance(this).deleteAll();


    }


    @OnClick({R.id.back, R.id.to_newSchoolAdress, R.id.to_newAppointmenttime, R.id.to_newContact})
    public void Click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.to_newSchoolAdress:


                showOptions();
                break;
            case R.id.to_newAppointmenttime:

                intent.setClass(this, NewInternshipDetailActivity.class);
                startActivity(intent);
            break;
            case R.id.to_newContact:

                intent.setClass(this, NewContactActivity.class);
                startActivity(intent);

                break;


        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void helloEventBus(String message) {
        final List<ContactBean> list = DatabaseAdapter.getIntance(NewSchoolActivity.this).queryAll();
        recyclerView.setVisibility(View.VISIBLE);
        if (list.size() > 0) {
            toNewContact.setImageResource(R.mipmap.add_contact);
            lineOrV.setVisibility(View.INVISIBLE);
        } else {
            lineOrV.setVisibility(View.VISIBLE);
            toNewContact.setImageResource(R.mipmap.goright);
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContactAdapter contactAdapter = null;
        if (contactAdapter == null) {
            contactAdapter = new ContactAdapter(this, list);
            recyclerView.setAdapter(contactAdapter);
        } else {
            contactAdapter.notifyDataSetChanged();
        }
        contactAdapter.setOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent();
                intent.setClass(NewSchoolActivity.this, NewContactActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("contactModify", list.get(position));
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                tvNewSchoolAdress.setText(tx);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
