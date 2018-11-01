package com.yijie.com.yijie.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.bean.bean.KindergartenDetail;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by 奕杰平台 on 2018/5/17.
 */

public class MyKinderWindow extends com.lvfq.pickerview.view.BasePickerView implements View.OnClickListener {
    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCLE = "cancle";
    private onConfirm onClick;

    public MyKinderWindow(Context context,KindergartenDetail kindergartenDetail) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.layout_popukinder, contentContainer);

        TextView tvKindName = (TextView) view.findViewById(R.id.tv_kindName);
        TextView tvKindDetail = (TextView) view.findViewById(R.id.tv_kindDetail);
        TextView tvStuNumber = (TextView) view.findViewById(R.id.tv_stuNumber);
        MaterialRatingBar materialRatingBar = (MaterialRatingBar) view.findViewById(R.id.mrb_star);
        tvStuNumber.setText("在职实习生:"+kindergartenDetail.getIncumbencyNum());
        tvKindName.setText(kindergartenDetail.getKindName());
        String wholeEvaluate = kindergartenDetail.getWholeEvaluate();
        if (null!=wholeEvaluate){
            materialRatingBar.setRating(Float.parseFloat(wholeEvaluate));
        }
          TextView tvDtail = (TextView) view.findViewById(R.id.tv_detail);

        tvDtail.setOnClickListener(this);

        tvDtail.setTag(TAG_SUBMIT);
        view.setTag(TAG_CANCLE);
        view.setOnClickListener(this);
        view.setOnClickListener(this);
    }
    /**
     * 点击事件回调
     */
    public interface onConfirm {
        void OnCheck();
    }

    public void  setOnClick(onConfirm onClick){
        this.onClick=onClick;
    }
    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();

        if(tag.equals(TAG_SUBMIT)){
            onClick.OnCheck();
            return;
        }
        if(tag.equals(TAG_CANCLE)){
            dismiss();
            return;
        }
    }
}
