package com.example.excelProj.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

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

    private Integer numberOfNotifications;
    private Integer numberOfFriendRequests;


    private String description;

    private String firebaseToken;

    @Lob
    private byte[] profilePicture;

    private Integer noOfFriends;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    List<Post> posts;


    @OneToMany(mappedBy = "userObj", cascade = CascadeType.ALL)
	@JsonIgnore
    private List<Friend> friendList;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<ProfileGallery> profileGalleries;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Event> events;

    @OneToMany(mappedBy = "notificationTo")
    @JsonIgnore
    private List<Notification> notifications;

    @OneToMany(mappedBy = "notificationFrom")
    @JsonIgnore
    private List<Notification> notificationFrom;

    public User(String email, String name, String password, Boolean active, String userType) {

        this.email = email;
        this.name = name;
        this.password = password;
        this.active = active;
        this.userType = userType;
    }

    public User(Long id, String email, String name, String password, Boolean active, String userType, String description, byte[] profilePicture) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.active = active;
        this.userType = userType;
        this.description = description;
        this.profilePicture = profilePicture;
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


    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<ProfileGallery> getProfileGalleries() {
        return profileGalleries;
    }

    public void setProfileGalleries(List<ProfileGallery> profileGalleries) {
        this.profileGalleries = profileGalleries;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Notification> getNotificationFrom() {
        return notificationFrom;
    }

    public void setNotificationFrom(List<Notification> notificationFrom) {
        this.notificationFrom = notificationFrom;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public Integer getNumberOfNotifications() {
        return numberOfNotifications;
    }

    public void setNumberOfNotifications(Integer numberOfNotifications) {
        this.numberOfNotifications = numberOfNotifications;
    }

    public Integer getNumberOfFriendRequests() {
        return numberOfFriendRequests;
    }

    public void setNumberOfFriendRequests(Integer numberOfFriendRequests) {
        this.numberOfFriendRequests = numberOfFriendRequests;
    }
}
