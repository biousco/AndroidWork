package com.biousco.xuehu.helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.biousco.xuehu.BaseActivity;
import com.biousco.xuehu.LoginActivity;
import com.biousco.xuehu.Model.UserInfoModel;

/**
 * Created by Biousco on 6/9.
 */

public class UserInfoHelper extends BaseActivity {


    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor spEditor;
    public final String SAVE_FILE_NAME = "userInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean checkIfLogin() {
        this.sharedPreferences = getSharedPreferences(SAVE_FILE_NAME, MODE_PRIVATE);
        String username = this.sharedPreferences.getString("token", "").toString();
        if(sharedPreferences == null || username.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean saveUserInfo(UserInfoModel ui) {
        sharedPreferences = getSharedPreferences(SAVE_FILE_NAME, 0);
        spEditor = sharedPreferences.edit();
        spEditor.putString("userid", ui.userid);
        spEditor.putString("username", ui.username);
        spEditor.putString("pwd", ui.pwd);
        spEditor.putString("imageurl", ui.imageurl);
        spEditor.putString("token", ui.token);
        spEditor.putString("runtime", ui.runtime);
        return true;
    }
}
