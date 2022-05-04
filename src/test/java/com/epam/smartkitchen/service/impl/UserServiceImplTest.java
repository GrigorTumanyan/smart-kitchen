package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.manager.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.manager.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.epam.smartkitchen.service.impl.TestHelperForUser.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @InjectMocks
    private final UserService userService = Mockito.spy(new UserServiceImpl(userRepository));

    private String id;
    private PageRequest pageRequest;
    private Optional<User> optionalUser;
    private UserDto userDto;
    private User user;
    private ResponseDeleteUserDto deleteUserDto;


    @BeforeEach
    void setUp() {
        pageRequest = PageRequest.of(0, 10);
        id = "123";
        optionalUser = toOptionalUser();
        userDto = toUserDtoFromOptionalUser();
        user = toUser();
        deleteUserDto = deleteUserDto();
    }

    @Test
    void getAllUser() {
        when(userRepository.findAll(pageRequest)).thenReturn((usersPageable()));
        when(userRepository.findAllByDeletedFalse(pageRequest)).thenReturn((usersPageable()));
        when(userRepository.findAllByDeletedTrue(pageRequest)).thenReturn((usersPageable()));

        List<UserDto> allUser = userService.getAllUser(0,10, null, null, null);
        Page<UserDto> usersToPage = new PageImpl<>(allUser);

        assertEquals(usersToPage, getUserDto(usersPageable()));
    }

    @Test
    void GetAllUserNegativeCase() {
        when(userRepository.findAll(pageRequest)).thenReturn(null);
        when(userRepository.findAllByDeletedTrue(pageRequest)).thenReturn(null);
        when(userRepository.findAllByDeletedFalse(pageRequest)).thenReturn(null);

        List<UserDto> allUser = userService.getAllUser(0,10, null, null, null);

        assertNull(allUser);
    }

    @Test
    void getUsersByType() {
        when(userRepository.findByUserType(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
        when(userRepository.findByUserTypeAndDeletedFalse(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
        when(userRepository.findByUserTypeAndDeletedTrue(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());

        List<UserDto> allUser = userService.getUsersByType(UserType.MANAGER, 0,10, null, null, null);
        Page<UserDto> userDtoPage = new PageImpl<>(allUser);

        assertEquals(userDtoPage, getUserDto(usersPageable()));
    }

    @Test
    void getUsersByTypeNegativeCase() {
        when(userRepository.findByUserType(UserType.MANAGER, pageRequest)).thenReturn(null);
        when(userRepository.findByUserTypeAndDeletedTrue(UserType.MANAGER, pageRequest)).thenReturn(null);
        when(userRepository.findByUserTypeAndDeletedFalse(UserType.MANAGER, pageRequest)).thenReturn(null);

        List<UserDto> allUser = userService.getUsersByType(UserType.MANAGER,0,10, null, null, null);

        assertNull(allUser);
    }

    @Test
    void findById() {
        when(userRepository.findById(id)).thenReturn(optionalUser);

        UserDto userById = userService.findById(id);

        assertEquals(userById, userDto);
    }

    @Test
    void findByIdNegativeCase(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserDto userById = userService.findById(id);

        assertNull(userById);
    }


    @Test
    void addUser() {
        when(userRepository.existsById(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        UserDto userDto = userService.addUser(this.userDto);

        assertEquals(userDto,this.userDto);

    }

    @Test
    void addUserNegativeCase(){
        when(userRepository.existsByEmail(any())).thenReturn(true);

        UserDto userDto = userService.addUser(this.userDto);

        assertNull(userDto);
    }

    @Test
    void updateUser() {
        when(userRepository.findById(any())).thenReturn(toOptionalUser());
        when(userRepository.save(user)).thenReturn(user);

        UserDto userDto = userService.updateUser(id,managerEditUserDto());

        assertEquals(userDto,this.userDto);


    }

    @Test
    void updateUserNegativeCase(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        UserDto userDto = userService.updateUser(id,managerEditUserDto());

        assertNull(userDto);
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(id)).thenReturn(toOptionalUser());
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseDeleteUserDto responseDeleteUserDto = userService.deleteUser(id);

        assertEquals(responseDeleteUserDto.isRemoved(),deleteUserDto.isRemoved());
    }

    @Test
    void deleteUserNegativeCase(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseDeleteUserDto responseDeleteUserDto = userService.deleteUser(id);

        assertNull(responseDeleteUserDto);
    }
}