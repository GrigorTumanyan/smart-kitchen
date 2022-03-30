package com.epam.smartkitchen.service;

import com.epam.smartkitchen.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAllUser();

    User findByEmail(String email);

    User findById(String id);

    void updateRefreshToken(User user, String refreshToken);

}
