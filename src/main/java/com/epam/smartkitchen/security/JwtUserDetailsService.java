package com.epam.smartkitchen.security;

import com.epam.smartkitchen.models.User;
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

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService (UserService userService){
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("AAA  " + email + " email not found");
        }
        return JwtUserFactory.create(user);
    }
}
