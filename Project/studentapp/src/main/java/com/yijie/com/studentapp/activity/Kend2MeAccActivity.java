package com.yijie.com.studentapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.adapter.LoadMoreKend2MeAdapter;
import com.yijie.com.studentapp.base.BaseActivity;
import com.yijie.com.studentapp.bean.StudentEducation;
import com.yijie.com.studentapp.bean.StudentEvaluate;
import com.yijie.com.studentapp.view.ninegrid.NineGridTestModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 园所对我的评价
 */
public class Kend2MeAccActivity extends BaseActivity {

    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.action_item)
    ImageView actionItem;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private LoadMoreKend2MeAdapter mAdapter;

    private List<List<StudentEvaluate>> dataList= new ArrayList<>();

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
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg"
           };

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_kend2meacc);
    }

    @Override
    public void init() {
        setColor(this, getResources().getColor(R.color.appBarColor)); // 改变状态栏的颜色
        setTranslucent(this); // 改变状态栏变成透明
        title.setText("园所对我的评价");
        tvRecommend.setVisibility(View.VISIBLE);
        tvRecommend.setText("我的");
        actionItem.setVisibility(View.GONE);
        initListData();



        mLayoutManager = new LinearLayoutManager(this){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
              return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT);

            }
        };

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LoadMoreKend2MeAdapter(dataList,this,R.layout.adapter_kend2me_item);
//        mAdapter.setList(mList);
        mRecyclerView.setAdapter(mAdapter);


//
//        mAdapter = new NineGridAdapter(this);
//        mAdapter.setList(mList);
//        mRecyclerView.setAdapter(mAdapter);
    }


    private void initListData() {
        ArrayList<StudentEvaluate> schoolAdresses1 = new ArrayList<>();
        ArrayList<StudentEvaluate> schoolAdresses2 = new ArrayList<>();

//        NineGridTestModel model = new NineGridTestModel();;
//        for (int i = 0; i < 4; i++) {
//            model.urlList.add(mUrls[i]);
//        }
////        for (int i = 0; i < 2; i++) {
////            NineGridTestModel model1 = new NineGridTestModel();;
////            model1.urlList.add(mUrls[i]);
////            schoolAdresses1.add(model1);
////        }
//        schoolAdresses1.add(model);
//        schoolAdresses2.add(model);
        dataList.add(schoolAdresses1);
        dataList.add(schoolAdresses2);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back,R.id.tv_recommend})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;


            case   R.id.tv_recommend:
                //园所对我的评价
                 finish();
                break;

        }
    }
}

