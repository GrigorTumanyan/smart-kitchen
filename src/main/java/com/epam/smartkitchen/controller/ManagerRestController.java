package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.user.UpdateUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.response.Response;
import com.epam.smartkitchen.service.ExcelWriter;
import com.epam.smartkitchen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/manager")
public class ManagerRestController {

    private final UserService userService;

    private final ExcelWriter excelWriter;

    public ManagerRestController(UserService userService, ExcelWriter excelWriter) {
        this.userService = userService;
        this.excelWriter = excelWriter;
    }

    @GetMapping("/users")
    public ResponseEntity<Response<ErrorResponse, List<UserDto>>> getUsersWithSort(@RequestParam(required = false) int pageSize,
                                                                                   @RequestParam int pageNumber,
                                                                                   @RequestParam(required = false) String deleted,
                                                                                   @RequestParam(required = false) String sortedField,
                                                                                   @RequestParam(required = false) String direction) {
        Response<ErrorResponse, List<UserDto>> response = userService.getAllUser(pageNumber, pageSize, sortedField, direction, deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userType}")
    public ResponseEntity<Response<ErrorResponse, List<UserDto>>> getUserByType(@PathVariable UserType userType,
                                                                                @RequestParam(required = false) int pageSize,
                                                                                @RequestParam int pageNumber,
                                                                                @RequestParam(required = false) String deleted,
                                                                                @RequestParam(required = false) String sortedField,
                                                                                @RequestParam(required = false) String direction) {
        Response<ErrorResponse, List<UserDto>> usersByType = userService.getUsersByType(userType, pageNumber, pageSize, sortedField, direction, deleted);
        return ResponseEntity.ok(usersByType);
    }

    @PostMapping("/download")
    public ResponseEntity<Response<ErrorResponse, ?>> exportSheet(HttpServletResponse httpResponse,
                                                                  @RequestParam(required = false) int pageSize,
                                                                  @RequestParam int pageNumber,
                                                                  @RequestParam(required = false) String deleted,
                                                                  @RequestParam(required = false) String sortedField,
                                                                  @RequestParam(required = false) String direction,
                                                                  @RequestParam(required = false) UserType userType) {
        Response<ErrorResponse, List<UserDto>> response = userService.exportExcel(userType, pageNumber, pageSize, sortedField, direction, deleted);

        excelWriter.write(response.getSuccessObject(), httpResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> getUserById(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, UserDto> userDto = userService.findById(id);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/user")
    public ResponseEntity<Response<ErrorResponse, UserDto>> addUser(@RequestBody UserDto userDto) {
        Response<ErrorResponse, UserDto> userDtoResponse = userService.addUser(userDto);
        return ResponseEntity.ok(userDtoResponse);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, UserDto>> updateUser(@PathVariable(name = "id") String id,
                                                                       @RequestBody UpdateUserDto updateUserDto) {
        Response<ErrorResponse, UserDto> userDtoResponse = userService.updateUser(id, updateUserDto);
        return ResponseEntity.ok(userDtoResponse);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Response<ErrorResponse, ResponseDeleteUserDto>> deleteUser(@PathVariable(name = "id") String id) {
        Response<ErrorResponse, ResponseDeleteUserDto> responseDeleteUserDto = userService.deleteUser(id);
        return ResponseEntity.ok(responseDeleteUserDto);
    }

}
