package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.requestObject.RequestParamObject;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {


    List<UserDto> getAllUser(RequestParamObject requestParamObject);

    UserDto findById(String id);

    List<UserDto> getUsersByType(UserType userType, RequestParamObject param);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(String id,UpdateUserDto updateUserDto);

    ResponseDeleteUserDto deleteUser(String id);

    public List<UserDto> exportExcel(UserType userType, RequestParamObject requestParamObject);

}
