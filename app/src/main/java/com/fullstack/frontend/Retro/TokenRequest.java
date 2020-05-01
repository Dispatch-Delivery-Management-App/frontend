package com.fullstack.frontend.Retro;

public class TokenRequest {
    public int user_id;
    public String token;

    public TokenRequest(int user_id, String token) {
        this.user_id = user_id;
        this.token = token;
    }

}
