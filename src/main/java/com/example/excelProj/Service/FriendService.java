package com.example.excelProj.Service;

import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    public ResponseEntity<String> sendRequest(Long userId, Long friendId) {

        Optional<User> user = userDaoRepository.findById(userId);
        Optional<User> friend = userDaoRepository.findById(friendId);

        if (user.isPresent() && friend.isPresent()) {
            friendRepository.save(new Friend(user.get(), friend.get(), "pending"));
            return new ResponseEntity<>("Friend request sent", HttpStatus.OK);
        }
        return new ResponseEntity<>("An Error occured while sending th request", HttpStatus.NOT_FOUND);

    }


    public ResponseEntity<String> acceptRequest(Long userId, Long friendId) {

        Optional<User> user = userDaoRepository.findById(userId);
        Optional<User> friend = userDaoRepository.findById(friendId);

        if (user.isPresent() && friend.isPresent()) {

            Friend friend1 = friendRepository.findByUserAndFriend(userId, friendId);
            friend1.setStatus("accepted");

            if (friend1 != null) {
                try {
                    friendRepository.saveAll(
                            Arrays.asList(new Friend(user.get(), friend.get(), "accepted"),
                                    friend1));
                    return new ResponseEntity<>("Friend Request accepted", HttpStatus.OK);

                } catch (Exception e) {
                    return new ResponseEntity<>("An error occured", HttpStatus.NOT_FOUND);
                }
            }
            return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
    }

    public List<Friend> getAllRequests(Long userId){

        return friendRepository.findAllUserRequests(userId);
    }


    public ResponseEntity<String> removeFriend(Long userId,Long friendId){

        friendRepository.removeFriend(userId,friendId);
        return new ResponseEntity<>("Removed", HttpStatus.OK);
    }



}
