package com.example.excelProj.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "VARCHAR(255)")
    private String token;
    private String email;
    private Date date;


    public ForgotPassword() {
    }

    public ForgotPassword(Long id, String token, String email, Date date) {
        this.id = id;
        this.token = token;
        this.email = email;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
