package com.yijie.com.yijie.activity.kendergard;


public class Item {
    private int idImage;
    private String name,description;

    public Item(int idImage, String name, String description, int type) {
        this.idImage = idImage;
        this.name = name;
        this.description = description;
        this.type=type;
        this.name = name;
    }
public Item(int type,String name){
    this.type=type;
    this.name=name;
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
