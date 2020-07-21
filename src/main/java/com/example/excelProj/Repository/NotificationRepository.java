package com.example.excelProj.Repository;

import com.example.excelProj.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    //Live Notification
    @Query(value = "select * from notification where to_user_id =:id and from_user_id=:fromId and type=:type and seen = false",nativeQuery = true)
    public Notification getLiveNotification(@Param("id") Long id,@Param("type") String type,@Param("fromId") Long fromId);

    @Transactional
    @Modifying
    @Query(value = "update notification set seen=true where id=:id",nativeQuery = true)
    void setSeenNotification(@Param("id") Long id);

    @Query(value = "select * from notification where to_user_id =:id and seen = false",nativeQuery = true)
    public List<Notification> getAllUnseenNotifications(@Param("id") Long id);

    @Query(value = "select * from notification where message=:msg and to_user_id=:id and seen = false",nativeQuery = true)
    public Notification getAlreadyAddedNotification(@Param("msg") String message, @Param("id") Long id);

    @Query(value = "select * from notification where to_user_id =:id",nativeQuery = true)
    public List<Notification> getAllNotifications(@Param("id") Long id);

    @Query(value = "select count(*) from notification where to_user_id=:id",nativeQuery = true)
    Long getNotificationCount(@Param("id") Long id);

}
