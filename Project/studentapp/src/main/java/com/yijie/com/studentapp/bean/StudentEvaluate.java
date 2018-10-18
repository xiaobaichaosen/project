package com.yijie.com.studentapp.bean;



import java.io.Serializable;
import java.util.Date;

/**
 * 学生app：学生对园所评价
 * @author 
 */
public class StudentEvaluate implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 学生用户id
     */
    private Integer studentUserId;

    /**
     * 园所id
     */
    private Integer kinderId;

    /**
     * 园所规模
     */
    private String scale;

    /**
     * 地理位置
     */
    private String position;

    /**
     * 硬件设置
     */
    private String hardware;

    /**
     * 交通便利程度
     */
    private String traffic;

    /**
     * 整体评价
     */
    private String totalEvaluate;

    /**
     * 拥挤程度
     */
    private String crowdingDegree;

    /**
     * 卫生状况
     */
    private String hygieneStatus;

    /**
     * 上下班便利程度
     */
    private String startEndWork;

    /**
     * 园所图片
     */
    private String kinderEvalPic;

    /**
     * 园所拍摄视频
     */
    private String kinderVideoUrl;

    /**
     * 是否匿名：1：匿名 2：不是匿名
     */
    private Integer isAnonymous;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }
/**
     * 创建时间
     */
    /**
     * 园所头像
     */
    private String headPic;

    /**
     * 园所名称
     */
    private String kindName;
    private String createTime;

    /**
     * 更新时间
     */

    private String updateTime;

    /**
     * 评价内容
     */
    private String evaluateContent;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentUserId() {
        return studentUserId;
    }

    public void setStudentUserId(Integer studentUserId) {
        this.studentUserId = studentUserId;
    }

    public Integer getKinderId() {
        return kinderId;
    }

    public void setKinderId(Integer kinderId) {
        this.kinderId = kinderId;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }

    public String getTotalEvaluate() {
        return totalEvaluate;
    }

    public void setTotalEvaluate(String totalEvaluate) {
        this.totalEvaluate = totalEvaluate;
    }

    public String getCrowdingDegree() {
        return crowdingDegree;
    }

    public void setCrowdingDegree(String crowdingDegree) {
        this.crowdingDegree = crowdingDegree;
    }

    public String getHygieneStatus() {
        return hygieneStatus;
    }

    public void setHygieneStatus(String hygieneStatus) {
        this.hygieneStatus = hygieneStatus;
    }

    public String getStartEndWork() {
        return startEndWork;
    }

    public void setStartEndWork(String startEndWork) {
        this.startEndWork = startEndWork;
    }

    public String getKinderEvalPic() {
        return kinderEvalPic;
    }

    public void setKinderEvalPic(String kinderEvalPic) {
        this.kinderEvalPic = kinderEvalPic;
    }

    public String getKinderVideoUrl() {
        return kinderVideoUrl;
    }

    public void setKinderVideoUrl(String kinderVideoUrl) {
        this.kinderVideoUrl = kinderVideoUrl;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }


}