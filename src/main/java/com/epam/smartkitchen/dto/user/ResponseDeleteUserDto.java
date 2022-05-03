package com.epam.smartkitchen.dto.user;

public class ResponseDeleteUserDto {
    boolean deleted;

    public ResponseDeleteUserDto( boolean deleted) {

        this.deleted = deleted;
    }

    public boolean isRemoved() {
        return deleted;
    }

    public void setRemoved(boolean deleted) {
        this.deleted = deleted;
    }


}
