package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {


    List<UserDto> getAllUser(Pageable pageable);

    UserDto findById(String id);

    List<UserDto> getUsersByType(UserType userType, Pageable pageable);

    UserDto addUser(UserDto userDto);

}
