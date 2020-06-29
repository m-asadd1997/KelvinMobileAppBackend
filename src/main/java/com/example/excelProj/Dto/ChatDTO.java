package com.example.excelProj.Dto;

import java.util.Date;

public class ChatDTO {

    Long id;
    Long userId;
    String name;
    String email;
    byte[] profilePicture;
    String message;
    Date date;


    public ChatDTO() {
    }

    public ChatDTO(Long id, Long userId, String name, String email, byte[] profilePicture, String message, Date date) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.profilePicture = profilePicture;
        this.message = message;
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
