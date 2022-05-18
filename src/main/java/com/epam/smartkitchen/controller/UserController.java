package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.user.UpdateUserDto;
import com.epam.smartkitchen.dto.user.ChangePasswordUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> myProfile(@PathVariable String id){
        Response<ErrorResponse, UserDto> userById = userService.getById(id);
        return ResponseEntity.ok(userById);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<ErrorResponse,UserDto>> updateProfile(@PathVariable String id, @RequestBody UpdateUserDto userDto){
        Response<ErrorResponse, UserDto> updatedUser = userService.update(id, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> changePassword(@PathVariable String id, @RequestBody ChangePasswordUserDto userDto){
        Response<ErrorResponse, UserDto> userDtoResponse = userService.changePassword(id, userDto);
        return ResponseEntity.ok(userDtoResponse);
    }


}
