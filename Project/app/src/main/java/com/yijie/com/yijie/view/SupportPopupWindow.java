package com.yijie.com.yijie.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class SupportPopupWindow extends PopupWindow {

    private final Activity context;

    public SupportPopupWindow(View contentView, int width, int height, Activity activity) {
        super(contentView, width, height);
        this.context=activity;
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (android.os.Build.VERSION.SDK_INT >=24) {
            int[] a = new int[2];
            anchor.getLocationInWindow(a);
            super.showAtLocation(((Activity) context).getWindow().getDecorView(), Gravity.NO_GRAVITY, 0 , a[1]+anchor.getHeight());
        } else{
            super.showAsDropDown(anchor);
        }

    }
}
