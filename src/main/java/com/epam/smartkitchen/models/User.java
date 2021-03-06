package com.epam.smartkitchen.models;

import com.epam.smartkitchen.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Entity
public class User extends BaseEntity {
    @Column
    String name;
    @Column
    String surname;
    @Column
    String email;
    @Column
    String password;
    @Column
    String image;
    @Column
    String phone;
    @Column
    String address;
    @Column
    @Enumerated(EnumType.STRING)
    UserType userType;
    @Column
    Boolean active = Boolean.FALSE;
    @Column(name = "refresh_token")
    String refreshToken;
    @Column(name = "activation_forgotten_password")
    String activationForgottenPassword;

    public User(String name, String surname, String email, String password, String image, String phone,
                String address, UserType userType, Boolean active, String refreshToken,String activationForgottenPassword) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.userType = userType;
        this.active = active;
        this.refreshToken = refreshToken;
        this.activationForgottenPassword = activationForgottenPassword;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getActivationForgottenPassword() {
        return activationForgottenPassword;
    }

    public void setActivationForgottenPassword(String activationForgottenPassword) {
        this.activationForgottenPassword = activationForgottenPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(image, user.image) && Objects.equals(phone, user.phone) && Objects.equals(address, user.address) && userType == user.userType && Objects.equals(active, user.active) && Objects.equals(refreshToken, user.refreshToken) && Objects.equals(activationForgottenPassword, user.activationForgottenPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, surname, email, password, image, phone, address, userType, active, refreshToken, activationForgottenPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userType=" + userType +
                ", active=" + active +
                ", refreshToken='" + refreshToken + '\'' +
                ", activationForgottenPassword='" + activationForgottenPassword + '\'' +
                '}';
    }
}
