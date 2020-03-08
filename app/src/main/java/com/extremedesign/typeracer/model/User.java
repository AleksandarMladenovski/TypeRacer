package com.extremedesign.typeracer.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String uid;
    private String name;
    private String email;
    private String photoName;
    private boolean emailVerified;

    public User() {
    }

    @Ignore
    public User(String uid, String name, String email, String photoName, boolean emailVerified) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.photoName = photoName;
        this.emailVerified = emailVerified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
