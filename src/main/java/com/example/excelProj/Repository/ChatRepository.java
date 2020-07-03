package com.example.excelProj.Repository;

import com.example.excelProj.Model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {

    @Query(value = "select * from chat where chatroom_id=:chatroomId  AND date>= DATE_SUB(NOW(),INTERVAL 12 HOUR)",nativeQuery = true)
    List<Chat> findAllChats(@Param("chatroomId") String chatroomId);

    @Transactional
    @Modifying
    @Query(value = "update chat set seen=true where chatroom_id=:chatroomId and sender=:id",nativeQuery = true)
    void setSeenMessage(@Param("chatroomId") String chatroomId,@Param("id") Long id);


}