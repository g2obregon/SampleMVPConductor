package com.sample.mvp.conductor.controller.login;


import android.os.Handler;

public class LoginPresenterImpl implements LoginPresenter {

    LoginView view;

    public LoginPresenterImpl(LoginView view){
        this.view = view;
    }

    @Override
    public void validateInput(final LoginModel model) {
        new Handler().post(new Runnable() {
            //Simulate async request
            @Override
            public void run() {
                if(model.getUserName().isEmpty())
                    view.showError("Username is Missing!!");
                else
                    view.goToNext();
            }
        });
    }
}
