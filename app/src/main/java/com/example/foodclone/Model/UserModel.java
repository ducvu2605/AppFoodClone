package com.example.foodclone.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserModel {
    private DatabaseReference dataNodeUser;

    String name;
    String photo;
    String user_code;
    public UserModel() {
        dataNodeUser = FirebaseDatabase.getInstance().getReference().child("users");
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
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
    public void addInfo(UserModel userModel,String uid){

        dataNodeUser.child(uid).setValue(userModel,uid);
    }
}
