package com.yijie.com.kindergartenapp.bean;

import com.lzy.imagepicker.bean.ImageItem;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 奕杰平台 on 2018/6/6.
 */

public class CommonBean  implements Serializable{
    private int type;
    private String rbString="";

    public CommonBean() {
    }

    private String content;
    private ArrayList<ImageItem> selImageList;

    public CommonBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public ArrayList<ImageItem> getSelImageList() {
        return selImageList;
    }

    public void setSelImageList(ArrayList<ImageItem> selImageList) {
        this.selImageList = selImageList;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommonBean{" +
                "type=" + type +
                ", rbString='" + rbString + '\'' +
                ", content='" + content + '\'' +
                ", selImageList=" + selImageList +
                ", cbString='" + cbString + '\'' +
                '}';
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRbString() {
        return rbString;
    }

    public void setRbString(String rbString) {
        this.rbString = rbString;
    }

    public String getCbString() {
        return cbString;
    }

    public void setCbString(String cbString) {
        this.cbString = cbString;
    }

    private String cbString="";
}
