package com.yijie.com.studentapp.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class ShowToastUtils {


    private static Toast toast;


    public static void showToastMsg(Context context, String msg) {


        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

        } else {
            toast.cancel();
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}


