package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.user.UpdateUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;

import java.util.List;


public interface UserService {


    Response<ErrorResponse, List<UserDto>> getAllUser(int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, UserDto> findById(String id);

    Response<ErrorResponse, List<UserDto>> getUsersByType(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, UserDto> addUser(UserDto userDto);

    Response<ErrorResponse, UserDto> updateUser(String id, UpdateUserDto updateUserDto);

    Response<ErrorResponse, ResponseDeleteUserDto> deleteUser(String id);

    Response<ErrorResponse, List<UserDto>> exportExcel(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

}
