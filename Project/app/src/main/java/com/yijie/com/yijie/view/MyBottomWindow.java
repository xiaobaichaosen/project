package com.yijie.com.yijie.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.yijie.R;

/**
 * Created by 奕杰平台 on 2018/5/17.
 */

public class MyBottomWindow extends com.lvfq.pickerview.view.BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private static final String TAG_CAREMA = "carema";

    private View btnSubmit, btnCancel;
    private onConfirm onClick;

    public MyBottomWindow(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow, contentContainer);

        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        btnCarema.setText("高德地图");
         btnCarema.setTextColor(context.getResources().getColor(R.color.colorTheme));
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
         btnPhoto.setText("百度地图");
          TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
          btnCancel.setTextColor(context.getResources().getColor(R.color.item_title));
        btnCarema.setOnClickListener(this);
        btnPhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnCarema.setTag(TAG_CAREMA);
        btnPhoto.setTag(TAG_SUBMIT);
        btnCancel.setTag(TAG_CANCEL);
    }
    /**
     * 点击事件回调
     */
    public interface onConfirm {
        void onGaoClick();
        void onBaiClick();
    }

    public void  setOnClick(onConfirm onClick){
        this.onClick=onClick;
    }
    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else if(tag.equals(TAG_CAREMA)){
            onClick.onGaoClick();
            return;
        }
        else if(tag.equals(TAG_SUBMIT)){
            onClick.onBaiClick();
            return;
        }
    }
}
