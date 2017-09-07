package com.ppx.latte.util.dimen;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.ppx.latte.app.Latte;

/**
 * 测量工具类
 * Created by PPX on 2017/9/5.
 */

public class DimenUtil {

    // 获取屏幕宽度
    public static int getScreenWidth() {
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    // 获取屏幕高度
    public static int getScreenHeight() {
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
