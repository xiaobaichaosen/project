package com.yijie.com.kindergartenapp.fragment.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 奕杰平台 on 2018/1/29.
 */

public class StudentFragment extends BaseFragment {
    @BindView(R.id.btn_start)
    Button btnStart;
    Unbinder unbinder;
    private OnButtonClick onButtonClick;//2、定义接口成员变量
    //定义接口变量的get方法
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }
    //定义接口变量的set方法
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }
    //1、定义接口
    public interface OnButtonClick{
        public void onClick(View view);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_student;
    }

    @Override
    protected void initData() {

    }

  @OnClick({R.id.btn_start})
  public void Click(View view){
      switch (view.getId()){
          case R.id.btn_start:
              if(onButtonClick!=null){
                  onButtonClick.onClick(view);
              }
              break;

      }

  }


    @Override
    protected void lazyLoad() {

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
}
