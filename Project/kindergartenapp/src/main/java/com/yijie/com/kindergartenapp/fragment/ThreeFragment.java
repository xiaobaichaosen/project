package com.yijie.com.kindergartenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.ConfigurationActivity;
import com.yijie.com.kindergartenapp.activity.ExaminationFeeActivity;
import com.yijie.com.kindergartenapp.activity.KinderDetailAdressActivity;
import com.yijie.com.kindergartenapp.activity.MealsActivity;
import com.yijie.com.kindergartenapp.activity.ModiKenderDetailActivity;
import com.yijie.com.kindergartenapp.activity.StayActivity;
import com.yijie.com.kindergartenapp.activity.UniformFreeActivity;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.activity.regist.PerfectInformationActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistDetailActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.base.JsonBean;
import com.yijie.com.kindergartenapp.base.JsonFileReader;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.KindergartenMember;
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
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

public class ThreeFragment extends BaseFragment {
    RegistKindActivity tempActivity;
    @BindView(R.id.btn_three)
    Button btnThree;
    Unbinder unbinder;
    @BindView(R.id.kenderName)
    EditText kenderName;
    @BindView(R.id.tv_adress)
    TextView tvAdress;
    @BindView(R.id.rl_gradAdress)
    RelativeLayout rlGradAdress;

    @BindView(R.id.tv_stay)
    TextView tvStay;
    @BindView(R.id.rl_gradStay)
    RelativeLayout rlGradStay;
    @BindView(R.id.tv_meal)
    TextView tvMeal;
    @BindView(R.id.rl_meal)
    RelativeLayout rlMeal;
    @BindView(R.id.et_peoplenum)
    EditText etPeoplenum;
    @BindView(R.id.et_classnum)
    EditText etClassnum;
    @BindView(R.id.tv_configuration)
    TextView tvConfiguration;
    @BindView(R.id.rl_configuration)
    RelativeLayout rlConfiguration;
    @BindView(R.id.tv_uniform)
    TextView tvUniform;
    @BindView(R.id.rl_uniform)
    RelativeLayout rlUniform;
    @BindView(R.id.tv_examinationFee)
    TextView tvExaminationFee;
    @BindView(R.id.rl_examinationFee)
    RelativeLayout rlExaminationFee;
    Unbinder unbinder1;
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private  String stayString="";
    private CommonBean  tempConfigCommonBean,tempMealCommonBean,  tempUniformCommonBean, tempExaminationCommonBean;
    private StayBean tempStayBean;
    private SchoolAdress tempSchoolAdress;
    private  String kindName;
    public KindergartenMember getKindergartenMember() {
        return kindergartenMember;
    }

    public void setKindergartenMember(KindergartenMember kindergartenMember) {
        this.kindergartenMember = kindergartenMember;
    }

    private KindergartenMember kindergartenMember;
    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }

    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.rl_gradAdress, R.id.rl_gradStay, R.id.rl_meal, R.id.rl_configuration, R.id.rl_uniform, R.id.rl_examinationFee})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {
            case R.id.rl_gradAdress:
                if (null != tempSchoolAdress) {
                    mBundle.putSerializable("tempSchoolAdress", tempSchoolAdress);
                }
                intent.putExtras(mBundle);
                intent.putExtra("kindName",kindName);
                intent.setClass(mActivity, KinderDetailAdressActivity.class);
                startActivity(intent);
                break;
//            case R.id.rl_detailAdress:
//                break;
            case R.id.rl_gradStay:
                //跳转到住宿安排
                if (null != tempStayBean) {
                mBundle.putSerializable("tempStayBean", tempStayBean);

                  }
                intent.putExtras(mBundle);
                intent.setClass(mActivity, StayActivity.class);
                startActivity(intent);

                break;
            case R.id.rl_meal:
                //跳转三餐
                if (null != tempMealCommonBean) {
                    mBundle.putSerializable("tempMealCommonBean", tempMealCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(mActivity, MealsActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_configuration:
                //跳转倒班级配置
                if (tempConfigCommonBean != null) {
                    mBundle.putSerializable("tempConfigCommonBean", tempConfigCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(mActivity, ConfigurationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_uniform:
                //跳转到工服押金
                if (tempUniformCommonBean != null) {
                    mBundle.putSerializable("tempUniformCommonBean", tempUniformCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(mActivity, UniformFreeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_examinationFee:
                //跳转到首年体检费
                if (tempExaminationCommonBean != null) {
                    mBundle.putSerializable("tempExaminationCommonBean", tempExaminationCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(mActivity, ExaminationFeeActivity.class);
                startActivity(intent);
                break;
        }
    }


    //1、定义接口
    public interface OnButtonClick {
        public void onClick(String string);
    }

    @Override
    protected void initView() {
        tempActivity = (RegistKindActivity) getActivity();

        kindName = kindergartenMember.getKindName();
        kenderName.setText(kindName);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInvisible() {

    }


    @Override
    protected int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.fragment_three;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (null != unbinder) {
            unbinder.unbind();

        }
        unbinder1.unbind();
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void bean(CommonBean commonBean) {
         if (commonBean.getType() == 5) {
             //班级配置
            tvConfiguration.setText(commonBean.getRbString());
            tempConfigCommonBean = commonBean;
        }else  if (commonBean.getType() == 4) {
             //三餐安排
            tvMeal.setText(commonBean.getRbString());
            tempMealCommonBean = commonBean;
        }else if (commonBean.getType() == 3) {
//            //工服押金
            tvUniform.setText(commonBean.getRbString()+commonBean.getCbString());
            tempUniformCommonBean = commonBean;
        } else if (commonBean.getType() == 9) {
             //首年体检费
             tvExaminationFee.setText(commonBean.getRbString());
             tempExaminationCommonBean = commonBean;
         }
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void StayBean(StayBean stayBean) {
        //初始化
        stayString="";
        String upString = stayBean.getUpString();
        String downString = stayBean.getDownString();
        String outString = stayBean.getOutString();
        String foureightString = stayBean.getFoureightString();
        String eighttwelveString = stayBean.getEighttwelveString();
        String twoString = stayBean.getTwoString();
        String threeString = stayBean.getThreeString();
        String wuString = stayBean.getWuString();
        String otherString = stayBean.getOtherString();
        if (!upString.isEmpty()){
            stayString+=upString+"、";
        }if (!downString.isEmpty()){
            stayString+=downString+"、";
        }if (!outString.isEmpty()){
            stayString+=outString+"、";
        }if (!foureightString.isEmpty()){
            stayString+=foureightString+"、";
        }if (!eighttwelveString.isEmpty()){
            stayString+=eighttwelveString+"、";
        }if (!twoString.isEmpty()){
            stayString+=twoString+"、";
        }if (!threeString.isEmpty()){
            stayString+=threeString+"、";
        }if (!wuString.isEmpty()){
            stayString+=wuString+"、";
        }if (!otherString.isEmpty()){
            stayString+=otherString+"、";
        }

        stayString = stayString.substring(0, stayString.length() - 1);
        tvStay.setText(stayString);
        tempStayBean=stayBean;
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void SchoolAdress(SchoolAdress schoolAdress) {
        tempSchoolAdress=schoolAdress;
        tvAdress.setText(tempSchoolAdress.getName()+tempSchoolAdress.getDetailAdress());

    }
    @OnClick(R.id.btn_three)
    public void onViewClicked() {
        regist();

    }
    /**
     * 注册用户
     */
    private void regist() {

        final String kendergradName = kenderName.getText().toString().trim();
        final String kenderAdress = tvAdress.getText().toString().trim();
        //三餐安排
        final String mealString = tvMeal.getText().toString().trim();
        //幼儿人数
        final String peopleNum = etPeoplenum.getText().toString().trim();
        //班级数量
        final String classNum = etClassnum.getText().toString().trim();

        if (TextUtils.isEmpty(kendergradName)) {
            ShowToastUtils.showToastMsg(mActivity, "请填写园所全称");
            return;
        } else if (TextUtils.isEmpty(kenderAdress)) {
            ShowToastUtils.showToastMsg(mActivity, "请填写园所地址");
            return;
        }  else if (TextUtils.isEmpty(stayString)) {
            ShowToastUtils.showToastMsg(mActivity, "请填写住宿安排");
            return;
        } else if (TextUtils.isEmpty(mealString)) {
            ShowToastUtils.showToastMsg(mActivity, "请填写三餐安排");
            return;
        }else if (TextUtils.isEmpty(peopleNum)){
            ShowToastUtils.showToastMsg(mActivity, "请填写幼儿人数");
            return;
        }else if (TextUtils.isEmpty(classNum)){
            ShowToastUtils.showToastMsg(mActivity, "请填写班级数量");
            return;
        }else if (null == tempConfigCommonBean) {
            ShowToastUtils.showToastMsg(mActivity, "请填写班级配置");
            return;
        }else if (null == tempUniformCommonBean) {
            ShowToastUtils.showToastMsg(mActivity, "请填写工服押金");
            return;
        }else if (null == tempExaminationCommonBean) {
            ShowToastUtils.showToastMsg(mActivity, "请填写首年体检费");
            return;
        }

        //调用注册接口
        Map map = new HashMap();
        map.put("cellphone", kindergartenMember.getCellphone());
        map.put("password", kindergartenMember.getPassword());
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
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
                    int userId = jsonObject.getJSONObject("data").getInt("id");

                    String cellphone = jsonObject.getJSONObject("data").getString("cellphone");
                    SharedPreferencesUtils.setParam(mActivity, "userId",userId+"");

                    SharedPreferencesUtils.setParam(mActivity, "cellphone",cellphone);;
                    if (rescode.equals("200")) {
                        KindergartenDetail kindergartenDetail = new KindergartenDetail();
                        kindergartenDetail.setKindName(kendergradName);
                        kindergartenDetail.setKindAddress(tempSchoolAdress.getName());
                        kindergartenDetail.setLatitude(tempSchoolAdress.getLat());
                        kindergartenDetail.setLongitude(tempSchoolAdress.getLon());
                        kindergartenDetail.setKindDetailAddress(tempSchoolAdress.getDetailAdress());
                        kindergartenDetail.setStay(stayString);
                        kindergartenDetail.setKindContact(kindergartenMember.getMemerName());
                        kindergartenDetail.setCellphone(kindergartenMember.getCellphone());
                        kindergartenDetail.setEat(mealString);
                        kindergartenDetail.setChildrenNum(Integer.parseInt(peopleNum));
                        kindergartenDetail.setClassNum(Integer.parseInt(classNum));
                        kindergartenDetail.setClassSet(tempConfigCommonBean.getRbString());
                        String rbString = tempUniformCommonBean.getRbString();
                        String cbString = tempUniformCommonBean.getCbString();
                        kindergartenDetail.setClothesDeposit(rbString+cbString);
                        kindergartenDetail.setFirstTestFee(tempExaminationCommonBean.getRbString());
                        kindergartenDetail.setCreateBy(userId);
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
                                LogUtil.e("注册成功返回园所信息"+s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String rescode1 = jsonObject.getString("rescode");
                                    if (rescode1.equals("200")){
                                        tempActivity.stepView.setStep(4);
                                        if (onButtonClick != null) {
                                            onButtonClick.onClick("注册成功");
                                        }
                                    }
//
                                    int kinderId = jsonObject.getJSONObject("data").getInt("kinderId");
                                    LogUtil.e("注册成功返回园所信息"+kinderId);
                                    SharedPreferencesUtils.setParam(mActivity, "kinderId",kinderId+"");


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

    }

}
