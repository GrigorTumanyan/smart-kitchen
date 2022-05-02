package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.requestObject.RequestParamObject;
import com.epam.smartkitchen.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<UserDto> getAllUser(RequestParamObject param) {
        PageRequest pageable = createPageable(param.getPageNumber(), param.getPageSize(), param.getField(), param.getDirection());
        String deleted = param.getDeleted();
        Page<User> allUser = null;
        if (deleted == null) {
            allUser = userRepository.findAllByDeletedFalse(pageable);
        } else if (deleted.equals("all")) {
            allUser = userRepository.findAll(pageable);
        } else if (deleted.equals("only")) {
            allUser = userRepository.findAllByDeletedTrue(pageable);
        }
        if (allUser == null) {
            return null;
        }
        return toUserDto(allUser);
    }

    @Override
    public List<UserDto> getUsersByType(UserType userType, RequestParamObject param) {
        PageRequest pageable = createPageable(param.getPageNumber(), param.getPageSize(), param.getField(), param.getDirection());
        String deleted = param.getDeleted();
        Page<User> allUser = null;
        if (deleted == null) {
            allUser = userRepository.findByUserTypeAndDeletedFalse(userType, pageable);
            ;
        } else if (deleted.equals("all")) {
            allUser = userRepository.findByUserType(userType, pageable);
        } else if (deleted.equals("only")) {
            allUser = userRepository.findByUserTypeAndDeletedTrue(userType, pageable);
        }
        if (allUser == null) {
            return null;
        }
        return toUserDto(allUser);
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
        if (user == null) {
            return null;
        }
        User updatedUser = changeUserFields(updateUserDto, user);
        User save = userRepository.save(updatedUser);
        return new UserDto(save);
    }

    @Override
    public ResponseDeleteUserDto deleteUser(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user.setDeleted(true);
        User savedUser = userRepository.save(user);
        return new ResponseDeleteUserDto(savedUser.getDeleted());
    }

    @Override
    public UserDto findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDto(user);
    }

    @Override
    public List<UserDto> exportExcel(UserType userType, RequestParamObject requestParamObject) {
        if (userType != null) {
            return getUsersByType(userType, requestParamObject);
        }
        return getAllUser(requestParamObject);
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
        return user;
    }

    private PageRequest createPageable(int pageNumber, int pageSize, String field, String direction) {
        if (field == null) {
            return PageRequest.of(pageNumber, pageSize);
        } else if (direction == null) {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).ascending());
        } else {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).descending());
        }
    }

}
