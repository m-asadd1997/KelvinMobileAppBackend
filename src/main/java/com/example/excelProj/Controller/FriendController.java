package com.example.excelProj.Controller;


import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.FriendsIdDto;
import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import com.example.excelProj.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import sun.reflect.generics.repository.FieldRepository;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FriendController {




    @Autowired
    FriendService friendService;

    @PostMapping("/send-request")
    public ApiResponse sendRequest(@RequestBody FriendsIdDto friendsIdDto) {
     return friendService.sendRequest(friendsIdDto);
    }


    @PostMapping("/accept-request")
    public ApiResponse acceptRequest(@RequestBody FriendsIdDto friendsIdDto) {
        return friendService.acceptRequest(friendsIdDto);
    }

    @GetMapping("/get-all-requests/{id}")
    public ApiResponse getAllRequests(@PathVariable("id")  Long userId) {
        return friendService.getAllRequests(userId);
    }

    @GetMapping("/remove-friend")
    public ApiResponse removeFriend(@RequestBody FriendsIdDto friendsIdDto) {
        return friendService.removeFriend(friendsIdDto);
    }

    @PostMapping("/cancel-request")
    public ApiResponse cancelRequest(@RequestBody FriendsIdDto friendsIdDto) {
        return friendService.removeFriend(friendsIdDto);
    }

    @GetMapping("/get-all-friends/{id}")
    public ApiResponse getAllFriends(@PathVariable("id")  Long userId) {
        return friendService.getAllFriends(userId);
    }

    @GetMapping("/get-all-friends-status/{id}")
    public ApiResponse getAllFriendsAndStatus(@PathVariable("id")  Long userId) {
        return friendService.getAllFriendsAndStatus(userId);
    }

    @GetMapping("/get-notification-count/{id}")
    public ApiResponse<Long> getNotificationCount(@PathVariable("id") Long userId)
    {return friendService.getNotificationCount(userId);}
}
