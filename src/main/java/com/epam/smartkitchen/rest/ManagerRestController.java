package com.epam.smartkitchen.rest;

import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/manager")
public class ManagerRestController {

    private final UserService userService;

    public ManagerRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") String id) {

        User user = userService.findById(id);
        if (user == null) {
             ResponseEntity.notFound().build();
            throw new RuntimeException("Id " + id + " is not found");
        }
        return ResponseEntity.ok(user);
    }
}
