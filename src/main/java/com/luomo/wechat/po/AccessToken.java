package com.luomo.wechat.po;

/**
 * Created by LiuMei on 2016-12-28.
 */
public class AccessToken {

    private String token;

    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
