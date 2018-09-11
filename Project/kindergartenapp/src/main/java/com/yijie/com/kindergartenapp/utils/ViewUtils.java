package com.yijie.com.kindergartenapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.view.CommomDialog;
import com.yijie.com.kindergartenapp.view.CustomDialog;
import com.yijie.com.kindergartenapp.view.MyPopuList;

import java.util.ArrayList;


public class ViewUtils {

    /**
     * 获取控件的高度
     */
    public static int getViewMeasuredHeight(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度
     */
    public static int getViewMeasuredWidth(View view) {
        calculateViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     */
    private static void calculateViewMeasure(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(w, h);
    }

    /**
     * 弹出底部滚轮选择
     *
     * @param context
     * @param list
     * @param click
     */
    public static void alertBottomWheelOption(final Context context, ArrayList<?> list, final OnWheelViewClick click) {
        MyPopuList myPopuList = new MyPopuList(context, (ArrayList<String>) list,click);
        myPopuList.show();


    }


    /**
     * 底部滚轮点击事件回调
     */
    public interface OnWheelViewClick {
        void onClick(View view, int postion);
    }
    /**
     * 时间选择回调
     */
    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }
    public static ProgressDialog getProgressDialog(Context context){
       ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("拼命加载中，请稍后。。。");
        return progressDialog;
    }
    public static CustomDialog getCustomDialog (Context context){
        CustomDialog progressDialog=new CustomDialog(context, R.style.CustomDialog);
        return progressDialog;
    }
}
