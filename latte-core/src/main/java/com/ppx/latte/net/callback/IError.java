package com.ppx.latte.net.callback;

/**
 * 网络请求错误的回调接口
 * Created by PPX on 2017/9/1.
 */

public interface IError {
    void onError(int code, String msg);
}
