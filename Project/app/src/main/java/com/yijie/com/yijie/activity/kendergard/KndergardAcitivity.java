package com.yijie.com.yijie.activity.kendergard;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.MyItemTouchHelperCallback;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.AnimationUtil;
import com.yijie.com.yijie.utils.ShowToastUtils;
import com.yijie.com.yijie.utils.ViewUtils;
import com.yijie.com.yijie.view.CommomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class KndergardAcitivity extends BaseActivity implements CallbackItemTouch {

    private static final String TAG = "gfh";
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_develop)
    TextView tvDevelop;
    @BindView(R.id.tv_give)
    TextView tvGive;
    @BindView(R.id.iv_develop)
    ImageView ivDevelop;
    @BindView(R.id.rl)
    RelativeLayout rl;
    PopupWindow mPopupWindow;
    @BindView(R.id.gray_layout)
    View grayLayout;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    private boolean isPopWindowShowing = false;
    int fromYDelta;
    private KendergardAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<Item> mList; // My List the object 'StudentBean'.

    // Array images
    private int images[] = new int[]{
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,

    };

    // Array names
    private String names[] = new String[]{
            "国际幼儿园",
            "Batman",
            "DeadPool",
            "Gambit",
            "Hulk",
            "Hulk",
            "Hulk",
            "Hulk",
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.kendergard_share_home);
    }

    @Override
    public void init() {

        String kinderName = getIntent().getStringExtra("kinderName");
        title.setText(kinderName);
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        //true是从推荐跳转过来的
        boolean isShowMove = getIntent().getBooleanExtra("isShowMove", false);
        if (isShowMove) {
            rl.setVisibility(View.GONE);
            title.setText("北京大风车双语幼儿园");
            actionItem.setVisibility(View.GONE);
            tvRecommend.setVisibility(View.VISIBLE);
            tvRecommend.setText("推荐");
        }
        initList(); //call method
        actionItem.setBackgroundResource(R.mipmap.setting);

    }

    @OnClick({R.id.back, R.id.tv_develop, R.id.tv_give, R.id.gray_layout,R.id.tv_recommend})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.gray_layout:
                if (isPopWindowShowing) {
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(KndergardAcitivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    }, AnimationUtil.ANIMATION_OUT_TIME);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                new CommomDialog(KndergardAcitivity.this, R.style.dialog, "同时推荐的有[胡瑞彩]", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm,String string) {
                        if(confirm){
                            ShowToastUtils.showToastMsg(KndergardAcitivity.this,"推荐");
                            dialog.dismiss();
                        }

                    }
                })
                        .setTitle("是否确认推荐给【国际幼稚园】")
                        .show();
                break;

            case R.id.tv_develop:
                if (isPopWindowShowing) {
                    ivDevelop.startAnimation(AnimationUtil.createRotatedownAnimation());


                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(KndergardAcitivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    }, AnimationUtil.ANIMATION_OUT_TIME);
                } else {
                    showPopupWindow();
                }
                break;
            case R.id.tv_give:
                break;
        }
    }

    /**
     * Add data to the List
     */
    private void initList() {
        // Adds data to List of Objects StudentBean
        mList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            if (i == 0) {
                mList.add(new Item(images[i], names[i], "", 2));
            } else if (i == 1) {
                mList.add(new Item(images[i], names[i], "", 1));

            } else if (i == 2) {
                mList.add(new Item(images[i], names[i], "", 3));
            } else if (i == 3) {
                mList.add(new Item(images[i], names[i], "", 4));
            } else if (i == 4) {
                mList.add(new Item(images[i], names[i], "", 5));
            } else if (i == 5) {
                mList.add(new Item(images[i], names[i], "", 6));
            } else if (i == 6) {
                mList.add(new Item(images[i], names[i], "", 7));
            } else if (i == 7) {
                mList.add(new Item(images[i], names[i], "", 8));
            }


        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        myAdapterRecyclerView = new KendergardAdapterRecyclerView(this, mList); // Create Instance of KendergardAdapterRecyclerView
        mRecyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);// create MyItemTouchHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
        touchHelper.attachToRecyclerView(mRecyclerView); // Attach ItemTouchHelper to RecyclerView
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition, mList.remove(oldPosition));// change position
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved

    }

    private void showPopupWindow() {
        //andorid 7.0如果内容超过屏幕showasdropdown 将会失效需要适配
        final View contentView = LayoutInflater.from(this).inflate(R.layout.selectlist, null);
        TextView t1 = (TextView) contentView.findViewById(R.id.text1);
        mPopupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.base_dimen_500));

        //
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);

        //获取popupwindow高度确定动画开始位置
        int contentHeight = ViewUtils.getViewMeasuredHeight(contentView);
        Log.i(TAG, "contentview height=" + contentHeight);
        fromYDelta = -contentHeight - 50;
        mPopupWindow.showAsDropDown(rl, 0, 0);
        Log.i(TAG, "fromYDelta=" + fromYDelta);
        grayLayout.setVisibility(View.VISIBLE);
//        grayLayout.startAnimation(AnimationUtil.createAlphaIntAnimation(this, fromYDelta));
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(this, fromYDelta));
        ivDevelop.startAnimation(AnimationUtil.createRotateupAnimation());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                grayLayout.startAnimation(AnimationUtil.createAlphaOutAnimation(KndergardAcitivity.this, fromYDelta));
                grayLayout.setVisibility(View.GONE);

                isPopWindowShowing = false;

            }
        });


        isPopWindowShowing = true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
