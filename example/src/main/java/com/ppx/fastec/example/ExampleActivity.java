package com.ppx.fastec.example;

import com.ppx.latte.activities.ProxyActivity;
import com.ppx.latte.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {

    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
