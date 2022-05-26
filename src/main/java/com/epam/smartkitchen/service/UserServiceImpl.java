package com.epam.smartkitchen.service;

import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repo.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(User user) {
        userRepository.save(user);
        User byEmail = findByEmail(user.getEmail());
        return byEmail;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException( email + " email not found");
        }
        return user;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void updateRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
        User userByDB = userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("User not found"));
        if (!refreshToken.equals(userByDB.getRefreshToken())){
            throw new RuntimeException(refreshToken + "refreshToken is not correct");
        }
    }


}
