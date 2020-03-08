package com.extremedesign.typeracer.model;

import androidx.annotation.NonNull;

public class UserInfo {
    private String name;
    private String email;
    private String photoName;
    private boolean emailVerified;

    public UserInfo() {
    }

    public UserInfo(String name, String email, String photoUrl, boolean emailVerified) {
        this.name = name;
        this.email = email;
        this.photoName = photoUrl;
        this.emailVerified = emailVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoName;
    }

    public void setPhotoName(String photoUrl) {
        this.photoName = photoUrl;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + email + " " + photoName + " " + isEmailVerified();
    }
}
