package com.epam.smartkitchen.models;

import com.epam.smartkitchen.enums.UserType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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
    Boolean active;

    public User(String name, String surname, String email, String password, String image, String phone, String address, UserType userType, Boolean active) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.userType = userType;
        this.active = active;
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
}
