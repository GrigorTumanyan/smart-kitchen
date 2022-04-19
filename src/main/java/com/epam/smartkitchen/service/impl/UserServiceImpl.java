package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDto> getAllUser(Pageable pageable) {
        Page<User> allUser = userRepository.findAll(pageable);
        if (allUser == null) {
            return null;
        }
        return toUserDto(allUser);
    }

    @Override
    public List<UserDto> getUsersByType(UserType userType, Pageable pageable) {
        Page<User> byUserType = userRepository.findByUserType(userType, pageable);
        if (byUserType == null) {
            return null;
        }
        return toUserDto(byUserType);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return null;
        }
        User user = UserDto.toUser(userDto);
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser);
    }

    @Override
    public UserDto updateUser(String id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        User updatedUser = changeUserFields(updateUserDto, user);
        User save = userRepository.save(updatedUser);
        return new UserDto(save);
    }

    @Override
    public ResponseDeleteUserDto deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        user.setRemoved(true);
        User savedUser = userRepository.save(user);
        return new ResponseDeleteUserDto(savedUser.getRemoved());
    }

    @Override
    public UserDto findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }


    private List<UserDto> toUserDto(Page<User> userList) {
        List<UserDto> allUserDto = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto(user);
            allUserDto.add(userDto);
        }
        return allUserDto;
    }

    private User changeUserFields(UpdateUserDto updateUserDto, User user) {
        user.setUserType(updateUserDto.getUserType());
        user.setActive(updateUserDto.getActive());
        user.setRemoved(updateUserDto.getRemoved());
        return user;
    }
}
