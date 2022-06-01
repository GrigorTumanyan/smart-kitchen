package com.epam.smartkitchen.dto.user;

import java.util.Objects;

public class AuthenticationUserDto {
    private String email;
    private String refreshToken;

    public AuthenticationUserDto(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationUserDto that = (AuthenticationUserDto) o;
        return Objects.equals(email, that.email) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, refreshToken);
    }
}
