package com.extremedesign.typeracer.model;

public class FriendlyPlayer {
    private String name;
    private String photo;

    public FriendlyPlayer() {
    }

    public FriendlyPlayer(String name, String emailUrl) {
        this.name = name;
        this.photo = emailUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
