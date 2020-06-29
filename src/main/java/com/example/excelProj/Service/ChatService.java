package com.example.excelProj.Service;

import com.example.excelProj.Commons.ResponseDTO;
import com.example.excelProj.Model.Chat;
import com.example.excelProj.Model.Chatroom;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.ChatRepository;
import com.example.excelProj.Repository.ChatroomRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatroomRepository chatroomRepository;

    @Autowired
    UserDaoRepository userDaoRepository;


    public ResponseEntity<String> initiateChat(Long user1Id, Long user2Id) {
        Chatroom chatroom = chatroomRepository.findChatroom(user1Id, user2Id);

        if (chatroom == null) {
            String uuid = UUID.randomUUID().toString();
            Optional<User> user1 = userDaoRepository.findById(user1Id);
            Optional<User> user2 = userDaoRepository.findById(user2Id);

            if (user1.isPresent() && user2.isPresent()) {
                chatroomRepository.save(new Chatroom(user1.get(), user2.get(), uuid));
                chatroomRepository.save(new Chatroom(user2.get(), user1.get(), uuid));

                return new ResponseEntity<>("\"" + uuid + "\"", HttpStatus.OK);

            }
            return new ResponseEntity<>("\"Chat users not found\"", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("\"" + chatroom.getChatroomId() + "\"", HttpStatus.OK);
    }


    public ResponseEntity<List<Chat>> getAllChats(String chatroomId, Long freindId) {
        chatRepository.setSeenMessage(chatroomId, freindId);
        Optional<User> user = userDaoRepository.findById(freindId);
        List<Chat> chatList = chatRepository.findAllChats(chatroomId);
        if (user.isPresent())
            return new ResponseEntity(new ResponseDTO(chatList, user.get()), HttpStatus.OK);

        return new ResponseEntity("user not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity seenAllUserChatroomMessages(String chatroomId, Long id) {
        chatRepository.setSeenMessage(chatroomId, id);
        return new ResponseEntity("\"Messages seen\"", HttpStatus.OK);
    }



//    public ResponseEntity getChatCount(Long id){
//        List<ChatroomDTO> chatroomDTOList=chatroomRepository.findChatrooms(id);
//
//        Long count= chatroomDTOList
//                .stream()
//                .filter((chatroomDTO)->chatroomDTO.getSender()!=id && chatroomDTO.getSeen().equals(false))
//                .count();
//        return new ResponseEntity(count, HttpStatus.OK);
//
//
//    }
}

