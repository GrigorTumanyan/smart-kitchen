package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestController {

    private final AuthService authService;

    public AuthenticationRestController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("login")
    public ResponseEntity<Response<ErrorResponse, UserDto>> login(@RequestBody AuthenticationRequestDto requestDto, HttpServletResponse response) {
        Response<ErrorResponse, UserDto> login = authService.login(requestDto, response);
        return ResponseEntity.ok(login);
    }

    @PostMapping("register")
    public ResponseEntity<Response<ErrorResponse, UserDto>> register(@RequestBody UserDto userDto) {
        Response<ErrorResponse, UserDto> register = authService.register(userDto);
        return ResponseEntity.ok(register);
    }

    @PostMapping("refresh")
    public ResponseEntity<HttpStatus> updateRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.updateRefreshToken(request, response);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
