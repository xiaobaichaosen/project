package com.yijie.com.yijie.activity.kendergard.kndergardadapter;

import android.content.Context;


import com.yijie.com.yijie.activity.kendergard.GroupEntity;

import java.util.ArrayList;

/**
 * 这是不带组尾的Adapter。
 * 只需要{@link GroupedRecyclerViewAdapter#hasFooter(int)}方法返回false就可以去掉组尾了。
 */
public class NoFooterAdapter extends GroupedListAdapter {
//
    public NoFooterAdapter(Context context, ArrayList<GroupEntity> groups) {
        super(context, groups);
    }
//
//    /**
//     * 返回false表示没有组尾
//     *
//     * @param groupPosition
//     * @return
//     */
//    @Override
//    public boolean hasFooter(int groupPosition) {
//        return false;
//    }





}
