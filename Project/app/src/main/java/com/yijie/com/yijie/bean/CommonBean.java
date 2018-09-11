package com.yijie.com.yijie.bean;

import java.io.Serializable;

/**
 * Created by 奕杰平台 on 2018/5/2.
 * 公用的bean
 */

public class CommonBean implements Serializable{
    private String name;
    private String money;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
