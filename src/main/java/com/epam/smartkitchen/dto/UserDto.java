package com.epam.smartkitchen.dto;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;

public class UserDto {
    private String name;
    private String surname;
    private String email;
    private String image;
    private String phone;
    private String address;
    private String password;
    private UserType userType;
    private boolean active;


    public User toUser(){
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setImage(image);
        user.setPhone(phone);
        user.setAddress(address);
        user.setActive(active);
        user.setUserType(userType);
        user.setPassword(password);
        return user;
    }

    public static UserDto toUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setImage(user.getImage());
        userDto.setPhone(user.getPhone());
        userDto.setAddress(user.getAddress());
        userDto.setActive(user.getActive());
        userDto.setPassword(user.getPassword());
        userDto.setUserType(user.getUserType());
        return userDto;

    }

    public UserDto (UserDto userDto){
        this.name = userDto.name;
        this.surname = userDto.surname;
        this.email = userDto.email;
        this.image = userDto.image;
        this.phone = userDto.phone;
        this.address = userDto.address;
        this.active = userDto.active;
        this.password = userDto.password;
        this.userType = userDto.userType;
    }

    public UserDto() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
