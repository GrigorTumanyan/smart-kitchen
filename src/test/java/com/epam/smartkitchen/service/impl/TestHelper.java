package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.GeoPage;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestHelper {
    protected static Page<User> usersPageable() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName(i + "300");
            user.setSurname(i + "300");
            user.setUserType(UserType.MANAGER);
            user.setEmail(i + "300");
            user.setActive(true);
            user.setPassword(i + "300");
            userList.add(user);
        }
        return new PageImpl<>(userList);

    }

    protected static Page<UserDto> getUserDto(Page<User> users) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user1 : users) {
            userDtoList.add(new UserDto(user1));
        }
        return new PageImpl<>(userDtoList);
    }


    protected static Optional<UserDto> toOptionalUserDto() {
        User user = new User();
        user.setName("9");
        user.setSurname("9");
        user.setUserType(UserType.MANAGER);
        user.setEmail("9");
        user.setActive(true);
        user.setPassword("9");
        UserDto userDto = new UserDto(user);
        return Optional.of(userDto);
    }

    protected static Optional<User> toOptionalUser() {
        User user = new User();
        user.setName("8");
        user.setSurname("8");
        user.setUserType(UserType.MANAGER);
        user.setEmail("8");
        user.setActive(true);
        user.setPassword("8");
        return Optional.of(user);
    }

    protected static User toUser() {
        User user = toOptionalUser().get();
        return user;
    }

    protected static UserDto toUserDtoFromOptionalUser(){
        return new UserDto(toUser());
    }
}
