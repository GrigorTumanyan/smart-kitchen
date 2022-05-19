package com.epam.smartkitchen.dto.user;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;

public class UpdateUserDtoByManager {
    private UserType userType;
    private Boolean active;


    public UpdateUserDtoByManager(UserType userType, Boolean removed, Boolean active) {
        this.userType = userType;
        this.active = active;
    }

    public static User toUser(UpdateUserDtoByManager managerEditUserDto){
        User user = new User();
        user.setUserType(managerEditUserDto.getUserType());
        user.setActive(managerEditUserDto.getActive());
        return user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}
