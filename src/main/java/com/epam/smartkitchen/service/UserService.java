package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;

import java.util.List;


public interface UserService {


    List<UserDto> getAllUser(int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    UserDto findById(String id);

    List<UserDto> getUsersByType(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(String id,UpdateUserDto updateUserDto);

    ResponseDeleteUserDto deleteUser(String id);

    public List<UserDto> exportExcel(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

}
