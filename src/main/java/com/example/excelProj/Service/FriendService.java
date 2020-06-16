package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.FriendsIdDto;
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

    public ApiResponse sendRequest(FriendsIdDto friendsIdDto) {

        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());

        if (user.isPresent() && friend.isPresent()) {


            friendRepository.save(new Friend(user.get(), friend.get(), "pending"));
            return new ApiResponse<>(200,"Friend request sent", null);
        }
        return new ApiResponse<>(400,"An Error occured while sending th request", null);

    }


    public ApiResponse acceptRequest(FriendsIdDto friendsIdDto) {

        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());

        if (user.isPresent() && friend.isPresent()) {

            Friend friend1 = friendRepository.findByUserAndFriend(friendsIdDto.getUserId(),friendsIdDto.getFriendId());
            friend1.setStatus("accepted");

            User user1 = user.get();
            User user2 = friend.get();
            setNoOfFriends(user1,user2);
            if (friend1 != null) {
                try {
                    friendRepository.saveAll(
                            Arrays.asList(new Friend(user1, user2, "accepted"),
                                    friend1));
                    return new ApiResponse(200,"Friend Request accepted",null);

                } catch (Exception e) {
                    return new ApiResponse(400,"An error occured",null);
                }
            }
            return new ApiResponse(400,"Something went wrong", null);
        }
        return new ApiResponse(400,"Something went wrong",null);
    }

    public ApiResponse getAllRequests(Long userId){
        List<Friend> friends = friendRepository.findAllUserRequests(userId);
        if(!friends.isEmpty()){
            return new ApiResponse(200,"Friend Reqs found",friends);
        }
        else{
            return new ApiResponse(400,"No Reqs found",null);
        }
    }


    public ApiResponse removeFriend(FriendsIdDto friendsIdDto){
        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());
        if (user.isPresent() && friend.isPresent()) {
            User user1 = user.get();
            User user2 = friend.get();

            if(user1.getNoOfFriends() > 0 && user2.getNoOfFriends() > 0){
                removeNoOfFriends(user1,user2);
            }
        }
        friendRepository.removeFriend(friendsIdDto.getUserId(),friendsIdDto.getFriendId());
        return new ApiResponse(200,"Request Canceled",null);
    }

    public ApiResponse getAllFriends(Long id){
        List<Friend> friends = friendRepository.findAllFriends(id);
        if(!friends.isEmpty()){
            return new ApiResponse(200,"Friends found",friends);
        }
        else{
            return new ApiResponse(400,"No friends found",null);
        }
    }


    public void  setNoOfFriends(User user1,User user2){

        Integer no1 = friendRepository.findAllNoOfFriends(user1.getId()) + 1;
        Integer no2 = friendRepository.findAllNoOfFriends(user2.getId()) + 1;
        user1.setNoOfFriends(no1);
        user2.setNoOfFriends(no2);
        userDaoRepository.save(user1);
        userDaoRepository.save(user2);

    }

    public void  removeNoOfFriends(User user1,User user2){

        Integer no1 = friendRepository.findAllNoOfFriends(user1.getId()) - 1;
        Integer no2 = friendRepository.findAllNoOfFriends(user2.getId()) - 1;
        user1.setNoOfFriends(no1);
        user2.setNoOfFriends(no2);
        userDaoRepository.save(user1);
        userDaoRepository.save(user2);

    }
}
