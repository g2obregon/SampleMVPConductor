package com.sample.mvp.conductor.controller.home;

import android.support.annotation.ColorRes;

import com.sample.mvp.conductor.R;

enum HomeModel {
        CHILD_CONTROLLERS("Second Controllers", R.color.orange_300);

        String title;
        @ColorRes
        int color;

        HomeModel(String title, @ColorRes int color) {
            this.title = title;
            this.color = color;
        }
    }