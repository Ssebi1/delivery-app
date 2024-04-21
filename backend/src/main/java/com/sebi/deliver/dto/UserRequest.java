package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;

@ApiModel(value = "User Request", description = "User request")
public class UserRequest {
    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "User name", required = true)
    private String name;
    @NotBlank(message = "Password is mandatory")
    @ApiModelProperty(value = "User password", required = true)
    private String password;
    @ApiModelProperty(value = "User address")
    private String address;
    @ApiModelProperty(value = "User address")
    private String phone;
    @ApiModelProperty(value = "User address")
    private String email;
    @ApiModelProperty(value = "User address")
    private String city;

    public UserRequest() {
    }

    public UserRequest(String name, String password, String address, String phone, String email, String city) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.city = city;
    }

    public UserRequest(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }
}
