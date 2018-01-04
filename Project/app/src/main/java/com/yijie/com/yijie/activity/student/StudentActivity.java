package com.yijie.com.yijie.activity.student;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.recommend.RecommendActivity;
import com.yijie.com.yijie.activity.student.adapter.GalleryAdapter;
import com.yijie.com.yijie.base.BaseActivity;
import com.yijie.com.yijie.utils.PhotoActivityForHor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2017/12/14.
 */

public class StudentActivity extends BaseActivity {
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
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;
    @BindView(R.id.ll_commit)
    LinearLayout llCommit;
    @BindView(R.id.tv_companionName)
    TextView tvCompanionName;
    private GalleryAdapter mAdapter;
    private List<String> mDatas;

    private String[] mUrls = new String[]{

            "http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
    };

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_student);

    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("简历审核");
        actionItem.setVisibility(View.INVISIBLE);
/**
 * 如果是从学生分组跳转过来的根据type类型去设置简历的样式
 */
        int type = getIntent().getIntExtra("type", 0);
        if (type == 2||type==5||type==6) {
            tvRecommend.setVisibility(View.VISIBLE);
            tvRecommend.setText("推荐");
            ivStutus.setBackgroundResource(R.mipmap.student_unchecked);
            llCommit.setVisibility(View.GONE);
        }
        //====================================
        initDatas();
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);


        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        final int width = outMetrics.widthPixels;
        final int height = outMetrics.heightPixels;
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(StudentActivity.this, PhotoActivityForHor.class);
                ArrayList<Rect> rects = new ArrayList<>();


                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

//Rect(57, 387 - 297, 597)
//                int childCount = mRecyclerView.getChildCount();
//                ShowToastUtils.showToastMsg(StudentActivity.this,childCount+"");

//                int viewCount = mAdapter.getItemCount();


//                for (int k = 0; k <viewCount; k++) {
//
//                    Rect rect = new Rect(width / 2, height / 2, width / 2, height / 2);
//                    rects.add(rect);
//                }


//                    Rect rect=new Rect();
//                    mRecyclerView.getGlobalVisibleRect(rect);
//                    int top = rect.top;
//                    int bottom = rect.bottom;
//                    int left = rect.left;
//                    int right = rect.right;
//
//                    Rect rect2= new Rect(left, top ,r, bottom);
//                        rects.add(rect2);
//                    rects.add(rect2);

//                    View viewN = mRecyclerView.getChildAt(k);
//                    Rect rect=new Rect();
//                    viewN.getGlobalVisibleRect(rect);
//                    rects.add(rect);


//                }


//                for (int k = 0; k < childCount; k++) {
//                    Rect rect= new Rect(width/2, height/2 ,width/2, height/2);
//                        rects.add(rect);
//                    rects.add(rect);
//
//
//                }


                intent.putExtra("imgUrl", mUrls[position]);
//                intent.putExtra("index",position );
                intent.putExtra("startBounds", rect);
                startActivity(intent);

            }
        });
    }


    private void initDatas() {

        mDatas = Arrays.asList(mUrls);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.back, R.id.tv_recommend,R.id.tv_companionName})
    public void click(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_recommend:
                intent.setClass(StudentActivity.this, RecommendActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_companionName:
               intent = new Intent();
                intent.setClass(StudentActivity.this, StudentActivity.class);
                startActivity(intent);
                break;

        }

    }
}
