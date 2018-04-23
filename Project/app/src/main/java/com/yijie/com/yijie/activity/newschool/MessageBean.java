package com.yijie.com.yijie.activity.newschool;

import com.lzy.imagepicker.bean.ImageItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 奕杰平台 on 2018/4/3.
 */

public class MessageBean implements Serializable{
    public MessageBean(String message,ArrayList<ImageItem> selImageList){
        this.content=message;
        this.selImageList=selImageList;
    }
   private String content;
    private  ArrayList<ImageItem> selImageList;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<ImageItem> getSelImageList() {
        return selImageList;
    }

    public void setSelImageList(ArrayList<ImageItem> selImageList) {
        this.selImageList = selImageList;
    }
}
