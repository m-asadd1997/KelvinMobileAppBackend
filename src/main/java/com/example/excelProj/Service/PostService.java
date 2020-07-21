package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Config.WebsocketConfig;
import com.example.excelProj.Dto.NotificationDto;
import com.example.excelProj.Dto.PostDto;
import com.example.excelProj.Model.Friend;
import com.example.excelProj.Model.Notification;
import com.example.excelProj.Model.Post;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.FriendRepository;
import com.example.excelProj.Repository.NotificationRepository;
import com.example.excelProj.Repository.PostRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    public ResponseEntity<String> submitUserPost(Long userId,PostDto postDto) {


//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String currentPrincipalName = authentication.getName();
//        User loggedInUser = userDaoRepository.findByEmail(currentPrincipalName);
//

       Optional<User>  user=userDaoRepository.findById(userId);

        if (user.isPresent()) {

            if (user.get().getUserType().equalsIgnoreCase("USER")) {
                Post post = new Post(postDto.getDescription(), postDto.getImage(), "USER", null, new Date(), user.get());
                postRepository.save(post);
                notificationService.savePostNotification(populateNotificationDto(user.get()));
                return new ResponseEntity<>("\"Post Submitted\"", HttpStatus.OK);
            } else if (user.get().getUserType().equalsIgnoreCase("Business User")) {
                Post post = new Post(postDto.getDescription(), postDto.getImage(), "BUSINESS", postDto.getUrl(), new Date(), user.get());
                postRepository.save(post);
                notificationService.savePostNotification(populateNotificationDto(user.get()));
                return new ResponseEntity<>("\"Post Submitted\"", HttpStatus.OK);
            }

        }


        return new ResponseEntity<>("\"Something went Wrong\"", HttpStatus.NOT_FOUND);
    }


    public ApiResponse editUserPost(Long postId,PostDto postDto){
        Optional<Post> post1 = postRepository.findById(postId);
        if(post1.isPresent()){
            Post post = post1.get();
            post.setImage(postDto.getImage());
            post.setDescription(postDto.getDescription());
            post.setDate(new Date());
            post.setUrl(postDto.getUrl());
            return new ApiResponse(200,"Post Updated",postRepository.save(post));
        }
        else{
            return new ApiResponse(400,"Post not found",null);
        }
    }

    public ApiResponse deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isPresent()) {
            postRepository.deleteById(id);
            return new ApiResponse(200,"Post Deleted",null);
        }
        return new ApiResponse(400,"Post not found",null);
    }


    public ResponseEntity<List<PostDto>> getAllUserPosts(Long id) {
        return new ResponseEntity<>(postRepository.getAllUserPosts(id), HttpStatus.OK);
    }


    public ResponseEntity<List<PostDto>> getAllBusinessPosts() {
        return new ResponseEntity<>(postRepository.findAllBusinessPosts(), HttpStatus.OK);
    }


    public ResponseEntity<PostDto> getPostById(Long id)
    {
       PostDto postDto=postRepository.findPostById(id);

       if(postDto!=null)
       {
           return new ResponseEntity<>(postDto,HttpStatus.OK);
       }
       return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

    public NotificationDto populateNotificationDto(User from){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setNotificationFrom(from);
        notificationDto.setMessage(from.getName() + " added a new post");

        return notificationDto;
    }


//    public void sendNotificationBySocketToAllFriends(User user){
//
//        List<Friend> friends = friendRepository.findAllFriends(user.getId());
//        if(!friends.isEmpty()){
//
//        }
//
//        simpMessagingTemplate.convertAndSend("/topic/post-notification/" + friend.get().getId(),
//                notificationService.getLiveNotification(friend.get().getId(),"request",user.get().getId()));
//    }
}
