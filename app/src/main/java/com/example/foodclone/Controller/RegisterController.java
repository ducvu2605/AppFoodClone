package com.example.foodclone.Controller;

import com.example.foodclone.Model.UserModel;

public class RegisterController {

    UserModel userModel ;

    public RegisterController() {
        userModel = new UserModel();
    }
    public void addInfoController(UserModel userModel,String uid){
        this.userModel.addInfo(userModel,uid);
    }
}
