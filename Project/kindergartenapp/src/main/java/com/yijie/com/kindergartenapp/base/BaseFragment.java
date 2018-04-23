package com.yijie.com.kindergartenapp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    Unbinder unbinder;
    /**
     * 获得全局的，防止使用getActivity()为空
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }

    protected boolean isVisible;
    protected boolean isPrepared;
    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
//            isVisible = false;
//            onInvisible();

        }
    }
    protected void onVisible(){
        lazyLoad();
    }
    protected abstract void lazyLoad();
//    protected abstract void onInvisible();
//    /**
//     * 打开activity
//     */
//    protected void openActivity(Class<?> cls) {
//        openActivity(mActivity, cls);
//        mActivity.   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//    }
//    /**
//     * 打开activity
//     */
//    public static void openActivity(Context context, Class<?> cls) {
//        Intent intent = new Intent(context, cls);
//        context.startActivity(intent);
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity)
                .inflate(getLayoutId(), container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    /**
     * 执行数据的加载
     */
    protected abstract void initData();


}