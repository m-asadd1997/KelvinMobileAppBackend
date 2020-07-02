package com.example.excelProj.Dto;

import java.io.Serializable;

public class FriendDto implements Serializable {

    private Long id;
    private String email;
    private String name;
    private Boolean active;
    private String userType;
    private String description;
    private byte[] profilePicture;
    private Boolean online;
    private Boolean seen;
    private Long sender;
    private String message;



//    public FriendDto(String email, String name, Boolean active, String userType, String description, byte[] profilePicture, Boolean online) {
////        this.email = email;
////        this.name = name;
////        this.active = active;
////        this.userType = userType;
////        this.description = description;
////        this.profilePicture = profilePicture;
////        this.online = online;
////    }

    public FriendDto(Long id, String email, String name, Boolean active, String userType, String description, byte[] profilePicture, Boolean seen, Long sender, String message) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.active = active;
        this.userType = userType;
        this.description = description;
        this.profilePicture = profilePicture;
        this.seen = seen;
        this.sender = sender;
        this.message = message;
    }

    public FriendDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
