package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {


    List<UserDto> getAllUser(Pageable pageable);

    UserDto findById(String id);

    List<UserDto> getUsersByType(UserType userType, Pageable pageable);

    UserDto addUser(UserDto userDto);

    UserDto updateUser(String id,UpdateUserDto updateUserDto);

}
