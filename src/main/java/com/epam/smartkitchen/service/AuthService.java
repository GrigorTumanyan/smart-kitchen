package com.epam.smartkitchen.service;

import com.epam.smartkitchen.dto.AuthenticationRequestDto;
import com.epam.smartkitchen.dto.user.AuthenticationUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.response.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    Response<ErrorResponse, UserDto> login(AuthenticationRequestDto requestDto, HttpServletResponse response);

    Response<ErrorResponse, UserDto> register(UserDto userDto);

    Response<ErrorResponse, String> activateAccount(String email);

    Response<ErrorResponse, String> forgottenPassword(String id, String checkMessage);

    Response<ErrorResponse, String> checkForgottenPassword(String email);
}
