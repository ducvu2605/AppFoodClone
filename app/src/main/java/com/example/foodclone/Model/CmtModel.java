package com.example.foodclone.Model;

import java.util.List;

public class CmtModel {
    long likes;
    double point;
    UserModel userModel;
    String content;
    String title;
    String user_code,codeCMT;
    List<String> imageCMT;
    public CmtModel() {
    }

    public List<String> getImageCMT() {
        return imageCMT;
    }

    public void setImageCMT(List<String> imageCMT) {
        this.imageCMT = imageCMT;
    }

    public String getCodeCMT() {
        return codeCMT;
    }

    public void setCodeCMT(String codeCMT) {
        this.codeCMT = codeCMT;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
