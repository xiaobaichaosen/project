package com.yijie.com.yijie.activity.kendergard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.EveryStudentNineGridAdapter;
import com.yijie.com.yijie.activity.kendergard.kndergardadapter.NineGridAdapter;
import com.yijie.com.yijie.activity.student.adapter.GalleryAdapter;
import com.yijie.com.yijie.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 奕杰平台 on 2017/12/28.
 * 园所-在岗学生--在岗和结束实习--每个学生的详情
 */

public class EveryStudentDetialAcivity extends BaseActivity {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.iv_stutus)
    ImageView ivStutus;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerViewPhoto)
    RecyclerView recyclerViewPhoto;



    @BindView(R.id.iv_head)
    TextView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    private LinearLayoutManager mLayoutManager;
    private EveryStudentNineGridAdapter mAdapter;
//    private List<NineGridTestModel> mList = new ArrayList<>();
    private List<NineGridTestModel> mList = new ArrayList<>();
    private List<String> mDatas;
    private String[] mUrls = new String[]{"http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",

            "http://d.hiphotos.baidu.com/image/h%3D200/sign=ea218b2c5566d01661199928a729d498/a08b87d6277f9e2fd4f215e91830e924b999f308.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3445377427,2645691367&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2644422079,4250545639&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1444023808,3753293381&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=882039601,2636712663&fm=21&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=4119861953,350096499&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};


    private String[] mPhotoUrls = new String[]{

            "http://d.hiphotos.baidu.com/image/h%3D200/sign=201258cbcd80653864eaa313a7dca115/ca1349540923dd54e54f7aedd609b3de9c824873.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2437456944,1135705439&fm=21&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=3251359643,4211266111&fm=21&gp=0.jpg",
            "http://img4.duitang.com/uploads/item/201506/11/20150611000809_yFe5Z.jpeg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
    };
    private GalleryAdapter mPhotoAdapter;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_everystudent);

    }

    @Override
    public void init() {
        title.setText("张欢");
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
initListData();
        mAdapter = new EveryStudentNineGridAdapter(this);
        mAdapter.setList(mList);
        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager  layoutManager = new LinearLayoutManager(this);
        recyclerViewPhoto.setLayoutManager(layoutManager);
        mPhotoAdapter = new GalleryAdapter(this, mDatas);
        recyclerViewPhoto.setAdapter(mPhotoAdapter);

    }
    private void initListData() {

        mDatas = Arrays.asList(mPhotoUrls);



        NineGridTestModel model6 = new NineGridTestModel();
        for (int i = 0; i < 9; i++) {
            model6.urlList.add(mUrls[i]);
        }
        mList.add(model6);




    }

    @OnClick({R.id.back,R.id.iv_stutus})
    public void Click(View view){
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.iv_stutus:
                Intent intent=new Intent();
                intent.setClass(EveryStudentDetialAcivity.this,MoreShareActivity.class);
                startActivity(intent);
                break;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
