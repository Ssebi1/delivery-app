package com.sebi.deliver.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;

@ApiModel(value = "Message Request", description = "Message request")
public class MessageRequest {
    @NotBlank(message = "Name is mandatory")
    @ApiModelProperty(value = "Message name", required = true)
    private String name;
    @NotBlank(message = "Mail is mandatory")
    @ApiModelProperty(value = "Message mail", required = true)
    private String email;
    @NotBlank(message = "Message is mandatory")
    @ApiModelProperty(value = "Message text", required = true)
    private String message;

    @NotBlank(message = "Phone is mandatory")
    @ApiModelProperty(value = "Message phone", required = true)
    private String phone;
    @ApiModelProperty(value = "Message company")
    private String company;

    public MessageRequest() {
    }

    public MessageRequest(String name, String email, String message, String phone, String company) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.phone = phone;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public String getPhone() {
        return phone;
    }

    public String getCompany() {
        return company;
    }
}
