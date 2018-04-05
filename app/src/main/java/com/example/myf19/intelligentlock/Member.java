package com.example.myf19.intelligentlock;

public class Member {

    private String name;

    private String imgString;

    public Member(String name, String imgString) {
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
