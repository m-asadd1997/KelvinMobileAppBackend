package com.example.excelProj.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;



    @JsonIgnore
    @ManyToOne
    User user1;



    @JsonIgnore
    @ManyToOne
    User user2;

    String chatroomId;


    public Chatroom(User user1, User user2, String chatroomId) {
        this.user1 = user1;
        this.user2 = user2;
        this.chatroomId = chatroomId;
    }

    public Chatroom() {
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }
}