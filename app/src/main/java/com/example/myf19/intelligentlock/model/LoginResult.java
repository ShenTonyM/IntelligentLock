package com.example.myf19.intelligentlock.model;

/**
 * Created by myf19 on 2018/4/6.
 */

public class LoginResult {
    private String status;

    private String info;

    private String sex;

    private String nicename;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNicename() {
        return nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
