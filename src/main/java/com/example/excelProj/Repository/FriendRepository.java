package com.example.excelProj.Repository;

import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {


    @Query(value = "select * from friend where user_id=:id", nativeQuery = true)
    public List<Friend> getByUser(@Param("id") Long id);


    @Query(value = "select * from friend where (user_id=:friendId AND friend_id=:userId)", nativeQuery = true)
    public Friend findByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);


    @Query(value = "select * from friend where friend_id=:userId AND status='pending'", nativeQuery = true)
    public List<Friend> findAllUserRequests(@Param("userId") Long userId);

    @Query(value = "select * from friend where user_id=:userId AND status='accepted'",nativeQuery = true)
    public List<Friend> findAllFriends(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query(value = "delete from friend where (user_id=:userId AND friend_id=:friendId) OR (user_id=:friendId AND friend_id=:userId)",nativeQuery = true)
    public void removeFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    @Query(value = "select count(*) from friend where user_id=:userId AND status='accepted'",nativeQuery = true)
    public Integer findAllNoOfFriends(@Param("userId") Long userId);

}
