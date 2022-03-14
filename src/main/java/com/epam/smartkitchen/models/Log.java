package com.epam.smartkitchen.models;

import com.epam.smartkitchen.enums.LogEvent;
import com.epam.smartkitchen.enums.LogType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Log extends BaseEntity {
    @Enumerated(EnumType.STRING)
    LogType logType;
    @Column
    String info;
    @Enumerated(EnumType.STRING)
    LogEvent event;

    public Log() {
    }

    public Log(LogType logType, String info, LogEvent event) {
        this.logType = logType;
        this.info = info;
        this.event = event;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LogEvent getEvent() {
        return event;
    }

    public void setEvent(LogEvent event) {
        this.event = event;
    }
}
