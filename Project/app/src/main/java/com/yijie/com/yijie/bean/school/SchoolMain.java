package com.yijie.com.yijie.bean.school;



import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * school_main
 * @author 
 */
public class SchoolMain implements Serializable {
    /**
     * 主键-
     */
    private Integer id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 经度

     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 学校地址（省，市）
     */
    private String location;

    /**
     * 学校地址（详细地址）
     */
    private String detailAddress;

    /**
     * 拥有者
     */
    private Integer owner;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 创建时间
     */

    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 是否删除
     */
    private Integer isDel;

    /**
     * 详情
     */
    private String content;

    /**
     * 图片url（多个url用英文分号;隔开）
     */
    private String img;

    /**
     * 缩略图url（多个url用英文分号;隔开）
     */
    private String litimg;
    /**
     * 学校logo图片
     */
    private String logo;
    /**
     * 统计所有项目
     */
    private Integer count;
    /**
     * 统计开发项目
     */
    private Integer count0;
    /**
     * 统计待培训项目
     */
    private Integer count1;
    /**
     * 联系人id
     */
    private Integer contactId;
    /**
     * 联系人名字
     */
//    private String username;
    /**
     * 手机号
     */
    private String cellphone;

    /**
     * 联系人名字
     */
    private String user_name;
    /**
     * 备忘录内容
     */
    private String  memo_content;
    /**
     * 备忘录状态 0：未删除，1：删除
     */
    private Integer memoIsDel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }




    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLitimg() {
        return litimg;
    }

    public void setLitimg(String litimg) {
        this.litimg = litimg;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount0() {
        return count0;
    }

    public void setCount0(Integer count0) {
        this.count0 = count0;
    }

    public Integer getCount1() {
        return count1;
    }

    public void setCount1(Integer count1) {
        this.count1 = count1;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMemo_content() {
        return memo_content;
    }

    public void setMemo_content(String memo_content) {
        this.memo_content = memo_content;
    }

    public Integer getMemoIsDel() {
        return memoIsDel;
    }

    public void setMemoIsDel(Integer memoIsDel) {
        this.memoIsDel = memoIsDel;
    }

    @Override
    public String toString() {
        return "SchoolMain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", location='" + location + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", owner=" + owner +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDel=" + isDel +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", litimg='" + litimg + '\'' +
                ", logo='" + logo + '\'' +
                ", count=" + count +
                ", count0=" + count0 +
                ", count1=" + count1 +
                ", contactId=" + contactId +
                ", cellphone='" + cellphone + '\'' +
                ", user_name='" + user_name + '\'' +
                ", memo_content='" + memo_content + '\'' +
                ", memoIsDel=" + memoIsDel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchoolMain that = (SchoolMain) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(location, that.location) &&
                Objects.equals(detailAddress, that.detailAddress) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(createBy, that.createBy) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDel, that.isDel) &&
                Objects.equals(content, that.content) &&
                Objects.equals(img, that.img) &&
                Objects.equals(litimg, that.litimg) &&
                Objects.equals(logo, that.logo) &&
                Objects.equals(count, that.count) &&
                Objects.equals(count0, that.count0) &&
                Objects.equals(count1, that.count1) &&
                Objects.equals(contactId, that.contactId) &&
                Objects.equals(cellphone, that.cellphone) &&
                Objects.equals(user_name, that.user_name) &&
                Objects.equals(memo_content, that.memo_content) &&
                Objects.equals(memoIsDel, that.memoIsDel);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, longitude, latitude, location, detailAddress, owner, createBy, createTime, updateTime, isDel, content, img, litimg, logo, count, count0, count1, contactId, cellphone, user_name, memo_content, memoIsDel);
    }
}