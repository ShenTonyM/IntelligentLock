package com.example.myf19.intelligentlock;

/**
 * Created by myf19 on 2018/4/11.
 */

public class User {
    private String name;

    private String image;

    public User(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
