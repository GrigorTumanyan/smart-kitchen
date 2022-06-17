package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


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

    @GetMapping("activation/{id}")
    public ResponseEntity<Response<ErrorResponse, String>> activation(@PathVariable String id){
        Response<ErrorResponse, String> activateAccount = authService.activateAccount(id);
        return ResponseEntity.ok(activateAccount);
    }

    @PostMapping("check")
    public ResponseEntity<Response<ErrorResponse, String>> checkForgottenPassword(@RequestBody Map<String, String> body){
        Response<ErrorResponse, String> response = authService.checkForgottenPassword(body.get("email"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("forgotten/{id}/{checkMessage}")
    public ResponseEntity<Response<ErrorResponse, String>> forgottenPassword(@PathVariable String id,
                                                                             @PathVariable String checkMessage ){
        authService.forgottenPassword(id, checkMessage);
        Response<ErrorResponse, String> response = new Response<>(null, "Check your mail", null);
        return ResponseEntity.ok(response);
    }
}
