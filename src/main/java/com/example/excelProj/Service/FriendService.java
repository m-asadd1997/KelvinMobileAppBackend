package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Config.WebsocketConfig;
import com.example.excelProj.Dto.*;
import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendService {


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    FriendRepository friendRepository;


    @Autowired
    UserDaoRepository userDaoRepository;


    @Autowired
    NotificationService notificationService;

    @Autowired
    WebsocketConfig websocketConfig;

    @Autowired
    RestTemplate restTemplate;

    public ApiResponse sendRequest(FriendsIdDto friendsIdDto) {

        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());


        if (user.isPresent() && friend.isPresent()) {

            NotificationBody notificationBody = new NotificationBody();
            NotificationObject notificationObject = new NotificationObject();
            friendRepository.save(new Friend(user.get(), friend.get(), "pending"));
//            notificationService.saveNotification(populateNotificationDto(user.get(),friend.get()));
            simpMessagingTemplate.convertAndSend("/topic/notification/" + friend.get().getId(),
                    notificationService.getLiveNotification(friend.get().getId(),"request",user.get().getId()));
            notificationObject.setTitle(friendsIdDto.getNotificationTitle());
            notificationObject.setBody(friendsIdDto.getNotificationBody());
            notificationBody.setNotification(notificationObject);
            notificationBody.setTo(friend.get().getFirebaseToken());
            System.out.println(notificationBody);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization","key=AAAA81_riiM:APA91bF8FtqkcElESD0Uh9bBY2IjGJsD4gHp7X5SIpyE66peD9pya6O3Mq7xlFZdMmlAlb8oFp9XSedYyrR5ImiUqep40g_GYBiXfjvjzcpm8ZpxyPjPK74Y4E0gK2uEnJk17-wMLmCJ");
            HttpEntity httpEntity = new HttpEntity(notificationBody, headers);
//            String resp =restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", httpEntity,String.class);
            saveFriendRequestNumberForUser(friend.get());
            return new ApiResponse<>(200, "Friend request sent", null);
        }
        return new ApiResponse<>(400, "An Error occured while sending th request", null);

    }


    public ApiResponse acceptRequest(FriendsIdDto friendsIdDto) {

        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());

        if (user.isPresent() && friend.isPresent()) {

            Friend friend1 = friendRepository.findByUserAndFriend(friendsIdDto.getUserId(), friendsIdDto.getFriendId());
            friend1.setStatus("accepted");

            User user1 = user.get();
            User user2 = friend.get();
            setNoOfFriends(user1, user2);
            if (friend1 != null) {
                try {
                    friendRepository.saveAll(
                            Arrays.asList(new Friend(user1, user2, "accepted"),
                                    friend1));
//                    notificationService.deletNotification(friendsIdDto.getNotificationId());
                    return new ApiResponse(200, "Friend Request accepted", null);

                } catch (Exception e) {
                    return new ApiResponse(400, "An error occured", null);
                }
            }
            return new ApiResponse(400, "Something went wrong", null);
        }
        return new ApiResponse(400, "Something went wrong", null);
    }

    public ApiResponse getAllRequests(Long userId) {
        List<Friend> friends = friendRepository.findAllUserRequests(userId);

        return new ApiResponse(200, "Friend Reqs found", friends);


    }


    public ApiResponse removeFriend(FriendsIdDto friendsIdDto) {
        Optional<User> user = userDaoRepository.findById(friendsIdDto.getUserId());
        Optional<User> friend = userDaoRepository.findById(friendsIdDto.getFriendId());
        if (user.isPresent() && friend.isPresent()) {
            User user1 = user.get();
            User user2 = friend.get();

            if (user1.getNoOfFriends() > 0 && user2.getNoOfFriends() > 0) {
                removeNoOfFriends(user1, user2);
            }
        }
        friendRepository.removeFriend(friendsIdDto.getUserId(), friendsIdDto.getFriendId());
//        notificationService.deletNotification(friendsIdDto.getNotificationId());
        return new ApiResponse(200, "Request Canceled", null);
    }

    public ApiResponse getAllFriends(Long id) {
        List<Friend> friends = friendRepository.findAllFriends(id);
        if (!friends.isEmpty()) {


            return new ApiResponse(200, "Friends found", friends);
        } else {
            return new ApiResponse(400, "No friends found", null);
        }
    }


    public void setNoOfFriends(User user1, User user2) {

        Integer no1 = friendRepository.findAllNoOfFriends(user1.getId()) + 1;
        Integer no2 = friendRepository.findAllNoOfFriends(user2.getId()) + 1;
        user1.setNoOfFriends(no1);
        user2.setNoOfFriends(no2);
        userDaoRepository.save(user1);
        userDaoRepository.save(user2);

    }

    public void removeNoOfFriends(User user1, User user2) {

        Integer no1 = friendRepository.findAllNoOfFriends(user1.getId()) - 1;
        Integer no2 = friendRepository.findAllNoOfFriends(user2.getId()) - 1;
        user1.setNoOfFriends(no1);
        user2.setNoOfFriends(no2);
        userDaoRepository.save(user1);
        userDaoRepository.save(user2);

    }

    public ApiResponse<Long> getNotificationCount(Long id) {
        return new ApiResponse<>(200, "Notifications found", friendRepository.getNotificationCount(id));
    }


    public ApiResponse getAllFriendsAndStatus(Long id) {
        List<FriendDto> friends = friendRepository.findAllFriendsAndSeen(id);
        if (!friends.isEmpty()) {
            List<FriendDto> friendDtoList = friends.stream()
                    .filter(this::isActive)
                    .collect(Collectors.toList());
            return new ApiResponse(200, "Friends found", friendDtoList);
        } else {
            return new ApiResponse(400, "No friends found", null);
        }
    }


//    public FriendDto mapToObject(Friend friend)
//    {
//        FriendDto friendDto = new FriendDto();
//        friendDto.setActive(friend.getFriend().getActive());
//        friendDto.setDescription(friend.getFriend().getDescription());
//        friendDto.setEmail(friend.getFriend().getEmail());
//        friendDto.setName(friend.getFriend().getName());
//        friendDto.setId(friend.getFriend().getId());
//        friendDto.setProfilePicture(friend.getFriend().getProfilePicture());
//        friendDto.setUserType(friend.getFriend().getUserType());
//        friendDto.setOnline(websocketConfig.onlineUsers.contains(friend.getFriend().getEmail()) ? true : false);
//        return friendDto;
//    }

    public Boolean isActive (FriendDto friendDto)
    { return websocketConfig.onlineUsers.contains(friendDto.getEmail())?true:false; }

    public NotificationDto populateNotificationDto(User from, User to){

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationFrom(from);
        notificationDto.setType("request");
        notificationDto.setMessage(from.getName() + " sent you a friend request");
        notificationDto.setNotificationTo(to);
        return notificationDto;
    }

    public void saveFriendRequestNumberForUser(User user){
        user.setNumberOfFriendRequests(user.getNumberOfFriendRequests() + 1);
        userDaoRepository.save(user);
    }
}
