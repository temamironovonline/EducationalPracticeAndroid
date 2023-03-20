package com.example.educationalpracticeandroid;

import java.io.File;

public class ProfileImages {
    public int id;
    public String path;
    public File imageProfile;
    public String date;

    public ProfileImages (int id, String path, File imageProfile, String date) {
        this.id = id;
        this.path = path;
        this.imageProfile = imageProfile;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(File imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getData() {
        return date;
    }

    public void setDate (String date) {
        this.date = date;
    }
}
