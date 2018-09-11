package com.yijie.com.kindergartenapp.activity.regist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.CertificateActivity;
import com.yijie.com.kindergartenapp.activity.ConfigurationActivity;
import com.yijie.com.kindergartenapp.activity.CourseActivity;
import com.yijie.com.kindergartenapp.activity.ExaminationFeeActivity;
import com.yijie.com.kindergartenapp.activity.KendPictureActivity;
import com.yijie.com.kindergartenapp.activity.KenderCategoryActivity;
import com.yijie.com.kindergartenapp.activity.KenderDetailAcitivity;
import com.yijie.com.kindergartenapp.activity.KenderGradeActivity;
import com.yijie.com.kindergartenapp.activity.KinderSimple;
import com.yijie.com.kindergartenapp.activity.LicenseActivity;
import com.yijie.com.kindergartenapp.activity.UniformFreeActivity;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.bean.CourseBean;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.DisplayUtil;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/6.
 * 完善园所信息
 */

public class PerfectInformationActivity extends BaseActivity {


    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.rl_category)
    RelativeLayout rlCategory;
    @BindView(R.id.tv_grade)
    TextView tvGrade;
    @BindView(R.id.rl_grade)
    RelativeLayout rlGrade;
    @BindView(R.id.rl_configuration)
    RelativeLayout rlConfiguration;
    @BindView(R.id.tv_configuration)
    TextView tvConfiguration;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.rl_course)
    RelativeLayout rlCourse;
    @BindView(R.id.tv_uniform)
    TextView tvUniform;
    @BindView(R.id.rl_uniform)
    RelativeLayout rlUniform;
    @BindView(R.id.tv_examinationFee)
    TextView tvExaminationFee;
    @BindView(R.id.rl_examinationFee)
    RelativeLayout rlExaminationFee;
    @BindView(R.id.tv_certificate)
    TextView tvCertificate;
    @BindView(R.id.rl_certificate)
    RelativeLayout rlCertificate;
    @BindView(R.id.tv_license)
    TextView tvLicense;
    @BindView(R.id.rl_license)
    RelativeLayout rlLicense;
    @BindView(R.id.tv_picture)
    TextView tvPicture;
    @BindView(R.id.rl_picture)
    RelativeLayout rlPicture;
    @BindView(R.id.btn_regist)
    Button btnRegist;
    @BindView(R.id.tv_kenderSimple)
    TextView tvKenderSimple;
    @BindView(R.id.rl_kenderSimple)
    RelativeLayout rlKenderSimple;
    @BindView(R.id.et_peoplenum)
    EditText etPeoplenum;
    @BindView(R.id.et_classnum)
    EditText etClassnum;
    private String summary;
    private CommonBean tempSimpleCommonBean, tempCategoryCommonBean, tempGradeCommonBean, tempConfigCommonBean,
            tempUniformCommonBean, tempExaminationCommonBean, tempCertificateCommonBean, tempPictureCommonBean, tempLicenseCommonBean;
    private CourseBean tempCourseBean;
    private KindergartenDetail modityKindergartenDetail;
    private int userId;
    public void setContentView() {
        setContentView(R.layout.activity_perfectinformation);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void init() {

        userId = (int) SharedPreferencesUtils.getParam(PerfectInformationActivity.this, "userId", 0);
        title.setText("完善园所信息");
        tvRecommend.setText("跳过");
        back.setVisibility(View.GONE);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void courseBean(CourseBean courseBean) {


        tempCourseBean = courseBean;

        //特色课程
        ArrayList<String> strings = new ArrayList<>();
        strings.clear();
        StringBuilder stringBuilder = new StringBuilder();
        if (null != tempCourseBean) {
            if (null != tempCourseBean.getDanceString()) {
                strings.add(tempCourseBean.getDanceString());
            }
            if (null != tempCourseBean.getArtString()) {
                strings.add(tempCourseBean.getArtString());
            }
            if (null != tempCourseBean.getEnglishString()) {
                strings.add(tempCourseBean.getEnglishString());
            }
            if (null != tempCourseBean.getRollString()) {
                strings.add(tempCourseBean.getRollString());
            }
            if (null != tempCourseBean.getPainoString()) {
                strings.add(tempCourseBean.getRollString());
            }
            if (null != tempCourseBean.getThoughtString()) {
                strings.add(tempCourseBean.getThoughtString());
            }
            if (null != tempCourseBean.getOtherString() && !tempCourseBean.getOtherString().equals("")) {
                strings.add(tempCourseBean.getOtherString());
            }
        }
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                stringBuilder.append(strings.get(i));
            } else {
                stringBuilder.append(strings.get(i) + "、");
            }
        }
        String  curseString = stringBuilder.toString();
        tvCourse.setText(curseString);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void bean(CommonBean commonBean) {
        if (commonBean.getType() == 0) {
            //园所类别
            tvCategory.setText(tempCategoryCommonBean.getRbString());
            tempCategoryCommonBean = commonBean;
        } else if (commonBean.getType() == 1) {
            //园所类别
            tvGrade.setText(tempGradeCommonBean.getRbString());
            tempGradeCommonBean = commonBean;
        } else if (commonBean.getType() == 2) {
//            //园所类别
//            tvConfiguration.setText("已填写");
//            tempCategoryCommonBean = commonBean;
        } else if (commonBean.getType() == 3) {
            //工服押金
            tvUniform.setText(tempUniformCommonBean.getRbString());
            tempUniformCommonBean = commonBean;
        } else if (commonBean.getType() == 9) {
            //首年体检费
            tvExaminationFee.setText(tempExaminationCommonBean.getRbString());
            tempExaminationCommonBean = commonBean;
        } else if (commonBean.getType() == 8) {
            //荣誉证书
            tvCertificate.setText("已上传");
            tempCertificateCommonBean = commonBean;
        } else if (commonBean.getType() == 6) {
            //营业执照
            tvLicense.setText("已上传");
            tempLicenseCommonBean = commonBean;
        } else if (commonBean.getType() == 7) {
            //园所照片
            tvPicture.setText("已上传");
            tempPictureCommonBean = commonBean;
            //班级配置
        } else if (commonBean.getType() == 5) {
            tvConfiguration.setText(commonBean.getRbString());
            tempConfigCommonBean = commonBean;
        } else if (commonBean.getType() == 100) {
            //园所照片
            tvKenderSimple.setText("已填写");
            summary = commonBean.getContent();
        }


    }

    @OnClick({R.id.rl_kenderSimple, R.id.tv_recommend, R.id.rl_category, R.id.rl_grade, R.id.rl_configuration, R.id.rl_course, R.id.rl_uniform, R.id.rl_examinationFee, R.id.rl_certificate, R.id.rl_license, R.id.rl_picture, R.id.btn_regist})
    public void onViewClicked(View view) {
        final Intent intent = new Intent();
        Bundle mBundle = new Bundle();
        switch (view.getId()) {

            case R.id.rl_kenderSimple:
                //跳转到园所简介
//                startActivity(intent);
                //跳转到园所简介
                CommonBean scommonBean = new CommonBean();
                scommonBean.setContent(summary);
                mBundle.putSerializable("tempSimpleCommonBean", scommonBean);
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, KinderSimple.class);
                startActivity(intent);
                break;

            case R.id.tv_recommend:
                //跳过
                commonDialog.show();

                Set<String> tags = new HashSet<String>();
                JPushInterface.setAliasAndTags(PerfectInformationActivity.this, "", tags, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                    }
                });
                //重新设置推送tags
                tags.clear();
                JPushInterface.setAliasAndTags(PerfectInformationActivity.this, userId + "", tags, new TagAliasCallback() {
                    @Override
                    public void gotResult(int code, String s, Set<String> set) {
                        switch (code) {
                            case 0:
                                //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                LogUtil.e("Set tag and alias success极光推送别名设置成功");
                                commonDialog.dismiss();
                                Intent intent = new Intent();
                                intent.setClass(PerfectInformationActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case 6002:
                                //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                                LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
                                commonDialog.dismiss();
                                break;
                            default:
                                LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
                                commonDialog.dismiss();
                                break;
                        }

                    }
                });


                break;
            case R.id.rl_category:
                //跳转到园所类别
                if (tempCategoryCommonBean != null) {
                    mBundle.putSerializable("tempCategoryCommonBean", tempCategoryCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, KenderCategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_grade:
                //跳转到园所级别
                if (tempGradeCommonBean != null) {
                    mBundle.putSerializable("tempGradeCommonBean", tempGradeCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, KenderGradeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_configuration:
                //跳转倒班级配置
                if (tempConfigCommonBean != null) {
                    mBundle.putSerializable("tempConfigCommonBean", tempConfigCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, ConfigurationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_course:
                //跳转到特色课程
                if (tempCourseBean != null) {
                    mBundle.putSerializable("tempCourseBean", tempCourseBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_uniform:
                //跳转到工服押金
                if (tempUniformCommonBean != null) {
                    mBundle.putSerializable("tempUniformCommonBean", tempUniformCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, UniformFreeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_examinationFee:
                //跳转到首年体检费
                if (tempExaminationCommonBean != null) {
                    mBundle.putSerializable("tempExaminationCommonBean", tempExaminationCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, ExaminationFeeActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_certificate:
                //跳转到荣誉证书
                if (tempCertificateCommonBean != null) {
                    mBundle.putSerializable("tempCertificateCommonBean", tempCertificateCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, CertificateActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_license:
                //跳转到荣誉证书
                if (tempLicenseCommonBean != null) {
                    mBundle.putSerializable("tempLicenseCommonBean", tempLicenseCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, LicenseActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_picture:
                //跳转到园所照片
                if (tempPictureCommonBean != null) {
                    mBundle.putSerializable("tempPictureCommonBean", tempPictureCommonBean);
                }
                intent.putExtras(mBundle);
                intent.setClass(PerfectInformationActivity.this, KendPictureActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_regist:

                //幼儿园人数
                if (!"null".equals(etPeoplenum.getText().toString().trim()) && !"".equals(etPeoplenum.getText().toString().trim())) {
                    if (!DisplayUtil.isNumeric(etPeoplenum.getText().toString().trim())) {
                        ShowToastUtils.showToastMsg(PerfectInformationActivity.this, "幼儿人数必须为数字");
                        return;
                    }
                }else if (!"null".equals(etClassnum.getText().toString().trim()) && !"".equals(etClassnum.getText().toString().trim()))
                {
                    if (!DisplayUtil.isNumeric(etClassnum.getText().toString().trim())){
                        ShowToastUtils.showToastMsg(PerfectInformationActivity.this,"班级数量必须为数字");
                        return;
                    }
                }

                getKenderDeail(String.valueOf(userId));

                break;

        }
    }

    /**
     * 通过id保存园所详情
     */
    public void ModityKenderDeail(KindergartenDetail kindergartenDetail) {
        final HttpUtils instance = HttpUtils.getinstance(PerfectInformationActivity.this);
        instance.postJson(Constant.KINDERGARTENDETAIL, kindergartenDetail, new BaseCallback<String>() {

            @Override
            public void onRequestBefore() {

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
                    String rescode = jsonObject.getString("rescode");
                    String resMessage = jsonObject.getString("resMessage");
                    if (rescode.equals("200")) {
                        Set<String> tags = new HashSet<String>();
                        JPushInterface.setAliasAndTags(PerfectInformationActivity.this, "", tags, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                            }
                        });
                        //重新设置推送tags
                        tags.clear();
                        JPushInterface.setAliasAndTags(PerfectInformationActivity.this, userId + "", tags, new TagAliasCallback() {
                            @Override
                            public void gotResult(int code, String s, Set<String> set) {
                                switch (code) {
                                    case 0:
                                        //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                                        LogUtil.e("Set tag and alias success极光推送别名设置成功");
                                        Intent intent = new Intent();
                                        intent.setClass(PerfectInformationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        commonDialog.dismiss();
                                        finish();
                                        break;
                                    case 6002:
                                        //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
                                        LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
                                        commonDialog.dismiss();
                                        break;
                                    default:
                                        LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
                                        commonDialog.dismiss();
                                        break;
                                }

                            }
                        });


                    } else if (rescode.equals("500")) {
                        ShowToastUtils.showToastMsg(PerfectInformationActivity.this, resMessage);
                        commonDialog.dismiss();
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

    }    /**
     * 通过id查询园所详情
     */
    public void getKenderDeail(String kenderId) {
        final HttpUtils instance = HttpUtils.getinstance(PerfectInformationActivity.this);
        Map map = new HashMap();
        map.put("id", kenderId);
        instance.post(Constant.KINDERGARTENDETAILBYID, map, new BaseCallback<String>() {
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
                    modityKindergartenDetail = gson.fromJson(data.toString(), KindergartenDetail.class);
                    //保存数据
                    KindergartenDetail kindergartenDetail = new KindergartenDetail();
                    //园所类别
                    if (null!=tempCategoryCommonBean){
                        kindergartenDetail.setKindType(tempCategoryCommonBean.getRbString());
                    }if (null!=tempGradeCommonBean){
                        //园所级别
                        kindergartenDetail.setKindLevel(tempGradeCommonBean.getRbString());
                    }

                    //幼儿园人数
                    if (!"null".equals(etPeoplenum.getText().toString().trim()) && !"".equals(etPeoplenum.getText().toString().trim())) {
                           kindergartenDetail.setChildrenNum(Integer.parseInt(etPeoplenum.getText().toString().trim()));
                    }
                    //班级数量
                    if (!"null".equals(etClassnum.getText().toString().trim()) && !"".equals(etClassnum.getText().toString().trim())) {
                        kindergartenDetail.setClassNum(Integer.parseInt(etClassnum.getText().toString().trim()));
                    }
                    //班级配置
                    kindergartenDetail.setClassSet(tvConfiguration.getText().toString().trim());
                    //特色课程
                    ArrayList<String> strings = new ArrayList<>();
                    strings.clear();
                    StringBuilder stringBuilder = new StringBuilder();
                    if (null != tempCourseBean) {
                        if (null != tempCourseBean.getDanceString()) {
                            strings.add(tempCourseBean.getDanceString());
                        }
                        if (null != tempCourseBean.getArtString()) {
                            strings.add(tempCourseBean.getArtString());
                        }
                        if (null != tempCourseBean.getEnglishString()) {
                            strings.add(tempCourseBean.getEnglishString());
                        }
                        if (null != tempCourseBean.getRollString()) {
                            strings.add(tempCourseBean.getRollString());
                        }
                        if (null != tempCourseBean.getPainoString()) {
                            strings.add(tempCourseBean.getRollString());
                        }
                        if (null != tempCourseBean.getThoughtString()) {
                            strings.add(tempCourseBean.getThoughtString());
                        }
                        if (null != tempCourseBean.getOtherString() && !tempCourseBean.getOtherString().equals("")) {
                            strings.add(tempCourseBean.getOtherString());
                        }
                    }
                    for (int i = 0; i < strings.size(); i++) {
                        if (i == strings.size() - 1) {
                            stringBuilder.append(strings.get(i));
                        } else {
                            stringBuilder.append(strings.get(i) + "、");
                        }
                    }
                    String  curseString = stringBuilder.toString();
                    kindergartenDetail.setFeatureCourse(curseString);
                    //工服押金
                    if (null!=tempUniformCommonBean){
                        kindergartenDetail.setClothesDeposit(tempUniformCommonBean.getRbString());
                    }
                    if (null!=tempExaminationCommonBean){
                        //首年体检费
                        kindergartenDetail.setFirstTestFee(tempExaminationCommonBean.getRbString());
                    }

                    //荣誉证书
                    //营业执照
                    //园所照片
                    //宿舍环境
                    //园所简介
                    kindergartenDetail.setSummary(summary);
                    kindergartenDetail.setId(modityKindergartenDetail.getId());
                    ModityKenderDeail(kindergartenDetail);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
            }
        });

    }

}
