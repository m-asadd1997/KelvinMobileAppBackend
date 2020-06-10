package com.example.excelProj.Dto;

public class FriendsIdDto {
    Long userId;
    Long friendId;

    public FriendsIdDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }
}
