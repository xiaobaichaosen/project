package com.yijie.com.yijie.bean.school;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * self_discovery
 * @author 
 */
public class SelfDiscovery implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    private Integer discoveryId;

    /**
     * 发现类型:0:新增待培训 1：待审核 2：新邮件查看 3:群聊
     */
    private int discoveryType;
    /**
     * 发现中图片
     */
    private String discoveryPic;
    /**
     * 发现标题
     */
    private String discoveryTitle;
    /**
     * 发现内容
     */
    private String discoveryContent;
    /**
     * 未读数量
     */
    private int unreadCount;
    /**
     * 推送消息数量
     */
     private int pushCount;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;


    private static final long serialVersionUID = 1L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiscoveryId() {
        return discoveryId;
    }

    public void setDiscoveryId(int discoveryId) {
        this.discoveryId = discoveryId;
    }

    public int getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(int discoveryType) {
        this.discoveryType = discoveryType;
    }

    public String getDiscoveryPic() {
        return discoveryPic;
    }

    public void setDiscoveryPic(String discoveryPic) {
        this.discoveryPic = discoveryPic;
    }

    public String getDiscoveryTitle() {
        return discoveryTitle;
    }

    public void setDiscoveryTitle(String discoveryTitle) {
        this.discoveryTitle = discoveryTitle;
    }

    public String getDiscoveryContent() {
        return discoveryContent;
    }

    public void setDiscoveryContent(String discoveryContent) {
        this.discoveryContent = discoveryContent;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
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

    public int getPushCount() {
        return pushCount;
    }

    public void setPushCount(int pushCount) {
        this.pushCount = pushCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelfDiscovery discovery = (SelfDiscovery) o;
        return Objects.equals(id, discovery.id) &&
                Objects.equals(discoveryId, discovery.discoveryId) &&
                Objects.equals(discoveryType, discovery.discoveryType) &&
                Objects.equals(discoveryPic, discovery.discoveryPic) &&
                Objects.equals(discoveryTitle, discovery.discoveryTitle) &&
                Objects.equals(discoveryContent, discovery.discoveryContent) &&
                Objects.equals(unreadCount, discovery.unreadCount) &&
                Objects.equals(pushCount, discovery.pushCount) &&
                Objects.equals(createTime, discovery.createTime) &&
                Objects.equals(updateTime, discovery.updateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, discoveryId, discoveryType, discoveryPic, discoveryTitle, discoveryContent, unreadCount, pushCount, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "SelfDiscovery{" +
                "id=" + id +
                ", discoveryId=" + discoveryId +
                ", discoveryType=" + discoveryType +
                ", discoveryPic='" + discoveryPic + '\'' +
                ", discoveryTitle='" + discoveryTitle + '\'' +
                ", discoveryContent='" + discoveryContent + '\'' +
                ", unreadCount=" + unreadCount +
                ", pushCount=" + pushCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}