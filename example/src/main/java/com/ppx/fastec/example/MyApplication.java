package com.ppx.fastec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.ppx.latte.app.Latte;
import com.ppx.latte.ec.Icon.FontEcModule;
import com.ppx.latte.ec.database.DataBaseManager;
import com.ppx.latte.net.interceptors.DebugInterceptor;

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
                .withLoaderDelayed(1000)
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index", R.raw.test))
                .configure();
        DataBaseManager.getInstance().init(this);
    }
}
