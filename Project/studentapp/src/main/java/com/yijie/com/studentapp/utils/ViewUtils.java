package com.yijie.com.studentapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.view.CustomDialog;
import com.yijie.com.studentapp.view.MyPopuList;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
     * 弹出时间选择
     *
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型
     * @param format   时间格式化
     * @param callBack 时间选择回调
     */
    public static void alertTimerPicker(Context context, TimePickerView.Type type, final String format, final TimerPickerCallBack callBack) {
        TimePickerView pvTime = new TimePickerView(context, type);
        //控制时间范围
        //        Calendar calendar = Calendar.getInstance();
        //        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(true);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
//                        tvTime.setText(getTime(date));
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                callBack.onTimeSelect(sdf.format(date));
            }
        });
        pvTime.setTextSize(16);
        //弹出时间选择器
        pvTime.show();
    }
    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMethod(Activity act) {
        View view = act.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) act
                    .getSystemService(act.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
