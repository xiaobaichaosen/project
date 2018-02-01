package com.yijie.com.studentapp.view;

import android.support.design.widget.AppBarLayout;

/**
 * Created by 奕杰平台 on 2018/2/1.
 * 监听APPBarLayout 折叠进度
 */

public abstract  class AppBarStateChangeListener  implements AppBarLayout.OnOffsetChangedListener{

    public enum State{
        EXPANDED,
        COLLAPSED,
        IDLE
    }
    private State mCurrentState=State.IDLE;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED,i);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED,i);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE,i);
            }
            mCurrentState = State.IDLE;
        }
    }
    public abstract void onStateChanged(AppBarLayout appBarLayout, State state,int i);
}
