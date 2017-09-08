package com.ppx.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ppx.latte.ec.database.DataBaseManager;
import com.ppx.latte.ec.database.UserProfile;

/**
 * Created by PPX on 2017/9/8
 */

public class SignHandler {
    public static void onSignUp(String response) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String adderss = profileJson.getString("address");
        final UserProfile profile = new UserProfile(userId, name, avatar, gender, adderss);
        DataBaseManager.getInstance().getDao().insert(profile);
    }
}
