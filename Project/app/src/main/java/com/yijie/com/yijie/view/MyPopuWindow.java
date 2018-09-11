package com.yijie.com.yijie.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lvfq.pickerview.TimePickerView;
import com.lvfq.pickerview.view.*;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.utils.ViewUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 奕杰平台 on 2018/5/17.
 */

public class MyPopuWindow extends com.lvfq.pickerview.view.BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private View btnSubmit, btnCancel;
    private onConfirm onClick;

    public MyPopuWindow(Context context) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_popupwindow, contentContainer);
        // -----确定和取消按钮
//        btnSubmit = findViewById(R.id.btn_camera);
//        btnSubmit.setTag(TAG_SUBMIT);
//        btnCancel = findViewById(R.id.btn_cancel);
//        btnCancel.setTag(TAG_CANCEL);
//        btnSubmit.setOnClickListener(this);
//        btnCancel.setOnClickListener(this);

        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        btnCarema.setText("是否确认删除该条消息?");
         btnCarema.setTextColor(context.getResources().getColor(R.color.item_content));
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
         btnPhoto.setText("确定");
          btnPhoto.setTextColor(Color.parseColor("#F26665"));
          TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
          btnCancel.setTextColor(context.getResources().getColor(R.color.item_title));
        btnPhoto.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnPhoto.setTag(TAG_SUBMIT);
        btnCancel.setTag(TAG_CANCEL);
    }
    /**
     * 点击事件回调
     */
    public interface onConfirm {
        void onClick();
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
        } else {
            onClick.onClick();
            return;
        }
    }
}
