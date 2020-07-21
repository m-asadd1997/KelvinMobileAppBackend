package com.example.excelProj.Controller;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/get-all-unseen/{toId}")
        public ApiResponse getAllUnseenNotifications(@PathVariable("toId") Long id){
           return notificationService.getAllUnseenNotifications(id);
    }

    @GetMapping("/seen-notification/{id}")
    public ApiResponse seenSingleNotfication(@PathVariable("id") Long id){
        return notificationService.seenNotification(id);
    }

    @GetMapping("/seen-all-notification/{id}/{userId}")
    public ApiResponse seenAllNotfication(@PathVariable("id") Long id,@PathVariable("userId") Long userId){
        return notificationService.seenPostNotification(id,userId);
    }

    @GetMapping("/get-all/{toId}")
    public ApiResponse getAllNotifications(@PathVariable("toId") Long id){
        return notificationService.getAllNotifications(id);
    }

    @DeleteMapping("/delete-notification/{id}")
    public ApiResponse deleteNotification(@PathVariable("id") Long id){
        return notificationService.deletNotification(id);
    }

    @GetMapping("/get-notification-count/{id}")
    public ApiResponse<Long> getNotificationCount(@PathVariable("id") Long userId)
    {return notificationService.getNotificationCount(userId);}

}
