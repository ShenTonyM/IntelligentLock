package com.example.myf19.intelligentlock;

public class Member {

    private String name;

    private byte[] pictureBt;

    public Member(String name, byte[] pictureBt) {
        this.name = name;
        this.pictureBt = pictureBt;
    }

    public String getName() {
        return name;
    }

    public byte[] getPictureBt() {
        return pictureBt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPictureBt(byte[] pictureBt) {
        this.pictureBt = pictureBt;
    }
}
