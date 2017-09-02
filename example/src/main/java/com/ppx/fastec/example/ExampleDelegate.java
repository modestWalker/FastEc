package com.ppx.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.ppx.latte.app.Latte;
import com.ppx.latte.delegates.LatteDelegate;
import com.ppx.latte.net.RestClient;
import com.ppx.latte.net.callback.IError;
import com.ppx.latte.net.callback.IFailure;
import com.ppx.latte.net.callback.ISuccess;

import butterknife.ButterKnife;

/**
 * Created by PPX on 2017/8/31.
 */

public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        // 获取程序可用最大内存
//        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024));
//        Toast.makeText(Latte.getApplication(), maxMemory + "MB", Toast.LENGTH_SHORT).show();
    }

    private void testRestClient() {
        RestClient.builder()
                .url("")
                .params("", "")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                }).build();
    }
}
