package com.ppx.fastec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.ppx.latte.app.Latte;
import com.ppx.latte.ec.Icon.FontEcModule;

/**
 * Created by PPX on 2017/8/31.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 核心库配置初始化
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())   // 引入自定义字体图标库
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}
