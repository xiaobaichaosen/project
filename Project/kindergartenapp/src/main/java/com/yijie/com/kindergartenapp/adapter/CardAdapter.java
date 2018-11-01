package com.yijie.com.kindergartenapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.yijie.com.kindergartenapp.Constant;
import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.activity.PhotoActivity;
import com.yijie.com.kindergartenapp.utils.ImageLoaderUtil;
import com.yijie.com.kindergartenapp.utils.PhotoActivityForHor;
import com.yijie.com.kindergartenapp.view.RatioImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements View.OnClickListener{
    private final Context mContext;
    private final String kinderId;
    private final String imagePath;
    private List<String> mList = new ArrayList<>();
//    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
private CardAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(CardAdapter.OnItemClickListener listener) {
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
    public CardAdapter(Context context, List<String> mList, String kinderId,String imagePath) {
        this.mContext=context;
        this.mList = mList;
        this.kinderId=kinderId;
        this.imagePath=imagePath;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        itemView.setOnClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
//        holder.mImageView.setImageResource(mList.get(position));
        final String urlString = mList.get(position);
        if (urlString.length()>0){
            //加载网络图片
            ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.certificateUrl+kinderId+"/"+imagePath+"/"+urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PhotoActivityForHor.class);
//                Rect rect = new Rect();
//                view.getGlobalVisibleRect(rect);
//                intent.putExtra("imgUrl",Constant.certificateUrl+kinderId+"/"+imagePath+"/"+urlString);
//                intent.putExtra("startBounds", rect);
//                mContext.startActivity(intent);
//                ((Activity)mContext).overridePendingTransition(0, 0);


//
//                Intent intent = new Intent(mContext, PhotoActivity.class);
//                String imageArray[] = new String[urlList.size()];
//                int childCount = getChildCount();
//                ArrayList<Rect> rects = new ArrayList<>();
//                for (int k = 0; k < childCount; k++) {
//
//                    Rect rect = new Rect();
//                    View child = getChildAt(k);
//                    if (child instanceof RatioImageView){
//                        child.getGlobalVisibleRect(rect);
//                        rects.add(rect);
//                    }
//                    imageArray[k] = urlList.get(k);
//
//                }
//
//
//                intent.putExtra("imgUrls", imageArray);
//                intent.putExtra("index", i);
//                intent.putExtra("bounds", rects);
//                mContext. startActivity(intent);


//            }
//        });
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
