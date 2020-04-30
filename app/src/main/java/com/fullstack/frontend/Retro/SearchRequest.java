package com.fullstack.frontend.Retro;

import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("user_id")
    private int userId;
    private String key;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}