package com.yijie.com.kindergartenapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.WebViewActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistDetailActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.bean.KindergartenMember;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.CheckUserUtils;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.StutasToolUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Request;
import okhttp3.Response;

public class OneFragment extends BaseFragment {
    RegistKindActivity tempActivity;
    @BindView(R.id.btn_one)
    Button btnOne;
    Unbinder unbinder;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_verviCode)
    EditText etVerviCode;
    @BindView(R.id.tv_verviCode)
    TextView tvVerviCode;
    @BindView(R.id.et_passWord)
    EditText etPassWord;
    @BindView(R.id.cb_isVisiable)
    CheckBox cbIsVisiable;
    @BindView(R.id.et_passWord_comfirm)
    EditText etPassWordComfirm;
    @BindView(R.id.cb_isVisiable_comfirm)
    CheckBox cbIsVisiableComfirm;
    @BindView(R.id.cb_isGreen)
    CheckBox cbIsGreen;
    @BindView(R.id.tv_user_yes)
    TextView tvUserYes;
    @BindView(R.id.tv_sm_yes)
    TextView tvSmYes;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    Unbinder unbinder1;
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    private MyCountDownTimer myCountDownTimer;

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

    @OnClick({R.id.tv_verviCode, R.id.btn_one,R.id.tv_user_yes, R.id.tv_sm_yes})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        final HttpUtils instance = HttpUtils.getinstance(mActivity);
        final Map map = new HashMap();
        switch (view.getId()) {
            case R.id.tv_verviCode:
                //获取验证码
                //new倒计时对象,总共的时间,每隔多少秒更新一次时间
                //获取验证码
                if (etName.getText().toString().trim().isEmpty()) {
                    ShowToastUtils.showToastMsg(mActivity, "请填写手机号");
                    return;
                } if (etName.getText().toString().length()!=11) {
                ShowToastUtils.showToastMsg(mActivity, "手机号格式不正确");
                return;
            }
                else {

                    map.put("cellphone", etName.getText().toString());
                    instance.post(Constant.SENDSMSCODE, map, new BaseCallback<String>() {
                        @Override
                        public void onRequestBefore() {
                            commonDialog.show();
                            commonDialog.setTitle("验证码获取中...");
                        }

                        @Override
                        public void onFailure(Request request, Exception e) {
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onSuccess(Response response, String s) throws JSONException {
                            JSONObject jsonObject = new JSONObject(s);
                            String resMessage = jsonObject.getString("resMessage");
                            String rescode = jsonObject.getString("rescode");
                            ShowToastUtils.showToastMsg(mActivity, resMessage);
                            if (rescode.equals("200")){
                                etVerviCode.setText(jsonObject.getString("data"));
                                myCountDownTimer = new MyCountDownTimer(60000, 1000);
                                myCountDownTimer.start();
                            }

                            LogUtil.e("===" + s);
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            commonDialog.dismiss();
                        }
                    });

                }
                break;
            case R.id.btn_one:

                if (cbIsGreen.isChecked()) {
                    //跳转到注册详情
                    if (etName.getText().toString().trim().isEmpty()) {
                        ShowToastUtils.showToastMsg(mActivity, "请填写手机号");
                        return;
                    }
                    if (etName.getText().toString().length()!=11) {
                        ShowToastUtils.showToastMsg(mActivity, "手机号格式有误");
                        return;
                    }
                    if (etVerviCode.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(mActivity, "请填写验证码");
                        return;
                    }

                    if (etPassWord.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(mActivity, "请填写密码");
                        return;
                    }
                    if (etPassWordComfirm.getText().toString().isEmpty()) {
                        ShowToastUtils.showToastMsg(mActivity, "请填写确定密码");
                        return;
                    }
                    if (!CheckUserUtils.checkString(etPassWord.getText().toString())||!CheckUserUtils.checkString(etPassWordComfirm.getText().toString())) {
                        ShowToastUtils.showToastMsg(mActivity, "必须6-20位数字和密码混合!");
                        return;
                    }
                    if (!etPassWordComfirm.getText().toString().equals(etPassWord.getText().toString())) {
                        ShowToastUtils.showToastMsg(mActivity, "两次密码输入不一致");
                        return;
                    }
                    map.put("cellphone", etName.getText().toString().trim());
                    map.put("password", etPassWord.getText().toString());
                    map.put("verifyCode", etVerviCode.getText().toString());
                    map.put("isPassword", etPassWordComfirm.getText().toString());

                    instance.post(Constant.CHECKKINDERUSER, map, new BaseCallback<String>() {

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
                            String resMessage = null;
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String rescode = jsonObject.getString("rescode");
                                resMessage = jsonObject.getString("resMessage");
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {

                                    KindergartenMember kindergartenMember=new KindergartenMember();
                                    kindergartenMember.setCellphone(etName.getText().toString().trim());
                                    kindergartenMember.setPassword(etPassWord.getText().toString().trim());
                                    if (onButtonClick != null) {
                                        onButtonClick.onClick(kindergartenMember);
                                    }
                                    tempActivity.stepView.setStep(2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ShowToastUtils.showToastMsg(mActivity, resMessage);
                            commonDialog.dismiss();
                        }

                        @Override
                        public void onError(Response response, int errorCode, Exception e) {
                            commonDialog.dismiss();
                        }
                    });
                } else {
                    ShowToastUtils.showToastMsg(mActivity, "请先同意服务协议");
                }

                break;
            case R.id.tv_user_yes:
                intent.setClass(mActivity, WebViewActivity.class);
                intent.putExtra("user","用户服务协议");
                startActivity(intent);
                break;
            case R.id.tv_sm_yes:
                intent.setClass(mActivity, WebViewActivity.class);
                intent.putExtra("user","隐私条款");
                startActivity(intent);
                break;

        }
    }


    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(false);
                tvVerviCode.setTextColor(Color.parseColor("#F38583"));
                tvVerviCode.setText(l / 1000 + "s 后");
            }


        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            if (tvVerviCode != null) {
                tvVerviCode.setClickable(true);
                tvVerviCode.setTextColor(getResources().getColor(R.color.colorTheme));
                tvVerviCode.setText(" 获取验证码");
            }
        }
    }

    //1、定义接口
    public interface OnButtonClick {
        public void onClick(KindergartenMember kindergartenMember);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();

        }
        unbinder1.unbind();
        if (null!=myCountDownTimer){
            myCountDownTimer.cancel();
        }
    }

    @OnClick()
    public void onViewClicked() {

    }


    @Override
    protected void initView() {
        tempActivity = (RegistKindActivity) getActivity();
    }

    @Override
    protected void initData() {
        if (!isPrepared || !isVisible) {
            return;
        }

    }

    @Override
    protected void onInvisible() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_one;
    }


    @OnCheckedChanged({R.id.cb_isVisiable})
    public void OnCheckedChangeListener(CheckBox view, boolean ischanged) {
        if (ischanged) {
            etPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassWord.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassWord.setSelection(etPassWord.getText().length());//set cursor to the end
    }

    @OnCheckedChanged({R.id.cb_isVisiable_comfirm})
    public void OnCheckedChangeListenerComfirm(CheckBox view, boolean ischanged) {
        if (ischanged) {
            etPassWordComfirm.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassWordComfirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
        etPassWordComfirm.setSelection(etPassWordComfirm.getText().length());//set cursor to the end
    }
}
