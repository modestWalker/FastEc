package com.ppx.latte.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.ppx.latte.delegates.LatteDelegate;
import com.ppx.latte.ec.R;
import com.ppx.latte.ec.R2;
import com.ppx.latte.net.RestClient;
import com.ppx.latte.net.callback.IFailure;
import com.ppx.latte.net.callback.ISuccess;
import com.ppx.latte.util.log.LatteLogger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by PPX on 2017/9/7
 */

public class SignUpDelegate extends LatteDelegate {

    @BindView(R2.id.edit_sign_up_name)
    TextInputEditText mName = null;
    @BindView(R2.id.edit_sign_up_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_up_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_up_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_up_re_password)
    TextInputEditText mRePassword = null;

    @OnClick(R2.id.btn_sign_up)
    void OnClickSignUp() {
        if (checkForm()) {
            RestClient.builder()
                    .url("http://192.168.37.17:8080/RestServer/data/user_profile.json")
                    .params("", "")
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
//                            LatteLogger.json("USER_PROFILE", response);
                            Toast.makeText(getContext(), "" + response, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            Toast.makeText(getContext(), "sssssss", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .build()
                    .post();
            Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R2.id.tv_link_sign_in)
    void OnClickLink() {
        start(new SignInDelegate());
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            mName.setError("请输入姓名！");
            isPass = false;
        } else {
            mName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式！");
            isPass = false;
        } else {
            mEmail.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码不正确！");
            isPass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码！");
            isPass = false;
        } else {
            mPassword.setError(null);
        }
        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("请保持两次输入密码一致！");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }
        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
