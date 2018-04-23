package com.yijie.com.yijie.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yijie.com.yijie.R;

/**
 * Created by 奕杰平台 on 2018/2/26.
 */

public class ShowTextViewDownPopuWindow  extends PopupWindow {
    public ShowTextViewDownPopuWindow(Context context) {
        super(context);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View contentView = LayoutInflater.from(context).inflate(R.layout.popuwindow_etlayout,
                null, false);
        setContentView(contentView);
    }

}
