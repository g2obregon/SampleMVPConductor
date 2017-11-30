package com.sample.mvp.conductor.controller.login;

import android.support.annotation.ColorRes;

import com.sample.mvp.conductor.R;

public class LoginModel {
    private String userName;

    public  LoginModel(String userName){
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}