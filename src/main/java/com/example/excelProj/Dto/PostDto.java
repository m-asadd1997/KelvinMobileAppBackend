package com.example.excelProj.Dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Date;

public class PostDto implements Serializable {


    Long id;

    String description;

    Long userId;

    byte[] userProfilePicture;


    String username;


    byte[] image;

    String type;

    String url;

    Date date;

    public PostDto(Long id, String description, Long userId, byte[] userProfilePicture, String username, byte[] image, String type, String url, Date date) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.userProfilePicture = userProfilePicture;
        this.username = username;
        this.image = image;
        this.type = type;
        this.url = url;
        this.date = date;
    }

    public PostDto() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(byte[] userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
