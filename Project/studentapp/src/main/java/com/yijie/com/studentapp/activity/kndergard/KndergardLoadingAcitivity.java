package com.yijie.com.studentapp.activity.kndergard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.CallbackItemTouch;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.Item;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.KendergardAdapterRecyclerView;
import com.yijie.com.studentapp.activity.kndergard.kndergardadapter.MyItemTouchHelperCallback;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.view.CommomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2018/1/31.
 * 正在招聘的园所
 */

public class KndergardLoadingAcitivity extends BaseActivity implements CallbackItemTouch {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_confrim)
    TextView tvConfrim;
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
        setContentView(R.layout.kendergard_loading_home);
    }

    @Override
    public void init() {


        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明


        initList(); //call method

    }

    //
    @OnClick({R.id.back,R.id.tv_confrim})
    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_confrim:


                new CommomDialog(this, R.style.dialog, "您还有2个园所可投递简历", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){

                            dialog.dismiss();
                        }

                    }
                })
                        .setTitle("投递成功")
                        .setPositiveButton("继续投递")
                        .setNegativeButtonVisable(true)
                        .show();


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
                mList.add(new Item(images[i], names[i], "", 1));

            } else if (i == 2) {
                mList.add(new Item(images[i], names[i], "", 3));
            } else if (i == 3) {
                mList.add(new Item(images[i], names[i], "", 4));
            } else if (i == 6) {
                mList.add(new Item(images[i], names[i], "", 7));
            } else if (i == 7) {
                mList.add(new Item(images[i], names[i], "", 8));
            }


        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        myAdapterRecyclerView = new KendergardAdapterRecyclerView(this, mList); // Create Instance of KendergardAdapterRecyclerView
        recyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(this);// create MyItemTouchHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
        touchHelper.attachToRecyclerView(recyclerView); // Attach ItemTouchHelper to RecyclerView
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition, mList.remove(oldPosition));// change position
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
