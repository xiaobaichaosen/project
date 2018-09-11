package com.yijie.com.yijie.activity.school;

/**
 * Created by Alessandro on 12/01/2016.
 */
public class StudentBean {
    private int idImage;
    private String name,description,concatName,phoneNumber,ziNubmer,memeryContent,creatDate;

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public String getMemeryContent() {
        return memeryContent;
    }

    public void setMemeryContent(String memeryContent) {
        this.memeryContent = memeryContent;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZiNubmer() {
        return ziNubmer;
    }

    public void setZiNubmer(String ziNubmer) {
        this.ziNubmer = ziNubmer;
    }

    public StudentBean(){

}
    public StudentBean(int idImage, String name, String description, int type) {
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.type=type;
    }
    public StudentBean(int type, String name) {

        this.name = name;

        this.type=type;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int type;
    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
