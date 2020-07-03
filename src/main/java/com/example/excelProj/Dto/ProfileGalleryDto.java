package com.example.excelProj.Dto;

import com.example.excelProj.Model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class ProfileGalleryDto {

    Long userId;
    MultipartFile galleryImage;
    Date date;

    public ProfileGalleryDto() {
    }

    public ProfileGalleryDto(Long userId, MultipartFile galleryImage) {
        this.userId = userId;
        this.galleryImage = galleryImage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MultipartFile getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(MultipartFile galleryImage) {
        this.galleryImage = galleryImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
