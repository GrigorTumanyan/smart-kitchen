package com.epam.smartkitchen.dto.user;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;

import java.util.Objects;

public class UserDto {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String image;
    private String phone;
    private String address;
    private UserType userType;

    public UserDto(String id, String name, String surname, String email, String image, String phone, String address, UserType userType) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.address = address;
        this.userType = userType;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setImage(userDto.getImage());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
        user.setUserType(userDto.getUserType());
        return user;
    }

    public static UserDto toUserDto (User user){
        return new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getImage(),
                user.getPhone(), user.getAddress(), user.getUserType() );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(surname, userDto.surname) && Objects.equals(email, userDto.email) && Objects.equals(image, userDto.image) && Objects.equals(phone, userDto.phone) && Objects.equals(address, userDto.address) && userType == userDto.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, image, phone, address, userType);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", userType=" + userType +
                '}';
    }
}
