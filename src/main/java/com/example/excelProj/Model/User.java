package com.example.excelProj.Model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;

    @Column
    private Boolean active;

    @Column
    private String userType;


    private String description;

    @Lob
    private byte[] profilePicture;

    private Integer noOfFriends;


    @OneToMany(mappedBy = "userObj", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Friend> friendList;


    public User(String email, String name, String password, Boolean active, String userType) {

        this.email = email;
        this.name = name;
        this.password = password;
        this.active = active;
        this.userType = userType;
    }




	public List<Friend> getFriendList() {
		return friendList;
	}

	public void setFriendList(List<Friend> friendList) {
		this.friendList = friendList;
	}

	public User() {
    }

    public Boolean getActive() {
        return active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean isActive) {
        this.active = isActive;
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

    public Integer getNoOfFriends() {
        return noOfFriends;
    }

    public void setNoOfFriends(Integer noOfFriends) {
        this.noOfFriends = noOfFriends;
    }
}
