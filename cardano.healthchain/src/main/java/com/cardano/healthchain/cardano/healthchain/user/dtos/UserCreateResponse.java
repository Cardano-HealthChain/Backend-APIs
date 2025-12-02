package com.cardano.healthchain.cardano.healthchain.user.dtos;

import lombok.*;

import java.util.UUID;

public class UserCreateResponse {
    private String user_email;
    private String token;

    public UserCreateResponse(String user_email, String token) {
        this.user_email = user_email;
        this.token = token;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}