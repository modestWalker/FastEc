package com.ppx.fastec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ppx.latte.delegates.LatteDelegate;

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

    }
}
