package com.yijie.com.yijie.homepage.holder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;


import com.yijie.com.yijie.R;
import com.yijie.com.yijie.homepage.CustomHolder;
import com.yijie.com.yijie.homepage.utils.ToastUtil;

import java.util.List;


/**
 * 是时候撸点真正的代码了！！！
 * <p>
 * 创建人：桑小年
 * 日期：  2017/7/10.
 * <p>
 * 功能描述：
 */

public class ItemHolder extends CustomHolder<String> {
    public ItemHolder(View itemView) {
        super(itemView);
    }

    public ItemHolder(List datas, View itemView) {
        super(datas, itemView);
    }

    public ItemHolder(Context context, List lists, int itemID) {
        super(context, lists, itemID);
    }

    @Override
    public void initView(int position, List datas, Context context) {
        TextView textView = (TextView) itemView.findViewById(R.id.tv);
        final String msg = (String) datas.get(position);
//        textView.setText(msg);
//        textView.setHeight(300);
//        textView.setPadding(20, 10, 20, 10);
//        if (position % 2 == 0) {
//            textView.setBackgroundColor(Color.parseColor("#abcdef"));
//        } else {
//            textView.setBackgroundColor(Color.parseColor("#fedcba"));
//        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showTextToast(msg);
            }
        });
    }
}
