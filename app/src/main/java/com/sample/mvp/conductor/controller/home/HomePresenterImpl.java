package com.sample.mvp.conductor.controller.home;


import android.os.Handler;

public class HomePresenterImpl implements HomePresenter{

    HomeView view;

    public HomePresenterImpl(HomeView view){
        this.view = view;
    }

    @Override
    public void loadItems() {
        new Handler().postDelayed(new Runnable() {
            //Simulate async request
            @Override
            public void run() {
                view.onItemsLoadFinished(HomeModel.values());
            }
        },5000);
    }
}
