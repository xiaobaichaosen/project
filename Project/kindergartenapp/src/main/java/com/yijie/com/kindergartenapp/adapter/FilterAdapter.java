package com.yijie.com.kindergartenapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.bean.KindergartenDetail;

import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends BaseAdapter implements Filterable {
    private ArrayFilter mFilter;

    public List<KindergartenDetail> getmList() {
        return mList;
    }

    public void setmList(List<KindergartenDetail> mList) {
        this.mList = mList;
    }

    private List<KindergartenDetail> mList;
    private Context context;
    private ArrayList<KindergartenDetail> mUnfilteredData;

    public FilterAdapter(List<KindergartenDetail> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {

        return mList==null ? 0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if(convertView==null){
            view = View.inflate(context, R.layout.adapter_popu_item, null);

            holder = new ViewHolder();
            holder.tv_name = (TextView) view.findViewById(R.id.text1);


            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        KindergartenDetail pc = mList.get(position);

        holder.tv_name.setText(pc.getKindName());


        return view;
    }

    static class ViewHolder{
        public TextView tv_name;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mUnfilteredData == null) {
                mUnfilteredData = new ArrayList<KindergartenDetail>(mList);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<KindergartenDetail> list = mUnfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<KindergartenDetail> unfilteredValues = mUnfilteredData;
                int count = unfilteredValues.size();

                ArrayList<KindergartenDetail> newValues = new ArrayList<KindergartenDetail>(count);

                for (int i = 0; i < count; i++) {
                    KindergartenDetail pc = unfilteredValues.get(i);
                    if (pc != null) {

                        if(pc.getKindName()!=null && pc.getKindName().startsWith(prefixString)){

                            newValues.add(pc);
                        }else if(pc.getEmail()!=null && pc.getEmail().startsWith(prefixString)){

                            newValues.add(pc);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            //noinspection unchecked
            mList = (List<KindergartenDetail>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
