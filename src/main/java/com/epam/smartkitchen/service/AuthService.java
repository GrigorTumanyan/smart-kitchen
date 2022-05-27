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

    public Response<ErrorResponse, UserDto> login(AuthenticationRequestDto requestDto, HttpServletResponse response);

    public void updateRefreshToken(HttpServletRequest request, HttpServletResponse response);

    public Response<ErrorResponse, UserDto> register(UserDto userDto);
    }
