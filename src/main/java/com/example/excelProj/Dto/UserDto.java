package com.example.excelProj.Dto;

import javax.persistence.Lob;

public class UserDto {

    private String name;
    private String email;
    private String password;
    private Boolean active ;
    private String userType;
	private String description;
	@Lob
	private byte[] profilePicture;
	private String firebaseToken;
	private Integer numberOfNotifications;
	private Integer numberOfFriendRequests;

	public UserDto(String name, String email, String password, Boolean active, String userType) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.active = active;
		this.userType = userType;
	}

	public UserDto() {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
