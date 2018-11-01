package com.yijie.com.yijie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.yijie.com.yijie.Constant;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.utils.ImageLoaderUtil;
import com.yijie.com.yijie.utils.PhotoActivityForHor;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
public class KindCardAdapter extends RecyclerView.Adapter<KindCardAdapter.ViewHolder> implements View.OnClickListener{
    private final Context mContext;
    private final String kinderId;
    private final String imagePath;
    private final String url;
    private List<String> mList = new ArrayList<>();
//    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
private KindCardAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(KindCardAdapter.OnItemClickListener listener) {
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
    public KindCardAdapter(Context context, List<String> mList, String kinderId, String imagePath, String url) {
        this.mContext=context;
        this.mList = mList;
        this.kinderId=kinderId;
        this.imagePath=imagePath;
        this.url=url;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_picture_item, parent, false);
//        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
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
            if (url.equals("info")){
                ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.infoUrl+kinderId+"/"+imagePath+"/"+urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
            }else if (url.equals("kinder")){
                ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.kinderUrl+kinderId+"/"+imagePath+"/"+urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
            }
        }
        //将position保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(position);
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, PhotoActivityForHor.class);
//                Rect rect = new Rect();
//                view.getGlobalVisibleRect(rect);
//                if (url.equals("info")){
//                    intent.putExtra("imgUrl",Constant.infoUrl+kinderId+"/"+imagePath+"/"+urlString);
//                }else if (url.equals("kinder")){
//                    intent.putExtra("imgUrl",Constant.kinderUrl+kinderId+"/"+imagePath+"/"+urlString);
//                }
//                intent.putExtra("startBounds", rect);
//                mContext.startActivity(intent);
//                ((Activity)mContext).overridePendingTransition(0, 0);
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
