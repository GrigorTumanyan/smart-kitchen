package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.AuthService;
import com.epam.smartkitchen.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
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

    @GetMapping("activation/{id}")
    public ResponseEntity<Response<ErrorResponse, String>> activation(@PathVariable String id){
        Response<ErrorResponse, String> activateAccount = authService.activateAccount(id);
        return ResponseEntity.ok(activateAccount);
    }

    @PostMapping("forgotten")
    public ResponseEntity<Response<ErrorResponse, String>> forgottenPassword(@RequestBody String email){
        authService.forgottenPassword(email);
        Response<ErrorResponse, String> response = new Response<>(null, "Check your mail", null);
        return ResponseEntity.ok(response);
    }
}
