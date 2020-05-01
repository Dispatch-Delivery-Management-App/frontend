package com.fullstack.frontend.config;

public class UserInfo {
    private static int user_id = 0;

    public static void setUser_id(int user_id) {
        UserInfo.user_id = user_id;
    }

    public static int getUser_id() {
        return user_id;
    }
}
