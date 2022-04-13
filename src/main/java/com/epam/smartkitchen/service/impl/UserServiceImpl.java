package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.UserDto;
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
        User user = UserDto.toUser(userDto);
        if (userRepository.existsById(user.getId())) {
            return null;
        }
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser);
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
}
