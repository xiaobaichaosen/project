package com.yijie.com.yijie.utils;

import android.content.Context;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;


public class AnimationUtil {


    public final static int ANIMATION_IN_TIME=500;
    public final static int ANIMATION_OUT_TIME=500;

    public static Animation createInAnimation(Context context, int fromYDelta){
        AnimationSet set=new AnimationSet(context,null);
        set.setFillAfter(true);

        TranslateAnimation animation=new TranslateAnimation(0,0,fromYDelta,0);
        animation.setDuration(ANIMATION_IN_TIME);
        set.addAnimation(animation);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(ANIMATION_IN_TIME);
        set.addAnimation(alphaAnimation);
        return set;
    }

    public static Animation createOutAnimation(Context context, int toYDelta){
        AnimationSet set=new AnimationSet(context,null);
        set.setFillAfter(true);

        TranslateAnimation animation=new TranslateAnimation(0,0,0,toYDelta);
        animation.setDuration(ANIMATION_OUT_TIME);
        set.addAnimation(animation);

        AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(ANIMATION_OUT_TIME);
        set.addAnimation(alphaAnimation);


        return set;
    }

    public static Animation createAlphaOutAnimation(Context context, int toYDelta){

        AlphaAnimation alphaAnimation=new AlphaAnimation(1,0);
        alphaAnimation.setDuration(ANIMATION_OUT_TIME);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    public static Animation createAlphaIntAnimation(Context context, int toYDelta){

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1);
        alphaAnimation.setDuration(ANIMATION_OUT_TIME);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }
    public static Animation createRotateupAnimation(){


        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(ANIMATION_OUT_TIME);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }
    public static Animation createRotatedownAnimation(){


        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(ANIMATION_OUT_TIME);
        rotateAnimation.setFillAfter(true);
        return rotateAnimation;
    }


}
