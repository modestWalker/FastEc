package com.ppx.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * 基础层delegate，主要进行基础配置
 * Created by PPX on 2017/8/31.
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        // 如果setLayout返回值是整型，即R.layout.xxx，直接加载布局
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            // 绑定视图控件
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 解除绑定
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
