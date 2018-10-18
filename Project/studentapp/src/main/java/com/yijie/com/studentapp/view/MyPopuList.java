package com.yijie.com.studentapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;


import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.utils.ViewUtils;

import java.util.ArrayList;

/**
 * Created by 奕杰平台 on 2018/5/17.
 */

public class MyPopuList extends com.lvfq.pickerview.view.BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private final ViewUtils.OnWheelViewClick click;
    private View btnSubmit, btnCancel;
    private WheelView wv_option;
    public MyPopuList(Context context, ArrayList<String> list, ViewUtils.OnWheelViewClick click) {
        super(context);
       this.click=click;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_wheel_option, contentContainer);

        wv_option = (WheelView) view.findViewById(R.id.wv_option);
        // -----确定和取消按钮
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        wv_option.setAdapter(new ArrayWheelAdapter(list));
        wv_option.setCyclic(false);



        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int top = view.findViewById(R.id.ll_container).getTop();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    int y = (int) motionEvent.getY();
                    if (y < top) {
                       dismiss();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            click.onClick(view, wv_option.getCurrentItem());
            dismiss();
            return;
        }
    }
}
