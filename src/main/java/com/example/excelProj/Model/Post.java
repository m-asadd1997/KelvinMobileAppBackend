package com.example.excelProj.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(columnDefinition = "LONGTEXT")
    String description;


    @Lob
    byte[] image;


    String type;

    String url;

    Date date;

    @JsonIgnoreProperties("posts")
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    public Post(String description, byte[] image, String type, String url, Date date, User user) {
        this.description = description;
        this.image = image;
        this.type = type;
        this.url = url;
        this.date = date;
        this.user = user;
    }

    public Post() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
