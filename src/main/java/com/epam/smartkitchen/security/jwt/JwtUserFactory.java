package com.epam.smartkitchen.security.jwt;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collections;
import java.util.List;

public final class JwtUserFactory {


    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                user.getActive().equals(true),
                user.getModifiedOn(),
                createGrantedAuthorityList(user.getUserType()));
    }

    private static List<GrantedAuthority> createGrantedAuthorityList(UserType userType) {
        return Collections.singletonList(new SimpleGrantedAuthority(userType.name()));
    }
}
