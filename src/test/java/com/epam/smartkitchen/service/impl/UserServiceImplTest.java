//package com.epam.smartkitchen.service.impl;
//
//import com.epam.smartkitchen.dto.user.ChangePasswordUserDto;
//import com.epam.smartkitchen.dto.user.ResponseDeleteUserDto;
//import com.epam.smartkitchen.dto.user.UserDto;
//import com.epam.smartkitchen.enums.UserType;
//import com.epam.smartkitchen.exceptions.ConflictException;
//import com.epam.smartkitchen.exceptions.ErrorResponse;
//import com.epam.smartkitchen.exceptions.RecordNotFoundException;
//import com.epam.smartkitchen.exceptions.ParamInvalidException;
//import com.epam.smartkitchen.models.User;
//import com.epam.smartkitchen.repository.UserRepository;
//import com.epam.smartkitchen.response.Response;
//import com.epam.smartkitchen.service.ExcelWriter;
//import com.epam.smartkitchen.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.epam.smartkitchen.service.impl.TestHelperForUser.*;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//public class UserServiceImplTest {
//
//    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
//
//    private final ExcelWriter excelWriter = Mockito.mock(ExcelWriter.class);
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
//
//    @InjectMocks
//    private final UserService userService = Mockito.spy(new UserServiceImpl(userRepository, excelWriter, bCryptPasswordEncoder));
//
//    private String id;
//    private PageRequest pageRequest;
//    private Optional<User> optionalUser;
//    private UserDto userDto;
//    private User user;
//    private ResponseDeleteUserDto deleteUserDto;
//
//
//    @BeforeEach
//    void setUp() {
//        pageRequest = PageRequest.of(0, 10);
//        id = "123";
//        optionalUser = toOptionalUser();
//        userDto = toUserDtoFromOptionalUser();
//        user = toUser();
//        deleteUserDto = deleteUserDto();
//    }
//
//    @Test
//    void getAll() {
//        when(userRepository.findAll(pageRequest)).thenReturn((usersPageable()));
//        when(userRepository.findAllByDeletedFalse(pageRequest)).thenReturn((usersPageable()));
//        when(userRepository.findAllByDeletedTrue(pageRequest)).thenReturn((usersPageable()));
//
//        Response<ErrorResponse, List<UserDto>> allUser = userService.getAll(0, 10, null, null, null);
//        Page<UserDto> usersToPage = new PageImpl<>(allUser.getSuccessObject());
//
//        assertEquals(usersToPage, getUserDto(usersPageable()));
//    }
//
//    @Test
//    void GetAllRequestParamInvalidExceptionCase() {
//        when(userRepository.findAll(pageRequest)).thenThrow(new ParamInvalidException("Parameter deleted is not correct: null"));
//
//        assertThrows(ParamInvalidException.class, () -> userService.getAll(0, 10, null, null, "null"));
//    }
//
//    @Test
//    void GetAllRecordNotFoundException(){
//        when(userRepository.findAllByDeletedFalse(pageRequest)).thenThrow(new RecordNotFoundException("Users are not found"));
//
//        assertThrows(RecordNotFoundException.class, () -> userService.getAll(0,10, null,null, null));
//    }
//
//    @Test
//    void getByType() {
//        when(userRepository.findByUserType(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
//        when(userRepository.findByUserTypeAndDeletedFalse(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
//        when(userRepository.findByUserTypeAndDeletedTrue(UserType.MANAGER, pageRequest)).thenReturn(usersPageable());
//
//        Response<ErrorResponse, List<UserDto>> usersByType = userService.getByType(UserType.MANAGER, 0, 10, null, null, null);
//        Page<UserDto> userDtoPage = new PageImpl<>(usersByType.getSuccessObject());
//
//        assertEquals(userDtoPage, getUserDto(usersPageable()));
//    }
//
//    @Test
//    void getByTypeRequestParamInvalidExceptionCase() {
//        when(userRepository.findByUserTypeAndDeletedTrue(UserType.WAITER, pageRequest)).thenThrow(new ParamInvalidException("Parameter deleted is not correct: null"));
//
//        assertThrows(ParamInvalidException.class, () -> userService.getByType(UserType.WAITER,0, 10, null, null, "only"));
//    }
//
//    @Test
//    void getByTypeRecordNotFoundException(){
//        when(userRepository.findByUserType(UserType.COOK, pageRequest)).thenThrow(new RecordNotFoundException("Users are not found by type "));
//
//        assertThrows(RecordNotFoundException.class, () -> userService.getByType(UserType.COOK,0,10, null,null, "all"));
//    }
//
//    @Test
//    void getById() {
//        when(userRepository.findById(id)).thenReturn(optionalUser);
//
//        Response<ErrorResponse, UserDto> byId = userService.getById(id);
//
//        assertEquals(byId.getSuccessObject(), userDto);
//    }
//
//    @Test
//    void getByIdRecordNotFoundExceptionCase(){
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> userService.getById(id));
//    }
//
//
//    @Test
//    void add() {
//        when(userRepository.existsById(any())).thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);
//
//        Response<ErrorResponse, UserDto> userDtoResponse = userService.add(this.userDto);
//
//        assertEquals(userDtoResponse.getSuccessObject(),this.userDto);
//
//    }
//
//    @Test
//    void addResourceExistExceptionCase(){
//        when(userRepository.existsByEmail(any())).thenReturn(true);
//
//        assertThrows(ConflictException.class, () -> userService.add(userDto));
//    }
//
//    @Test
//    void updateByManager() {
//        when(userRepository.findById(any())).thenReturn(optionalUser);
//        when(userRepository.save(user)).thenReturn(user);
//
//        Response<ErrorResponse, UserDto> userDtoResponse = userService.updateByManager(id, managerEditUserDto());
//
//        assertEquals(userDtoResponse.getSuccessObject(),this.userDto);
//    }
//
//    @Test
//    void updateByManagerNegativeCase(){
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> userService.updateByManager(id,managerEditUserDto()));
//    }
//
//    @Test
//    void update(){
//        when(userRepository.findById(any())).thenReturn(optionalUser);
//        when(userRepository.existsByEmail(any())).thenReturn(false);
//        when(userRepository.save(any())).thenReturn(user);
//
//        Response<ErrorResponse, UserDto> update = userService.update("4", toUpdateUserDto());
//
//        assertEquals(update.getSuccessObject(), userDto);
//    }
//
//    @Test
//    void updateNegativeCaseRecordNotFoundException(){
//        when(userRepository.findById("10")).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> userService.update("10", toUpdateUserDto()));
//    }
//
//    @Test
//    void updateNegativeCaseConflictException(){
//        when(userRepository.findById(any())).thenReturn(optionalUser);
//        when(userRepository.existsByEmail(any())).thenReturn(true);
//
//        assertThrows(ConflictException.class, () -> userService.update("15", toUpdateUserDtoForNegativeCase()));
//    }
//
//    @Test
//    void delete() {
//        when(userRepository.findById(id)).thenReturn(optionalUser);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        Response<ErrorResponse, ResponseDeleteUserDto> responseDeleteUserDto = userService.delete(id);
//
//        assertEquals(responseDeleteUserDto.getSuccessObject().isRemoved(),deleteUserDto.isRemoved());
//    }
//
//    @Test
//    void deleteNegativeCase(){
//        when(userRepository.findById(id)).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> userService.delete(id));
//    }
//
//    @Test
//    void changePassword() {
//        when(userRepository.findById(any())).thenReturn(optionalUser);
//        when(userRepository.save(any())).thenReturn(user);
//
//        Response<ErrorResponse, UserDto> userDtoResponse = userService.changePassword("8",
//                new ChangePasswordUserDto("8", "88", "88"));
//
//        assertEquals(userDto, userDtoResponse.getSuccessObject());
//    }
//
//    @Test
//    void changePasswordNegativeCaseRecordNotFoundException(){
//        when(userRepository.findById(any())).thenReturn(Optional.empty());
//
//        assertThrows(RecordNotFoundException.class, () -> userService.changePassword("a",
//                new ChangePasswordUserDto("8", "88", "88")));
//    }
//
//    @Test
//    void changePasswordNegativeCaseConflictException(){
//        when(userRepository.findById(any())).thenReturn(optionalUser);
//
//        assertThrows(ConflictException.class, () -> userService.changePassword("f",
//                new ChangePasswordUserDto("8", "8", "88")));
//
//    }
//
//}