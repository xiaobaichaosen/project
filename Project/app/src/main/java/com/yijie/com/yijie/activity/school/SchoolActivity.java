package com.yijie.com.yijie.activity.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ContactModityActivity;
import com.yijie.com.yijie.activity.PowerActivity;
import com.yijie.com.yijie.activity.TrainDetailAcitity;
import com.yijie.com.yijie.activity.newschool.MemorandumActivity;
import com.yijie.com.yijie.activity.newschool.NewInternshipDetailActivity;
import com.yijie.com.yijie.activity.newschool.NewSchoolIntroduction;
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
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_memorandum)
    TextView tvMemorandum;
    @BindView(R.id.tv_newSchoolSample)
    TextView tvNewSchoolSample;
    @BindView(R.id.tv_InternshipDetail)
    TextView tvInternshipDetail;
    @BindView(R.id.tv_trainDetail)
    TextView tvTrainDetail;
    private SchoolAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<StudentBean> mList; // My List the object 'StudentBean'.


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
    public void click(View v) {
        finish();
    }

    /**
     * Add data to the List
     */
    private void initList() {
        // Adds data to List of Objects StudentBean
        mList = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            if (i == 0) {
                mList.add(new StudentBean(1, names[i]));
            } else if (i == 1) {
                mList.add(new StudentBean(2, names[i]));
            } else if (i == 2) {
                mList.add(new StudentBean(3, names[i]));
            } else if (i == 3) {
                mList.add(new StudentBean(4, names[i]));
            } else {
                mList.add(new StudentBean(5, names[i]));
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

    @OnClick({R.id.tv_contact, R.id.tv_memorandum, R.id.tv_newSchoolSample, R.id.tv_InternshipDetail, R.id.tv_trainDetail,R.id.action_item})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.tv_contact:

                intent.setClass(this, ContactModityActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_memorandum:
                intent.setClass(this, MemorandumActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_newSchoolSample:
                intent.setClass(this, NewSchoolIntroduction.class);
                startActivity(intent);
                break;
            case R.id.tv_InternshipDetail:
                intent.setClass(this, NewInternshipDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_trainDetail:intent.setClass(this, TrainDetailAcitity.class);
                startActivity(intent);

                break;

            case R.id.action_item:
                intent.setClass(this, PowerActivity.class);
                startActivity(intent);
                break;
        }
    }
}
