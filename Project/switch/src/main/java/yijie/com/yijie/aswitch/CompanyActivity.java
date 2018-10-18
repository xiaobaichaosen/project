package yijie.com.yijie.aswitch;


import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.BindView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import yijie.com.yijie.aswitch.bean.Contact;
import yijie.com.yijie.aswitch.widget.SideBar;

public class CompanyActivity extends KJActivity  {



    @Override
    public void setRootView() {
        setContentView(R.layout.activity_company);
    }
   int miss;


    @Override
    public void initData() {
        super.initData();

    }
    @Override
    public void initWidget() {
        super.initWidget();
        String dateTime = "01:30:55";
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("HH:mm:ss").parse(dateTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        miss = (int)formatTurnSecond(dateTime);
        Chronometer chronometer = (Chronometer) findViewById(R.id.cc);
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer ch) {
                miss++;
                ch.setText(FormatMiss(miss));
            }
        });
    }
    /*    * 将时分秒转为秒数    * */
    public long formatTurnSecond(String time) {
        String s = time;
        int index1 = s.indexOf(":");
        int index2 = s.indexOf(":", index1 + 1);
        int hh = Integer.parseInt(s.substring(0, index1));
        int mi = Integer.parseInt(s.substring(index1 + 1, index2));
        int ss = Integer.parseInt(s.substring(index2 + 1));
        return hh * 60 * 60 + mi * 60 + ss;
    }
    // 将秒转化成小时分钟秒
    public String FormatMiss(int miss) {
        String hh = miss / 3600 > 9 ? (miss / 3600 + "" ): ("0" + miss / 3600);
        String mm = (miss % 3600) / 60 > 9 ? (miss % 3600) / 60 + "" : "0" + (miss % 3600) / 60;
        String ss = (miss % 3600) % 60 > 9 ? (miss % 3600) % 60 + "" : "0" + (miss % 3600) % 60;
        return hh + ":" + mm + ":" + ss;

    }

    }
