package com.ppx.latte.app;

import android.content.Context;

import java.util.HashMap;

/**
 * 工具类
 * Created by PPX on 2017/8/31.
 */

public class Latte {
    // 配置初始化
    public static Configurator init(Context context) {
        Configurator.getInstance().getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    // 获取配置类实例
    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    // 获取配置信息项
    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getconfiguration(key);
    }

    // 获取全局上下文
    public static Context getApplicationContext() {
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);
    }
}
