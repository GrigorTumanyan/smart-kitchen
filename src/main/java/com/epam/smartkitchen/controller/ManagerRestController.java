package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.service.ExcelWriter;
import com.epam.smartkitchen.service.UserService;
import com.epam.smartkitchen.requestObject.RequestParamObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<List<UserDto>> getUsersWithSort(RequestParamObject requestParamCustom) {
        List<UserDto> allUser = userService.getAllUser(requestParamCustom);
        if (allUser == null) {
            return ResponseEntity.notFound().eTag("Users are not found").build();
        }
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/users/{userType}")
    public ResponseEntity<List<UserDto>> getUserByType(@PathVariable UserType userType, RequestParamObject requestParamCustom) {
        List<UserDto> usersByType = userService.getUsersByType(userType, requestParamCustom);
        if (usersByType == null) {
            return ResponseEntity.notFound().eTag("You don't have " + userType + "user").build();
        }
        return ResponseEntity.ok(usersByType);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") String id) {

        UserDto user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().eTag("Id " + id + " is not found").build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/download")
    public ResponseEntity<?> exportSheet(HttpServletResponse response, RequestParamObject requestParamObject,
                                         @RequestParam(required = false) UserType userType){
        List<UserDto> userDto = userService.exportExcel(userType, requestParamObject);
        excelWriter.write(userDto, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.addUser(userDto);
        if (savedUserDto == null) {
            return ResponseEntity.notFound().eTag(userDto.getEmail() + " Email already exists").build();
        }
        return ResponseEntity.ok(savedUserDto);
    }

    @PatchMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") String id ,@RequestBody UpdateUserDto updateUserDto){
        UserDto userDto = userService.updateUser(id,updateUserDto);
        if (userDto == null){
            return ResponseEntity.notFound().eTag(id + " id is not exist").build();
        }
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseDeleteUserDto> deleteUser(@PathVariable(name = "id") String id){
        ResponseDeleteUserDto responseDeleteUserDto = userService.deleteUser(id);
        if (responseDeleteUserDto == null){
            return ResponseEntity.notFound().eTag(id + " id is not exist").build();
        }
        return ResponseEntity.ok(responseDeleteUserDto);
    }
}
