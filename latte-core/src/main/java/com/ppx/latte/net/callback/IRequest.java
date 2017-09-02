package com.ppx.latte.net.callback;

/**
 * Created by PPX on 2017/9/1.
 */

public interface IRequest {

    // 网络请求开始的回调方法
    void onRequestStart();

    // 网络请求结束的回调方法
    void onRequestEnd();
}
