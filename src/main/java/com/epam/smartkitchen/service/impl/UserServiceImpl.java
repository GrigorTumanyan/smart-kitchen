package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.user.UpdateUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RequestParamInvalidException;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.exceptions.ResourceExistException;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Response<ErrorResponse, List<UserDto>> getAllUser(int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        PageRequest pageable = createPageable(pageNumber, pageSize, sortedField, direction);
        Page<User> allUser;
        if (deleted == null) {
            allUser = userRepository.findAllByDeletedFalse(pageable);
        } else if (deleted.equals("all")) {
            allUser = userRepository.findAll(pageable);
        } else if (deleted.equals("only")) {
            allUser = userRepository.findAllByDeletedTrue(pageable);
        } else {
            throw new RequestParamInvalidException("Parameter deleted is not correct: " + deleted);
        }
        if (allUser.getContent().size() < 1) {
            throw new RecordNotFoundException("Users are not found");
        }
        return new Response<>(null, toUserDto(allUser), UserDto.class.getName());
    }

    @Override
    public Response<ErrorResponse, List<UserDto>> getUsersByType(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        PageRequest pageable = createPageable(pageNumber, pageSize, sortedField, direction);
        Page<User> allUser;
        if (deleted == null) {
            allUser = userRepository.findByUserTypeAndDeletedFalse(userType, pageable);
        } else if (deleted.equals("all")) {
            allUser = userRepository.findByUserType(userType, pageable);
        } else if (deleted.equals("only")) {
            allUser = userRepository.findByUserTypeAndDeletedTrue(userType, pageable);
        } else {
            throw new RequestParamInvalidException("Parameter deleted is not correct: " + deleted);
        }
        if (allUser.getContent().size() < 1) {
            throw new RecordNotFoundException("Users are not found by type : " + userType);
        }
        return new Response<>(null, toUserDto(allUser), UserDto.class.getName());
    }

    @Override
    public Response<ErrorResponse, UserDto> addUser(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ResourceExistException(userDto.getEmail() + " Email already exists");
        }
        User user = UserDto.toUser(userDto);
        User savedUser = userRepository.save(user);
        return new Response<>(null, new UserDto(savedUser), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, UserDto> updateUser(String id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        User updatedUser = changeUserFields(updateUserDto, user);
        User save = userRepository.save(updatedUser);
        return new Response<>(null, new UserDto(save), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, ResponseDeleteUserDto> deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        user.setDeleted(true);
        User savedUser = userRepository.save(user);
        return new Response<>(null,
                new ResponseDeleteUserDto(savedUser.getDeleted()), ResponseDeleteUserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, UserDto> findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        return new Response<>(null, new UserDto(user), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, List<UserDto>> exportExcel(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        if (userType != null) {
            return getUsersByType(userType, pageNumber, pageSize, sortedField, direction, deleted);
        }
        return getAllUser(pageNumber, pageSize, sortedField, direction, deleted);
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
