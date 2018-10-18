package com.yijie.com.kindergartenapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.lvfq.pickerview.TimePickerView;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.fragment.DayFragment;
import com.yijie.com.kindergartenapp.fragment.MouthFragment;
import com.yijie.com.kindergartenapp.utils.ViewUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增班次
 */
public class NewClassSetActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.SignName)
    EditText SignName;
    @BindView(R.id.radio_one)
    RadioButton radioOne;
    @BindView(R.id.radio_two)
    RadioButton radioTwo;
    @BindView(R.id.main_tab_RadioGroup)
    RadioGroup mainTabRadioGroup;

    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_oneup)
    TextView tvOneup;
    @BindView(R.id.rl_oneup)
    RelativeLayout rlOneup;
    @BindView(R.id.tv_onedown)
    TextView tvOnedown;
    @BindView(R.id.rl_onedown)
    RelativeLayout rlOnedown;
    @BindView(R.id.tv_twoup)
    TextView tvTwoup;
    @BindView(R.id.rl_twoup)
    RelativeLayout rlTwoup;
    @BindView(R.id.tv_twodown)
    TextView tvTwodown;
    @BindView(R.id.rl_twodown)
    RelativeLayout rlTwodown;

    @BindView(R.id.rl_signSet)
    RelativeLayout rlSignSet;
    @BindView(R.id.tv_later)
    TextView tvLater;
    @BindView(R.id.rl_later)
    RelativeLayout rlLater;
    @BindView(R.id.tv_out)
    TextView tvOut;
    @BindView(R.id.rl_out)
    RelativeLayout rlOut;
    @BindView(R.id.swith_timeSet)
    SwitchButton swithTimeSet;
    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.rl_start)
    RelativeLayout rlStart;
    @BindView(R.id.rl_end)
    RelativeLayout rlEnd;
    @BindView(R.id.tv_end)
    TextView tvEnd;
    private MouthFragment mounthFragment;
    private DayFragment dayFragment;
    private ArrayList<String> lineList = new ArrayList<String>();
    private ArrayList<String>minList = new ArrayList<String>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newclassset);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("班次设置");
        mainTabRadioGroup.setOnCheckedChangeListener(this);
        mainTabRadioGroup.check(R.id.radio_one);
        for (int i = 0; i <= 360; i++) {
            if (i == 0) {
                lineList.add(new String("未设置"));
            } else {
                lineList.add(new String(i + "分钟"));
            }
        }
        for (int i = 0; i <= 18; i++) {
            if (i == 0) {
                minList.add(new String("未设置"));
            } else {
                minList.add(new String(i + "0分钟"));
            }
        }

        swithTimeSet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton s, boolean isChecked) {
                if (isChecked) {
                  rlStart.setVisibility(View.VISIBLE);
                  rlEnd.setVisibility(View.VISIBLE);

                }else {
                    rlStart.setVisibility(View.GONE);
                    rlEnd.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_start, R.id.rl_end, R.id.back, R.id.title, R.id.tv_next, R.id.rl_oneup, R.id.rl_onedown, R.id.rl_twoup, R.id.rl_twodown, R.id.rl_signSet, R.id.rl_out, R.id.rl_later})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.title:
                break;
            case R.id.tv_next:

                break;
            case R.id.rl_oneup:
                TimePickerView.Type yeartype = TimePickerView.Type.HOURS_MINS;
                String format = "HH:mm";
                ViewUtils.alertTimerPicker(this, yeartype, format, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvOneup.setText(date);
                    }
                });
                break;
            case R.id.rl_onedown:
                TimePickerView.Type onedown = TimePickerView.Type.HOURS_MINS;
                String oneformat = "HH:mm";
                ViewUtils.alertTimerPicker(this, onedown, oneformat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvOnedown.setText(date);
                    }
                });
                break;
            case R.id.rl_twoup:
                TimePickerView.Type twoup = TimePickerView.Type.HOURS_MINS;
                String twoupformat = "HH:mm";
                ViewUtils.alertTimerPicker(this, twoup, twoupformat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvTwoup.setText(date);
                    }
                });
                break;
            case R.id.rl_twodown:
                TimePickerView.Type twodown = TimePickerView.Type.HOURS_MINS;
                String twodownformat = "HH:mm";
                ViewUtils.alertTimerPicker(this, twodown, twodownformat, new ViewUtils.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        tvTwodown.setText(date);
                    }
                });
                break;
            case R.id.rl_signSet:
                break;
            case R.id.rl_later:
                ViewUtils.alertBottomWheelOption(this, lineList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvLater.setText(lineList.get(postion));
                    }

                });
                break;
            case R.id.rl_out:
                ViewUtils.alertBottomWheelOption(this, lineList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvOut.setText(lineList.get(postion));
                    }

                });
                break;

            case R.id.rl_start:
                //上班最早打卡时间
                ViewUtils.alertBottomWheelOption(this, minList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvStart.setText(minList.get(postion));
                    }

                });
                break;
            case R.id.rl_end:
                //下班最晚打卡时间
                ViewUtils.alertBottomWheelOption(this, minList, new ViewUtils.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        tvEnd.setText(minList.get(postion));
                    }

                });
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int CheckedId) {
        switch (CheckedId) {

            case R.id.radio_one:
                rlTwoup.setVisibility(View.GONE);
                rlTwodown.setVisibility(View.GONE);
                break;
            case R.id.radio_two:
                rlTwoup.setVisibility(View.VISIBLE);
                rlTwodown.setVisibility(View.VISIBLE);
                break;


        }

    }


}
