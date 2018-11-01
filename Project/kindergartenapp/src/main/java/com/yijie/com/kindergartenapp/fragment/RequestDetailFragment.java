package com.yijie.com.kindergartenapp.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.RequestDetailActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/6/25.
 * 招聘发布详情碎片
 */

public class RequestDetailFragment extends BaseFragment {
    @BindView(R.id.tv_projectName)
    TextView tvProjectName;
    @BindView(R.id.tv_shcoolcity)
    TextView tvShcoolcity;
    @BindView(R.id.tv_requestnum)
    TextView tvRequestnum;
    @BindView(R.id.tv_nb)
    TextView tvNb;

    Unbinder unbinder;
    @BindView(R.id.loading)
    LinearLayout llRoot;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_getTime)
    TextView tvGetTime;
    @BindView(R.id.tv_mode)
    TextView tvMode;
    @BindView(R.id.tv_salery)
    TextView tvSalery;
    @BindView(R.id.tv_manageFree)
    TextView tvManageFree;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_totalFree)
    TextView tvTotalFree;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_ordernum)
    TextView tvOrdernum;
    @BindView(R.id.tv_payway)
    TextView tvPayway;
    @BindView(R.id.tv_createtime)
    TextView tvCreatetime;
    @BindView(R.id.tv_firstpay)
    TextView tvFirstpay;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    private int kenderNeedId;
    private StatusLayoutManager statusLayoutManager;
    //取消招聘
    private ArrayList<String> cancleList = new ArrayList<String>();

    @Override
    protected void initView() {

        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(llRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        commonDialog.show();
                        getData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        commonDialog.show();
                        getData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        //获取父activity中得详情id
        RequestDetailActivity requestDetailActivity = (RequestDetailActivity) mActivity;
        kenderNeedId = requestDetailActivity.kenderNeedId;
        cancleList.add(new String("我不想招聘了"));
        cancleList.add(new String("已经招满了"));
        cancleList.add(new String("项目选错了，想看看其他项目"));
        cancleList.add(new String("选择其他机构了"));
        cancleList.add(new String("其他原因"));
    }

    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        commonDialog.show();
        initData();

    }

    @Override
    protected void initData() {
        if (!isPrepared || isVisible) {
            return;
        }
        getData();
    }

    @Override
    protected void onInvisible() {

    }

    /**
     * 获取需求详情
     */
    private void getData() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", kenderNeedId + "");
        getinstance.post(Constant.SELECTRECRUITDETAILBYID, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
//                statusLayoutManager.showLoadingLayout();

            }

            @Override
            public void onFailure(Request request, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {

                LogUtil.e(o);
                JSONObject data = null;
                try {
                    JSONObject jsonObject = new JSONObject(o);

                    data = jsonObject.getJSONObject("data");
                    tvProjectName.setText(data.getString("projectName"));
                    tvShcoolcity.setText(data.getString("location"));
                    tvType.setText(data.getString("practiceType"));
                    tvEducation.setText(data.getString("education"));
                    tvGetTime.setText(data.getString("toBeijingTime"));
                    tvMode.setText(data.getString("manageModel"));
                    tvSalery.setText(data.getString("kinderSalarySet") + "/月");
                    tvManageFree.setText(data.getString("manageFee") + "/人");
                    tvTotalFree.setText("￥" + data.getString("amout"));
                    tvRequestnum.setText(data.getString("studentNum"));
                    tvFirstpay.setText(data.getInt("firstPayMonth")+"");
                    int status = data.getInt("status");
                    if (status==0){
                        tvStatus.setText("匹配中");
                    }else if (status==1){
                        tvStatus.setText("匹配成功");
                    }
                    tvOrdernum.setText(data.getString("orderId"));

                    List<String> strings = new ArrayList<>();
                    String dance = data.getString("dance");
                    String arts = data.getString("arts");
                    String english = data.getString("english");
                    String skidding = data.getString("skidding");
                    String piano = data.getString("piano");
                    String electronicOrgan = data.getString("electronicOrgan");
                    String other = data.getString("other");
                    String all;
                    StringBuilder stringBuilder = new StringBuilder();
                    if ("null" != dance) {
                        all = dance;
                    }
                    if ("null" != arts) {
                        strings.add(arts);
                    }
                    if ("null" != english) {
                        strings.add(english);
                    }
                    if ("null" != skidding) {
                        strings.add(skidding);
                    }
                    if ("null" != piano) {
                        strings.add(piano);
                    }
                    if ("null" != electronicOrgan) {
                        strings.add(electronicOrgan);
                    }
                    if ("null" != other) {
                        strings.add(other);
                    }
                    for (int i = 0; i < strings.size(); i++) {
                        if (i == 0) {
                            stringBuilder.append(strings.get(0) + "、");
                        } else if (i == strings.size() - 1) {
                            stringBuilder.append(strings.get(i));
                        } else {
                            stringBuilder.append(strings.get(i) + "、");
                        }
                    }
                    tvNb.setText(stringBuilder.toString());
                    tvCreatetime.setText(data.getString("createTime"));
                    statusLayoutManager.showSuccessLayout();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_requestdetail;
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

    @OnClick({R.id.tv_cancle, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_phone:
                new CommomDialog(mActivity, R.style.dialog, "您确定拨打电话么？", new CommomDialog.OnCloseListener() {


                    @Override
                    public void onClick(Dialog dialog, boolean confirm, String sContent) {
                        if (confirm) {
                            call(tvPhone.getText().toString().trim());
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onContentClick() {

                    }
                })
                        .setTitle("提示").show();
                break;
            case R.id.tv_cancle:
                ViewUtils.alertBottomWheelOption(mActivity, cancleList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        ShowToastUtils.showToastMsg(mActivity, cancleList.get(postion));
                    }

                });


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
}
