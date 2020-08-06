package com.example.excelProj.Dto;

public class NotificationBody {

    private NotificationObject notification;
    private String to;

    public NotificationBody() {
    }

    public NotificationObject getNotification() {
        return notification;
    }

    public void setNotification(NotificationObject notification) {
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
