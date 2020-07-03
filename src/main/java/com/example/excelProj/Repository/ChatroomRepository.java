package com.example.excelProj.Repository;

import com.example.excelProj.Model.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom,Long> {


    @Query(value = "select * from chatroom where user1_id=:user1 And user2_id=:user2",nativeQuery = true)
    Chatroom findChatroom(@Param("user1") Long user1, @Param("user2") Long user2);

//    @Query("select new com.example.excelProj.Dto.ChatroomDTO(cr.user2,c.message,cr.chatroomId,c.seen,c.date,c.sender) " +
//            "from Chatroom cr join Chat c \n" +
//            "on cr.chatroomId = c.chatroomId \n" +
//            "where cr.user1.id = :id \n" +
//            "and c.date in (select max(c.date) as maxDate from Chat c \n" +
//            "group by c.chatroomId)\n" +
//            "order by c.date desc")
//    List<ChatroomDTO> findChatrooms(@Param("id") Long id);



}