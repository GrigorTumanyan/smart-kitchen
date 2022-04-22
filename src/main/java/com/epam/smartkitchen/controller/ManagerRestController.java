package com.epam.smartkitchen.controller;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UpdateUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/manager")
public class ManagerRestController {

    private final UserService userService;

    public ManagerRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsersWithSort(@RequestParam int pageSize, @RequestParam int pageNumber,
                                                          @RequestParam(required = false) String deleted,
                                                          @RequestParam(required = false) String field,
                                                          @RequestParam(required = false) String direction) {
        List<UserDto> allUser = userService.getAllUser(createPageable(pageNumber, pageSize, field, direction), deleted);
        if (allUser == null) {
            return ResponseEntity.notFound().eTag("Users are not found").build();
        }
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/users/{userType}")
    public ResponseEntity<List<UserDto>> getUserByType(@PathVariable UserType userType, @RequestParam int pageNumber,
                                                       @RequestParam(required = false) String deleted,
                                                       @RequestParam int pageSize,
                                                       @RequestParam(required = false) String direction,
                                                       @RequestParam(required = false) String field) {
        List<UserDto> usersByType = userService.getUsersByType(userType, createPageable(pageNumber, pageSize, field, direction), deleted);
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


    private PageRequest createPageable(int pageNumber, int pageSize, String field, String direction) {
        if (field == null) {
            return PageRequest.of(pageNumber, pageSize);
        } else if (direction == null) {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).ascending());
        } else {
            return PageRequest.of(pageNumber, pageSize).withSort(Sort.by(field).descending());
        }
    }
}
