package com.epam.smartkitchen.dto.manager;

import com.epam.smartkitchen.enums.UserType;
import com.epam.smartkitchen.models.User;

public class UpdateUserDto {
    UserType userType;
    Boolean removed;
    Boolean active;


    public UpdateUserDto(UserType userType, Boolean removed, Boolean active) {
        this.userType = userType;
        this.removed = removed;
        this.active = active;
    }

    public static User toUser(UpdateUserDto managerEditUserDto){
        User user = new User();
        user.setUserType(managerEditUserDto.getUserType());
        user.setRemoved(managerEditUserDto.getRemoved());
        user.setActive(managerEditUserDto.getActive());
        return user;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}
