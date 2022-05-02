package com.epam.smartkitchen.requestObject;


import java.util.Objects;

public class RequestParamObject {
    int pageNumber;
    int pageSize;
    String direction;
    String field;
    String deleted;

    public RequestParamObject(int pageNumber, int pageSize, String direction, String field, String deleted) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.direction = direction;
        this.field = field;
        this.deleted = deleted;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestParamObject that = (RequestParamObject) o;
        return pageNumber == that.pageNumber && pageSize == that.pageSize && Objects.equals(direction, that.direction) && Objects.equals(field, that.field) && Objects.equals(deleted, that.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, direction, field, deleted);
    }
}
