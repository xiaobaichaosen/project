package com.yijie.com.studentapp.view.schoolcalender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.yijie.com.studentapp.utils.LogUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 自定义的日历控件
 * Created by xiaozhu on 2016/8/1.
 */
public class CalendarView extends View {

    private Context context;
    /**
     * 画笔
     */
    private Paint paint;
    /***
     * 当前的时间
     */
    private Calendar calendar;
    private OnSelectChangeListener listener;

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        return super.onCreateDrawableState(extraSpace);
    }

    /**
     * 改变日期，并更改当前状态，由于绘图是在calendar基础上进行绘制的，所以改变calendar就可以改变图片
     *
     * @param calendar
     */
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        if ((calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR)).equals(DayManager.getCurrentTime())) {
            DayManager.setCurrent(DayManager.getTempcurrent());
        } else {
            DayManager.setCurrent(-1);
        }
        invalidate();
    }

    public CalendarView(Context context) {
        super(context);
        this.context = context;
        //初始化控件
        initView();
    }


    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //初始化控件
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //初始化控件
        initView();
    }

    /***
     * 初始化控件
     */
    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        calendar = Calendar.getInstance();
        DayManager.setCurrent(calendar.get((Calendar.DAY_OF_MONTH)));
        DayManager.setTempcurrent(calendar.get(Calendar.DAY_OF_MONTH
        ));
        DayManager.setCurrentTime(calendar.get(Calendar.MONTH) + "" + calendar.get(Calendar.YEAR));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取day集合并绘制
        List<Day> days = DayManager.createDayByCalendar(calendar, getMeasuredWidth(), getMeasuredHeight());
        for (Day day : days) {
            day.drawDays(canvas, context, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            //判断点击的是哪个日期
            float x = event.getX();
            float y = event.getY();
            //计算点击的是哪个日期
            int locationX = (int) (x * 7 / getMeasuredWidth());
            int locationY = (int) ((calendar.getActualMaximum(Calendar.WEEK_OF_MONTH) + 1) * y / getMeasuredHeight());
//
            LogUtil.e("========"+locationY);
            if (locationY == 0) {
                LogUtil.e("========0"+locationY);
                return super.onTouchEvent(event);
            } else if (locationY == 1) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                if (locationX < calendar.get(Calendar.DAY_OF_WEEK) - 1) {
                    LogUtil.e("========1"+locationY);
                    return super.onTouchEvent(event);

                }
                //后来加的，否则点击日历，会跳转月份
            }  else if (locationY == 6) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                    LogUtil.e("========6"+locationY);
                    return super.onTouchEvent(event);

            }

            else if (locationY == calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                if (locationX > calendar.get(Calendar.DAY_OF_WEEK) + 1) {
                    LogUtil.e("========2"+locationY);
                    return super.onTouchEvent(event);
                }
            }
            calendar.set(Calendar.WEEK_OF_MONTH, (int) locationY);
            calendar.set(Calendar.DAY_OF_WEEK, (int) (locationX + 1));
            DayManager.setSelect(calendar.get(Calendar.DAY_OF_MONTH));
//
            if (listener!=null){
                listener.selectChange(this,calendar.getTime());
            }
            invalidate();

        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置日期选择改变监听
     * @param listener
     */
    public void setOnSelectChangeListener (OnSelectChangeListener listener){

        this.listener = listener;
    }

    /**
     * 日期选择改变监听的接口
     */
    public interface OnSelectChangeListener {
        void selectChange(CalendarView calendarView, Date date);
    }


}
