package com.example.excelProj.Controller;


import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import com.example.excelProj.Service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.repository.FieldRepository;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FriendController {




    @Autowired
    FriendService friendService;

    @GetMapping("/send-request")
    public ResponseEntity<String> sendRequest(@RequestParam  Long userId, @RequestParam Long friendId) {
     return friendService.sendRequest(userId,friendId);
    }


    @GetMapping("/accept-request")
    public ResponseEntity<String> acceptRequest(@RequestParam  Long userId, @RequestParam Long friendId) {
        return friendService.acceptRequest(userId,friendId);
    }

    @GetMapping("/get-all-requests/{id}")
    public List<Friend> getAllRequests(@PathVariable("id")  Long userId) {
        return friendService.getAllRequests(userId);
    }

    @GetMapping("/remove-friend")
    public ResponseEntity<String> removeFriend(@RequestParam  Long userId, @RequestParam Long friendId) {
        return friendService.removeFriend(userId,friendId);
    }

    @GetMapping("/cancel-request")
    public ResponseEntity<String> cancelRequest(@RequestParam  Long userId, @RequestParam Long friendId) {
        return friendService.removeFriend(userId,friendId);
    }

}
