package com.example.excelProj.Dto;

import com.example.excelProj.Model.User;

import java.util.Date;

public class NotificationDto {

    User notificationFrom;

    User notificationTo;

    String type;
    Boolean seen;
    Date date;
    String message;

    public NotificationDto() {
    }

    public User getNotificationFrom() {
        return notificationFrom;
    }

    public void setNotificationFrom(User notificationFrom) {
        this.notificationFrom = notificationFrom;
    }

    public User getNotificationTo() {
        return notificationTo;
    }

    public void setNotificationTo(User notificationTo) {
        this.notificationTo = notificationTo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
