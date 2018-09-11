package com.yijie.com.kindergartenapp.activity.regist;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;
import com.yijie.com.kindergartenapp.bean.KindergartenMember;
import com.yijie.com.kindergartenapp.fragment.FourFragment;
import com.yijie.com.kindergartenapp.fragment.OneFragment;
import com.yijie.com.kindergartenapp.fragment.ThreeFragment;
import com.yijie.com.kindergartenapp.fragment.TwoFragment;
import com.yijie.com.kindergartenapp.fragment.FiveFragment;
import com.yijie.com.kindergartenapp.view.StepView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新注册页面
 */
public class RegistKindActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.stepView)
     public StepView stepView;
     private String isBack;
    private FragmentTransaction fragmentTransaction;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_registkind);
    }

    @Override
    public void init() {
        title.setText("注册");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        final OneFragment oneFragment = new OneFragment();
        final TwoFragment twoFragment = new TwoFragment();
        final ThreeFragment threeFragment = new ThreeFragment();
        final FourFragment fourFragment = new FourFragment();
        final FiveFragment fiveFragment = new FiveFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_kndergard_layout, oneFragment);
        fragmentTransaction.commit();
        oneFragment.setOnButtonClick(new OneFragment.OnButtonClick() {
            @Override
            public void onClick(KindergartenMember kindergartenMember) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                //进从右向左   出从左向右
                 fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout, twoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                twoFragment.setKindergartenMember(kindergartenMember);
            }
        });
        twoFragment.setOnButtonClick(new TwoFragment.OnButtonClick() {
            @Override
            public void onClick(KindergartenMember kindergartenMember) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout, threeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                threeFragment.setKindergartenMember(kindergartenMember);
            }

            @Override
            public void onSend(KindergartenMember kindergartenMember) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout, fiveFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fiveFragment.setKindergartenMember(kindergartenMember);
            }
        });
        threeFragment.setOnButtonClick(new ThreeFragment.OnButtonClick() {
            @Override
            public void onClick(String string) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout, fourFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fourFragment.setTitleString(string);
                isBack=string;
                back.setVisibility(View.GONE);
            }
        });

        fiveFragment.setOnButtonClick(new FiveFragment.OnButtonClick() {
            @Override
            public void onClick(String string) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.on_activity_open,R.anim.on_activity_puse);
                fragmentTransaction.replace(R.id.main_kndergard_layout, fourFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                fourFragment.setTitleString(string);
                isBack=string;
                back.setVisibility(View.GONE);
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if (null!=isBack){
            if(keyCode==KeyEvent.KEYCODE_BACK&&!isBack.isEmpty())
                return true;//不执行父类点击事件
        }
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        getSupportFragmentManager().popBackStack();
        int step = stepView.getStep();
        if (step>1){
            stepView.setStep(--step);
        }else {
            finish();
        }
    }
}
