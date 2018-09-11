package com.yijie.com.yijie.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 奕杰平台 on 2018/5/2.
 */

public class StudentSample implements Serializable {
    private String sample;
    private List<String>imagePath;

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public List<String> getImagePath() {
        return imagePath;
    }

    public void setImagePath(List<String> imagePath) {
        this.imagePath = imagePath;
    }
}
