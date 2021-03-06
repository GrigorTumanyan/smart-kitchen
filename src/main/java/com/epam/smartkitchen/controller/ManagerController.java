package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.user.UpdateUserDtoByManager;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.AuthService;
import com.epam.smartkitchen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/manager")
public class ManagerController {

    private final UserService userService;

    private final AuthService authService;


    public ManagerController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/users")
    public ResponseEntity<Response<ErrorResponse, List<UserDto>>> getByField(@RequestParam(required = false) int pageSize,
                                                                                   @RequestParam int pageNumber,
                                                                                   @RequestParam(required = false) String deleted,
                                                                                   @RequestParam(required = false) String sortedField,
                                                                                   @RequestParam(required = false) String direction) {
        Response<ErrorResponse, List<UserDto>> response = userService.getAll(pageNumber, pageSize, sortedField, direction, deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userType}")
    public ResponseEntity<Response<ErrorResponse, List<UserDto>>> getByTypeAndField(@PathVariable UserType userType,
                                                                                @RequestParam(required = false) int pageSize,
                                                                                @RequestParam int pageNumber,
                                                                                @RequestParam(required = false) String deleted,
                                                                                @RequestParam(required = false) String sortedField,
                                                                                @RequestParam(required = false) String direction) {
        Response<ErrorResponse, List<UserDto>> usersByType = userService.getByType(userType, pageNumber, pageSize, sortedField, direction, deleted);
        return ResponseEntity.ok(usersByType);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<ErrorResponse, UserDto>> register(@RequestBody UserDto userDto) {
        Response<ErrorResponse, UserDto> register = authService.register(userDto);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/download")
    public ResponseEntity<Response<ErrorResponse, List<UserDto>>> exportSheet(HttpServletResponse httpResponse,
                                                                  @RequestParam(required = false) int pageSize,
                                                                  @RequestParam int pageNumber,
                                                                  @RequestParam(required = false) String deleted,
                                                                  @RequestParam(required = false) String sortedField,
                                                                  @RequestParam(required = false) String direction,
                                                                  @RequestParam(required = false) UserType userType) {
        Response<ErrorResponse, List<UserDto>> response = userService.exportExcel(httpResponse,userType, pageNumber,
                pageSize, sortedField, direction, deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> getById(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, UserDto> userDto = userService.getById(id);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> update(@PathVariable(name = "id") String id,
                                                                       @RequestBody UpdateUserDtoByManager updateUserDto) {
        Response<ErrorResponse, UserDto> userDtoResponse = userService.updateByManager(id, updateUserDto);
        return ResponseEntity.ok(userDtoResponse);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, ResponseDeleteUserDto>> delete(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, ResponseDeleteUserDto> responseDeleteUserDto = userService.delete(id);
        return ResponseEntity.ok(responseDeleteUserDto);
    }

}
