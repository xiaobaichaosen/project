package com.yijie.com.kindergartenapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.adapter.FilterAdapter;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.KindergartenMember;
import com.yijie.com.kindergartenapp.bean.SchoolAdress;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

public class FiveFragment extends BaseFragment {
    RegistKindActivity tempActivity;
    @BindView(R.id.btn_five)
    Button btnFive;
    Unbinder unbinder;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    Unbinder unbinder1;
    @BindView(R.id.tv_text)
    TextView tvText;

    public KindergartenMember getKindergartenMember() {
        return kindergartenMember;
    }

    public void setKindergartenMember(KindergartenMember kindergartenMember) {
        this.kindergartenMember = kindergartenMember;
    }

    private KindergartenMember kindergartenMember;
    private OnButtonClick onButtonClick;//2、定义接口成员变量

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

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(String string);
    }

    @Override
    protected void initView() {
        tempActivity = (RegistKindActivity) getActivity();
        tvMessage.setText("向" +  kindergartenMember.getKindName() );
        tvText.setText("发送加入申请");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInvisible() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_five;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();

        }
        unbinder1.unbind();
    }

    @OnClick(R.id.btn_five)
    public void onViewClicked() {
        regist();

    }

    /**
     * 注册用户
     */
    private void regist() {
        HttpUtils getinstance = HttpUtils.getinstance(mActivity);
        getinstance.postJson(Constant.regKinderMember, kindergartenMember, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
//                loading.showError();
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(o);
                    String rescode= jsonObject.getString("rescode");
                    String resMessage= jsonObject.getString("resMessage");
                    ShowToastUtils.showToastMsg(mActivity,resMessage);

                    if (rescode.equals("200")){
                        tempActivity.stepView.setStep(4);
                        if (onButtonClick != null) {
                            onButtonClick.onClick("发送成功");
                        }
                        int userId = jsonObject.getJSONObject("data").getInt("id");
                        int kinderId = jsonObject.getJSONObject("data").getInt("kinderId");
                        String cellphone = jsonObject.getJSONObject("data").getString("cellphone");
                        SharedPreferencesUtils.setParam(mActivity, "userId",userId+"");
                        SharedPreferencesUtils.setParam(mActivity, "kinderId",kinderId+"");
                        SharedPreferencesUtils.setParam(mActivity, "cellphone",cellphone);;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commonDialog.dismiss();

            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                commonDialog.dismiss();
//                statusLayoutManager.showErrorLayout();
            }
        });

    }
}
