package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Login response", description = "Login response")
public class LoginResponse {
    private String token;
    private String refreshToken;
    private String name;
    private String email;

    public LoginResponse() {
    }

    public LoginResponse(String token, String refreshToken, String name, String email) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.name = name;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
