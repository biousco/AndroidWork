package com.biousco.xuehu.Model;

import java.util.Map;

/**
 * Created by Biousco on 6/9.
 */
public class UserInfoModel {

    public String userid;
    public String username;
    public String pwd;
    public String imageurl;
    public String token;
    public String runtime;
    public Map userInfo;

    public UserInfoModel(Map userinfo) {
        this.userid = userinfo.get("userid").toString();
        this.username = userinfo.get("username").toString();
        this.pwd = userinfo.get("pwd").toString();
        this.imageurl = userinfo.get("imageurl").toString();
        this.token = userinfo.get("token").toString();
        this.runtime = userinfo.get("runtime").toString();
        this.userInfo = userinfo;
    }

    public UserInfoModel() {

    }

    public Map getUserInfo() {
        return this.userInfo;
    }
}
