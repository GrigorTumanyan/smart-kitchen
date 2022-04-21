package com.epam.smartkitchen.dto.manager;

public class ResponseDeleteUserDto {
    boolean removed;

    public ResponseDeleteUserDto( boolean removed) {

        this.removed = removed;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }


}
