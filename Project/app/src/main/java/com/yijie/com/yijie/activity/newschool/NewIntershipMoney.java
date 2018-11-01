package com.yijie.com.yijie.activity.newschool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.CommonBean;
import com.yijie.com.yijie.bean.school.SchoolOtherFree;
import com.yijie.com.yijie.bean.school.SchoolPractice;
import com.yijie.com.yijie.bean.school.SchoolSalaryInfo;
import com.yijie.com.yijie.db.DatabaseAdapter;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.TimeUtil;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.LoadingLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/4/3.
 * 实习薪资
 */

public class NewIntershipMoney extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.to_newPerson)
    RelativeLayout toNewPerson;
    @BindView(R.id.to_newContact)
    ImageView toNewContact;
    @BindView(R.id.et_pay)
    EditText etPay;

    @BindView(R.id.et_yijie)
    EditText etYijie;

    @BindView(R.id.et_school)
    EditText etSchool;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime;
    @BindView(R.id.rl_start)
    RelativeLayout rlStart;
    @BindView(R.id.rl_end)
    RelativeLayout rlEnd;
    @BindView(R.id.loading)
    LoadingLayout loading;

    //用来存个人信息(名字和金额)
    private List<CommonBean> commonBeans;
    private CommenAdapter contactAdapter;
    //总额=学校+奕杰+多个个人
    private int countMoney;
    private int yijieMoney = 0;
    private int schooleMoney = 0;
    private int personMoney = 0;
    //是从网络过来的数据，直接替提交数据库
    private boolean isFromNet;
    //是从网络过来的数据，获取id更新
    SchoolSalaryInfo fromNetSchoolSalaryInfo;
    SchoolPractice fromNetSchoolPractices;
    //项目id用来关系实习薪资
    Integer practiceId;
    //实习薪资id，用来更新实习薪资
    private int tempSchoolSalaryInfoId = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newintershipmoney);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    public void init() {
        //清空数据 ,当新建学校完成的时候再一起清除
//        DatabaseAdapter.getIntance(NewIntershipMoney.this).deletePersonAll();
        commonBeans = new ArrayList<>();
        loading.showContent();
        practiceId = getIntent().getExtras().getInt("practiceId");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("实习薪资");
        tvRecommend.setText("保存");
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SchoolSalaryInfo tempSchoolSalaryInfo = (SchoolSalaryInfo) getIntent().getExtras().getSerializable("tempSchoolSalaryInfo");
        //回显数据
        if (tempSchoolSalaryInfo != null) {

//            //先清除数据库
            DatabaseAdapter.getIntance(NewIntershipMoney.this).deletePersonAll();
            String period = tempSchoolSalaryInfo.getPeriod();
            String[] split = period.split("-");
            tempSchoolSalaryInfoId = tempSchoolSalaryInfo.getId();
            tvStartTime.setText(split[0]);
            tvEndTime.setText(split[1]);
            etPay.setText(tempSchoolSalaryInfo.getSalary() + "");
            String otherFee = tempSchoolSalaryInfo.getOtherFee();
            BigDecimal manageFee = tempSchoolSalaryInfo.getManageFee();
            try {
                JSONObject jsonObject = new JSONObject(otherFee);
                String schoolFree = jsonObject.getString("schoolFree");
                String yijieFree = jsonObject.getString("yijieFree");
                etSchool.setText(schoolFree);
                etYijie.setText(yijieFree);
                tvCount.setText(manageFee + "");
                final JSONArray commonBeans = jsonObject.getJSONArray("commonBeans");
                if (commonBeans.length() > 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    final ArrayList<CommonBean> tempCommonBean = new ArrayList<>();
                    for (int i = 0; i < commonBeans.length(); i++) {
                        JSONObject jsonObject1 = commonBeans.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String money = jsonObject1.getString("money");
                        String name = jsonObject1.getString("name");
                        CommonBean commonBean = new CommonBean();
                        commonBean.setId(id);
                        commonBean.setMoney(money);
                        commonBean.setName(name);
                        tempCommonBean.add(commonBean);
                        //把联系人存到数据库，
                        DatabaseAdapter.getIntance(NewIntershipMoney.this).inserPersonInfo(commonBean);
                    }
                    contactAdapter = new CommenAdapter(this, tempCommonBean);
                    recyclerView.setAdapter(contactAdapter);

                    contactAdapter.setOnItemClickListener(new CommenAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(View v, int position) {
                            Intent intent = new Intent();
                            intent.setClass(NewIntershipMoney.this, NewPersonActivity.class);
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("commenModify", tempCommonBean.get(position));
                            intent.putExtras(mBundle);
                            startActivity(intent);
                        }
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        contactAdapter = new CommenAdapter(this, commonBeans);
//        IntershipMoney intershipMoney= (IntershipMoney) getIntent().getExtras().getSerializable("IntershipModify");
//        fromNetSchoolPractices =(SchoolPractice) getIntent().getSerializableExtra("fromNetSchoolPractice");
//        isFromNet = getIntent().getExtras().getBoolean("isFromNet", false);
        //新建学校得到要修改的bean回显数据
//        if (null!=intershipMoney&&!isFromNet){
//            tvStartTime.setText(intershipMoney.getStartDate());
//            tvEndTime.setText(intershipMoney.getEndDate());
//            etPay.setText(intershipMoney.getPay());
//            etSchool.setText(intershipMoney.getSchoolMoney());
//            commonBeans = DatabaseAdapter.getIntance(NewIntershipMoney.this).queryPersonAll();
//            if (commonBeans.size()>0){
//                recyclerView.setVisibility(View.VISIBLE);
//                contactAdapter = new CommenAdapter(this, commonBeans);
//                recyclerView.setAdapter(contactAdapter);
//            }
//            etYijie.setText(intershipMoney.getYijieMoney());
//            //放在文本变化覆盖值
//            tvCount.setText(intershipMoney.getCountMoney());
//        }
        //从网络过来得数据
//        if(isFromNet){
//            DatabaseAdapter.getIntance(NewIntershipMoney.this).deletePersonAll();
//            //获取实习薪资
//            getshipMoney();
//
//        }
//        if (null!=fromNetSchoolSalaryInfo){
//            //先清除数据库
//            DatabaseAdapter.getIntance(NewIntershipMoney.this).deletePersonAll();
//            tvStartTime.setText(fromNetSchoolSalaryInfo.getPeriod());
//            tvEndTime.setText(fromNetSchoolSalaryInfo.getPeriod());
//            etPay.setText(fromNetSchoolSalaryInfo.getSalary()+"");
//            String otherFee = fromNetSchoolSalaryInfo.getOtherFee();
//            BigDecimal manageFee = fromNetSchoolSalaryInfo.getManageFee();
//            try {
//                JSONObject jsonObject = new JSONObject(otherFee);
//                String schoolFree = jsonObject.getString("schoolFree");
//                String yijieFree = jsonObject.getString("yijieFree");
//                etSchool.setText(schoolFree);
//                etYijie.setText(yijieFree);
//                tvCount.setText(manageFee+"");
//                final JSONArray commonBeans = jsonObject.getJSONArray("commonBeans");
//                if (commonBeans.length()>0){
//                    recyclerView.setVisibility(View.VISIBLE);
//                    final ArrayList<CommonBean> tempCommonBean = new ArrayList<>();
//                    for (int i = 0; i < commonBeans.length(); i++) {
//                        JSONObject jsonObject1 = commonBeans.getJSONObject(i);
//                        String id = jsonObject1.getString("id");
//                        String money = jsonObject1.getString("money");
//                        String name = jsonObject1.getString("name");
//                        CommonBean commonBean = new CommonBean();
//                        commonBean.setId(id);
//                        commonBean.setMoney(money);
//                        commonBean.setName(name);
//                        tempCommonBean.add(commonBean);
//                        //把联系人存到数据库，
//                        DatabaseAdapter.getIntance(NewIntershipMoney.this).inserPersonInfo(commonBean);
//                    }
//                    contactAdapter = new CommenAdapter(this, tempCommonBean);
//                    recyclerView.setAdapter(contactAdapter);
//
//                    contactAdapter.setOnItemClickListener(new CommenAdapter.OnItemClickListener() {
//                        @Override
//                        public void onClick(View v, int position) {
//                            Intent intent = new Intent();
//                            intent.setClass(NewIntershipMoney.this, NewPersonActivity.class);
//                            Bundle mBundle = new Bundle();
//                            mBundle.putSerializable("commenModify", tempCommonBean.get(position));
//                            intent.putExtras(mBundle);
//                            startActivity(intent);
//                        }
//                    });
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//;
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.tv_recommend, R.id.to_newPerson, R.id.rl_start, R.id.rl_end})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                String startString = tvStartTime.getText().toString();
                String endString = tvEndTime.getText().toString();
                String payString = etPay.getText().toString();
                String schoolString = etSchool.getText().toString();
                if (startString.equals("")) {
                    ShowToastUtils.showToastMsg(NewIntershipMoney.this, "请填写开始时间");
                    return;
                } else if (endString.equals("")) {
                    ShowToastUtils.showToastMsg(NewIntershipMoney.this, "请填写结束时间");
                    return;
                } else if (payString.equals("")) {
                    ShowToastUtils.showToastMsg(NewIntershipMoney.this, "请填写薪资");
                    return;
                } else if (schoolString.equals("")) {
                    ShowToastUtils.showToastMsg(NewIntershipMoney.this, "请填写学校费用");
                    return;
                }

//                if(isFromNet) {

                SchoolSalaryInfo schoolSalaryInfo = new SchoolSalaryInfo();
                if (practiceId != null) {
                    if (tempSchoolSalaryInfoId != 0) {
                        schoolSalaryInfo.setId(tempSchoolSalaryInfoId);
                    }
                    schoolSalaryInfo.setSchoolPracticeId(practiceId);
                    schoolSalaryInfo.setPeriod(startString + "-" + endString);
                    schoolSalaryInfo.setSalary(BigDecimal.valueOf(Integer.parseInt(etPay.getText().toString())));
                    schoolSalaryInfo.setManageFee(BigDecimal.valueOf(Integer.parseInt(tvCount.getText().toString())));
                    SchoolOtherFree schoolOtherFree = new SchoolOtherFree();
                    schoolOtherFree.setSchoolFree(schoolString);
                    schoolOtherFree.setYijieFree(etYijie.getText().toString());
                    List selectList = DatabaseAdapter.getIntance(NewIntershipMoney.this).queryPersonAll();
                    schoolOtherFree.setCommonBeans(selectList);
                    schoolSalaryInfo.setOtherFee(new Gson().toJson(schoolOtherFree).toString());
                    updateShipMoney(schoolSalaryInfo);
                }
//                }else {
//                    IntershipMoney intershipMoney = new IntershipMoney();
//                    intershipMoney.setStartDate(startString);
//                    intershipMoney.setEndDate(endString);
//                    intershipMoney.setPay(payString);
//                    intershipMoney.setCountMoney(tvCount.getText().toString());
//                    intershipMoney.setSchoolMoney(schoolString);
//                    intershipMoney.setCommonBeans(commonBeans);
//                    intershipMoney.setYijieMoney(etYijie.getText().toString());
//                    EventBus.getDefault().post(intershipMoney);
//                }
//                finish();

                break;

            case R.id.rl_start:
                TimePickerView.Type type = TimePickerView.Type.YEAR_MONTH_DAY;
                String format = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(this, type, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        if (TimeUtil.compare_date("yyyy-MM-dd",date,TimeUtil.getCurrentDate("yyyy-MM-dd"))==1){
                            tvStartTime.setText(date);
                        }else {
                            ShowToastUtils.showToastMsg(NewIntershipMoney.this,"选择的当前日期之前的日期");
                        }
                    }
                });
                break;

            case R.id.rl_end:
                TimePickerView.Type type2 = TimePickerView.Type.YEAR_MONTH_DAY;
                String format2 = "yyyy-MM-dd";
                ViewUtils.alertTimerPicker(this, type2, format2, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        if (TimeUtil.compare_date("yyyy-MM-dd",date,TimeUtil.getCurrentDate("yyyy-MM-dd"))==1){
                            tvEndTime.setText(date);
                        }else {
                            ShowToastUtils.showToastMsg(NewIntershipMoney.this,"选择的当前日期之前的日期");
                        }

                    }
                });
                break;

            case R.id.to_newPerson:
                intent.setClass(this, NewPersonActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 更新实习薪资到数据库
     */
    private void updateShipMoney(SchoolSalaryInfo schoolSalaryInfo) {
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(NewIntershipMoney.this);
        HttpUtils getinstance = HttpUtils.getinstance(NewIntershipMoney.this);
        getinstance.postJson(Constant.UPDATESHIPMEMONY, schoolSalaryInfo, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                loading.showLoading();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                loading.showError();
            }

            @Override
            public void onSuccess(Response response, String shipMemory) {
                LogUtil.e(shipMemory);
                //将数据保存到上一个页面
                try {
                    JSONObject jsonObject = new JSONObject(shipMemory);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    SchoolSalaryInfo schoolSalaryInfo1 = gson.fromJson(data.toString(), SchoolSalaryInfo.class);
                    EventBus.getDefault().post(schoolSalaryInfo1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                loading.showContent();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                loading.showError();
            }
        });

    }

    /**
     * 简单文本金额变化计算总额
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged(value = R.id.et_school, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("") && s.toString() != "") {
            schooleMoney = Integer.parseInt(s.toString());
            tvCount.setText(schooleMoney + yijieMoney + personMoney + "");
        }
    }

    @OnTextChanged(value = R.id.et_yijie, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged1(CharSequence s, int start, int before, int count) {
        if (!s.toString().equals("") && s.toString() != "") {
            yijieMoney = Integer.parseInt(s.toString());
            tvCount.setText(schooleMoney + yijieMoney + personMoney + "");
        }

    }


    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onEventMainThread(CommonBean event) {

        commonBeans = DatabaseAdapter.getIntance(NewIntershipMoney.this).queryPersonAll();

        recyclerView.setVisibility(View.VISIBLE);
        if (contactAdapter == null) {
            contactAdapter = new CommenAdapter(this, commonBeans);
            recyclerView.setAdapter(contactAdapter);
        } else {
            contactAdapter.refreshRecycle(commonBeans);
        }
        //计算总的个人费用
        int count = 0;
        for (CommonBean commonBean : commonBeans) {
            String money = commonBean.getMoney();
            int parseIntMoney = Integer.parseInt(money);
            count += parseIntMoney;

        }
        personMoney = count;
        tvCount.setText(schooleMoney + yijieMoney + personMoney + "");
        contactAdapter.setOnItemClickListener(new CommenAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent();
                intent.setClass(NewIntershipMoney.this, NewPersonActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("commenModify", commonBeans.get(position));
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

    /**
     * 获取实习薪资
     */
    public void getshipMoney() {
        final ProgressDialog progressDialog = ViewUtils.getProgressDialog(NewIntershipMoney.this);
        HttpUtils getinstance = HttpUtils.getinstance(NewIntershipMoney.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        if (fromNetSchoolPractices != null) {
            stringStringHashMap.put("schoolPracticeId", fromNetSchoolPractices.getId() + "");
        }
        getinstance.post(Constant.SELECTSHIPMEMONY, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                progressDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                progressDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                try {
                    //学校联系人
                    JSONObject jsonObject = new JSONObject(o);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    tvStartTime.setText(jsonObject1.getString("period"));
                    tvEndTime.setText(jsonObject1.getString("period"));
                    etPay.setText(jsonObject1.getInt("salary") + "");
                    String otherFree = jsonObject1.getString("otherFee");
                    JSONObject jsonObject2 = new JSONObject(otherFree);
                    String schoolFree = jsonObject2.getString("schoolFree");
                    String yijieFree = jsonObject2.getString("yijieFree");
                    etSchool.setText(schoolFree);
                    etYijie.setText(yijieFree);
                    JSONArray jsonArray = jsonObject2.getJSONArray("commonBeans");
                    final ArrayList<CommonBean> commonBeans = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                        CommonBean commonBean = new CommonBean();
                        String id = jsonObject3.getString("id");
                        String money = jsonObject3.getString("money");
                        String name = jsonObject3.getString("name");
                        commonBean.setId(id);
                        commonBean.setName(name);
                        commonBean.setMoney(money);
                        commonBeans.add(commonBean);
                        DatabaseAdapter.getIntance(NewIntershipMoney.this).inserPersonInfo(commonBean);
                    }
                    if (commonBeans.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        contactAdapter = new CommenAdapter(NewIntershipMoney.this, commonBeans);
                        recyclerView.setAdapter(contactAdapter);
                        contactAdapter.setOnItemClickListener(new CommenAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                Intent intent = new Intent();
                                intent.setClass(NewIntershipMoney.this, NewPersonActivity.class);
                                Bundle mBundle = new Bundle();
                                mBundle.putSerializable("commenModify", commonBeans.get(position));
                                intent.putExtras(mBundle);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                progressDialog.dismiss();
            }
        });
    }
}
