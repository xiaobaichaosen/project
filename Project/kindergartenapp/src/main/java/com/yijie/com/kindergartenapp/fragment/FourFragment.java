package com.yijie.com.kindergartenapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.MainActivity;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.regist.PerfectInformationActivity;
import com.yijie.com.kindergartenapp.activity.regist.RegistKindActivity;
import com.yijie.com.kindergartenapp.base.BaseFragment;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class FourFragment extends BaseFragment {
    RegistKindActivity tempActivity;
    @BindView(R.id.btn_four)
    Button btnThree;
    Unbinder unbinder;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    Unbinder unbinder1;

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    private String titleString;

    @Override
    protected void initView() {
        tempActivity = (RegistKindActivity) getActivity();
        tvMessage.setText(titleString);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onInvisible() {

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_four;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != unbinder) {
            unbinder.unbind();

        }
        unbinder1.unbind();
    }

    @OnClick(R.id.btn_four)
    public void onViewClicked() {
        commonDialog.show();
        String  userId = (String) SharedPreferencesUtils.getParam(mActivity, "userId", "");
        Intent intent = new Intent();
        intent.setClass(mActivity, MainActivity.class);
        startActivity(intent);
        mActivity.finish();

//        Set<String> tags = new HashSet<String>();
//        JPushInterface.setAliasAndTags(mActivity, "", tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int i, String s, Set<String> set) {
//            }
//        });
//        //重新设置推送tags
//        tags.clear();
//        JPushInterface.setAliasAndTags(mActivity, userId + "", tags, new TagAliasCallback() {
//            @Override
//            public void gotResult(int code, String s, Set<String> set) {
//                switch (code) {
//                    case 0:
//                        //这里可以往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                        LogUtil.e("Set tag and alias success极光推送别名设置成功");
//                        commonDialog.dismiss();
//                        Intent intent = new Intent();
//                        intent.setClass(mActivity, MainActivity.class);
//                        startActivity(intent);
//                        mActivity.finish();
//                        break;
//                    case 6002:
//                        //极低的可能设置失败 我设置过几百回 出现3次失败 不放心的话可以失败后继续调用上面那个方面 重连3次即可 记得return 不要进入死循环了...
//                        LogUtil.e("Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试");
//                        commonDialog.dismiss();
//                        break;
//                    default:
//                        LogUtil.e("极光推送设置失败，Failed with errorCode = " + code);
//                        commonDialog.dismiss();
//                        break;
//                }
//
//            }
//        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
