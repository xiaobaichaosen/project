package com.yijie.com.yijie.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.bean.SchoolAdress;
import com.yijie.com.yijie.utils.BaseCallback;
import com.yijie.com.yijie.utils.HttpUtils;
import com.yijie.com.yijie.utils.LogUtil;
import com.yijie.com.yijie.utils.SharedPreferencesUtils;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2018/2/26.
 * 权限控制
 */

public class PowerActivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rl_train)
    RelativeLayout rlTrain;
    @BindView(R.id.rl_kendergard)
    RelativeLayout rlKendergard;

    String practiceId;
    @BindView(R.id.toOtherTearch)
    RelativeLayout toOtherTearch;
    @BindView(R.id.rl_root)
    LinearLayout rlRoot;
    @BindView(R.id.swith_train)
    SwitchButton swithTrain;
    @BindView(R.id.switch_kendergard)
    SwitchButton switchKendergard;
    @BindView(R.id.rl_numset)
    RelativeLayout rlNumset;
    private String schoolId;
    private StatusLayoutManager statusLayoutManager;
    private String kindpeoNumSet;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_power);
    }

    @Override
    public void init() {
        // 设置重试事件监听器
        statusLayoutManager = new StatusLayoutManager.Builder(rlRoot)
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                    }

                    @Override
                    public void onCustomerChildClick(View view) {

                    }

                })
                .build();
        practiceId = getIntent().getStringExtra("practiceId");
        schoolId = getIntent().getStringExtra("schoolId");
        String projectStatus = getIntent().getStringExtra("projectStatus");
        kindpeoNumSet = getIntent().getStringExtra("kindpeoNumSet");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("权限管理");
        //TODO 如果不是待培训进来swith状态为选中状态，并且不可修改
        toOtherTearch.setVisibility(View.VISIBLE);
        //先判断账号的角色
        String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");
        if (roleName.contains("开发老师")) {
            //如果是开发老师 显示培训switch
            rlTrain.setVisibility(View.VISIBLE);
            if (projectStatus.equals("0")) {
                swithTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //确认提交
                            new CommomDialog(PowerActivity.this, R.style.dialog, "打开按钮前，先确认信息是否填写正确", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm, String string) {
                                    if (confirm) {
                                        changeStatus(practiceId, "1", "培训老师");
                                        dialog.dismiss();
                                    } else {
                                        swithTrain.setChecked(false);
                                    }
                                }
                            })
                                    .setTitle("提示").show();
                        }

                    }
                });

                toOtherTearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("schoolId", schoolId);
                        intent.putExtra("practiceId", practiceId);
                        intent.setClass(PowerActivity.this, TransferActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (projectStatus.equals("1")) {
                swithTrain.setChecked(true);
                toOtherTearch.setOnClickListener(null);
                swithTrain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton s, boolean isChecked) {
                        if (!isChecked) {
                            new CommomDialog(PowerActivity.this, R.style.dialog, "关闭按钮后，培训老师将看不到此项目", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm, String string) {
                                    if (confirm) {
                                        changeStatus(practiceId, "0", "");
                                        dialog.dismiss();
                                    } else {
                                        swithTrain.setChecked(true);
                                    }
                                }
                            })
                                    .setTitle("提示").show();

                        }
                    }
                });
            } else {
//                rlTrain.setVisibility(View.GONE);
                swithTrain.setChecked(true);
                swithTrain.setClickable(false);
                swithTrain.setFocusable(false);

            }
        } else if (roleName.contains("培训老师")) {
            //如果是培训老师 显示对园所公共switch
            rlKendergard.setVisibility(View.VISIBLE);
            rlNumset.setVisibility(View.VISIBLE);

            if (projectStatus.equals("2")) {
                switchKendergard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton s, boolean isChecked) {
                        if (isChecked) {
                            //TODO 对园所开放接口2--》--3
                            new CommomDialog(PowerActivity.this, R.style.dialog, "打开按钮，此项目将对园所公开", new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm, String string) {
                                    if (confirm) {
                                        changeStatus(practiceId, "3", "培训老师");
                                        dialog.dismiss();
                                    } else {
                                    }
                                }
                            })
                                    .setTitle("提示").show();
                        }
                    }
                });
                toOtherTearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("schoolId", schoolId);
                        intent.putExtra("practiceId", practiceId);
                        intent.setClass(PowerActivity.this, TransferActivity.class);
                        startActivity(intent);
                    }
                });
                rlNumset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("schoolId", schoolId);
                        intent.putExtra("practiceId", practiceId);
                        intent.putExtra("kindpeoNumSet", kindpeoNumSet);
                        intent.setClass(PowerActivity.this, SettingNumActivity.class);
                        startActivity(intent);
                    }
                });
            } else if (projectStatus.equals("3")) {
                switchKendergard.setChecked(true);
                rlNumset.setFocusable(false);
                rlNumset.setClickable(false);
                switchKendergard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton s, boolean isChecked) {
                        if (!isChecked) {
                            //TODO 对园所开放接口3--》--2
                        }
                    }
                });
            } else {
//                rlTrain.setVisibility(View.GONE);
                swithTrain.setChecked(true);
                swithTrain.setClickable(false);
                swithTrain.setFocusable(false);

            }
        }


//        final String roleName = (String) SharedPreferencesUtils.getParam(this, "roleName", "");

    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;

        }
    }
    //最多招聘人数
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void handleSomethingElse(String requestNum) {
//        etDetailAdress.setText(schoolAdress.getName());
    }

    public void changeStatus(String id, String status, String alias) {
        HttpUtils getinstance = HttpUtils.getinstance(PowerActivity.this);
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("id", id);
        stringStringHashMap.put("state", status);
        stringStringHashMap.put("alias", alias);
        getinstance.post(Constant.STATEUPDATE, stringStringHashMap, new BaseCallback<String>() {
            @Override
            public void onRequestBefore() {
                commonDialog.show();
            }

            @Override
            public void onFailure(Request request, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }

            @Override
            public void onSuccess(Response response, String o) {
                LogUtil.e(o);
                finish();
                statusLayoutManager.showSuccessLayout();
                commonDialog.dismiss();
            }

            @Override
            public void onError(Response response, int errorCode, Exception e) {
                statusLayoutManager.showErrorLayout();
                commonDialog.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
