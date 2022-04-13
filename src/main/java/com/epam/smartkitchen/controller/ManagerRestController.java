package com.epam.smartkitchen.controller;

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
    public ResponseEntity<List<UserDto>> getUsersWithSort(@RequestParam int pageSize, @RequestParam int offset,
                                                          @RequestParam(required = false) String field,
                                                          @RequestParam(required = false) String direction) {
        List<UserDto> allUser = userService.getAllUser(createPageable(offset, pageSize, field, direction));
        if (allUser == null) {
            return ResponseEntity.notFound().eTag("Users are not found").build();
        }
        return ResponseEntity.ok(allUser);
    }

    @GetMapping("/users/{userType}")
    public ResponseEntity<List<UserDto>> getUserByType(@PathVariable UserType userType, @RequestParam int offset, @RequestParam int pageSize,
                                                       @RequestParam(required = false) String direction,
                                                       @RequestParam(required = false) String field) {
        List<UserDto> usersByType = userService.getUsersByType(userType, createPageable(offset, pageSize, field, direction));
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

    @PostMapping("/add/user")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.addUser(userDto);
        if (savedUserDto == null) {
            return ResponseEntity.notFound().eTag(userDto.getEmail() + " Email already exists").build();
        }
        return ResponseEntity.ok(savedUserDto);
    }

    @PostMapping("/update/user")
    public ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto updateUserDto){
        UserDto userDto = userService.updateUser(updateUserDto);
        if (userDto == null){
            return ResponseEntity.notFound().eTag(updateUserDto.getEmail() + " Email is not exist").build();
        }
        return ResponseEntity.ok(userDto);
    }


    private PageRequest createPageable(int offset, int pageSize, String field, String direction) {
        if (field == null) {
            return PageRequest.of(offset, pageSize);
        } else if (direction == null) {
            return PageRequest.of(offset, pageSize).withSort(Sort.by(field).ascending());
        } else {
            return PageRequest.of(offset, pageSize).withSort(Sort.by(field).descending());
        }
    }
}
