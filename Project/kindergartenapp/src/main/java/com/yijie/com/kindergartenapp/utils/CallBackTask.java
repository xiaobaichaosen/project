package com.yijie.com.kindergartenapp.utils;

import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 奕杰平台 on 2018/7/5.
 */

public class CallBackTask {
    private CallBackTask() {
        id = 0;
        map = new HashMap<>();
    }

    private long id;//唯一的key,依次递增
    /**
     * 存储CallBack的map
     * key为对象的内存地址+id
     * 值为对象的弱引用
     */
    private HashMap<String, WeakReference<BaseCallback>> map;

    private static CallBackTask task = new CallBackTask();

    /**
     * 获取单实例,由于网络请求在应用中基本一直会用到,所以直接使用饿汉单例
     * @return
     */
    public static CallBackTask create() {
        return task;
    }

    /**
     * 将回调存储在此,并返回key
     */
    public String add(BaseCallback value) {
        if (value == null)
            return "";
        String key;
        synchronized (CallBackTask.class) {
            key = value.toString() + id;
            id++;
        }
        map.put(key, new WeakReference<>(value));
        return key;
    }

    /**
     * 根据key移除引用
     */
    public void remove(String key) {
        if (TextUtils.isEmpty(key) || map.size() == 0)
            return;
        map.remove(key);
    }

    /**
     * 根据对象的地址移除对象
     */
    public void remove(BaseCallback value) {
        if (value == null || map.size() == 0)
            return;
        List<String> list = new ArrayList<>(5);
        synchronized (CallBackTask.class) {
            if (map.size() == 0)
                return;
            for (String key : map.keySet())
                if (key.contains(value.toString()))
                    list.add(key);
            for (String key : list)
                map.remove(key);
        }
    }

    /**
     * 根据key取出回调
     */
    public BaseCallback get(String key) {
        if (TextUtils.isEmpty(key) || map.size() == 0)
            return null;
        WeakReference<BaseCallback> baseHttpWeakReference = map.get(key);
        if (baseHttpWeakReference == null) {
            map.remove(key);
            return null;
        }
        BaseCallback callBack = baseHttpWeakReference.get();
        if (callBack == null)
            map.remove(key);
        return callBack;
    }
}
