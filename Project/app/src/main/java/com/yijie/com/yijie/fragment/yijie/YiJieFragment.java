package com.yijie.com.yijie.fragment.yijie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.ProjectListAcitivity;
import com.yijie.com.yijie.activity.SchoolListAcitivity;
import com.yijie.com.yijie.activity.kendergard.KendergardStatusActivity;
import com.yijie.com.yijie.activity.student.StudentStatusActivity;
import com.yijie.com.yijie.base.BaseFragment;
import com.yijie.com.yijie.base.baseadapter.DividerItemDecoration;
import com.yijie.com.yijie.homepage.CustomHolder;
import com.yijie.com.yijie.homepage.CustomPeakHolder;
import com.yijie.com.yijie.homepage.DefaultAdapterViewLisenter;
import com.yijie.com.yijie.homepage.DefaultRefrushListener;
import com.yijie.com.yijie.homepage.OnToolsItemClickListener;
import com.yijie.com.yijie.homepage.RefrushAdapter;
import com.yijie.com.yijie.homepage.RefrushRecycleView;
import com.yijie.com.yijie.homepage.bean.GrideBean;
import com.yijie.com.yijie.homepage.holder.GrideHolder;
import com.yijie.com.yijie.homepage.holder.HomeCarouselHolder;
import com.yijie.com.yijie.homepage.holder.ItemHolder;
import com.yijie.com.yijie.homepage.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by 奕杰平台 on 2018/4/19.
 */

public class YiJieFragment extends BaseFragment implements OnToolsItemClickListener<GrideBean> {

    @BindView(R.id.rc_home)
    RefrushRecycleView refrushRecycleView;
    @BindView(R.id.btn_yijie_school)
    Button btnYijieSchool;
    @BindView(R.id.btn_yijie_project)
    Button btnYijieProject;
    @BindView(R.id.btn_yijie_kinder)
    Button btnYijieKinder;
    @BindView(R.id.btn_yijie_student)
    Button btnYijieStudent;
    @BindView(R.id.img_yijie_school)
    ImageButton imgYijieSchool;
    @BindView(R.id.img_yijie_project)
    ImageButton imgYijieProject;
    @BindView(R.id.img_yijie_kinder)
    ImageButton imgYijieKinder;
    @BindView(R.id.img_yijie_student)
    ImageButton imgYijieStudent;
    private List<String> lists;
    private GrideHolder holder_up;
    private HomeCarouselHolder carouselHolder;
    //    @BindView(R.id.btn_school)
//    Button btnSchool;
//    @BindView(R.id.btn_kendergard)
//    Button btnKendergard;
    Unbinder unbinder;
    private RefrushAdapter<Object> adapter;
//    @BindView(R.id.btn_student)
//    Button btnStudent;
//    @BindView(R.id.btn_project)
//    Button btnProject;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yj;
    }

    @Override
    protected void initView() {

        lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            lists.add("ITEM" + i);
        }

        adapter = new RefrushAdapter<>(mActivity, lists, R.layout.adapter_yijie_item, new DefaultAdapterViewLisenter() {
            @Override
            public CustomHolder getBodyHolder(Context context, List lists, int itemID) {
                return new ItemHolder(context, lists, itemID);
            }
        });
        refrushRecycleView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        //显示下拉刷新
        refrushRecycleView.setRefrushListener(new DefaultRefrushListener() {
            @Override
            public void onLoading() {
                super.onLoading();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1000);
                        refrushRecycleView.post(new Runnable() {
                            @Override
                            public void run() {
                                refrushRecycleView.loadSuccess();
                            }
                        });

                    }
                }).start();

            }
        });
        /**
         * 设置下拉刷新位置
         */
        adapter.setRefrushPosition(1);
//        List<GrideBean> list = new ArrayList<>();
//        list.add(new GrideBean("通知", R.mipmap.notice));
////        list.add(new GrideBean("", R.mipmap.home_banner2));
////        list.add(new GrideBean("", R.mipmap.home_banner3));
////        list.add(new GrideBean("", R.mipmap.home_banner4));
////        list.add(new GrideBean("", R.mipmap.home_banner5));
//
//        GrideHolder2 carousel = new GrideHolder2(mActivity, list, R.layout.item2);
//        carousel.setOnTOnToolsItemClickListener(this);
//        adapter.addHead(carousel);
//        //中间广告条
//        carouselHolder = new HomeCarouselHolder(mActivity, list, R.layout.item_home_carousel);
//        adapter.addHead(carouselHolder);

        //上侧九宫格

        List<GrideBean> UP = new ArrayList<>();
        UP.add(new GrideBean(mActivity.getString(R.string.compact), R.mipmap.yijie_compact));
        UP.add(new GrideBean(mActivity.getString(R.string.insurance), R.mipmap.yijie_insurance));
        UP.add(new GrideBean(mActivity.getString(R.string.pay), R.mipmap.yijie_pay));
        UP.add(new GrideBean(mActivity.getString(R.string.performance), R.mipmap.yijie_performance));
        UP.add(new GrideBean(mActivity.getString(R.string.leave), R.mipmap.yijie_leave));
        UP.add(new GrideBean(mActivity.getString(R.string.visit), R.mipmap.yijie_visit));


        holder_up = new GrideHolder(mActivity, UP, R.layout.item);
        holder_up.setOnTOnToolsItemClickListener(this);
        adapter.addHead(holder_up);



//        List<GrideBean> list = new ArrayList<>();
//        list.add(new GrideBean("通知", R.mipmap.notice));
////        list.add(new GrideBean("", R.mipmap.home_banner2));
////        list.add(new GrideBean("", R.mipmap.home_banner3));
////        list.add(new GrideBean("", R.mipmap.home_banner4));
////        list.add(new GrideBean("", R.mipmap.home_banner5));
//
//        GrideHolder2 carousel = new GrideHolder2(mActivity, list, R.layout.item2);
//        carousel.setOnTOnToolsItemClickListener(this);
//        adapter.addHead(carousel);
//        //中间广告条
//        carouselHolder = new HomeCarouselHolder(mActivity, list, R.layout.item_home_carousel);
//        adapter.addHead(carouselHolder);


        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        refrushRecycleView.setLayoutManager(manager);
        refrushRecycleView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(int position, GrideBean item) {
        ToastUtil.showTextToast(item.title);
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        ToastUtil.init(mActivity);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_yijie_school, R.id.btn_yijie_project, R.id.btn_yijie_kinder, R.id.btn_yijie_student,R.id.img_yijie_school, R.id.img_yijie_project, R.id.img_yijie_kinder, R.id.img_yijie_student})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_yijie_school:
                intent.setClass(mActivity, SchoolListAcitivity.class);
                startActivity(intent);
                break;
            case R.id.btn_yijie_project:
                intent.setClass(mActivity, ProjectListAcitivity.class);
                startActivity(intent);
                break;
            case R.id.btn_yijie_kinder:
                intent.setClass(mActivity, KendergardStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_yijie_student:
                intent.setClass(mActivity, StudentStatusActivity.class);
                startActivity(intent);
                break;

            case R.id.img_yijie_school:
                intent.setClass(mActivity, SchoolListAcitivity.class);
                startActivity(intent);
                break;
            case R.id.img_yijie_project:
                intent.setClass(mActivity, ProjectListAcitivity.class);
                startActivity(intent);
                break;
            case R.id.img_yijie_kinder:
                intent.setClass(mActivity, KendergardStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.img_yijie_student:
                intent.setClass(mActivity, StudentStatusActivity.class);
                startActivity(intent);
                break;
        }
    }

}
