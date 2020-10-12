package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Config.WebsocketConfig;
import com.example.excelProj.Dto.NotificationBody;
import com.example.excelProj.Dto.NotificationDto;
import com.example.excelProj.Dto.NotificationObject;
import com.example.excelProj.Dto.PostDto;
import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.Notification;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.NotificationRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    WebsocketConfig websocketConfig;

    @Autowired
    RestTemplate restTemplate;



    public ApiResponse saveNotification(NotificationDto notificationDto){

        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setNotificationFrom(notificationDto.getNotificationFrom());
        notification.setNotificationTo(notificationDto.getNotificationTo());
        notification.setMessage(notificationDto.getMessage());
        notification.setType(notificationDto.getType());
        notification.setSeen(false);
        Notification alredyAddednotification = notificationRepository.getAlreadyAddedNotification(notificationDto.getMessage(),notificationDto.getNotificationTo().getId());
        if(alredyAddednotification != null){
            notificationRepository.setSeenNotification(alredyAddednotification.getId());
        }
        return new ApiResponse(200,"Notification saved",notificationRepository.save(notification));
    }

    public ApiResponse savePostNotification(NotificationObject notificationObject,NotificationBody notificationBody, PostDto postDto,NotificationDto notificationDto){

        List<Friend> friends = friendRepository.findAllFriends(notificationDto.getNotificationFrom().getId());

        if(!friends.isEmpty()){
            friends.forEach(friend -> {
                Notification notification = new Notification();
                notification.setNotificationFrom(notificationDto.getNotificationFrom());
                notification.setNotificationTo(friend.getFriend());
                notification.setMessage(notificationDto.getMessage());
                notification.setSeen(false);
                notification.setDate(new Date());
                notification.setType("post");
                saveNotificationNumberForUser(friend.getFriend());
                Notification alredyAddednotification = notificationRepository.getAlreadyAddedNotification(notificationDto.getMessage(),friend.getFriend().getId());
                if(alredyAddednotification != null){
                    notificationRepository.setSeenNotification(alredyAddednotification.getId());
                }
                notificationRepository.save(notification);
                simpMessagingTemplate.convertAndSend("/topic/post-notification/" + friend.getFriend().getId(),getLiveNotification(friend.getFriend().getId(),"post",notificationDto.getNotificationFrom().getId()));
                sendPushNotification(notificationBody,notificationObject,postDto,friend.getFriend());
            });
            return new ApiResponse(200,"Notification saved",null);
        }
        return new ApiResponse(400,"Notification not saved",null);
    }

    public void sendPushNotification(NotificationBody notificationBody, NotificationObject notificationObject, PostDto postDto, User user){
        notificationObject.setTitle(postDto.getNotificationTitle());
        notificationObject.setBody(postDto.getNotificationBody());
        notificationBody.setNotification(notificationObject);
        notificationBody.setTo(user.getFirebaseToken());
        System.out.println(notificationBody);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization","key=AAAA81_riiM:APA91bF8FtqkcElESD0Uh9bBY2IjGJsD4gHp7X5SIpyE66peD9pya6O3Mq7xlFZdMmlAlb8oFp9XSedYyrR5ImiUqep40g_GYBiXfjvjzcpm8ZpxyPjPK74Y4E0gK2uEnJk17-wMLmCJ");
        HttpEntity httpEntity = new HttpEntity(notificationBody, headers);
        String resp =restTemplate.postForObject("https://fcm.googleapis.com/fcm/send", httpEntity,String.class);
    }

    public ApiResponse getLiveNotification(Long toUserId,String type,Long fromUserId){
        Notification notification = notificationRepository.getLiveNotification(toUserId,type,fromUserId);
        return new ApiResponse(200,"Notification received",notification);
    }

    public ApiResponse getAllUnseenNotifications(Long toUserId){
        List<Notification> notifications = notificationRepository.getAllUnseenNotifications(toUserId);
        if(!notifications.isEmpty()){
            notifications.forEach(notification -> {
                notificationRepository.setSeenNotification(notification.getId());
            });
            return new ApiResponse(200,"Notifications found",notifications);
        }
        return new ApiResponse(400,"No new notifications",null);
    }

    public ApiResponse seenNotification(Long id){
        notificationRepository.setSeenNotification(id);
        return new ApiResponse(200,"Notification seen",null);
    }

    public ApiResponse seenPostNotification(Long id,Long userId){
        List<Friend> friends = friendRepository.findAllFriends(userId);
        friends.forEach(friend -> {
            notificationRepository.setSeenNotification(id);
        });
        return new ApiResponse(200,"Notfications seen",null);
    }

    public ApiResponse getAllNotifications(Long toUserId){
        List<Notification> notifications = notificationRepository.getAllNotifications(toUserId);
        if(!notifications.isEmpty()){
            return new ApiResponse(200,"Notifications found",notifications);
        }
        return new ApiResponse(400,"No new notifications",null);
    }

    public ApiResponse deletNotification(Long id){
        Optional<Notification> notification = notificationRepository.findById(id);
        if(notification.isPresent()){
            notificationRepository.deleteById(id);
            return new ApiResponse(200,"Notification deleted",null);
        }
        return new ApiResponse(400,"Notification not found",null);


    }

    public ApiResponse<Long> getNotificationCount(Long id) {
        return new ApiResponse<>(200, "Notifications found", notificationRepository.getNotificationCount(id));
    }

    public void saveNotificationNumberForUser(User user){
        user.setNumberOfNotifications(user.getNumberOfNotifications() + 1);
        userDaoRepository.save(user);
    }


}
