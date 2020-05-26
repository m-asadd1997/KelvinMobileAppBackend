package com.example.excelProj.Repository;

import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Long> {


    @Query(value = "select * from friend where user_id=:id",nativeQuery = true)
    public List<Friend> getByUser(@Param("id") Long id);
}
