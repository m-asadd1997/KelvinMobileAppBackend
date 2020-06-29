package com.example.excelProj.Model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ProfileGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String galleryImage;
    Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public ProfileGallery() {
    }

    public ProfileGallery(Long id, User user, String galleryImage) {
        this.id = id;
        this.user = user;
        this.galleryImage = galleryImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGalleryImage() {
        return galleryImage;
    }

    public void setGalleryImage(String galleryImage) {
        this.galleryImage = galleryImage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
