package com.biousco.xuehu.Model;

import java.util.Map;

/**
 * Created by Biousco on 6/9.
 */
public class UserInfo {

    private String userid;
    private String username;
    private String pwd;
    private String imageurl;
    private String token;
    private String runtime;
    private Map userInfo;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public UserInfo(Map userinfo) {
        this.userid = userinfo.get("userid").toString();
        this.username = userinfo.get("username").toString();
        this.pwd = userinfo.get("pwd").toString();
        this.imageurl = userinfo.get("imageurl").toString();
        this.token = userinfo.get("token").toString();
        this.runtime = userinfo.get("runtime").toString();
        this.userInfo = userinfo;
    }

    public Map getUserInfo() {
        return this.userInfo;
    }
}
