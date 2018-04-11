package com.example.myf19.intelligentlock;

/**
 * Created by myf19 on 2018/4/11.
 */

public class User {
    private String name;

    private String imgString;

    public User(String name, String imgString) {
        this.name = name;
        this.imgString = imgString;
    }

    public String getName() {
        return name;
    }

    public String getImgString() {
        return imgString;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgString(String imgString) {
        this.imgString = imgString;
    }
}
