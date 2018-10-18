package com.yijie.com.studentapp.view.ninegrid;

import com.yijie.com.studentapp.bean.StudentEvaluate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：
 * 作者：HMY
 * 时间：2016/5/13
 */
public class NineGridTestModel implements Serializable {
    private static final long serialVersionUID = 2189052605715370758L;

    public List<String> urlList = new ArrayList<>();
    public StudentEvaluate studentEvaluate;
    public boolean isShowAll;
}
