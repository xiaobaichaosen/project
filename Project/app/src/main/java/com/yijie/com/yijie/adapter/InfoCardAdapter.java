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

public class InfoCardAdapter extends RecyclerView.Adapter<InfoCardAdapter.ViewHolder> {
    private final Context mContext;
    private final String kinderId;
    private final String imagePath;
    private final String url;
    private List<String> mList = new ArrayList<>();
//    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public InfoCardAdapter(Context context, List<String> mList, String kinderId, String imagePath,String url) {
        this.mContext=context;
        this.mList = mList;
        this.kinderId=kinderId;
        this.imagePath=imagePath;
        this.url=url;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_item, parent, false);
//        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
//        holder.mImageView.setImageResource(mList.get(position));
        final String urlString = mList.get(position);
        if (urlString.length()>0){
            //加载网络图片
//            if (url.equals("info")) {
//                ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.infoUrl + kinderId + "/" + imagePath + "/" + urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
//            }else if(url.equals("certificate")){
                ImageLoaderUtil.getImageLoader(mContext).displayImage(Constant.infoUrl + kinderId + "/" + imagePath + "/" + urlString, holder.mImageView, ImageLoaderUtil.getPhotoImageOption());
//            }
        }

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhotoActivityForHor.class);
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);
//                if (url.equals("info")){
//                    intent.putExtra("imgUrl",Constant.infoUrl+kinderId+"/"+imagePath+"/"+urlString);
//                }else if(url.equals("certificate")){
                    intent.putExtra("imgUrl",Constant.infoUrl+kinderId+"/"+imagePath+"/"+urlString);
//                }
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

