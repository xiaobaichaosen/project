package com.yijie.com.studentapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.yijie.com.studentapp.utils.ImageLoaderUtil;

import java.util.List;

/**
 * Created by 奕杰平台 on 2017/12/14.
 */

public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>implements View.OnClickListener
{
    private Context context;
    private LayoutInflater mInflater;
    private List<String> mDatas;
    private OnItemClickListener mOnItemClickListener = null;
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    public GalleryAdapter(Context context, List<String> datats)
    {
        this.context=context;
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.student_list_item,
                viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view
                .findViewById(R.id.id_index_gallery_item_image);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.itemView.setTag(i);
//        viewHolder.mImg.setImageResource(mDatas.get(i));
        ImageLoaderUtil.getImageLoader(context).displayImage(mDatas.get(i), viewHolder.mImg, ImageLoaderUtil.getPhotoImageOption());
    }

}
