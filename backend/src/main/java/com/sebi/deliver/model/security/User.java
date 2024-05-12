package com.sebi.deliver.model.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sebi.deliver.model.CartItem;
import com.sebi.deliver.model.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@ToString
@Data
@Entity @Table(name = "users")
public class User implements UserDetails {
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

    @Schema(name = "User admin", description = "True if the user is an admin, false otherwise", example = "true")
    private boolean isAdmin;

    @JsonBackReference(value = "user")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    @JsonBackReference(value = "coupon")
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Coupon coupon;

    public User(
            @NonNull Long id,
            @NonNull String name,
            @NonNull String email,
            @NonNull String password,
            @NonNull String city,
            @NonNull String phone,
            @NonNull String address,
            @NonNull String notes,
            boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.notes = notes;
        this.isAdmin = isAdmin;
    }

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

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = isAdmin ? "ADMIN" : "USER";
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
