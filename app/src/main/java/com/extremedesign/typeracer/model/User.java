package com.extremedesign.typeracer.model;

import android.net.Uri;

public class User {
    private String uid;
    private UserInfo userInfo;

    public User() {
    }

    public User(String uid,UserInfo userInfo) {
        this.uid=uid;
        this.userInfo = userInfo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
