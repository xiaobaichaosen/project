package com.yijie.com.studentapp.fragment.yijie;

/**
 * Created by Alessandro on 12/01/2016.
 */
public class StudentBean {
    private int idImage;
    private String name;
    private String description;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    private String dateString;

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
    public StudentBean() {

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
