package com.ppx.fastec.example;

import android.app.Application;

import com.ppx.latte.app.Latte;

/**
 * Created by PPX on 2017/8/31.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 核心库配置初始化
        Latte.init(this).configure();
    }
}
