package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.user.*;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ConflictException;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RequestParamInvalidException;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
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
    public Response<ErrorResponse, List<UserDto>> getAll(int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
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
        return new Response<>(null, toUserDtoList(allUser), UserDto.class.getName());
    }

    @Override
    public Response<ErrorResponse, List<UserDto>> getByType(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
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
        return new Response<>(null, toUserDtoList(allUser), UserDto.class.getName());
    }

    @Override
    public Response<ErrorResponse, UserDto> add(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ConflictException(userDto.getEmail() + " Email already exists");
        }
        User user = UserDto.toUser(userDto);
        User savedUser = userRepository.save(user);
        return new Response<>(null, new UserDto(savedUser), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, UserDto> updateByManager(String id, UpdateUserDtoByManager updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        User updatedUser = updateUserFields(updateUserDto, user);
        User save = userRepository.save(updatedUser);
        return new Response<>(null, new UserDto(save), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, UserDto> update(String id, UpdateUserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        if (userRepository.existsByEmail(userDto.getEmail()) && !(user.getEmail().equals(userDto.getEmail()))) {
            throw new ConflictException("Email : " + userDto.getEmail() + "  already exists");
        }
        User updatedUser = updateUserFields(userDto, user);
        User savedUser = userRepository.save(updatedUser);
        return new Response<>(null, new UserDto(savedUser), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, ResponseDeleteUserDto> delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        user.setDeleted(true);
        User savedUser = userRepository.save(user);
        return new Response<>(null,
                new ResponseDeleteUserDto(savedUser.getDeleted()), ResponseDeleteUserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, UserDto> getByID(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        return new Response<>(null, new UserDto(user), UserDto.class.getSimpleName());
    }

    @Override
    public Response<ErrorResponse, List<UserDto>> exportExcel(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted) {
        if (userType != null) {
            return getByType(userType, pageNumber, pageSize, sortedField, direction, deleted);
        }
        return getAll(pageNumber, pageSize, sortedField, direction, deleted);
    }

    @Override
    public Response<ErrorResponse,UserDto> changePassword(String id, UserChangePasswordDto userDto){
        User user = userRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("User is not found with id : " + id));
        if (!userDto.getNewPassword().equals(userDto.getConfirmPassword())){
            throw new ConflictException("password and confirm is different");
        }
        if (!user.getPassword().equals(userDto.getOldPassword())){
            throw new ConflictException("Old password is not correct");
        }
        user.setPassword(userDto.getNewPassword());
        User save = userRepository.save(user);
        UserDto savedUserDto = new UserDto(save);
        return new Response<>(null, savedUserDto, UserDto.class.getSimpleName());
    }

    private List<UserDto> toUserDtoList(Page<User> userList) {
        List<UserDto> allUserDto = new ArrayList<>();
        for (User user : userList) {
            UserDto userDto = new UserDto(user);
            allUserDto.add(userDto);
        }
        return allUserDto;
    }

    private User updateUserFields(UpdateUserDtoByManager updateUserDto, User user) {
        user.setUserType(updateUserDto.getUserType());
        user.setActive(updateUserDto.getActive());
        return user;
    }

    private User updateUserFields(UpdateUserDto userDto, User user) {
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setImage(userDto.getImage());
        user.setPhone(userDto.getPhone());
        user.setAddress(userDto.getAddress());
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
