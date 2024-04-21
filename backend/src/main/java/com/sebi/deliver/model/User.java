package com.sebi.deliver.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
@Data
@Entity @Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
    @Schema(name = "User id", description = "Unique identifier", example = "1")
    private Long id;

    @NonNull
    @Schema(name = "User name", description = "User name", example = "John Doe")
    private String name;

    @NonNull
    @Email
    @Schema(name = "User email", description = "User email", example = "test@yahoo.com")
    private String email;

    @NonNull
    @Schema(name = "User password", description = "User password", example = "password")
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Schema(name = "User city", description = "User city", example = "Bucharest")
    private String city;

    @Schema(name = "User phone", description = "User phone", example = "0722222222")
    private String phone;

    @Schema(name = "User address", description = "User address", example = "Bucharest, 1st Street")
    private String address;

    @Schema(name = "User notes", description = "User notes", example = "Notes")
    private String notes;

    @Transient
    @Schema(name = "User token", description = "User token", example = "hashed_toke")
    private String token;

    @Schema(name = "User admin", description = "True if the user is an admin, false otherwise", example = "true")
    private boolean isAdmin;

    public User(
            @NonNull String name,
            @NonNull String email,
            @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(
            @NonNull Long id,
            @NonNull String name,
            @NonNull String email,
            @NonNull String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(
            @NonNull String email,
            @NonNull String password) {
        this.email = email;
        this.password = password;
    }
}
