package com.epam.smartkitchen.dto.user;

public class UpdateUserDto {
   private String name;
   private String surname;
   private String email;
   private String image;
   private String phone;
   private String address;

    public UpdateUserDto(String name, String surname, String email, String image, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.image = image;
        this.phone = phone;
        this.address = address;
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

}
