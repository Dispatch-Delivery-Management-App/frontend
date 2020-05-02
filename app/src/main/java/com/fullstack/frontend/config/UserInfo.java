package com.fullstack.frontend.config;

// wrong usage. SharePreference or separate class to save user info
public class UserInfo {
    private int userId;

    private static UserInfo instance;

    public static UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo();
        }
        return instance;
    }

    public void setUserId(int userId) {
       this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
