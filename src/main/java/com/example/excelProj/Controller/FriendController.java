package com.example.excelProj.Controller;


import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.repository.FieldRepository;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FriendController {


    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    FriendRepository friendRepository;

    @GetMapping("/addfriend")
    public String addFriend(@RequestParam  Long userId,@RequestParam Long friendId) {

        User user=userDaoRepository.findById(userId).get();
        User friendUser=userDaoRepository.findById(friendId).get();


        Friend friend=new Friend(user,friendUser);
        friendRepository.save(friend);
        return "friend added";

    }


    @GetMapping("/getfriends/{id}")
    public User getFriend(@PathVariable("id") Long id){

        return userDaoRepository.findById(id).get();
//        return friendRepository.getByUser(1l);

    }

}
