package com.epam.smartkitchen.service;



import com.epam.smartkitchen.dto.user.*;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface UserService {


    Response<ErrorResponse, List<UserDto>> getAll(int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, UserDto> getById(String id);

    Response<ErrorResponse, List<UserDto>> getByType(UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse, UserDto> add(UserDto userDto);

    Response<ErrorResponse, UserDto> updateByManager(String id, UpdateUserDtoByManager updateUserDto);

    Response<ErrorResponse, UserDto> update(String id, UpdateUserDto userDto);

    Response<ErrorResponse, ResponseDeleteUserDto> delete(String id);

    Response<ErrorResponse, List<UserDto>> exportExcel(HttpServletResponse httpResponse, UserType userType, int pageNumber, int pageSize, String sortedField, String direction, String deleted);

    Response<ErrorResponse,UserDto> changePassword(String id, ChangePasswordUserDto userDto);

    Response<ErrorResponse, String> expiredLink(String id);
}
