package com.epam.smartkitchen.security;

import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.security.jwt.JwtUser;
import com.epam.smartkitchen.security.jwt.JwtUserFactory;
import com.epam.smartkitchen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)  {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new RecordNotFoundException("Email : " + email + " is not found"));
        return JwtUserFactory.create(user);
    }
}
