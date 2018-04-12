package com.example.myf19.intelligentlock;

public class Member {

    private String username;

    private byte[] pictureBt;

    public Member(String username, byte[] pictureBt) {
        this.username = username;
        this.pictureBt = pictureBt;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPictureBt() {
        return pictureBt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPictureBt(byte[] pictureBt) {
        this.pictureBt = pictureBt;
    }
}
