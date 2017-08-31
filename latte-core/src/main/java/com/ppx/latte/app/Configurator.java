package com.ppx.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 配置信息存储及获取
 * Created by PPX on 2017/8/31.
 */

public class Configurator {
    // 存储配置信息
    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
    // 存储字体图标
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    final HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    // 静态内部类实现单例模式
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initIcon();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    // 添加域名
    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    // 初始化字体图标
    private void initIcon() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    // 添加自己的字体图标
    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    // 判断配置是否完成
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getconfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }
}
