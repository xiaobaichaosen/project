package com.yijie.com.kindergartenapp.fragment.yijie;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.interfaces.INearbySearch;
import com.google.gson.Gson;
import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.ModiKenderDetailActivity;
import com.yijie.com.kindergartenapp.activity.RequstActivity;
import com.yijie.com.kindergartenapp.activity.login.LoginActivity;
import com.yijie.com.kindergartenapp.bean.KindergartenNeed;
import com.yijie.com.kindergartenapp.bean.SchoolPractice;
import com.yijie.com.kindergartenapp.utils.BaseCallback;
import com.yijie.com.kindergartenapp.utils.HttpUtils;
import com.yijie.com.kindergartenapp.utils.LogUtil;
import com.yijie.com.kindergartenapp.utils.SharedPreferencesUtils;
import com.yijie.com.kindergartenapp.utils.ShowToastUtils;
import com.yijie.com.kindergartenapp.view.CommomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 奕杰平台 on 2017/12/11.
 */

public class LoadMoreYijieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final int res;
    private final List<SchoolPractice> schoolPracticeList;
    private List<KindergartenNeed> dataList;
    private Context mContext;
    //true 可以提需求
    private  boolean isRequst;
    public LoadMoreYijieAdapter(List<SchoolPractice> schoolPracticeList,List<KindergartenNeed> dataList, int res,boolean isRequst) {
        this.dataList = dataList;
        this.res=res;
        this.schoolPracticeList=schoolPracticeList;
        this.isRequst=isRequst;

    }
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(res, parent, false);
        view.setOnClickListener(this);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
//        recyclerViewHolder.tvItem.setText(dataList.get(position).getName());
        KindergartenNeed kindergartenNeed = dataList.get(position);
        recyclerViewHolder.tvProjectName.setText(kindergartenNeed.getProjectName());
        recyclerViewHolder.tvShcoolName.setText(kindergartenNeed.getLocation());
        //园所需求人数
        Integer demandNum = kindergartenNeed.getDemandNum();
        //收到简历个数
        Integer receiveNum = kindergartenNeed.getReceiveResume();
        if (null!=demandNum&&demandNum>0){
            //园所已经提需求了
            String str="<font color='#AAAAAA'>需求</font>"+" <font color='#FF0000'>"+demandNum +"人</font>";
            recyclerViewHolder.tvRequest.setText(Html.fromHtml(str));

        }
        if (null==demandNum){
            recyclerViewHolder.tvRequest.setText("提需求");
            recyclerViewHolder.tvRequest.setTextColor(Color.parseColor("#f66168"));
            recyclerViewHolder.tvRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //先判断是否通过审核，没有通过审核只能看，不能提需求

                    if (isRequst){
                        Intent intent = new Intent();
                        intent.putExtra("projectName",dataList.get(position).getProjectName());
                        intent.putExtra("schoolId",dataList.get(position).getSchoolId());
                        intent.putExtra("projectId",dataList.get(position).getSchoolPracticeId());
                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("SchoolPractice", schoolPracticeList.get(position));
                        intent.putExtras(mBundle);
                        intent.setClass(mContext, RequstActivity.class);
                        mContext.startActivity(intent);
                    }else {

                        new CommomDialog(mContext, R.style.dialog, "请到我的-园所信息中,完善园所信息,审核通过后,可提交招聘需求", new CommomDialog.OnCloseListener() {


                            @Override
                            public void onClick(Dialog dialog, boolean confirm, String sContent) {
                                if (confirm) {
                                    Intent intent=new Intent();
                                    intent.setClass(mContext, ModiKenderDetailActivity.class);
                                    mContext.startActivity(intent);

                                }else{

                                }
                                dialog.dismiss();
                            }

                            @Override
                            public void onContentClick() {

                            }




                        }).setNegativeButton("我知道了")
                                .setNegativeButtonInV(true)
                                .setPositiveButton("完善资料")
                                .setTitle("提示").show();

                    }

                }
            });
        }else{
            recyclerViewHolder.tvRequest.setClickable(false);
            recyclerViewHolder.tvRequest.setFocusable(false);
        }
        if (null!=receiveNum&&receiveNum>0){
            recyclerViewHolder.tvReceiveNum.setText("收到简历:"+kindergartenNeed.getReceiveResume());
        }else {
            recyclerViewHolder.tvReceiveNum.setText("");
        }
        recyclerViewHolder.tvDate.setText(kindergartenNeed.getUpdateTime());

        //将position保存在itemView的Tag中，以便点击时进行获取
        //判断注册信息是否审核通过


        recyclerViewHolder.itemView.setTag(position);
    }
    /**
     * 获得当前view的type类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return 1;
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_shcoolName)
        TextView tvShcoolName;

        @BindView(R.id.tv_request)
        TextView tvRequest;
        @BindView(R.id.tv_projectName)
        TextView tvProjectName;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_receiveNum)
        TextView tvReceiveNum;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            tvItem = (TextView) itemView.findViewById(R.id.tv_edit);
//            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);

        }
    }





}