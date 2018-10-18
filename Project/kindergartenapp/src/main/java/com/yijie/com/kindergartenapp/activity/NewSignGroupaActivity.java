package com.yijie.com.kindergartenapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新建考勤组
 */
public class NewSignGroupaActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.SignName)
    EditText SignName;
    @BindView(R.id.rl_allPeople)
    RelativeLayout rlAllPeople;
    @BindView(R.id.rl_groupManager)
    RelativeLayout rlGroupManager;
    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_newsigngroup);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("新增考勤组");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.rl_allPeople, R.id.rl_groupManager,R.id.tv_next})
    public void onViewClicked(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.rl_allPeople:
                intent.setClass(NewSignGroupaActivity.this, AllPeopleActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_groupManager:
                break;
                case  R.id.tv_next:
                    intent.setClass(NewSignGroupaActivity.this, RuleSetting.class);
                    startActivity(intent);

                    break;
        }
    }
}
