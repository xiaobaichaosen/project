package com.yijie.com.yijie.activity.school;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.school.adapter.MyItemTouchHelperCallback;
import com.yijie.com.yijie.activity.school.adapter.SchoolAdapterRecyclerView;
import com.yijie.com.yijie.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SchoolActivity extends BaseActivity implements CallbackItemTouch {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SchoolAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<Item> mList; // My List the object 'Item'.

    // Array images
    private int images[] = new int[]{
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,


    };

    // Array names
    private String names[] = new String[]{
            "河北省新华冶金技术学校",
            "Batman",
            "DeadPool",
            "Gambit",
            "Hulk",


    };

    // Description text
    private String textDescription = "Subtitle description,lorem ipsum text generic etc";


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_school);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        initList(); //call method
        actionItem.setBackgroundResource(R.mipmap.setting);

    }
    @OnClick(R.id.back)
    public void click(View v){
        finish();
    }

    /**
     * Add data to the List
     */
    private void initList() {
        // Adds data to List of Objects Item
        mList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {


            if (i == 0) {
                mList.add(new Item(images[i], names[i], textDescription, 1));
            } else if (i == 1) {
                mList.add(new Item(images[i], names[i], textDescription, 2));
            } else if (i == 2) {
                mList.add(new Item(images[i], names[i], textDescription, 3));
            } else if (i == 3) {
                mList.add(new Item(images[i], names[i], textDescription, 4));
            } else {
                mList.add(new Item(images[i], names[i], textDescription, 5));
            }

        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        myAdapterRecyclerView = new SchoolAdapterRecyclerView(this, mList); // Create Instance of SchoolAdapterRecyclerView
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
