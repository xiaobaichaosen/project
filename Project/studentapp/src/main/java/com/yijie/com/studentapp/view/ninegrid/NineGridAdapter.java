package com.yijie.com.studentapp.view.ninegrid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijie.com.studentapp.Constant;
import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.bean.StudentEvaluate;
import com.yijie.com.studentapp.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by HMY on 2016/8/6
 */
public class NineGridAdapter extends RecyclerView.Adapter<NineGridAdapter.ViewHolder> {

    private Context mContext;
    private  List<StudentEvaluate> mList;
    protected LayoutInflater inflater;

    public NineGridAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList( List<StudentEvaluate> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = inflater.inflate(R.layout.access_ninegrid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StudentEvaluate studentEvaluates = mList.get(position);
        String kinderEvalPic = studentEvaluates.getKinderEvalPic();
        //TODO 转换成url
        String userId = (String) SharedPreferencesUtils.getParam(mContext, "id", "");

        String[] split = kinderEvalPic.split(";");
        ArrayList<String> strings1 = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            strings1.add(Constant.infoUrl+userId+"/evaluate/"+split[i]);
        }
        holder.layout.setUrlList(strings1);
        holder.tvContent.setText(studentEvaluates.getEvaluateContent());
        holder.tvDate.setText(studentEvaluates.getCreateTime());
    }

    @Override
    public int getItemCount() {
        return getListSize(mList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NineGridTestLayout layout;
        TextView tvDate;
        TextView tvContent;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    private int getListSize(List<StudentEvaluate> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
