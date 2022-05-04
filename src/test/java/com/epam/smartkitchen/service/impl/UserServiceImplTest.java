package com.epam.smartkitchen.service.impl;

import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
import com.epam.smartkitchen.dto.user.UserDto;
import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.exceptions.ErrorResponse;
import com.epam.smartkitchen.exceptions.RecordNotFoundException;
import com.epam.smartkitchen.exceptions.RequestParamInvalidException;
import com.epam.smartkitchen.exceptions.ResourceExistException;
import com.epam.smartkitchen.models.User;
import com.epam.smartkitchen.repository.UserRepository;
import com.epam.smartkitchen.response.Response;
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

        Response<ErrorResponse, List<UserDto>> allUser = userService.getAllUser(0, 10, null, null, null);
        Page<UserDto> usersToPage = new PageImpl<>(allUser.getSuccessObject());

        assertEquals(usersToPage, getUserDto(usersPageable()));
    }

    @Test
    void GetAllUserRequestParamInvalidExceptionCase() {
        when(userRepository.findAll(pageRequest)).thenThrow(new RequestParamInvalidException("Parameter deleted is not correct: null"));

        assertThrows(RequestParamInvalidException.class, () -> userService.getAllUser(0, 10, null, null, "null"));
    }

    @Test
    void GetAllUserRecordNotFoundException(){
        when(userRepository.findAllByDeletedFalse(pageRequest)).thenThrow(new RecordNotFoundException("Users are not found"));

        assertThrows(RecordNotFoundException.class, () -> userService.getAllUser(0,10, null,null, null));
    }

    @Test
    void getUsersByType() {
        when(userRepository.findByUserType(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
        when(userRepository.findByUserTypeAndDeletedFalse(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
        when(userRepository.findByUserTypeAndDeletedTrue(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());

        Response<ErrorResponse, List<UserDto>> usersByType = userService.getUsersByType(UserType.MANAGER, 0, 10, null, null, null);
        Page<UserDto> userDtoPage = new PageImpl<>(usersByType.getSuccessObject());

        assertEquals(userDtoPage, getUserDto(usersPageable()));
    }

    @Test
    void getUsersByTypeRequestParamInvalidExceptionCase() {
        when(userRepository.findByUserTypeAndDeletedTrue(UserType.WAITER, pageRequest)).thenThrow(new RequestParamInvalidException("Parameter deleted is not correct: null"));

        assertThrows(RequestParamInvalidException.class, () -> userService.getUsersByType(UserType.WAITER,0, 10, null, null, "only"));
    }

    @Test
    void getUsersByTypeRecordNotFoundException(){
        when(userRepository.findByUserType(UserType.COOK, pageRequest)).thenThrow(new RecordNotFoundException("Users are not found by type "));

        assertThrows(RecordNotFoundException.class, () -> userService.getUsersByType(UserType.COOK,0,10, null,null, "all"));
    }

    @Test
    void findById() {
        when(userRepository.findById(id)).thenReturn(optionalUser);

        Response<ErrorResponse, UserDto> byId = userService.findById(id);

        assertEquals(byId.getSuccessObject(), userDto);
    }

    @Test
    void findByIdRecordNotFoundExceptionCase(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.findById(id));
    }


    @Test
    void addUser() {
        when(userRepository.existsById(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        Response<ErrorResponse, UserDto> userDtoResponse = userService.addUser(this.userDto);

        assertEquals(userDtoResponse.getSuccessObject(),this.userDto);

    }

    @Test
    void addUserResourceExistExceptionCase(){
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ResourceExistException.class, () -> userService.addUser(userDto));
    }

    @Test
    void updateUser() {
        when(userRepository.findById(any())).thenReturn(toOptionalUser());
        when(userRepository.save(user)).thenReturn(user);

        Response<ErrorResponse, UserDto> userDtoResponse = userService.updateUser(id, managerEditUserDto());

        assertEquals(userDtoResponse.getSuccessObject(),this.userDto);
    }

    @Test
    void updateUserNegativeCase(){
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.updateUser(id,managerEditUserDto()));
    }

    @Test
    void deleteUser() {
        when(userRepository.findById(id)).thenReturn(toOptionalUser());
        when(userRepository.save(any(User.class))).thenReturn(user);

        Response<ErrorResponse, ResponseDeleteUserDto> responseDeleteUserDto = userService.deleteUser(id);

        assertEquals(responseDeleteUserDto.getSuccessObject().isRemoved(),deleteUserDto.isRemoved());
    }

    @Test
    void deleteUserNegativeCase(){
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> userService.deleteUser(id));
    }
}