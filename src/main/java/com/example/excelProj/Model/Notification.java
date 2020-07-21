package com.example.excelProj.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_Id")
    User notificationFrom;

    @ManyToOne
    @JoinColumn(name = "to_user_Id")
    User notificationTo;

    String type;
    Boolean seen;
    Date date;
    String message;

    public Notification() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
