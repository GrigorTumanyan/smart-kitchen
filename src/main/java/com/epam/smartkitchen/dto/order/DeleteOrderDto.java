package com.epam.smartkitchen.dto.order;

import java.util.Objects;

public class DeleteOrderDto {

    boolean isDeleted;

    public DeleteOrderDto(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteOrderDto that = (DeleteOrderDto) o;
        return isDeleted == that.isDeleted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDeleted);
    }

    @Override
    public String toString() {
        return "DeleteOrderDto{" +
                "isDeleted=" + isDeleted +
                '}';
    }
}
