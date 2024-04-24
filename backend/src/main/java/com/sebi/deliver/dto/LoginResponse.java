package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "Login response", description = "Login response")
public class LoginResponse {
    private String token;
    private String refreshToken;
    private String name;
    private String email;
    private Long id;
    private Boolean isAdmin;

    public LoginResponse() {
    }

    public LoginResponse(String token, String refreshToken, String name, String email, Long id, Boolean isAdmin) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.name = name;
        this.email = email;
        this.id = id;
        this.isAdmin = isAdmin;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
