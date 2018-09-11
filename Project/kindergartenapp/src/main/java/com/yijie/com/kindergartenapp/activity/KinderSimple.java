package com.yijie.com.kindergartenapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.adapter.ImagePickerAdapter;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.CommonBean;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.utils.ViewUtils;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import de.greenrobot.event.EventBus;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 园所简介
 */
public class KinderSimple extends BaseActivity  {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_num)
    TextView tvNum;
    //要去提交数据
    boolean isFromNet;
    //从网络来的数据
    private CommonBean tempSimpleCommonBean ;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kindersample);

    }
    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("园所简介");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("保存");
        tempSimpleCommonBean = (CommonBean) getIntent().getExtras().getSerializable("tempSimpleCommonBean");
            //添加时修改回显数据
            if (null!=tempSimpleCommonBean&&null!=tempSimpleCommonBean.getContent()){
                etContent.setText(tempSimpleCommonBean.getContent());

            }
    }

    //监听文本变化
    @OnTextChanged(value = R.id.et_content, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChanged(CharSequence s, int start, int before, int count) {
        tvNum.setText((2000 - s.length()) + "/2000");
    }

    @OnClick({R.id.tv_recommend, R.id.back})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_recommend:
                if (!etContent.getText().toString().trim().equals("")){
                    tempSimpleCommonBean.setContent(etContent.getText().toString().trim());
                }
                tempSimpleCommonBean.setType(100);
                EventBus.getDefault().post(tempSimpleCommonBean);
                finish();
                break;

            case R.id.back:
                finish();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



}
