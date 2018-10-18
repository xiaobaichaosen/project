package com.yijie.com.studentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.activity.PhotoActivityForHor;
import com.yijie.com.studentapp.utils.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

public class BigCardAdapter  extends RecyclerView.Adapter<BigCardAdapter.ViewHolder> {
    private final Context mContext;
    private final int schoolId;
    private List<String> mList = new ArrayList<>();
    private BigCardAdapterHelper mCardAdapterHelper = new BigCardAdapterHelper();

    public BigCardAdapter(Context context,List<String> mList,int schoolId) {
        this.mContext=context;
        this.mList = mList;
        this.schoolId=schoolId;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bigview_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
//        holder.mImageView.setImageResource(mList.get(position));
        final String urlString = mList.get(position);
        if (urlString.length()>0){
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.kinderUrl+schoolId+"/environment/"+urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhotoActivityForHor.class);
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
                intent.putExtra("imgUrl", Constant.kinderUrl+schoolId+"/environment/"+urlString);
                intent.putExtra("startBounds", rect);
                mContext.startActivity(intent);
                ((Activity)mContext).overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImageView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

    }

}

