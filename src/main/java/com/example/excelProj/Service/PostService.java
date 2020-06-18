package com.example.excelProj.Service;

import com.example.excelProj.Dto.PostDto;
import com.example.excelProj.Model.Post;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.PostRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    public ResponseEntity<String> submitUserPost(PostDto postDto) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User loggedInUser = userDaoRepository.findByEmail(currentPrincipalName);

        if (loggedInUser != null) {
            if (loggedInUser.getUserType().equalsIgnoreCase("USER")) {
                Post post = new Post(postDto.getDescription(), postDto.getImage(), "USER", null, new Date(), loggedInUser);
                postRepository.save(post);
                return new ResponseEntity<>("Post submitted", HttpStatus.OK);
            } else if (loggedInUser.getUserType().equalsIgnoreCase("ADMIN")) {
                Post post = new Post(postDto.getDescription(), postDto.getImage(), "BUSINESS", postDto.getUrl(), new Date(), loggedInUser);
                postRepository.save(post);
                return new ResponseEntity<>("Post submitted", HttpStatus.OK);
            }

        }


        return new ResponseEntity<>("Something went wrong", HttpStatus.NOT_FOUND);
    }


    public ResponseEntity<List<PostDto>> getAllUserPosts(Long id) {
        return new ResponseEntity<>(postRepository.getAllUserPosts(id), HttpStatus.OK);
    }


    public ResponseEntity<List<PostDto>> getAllBusinessPosts() {
        return new ResponseEntity<>(postRepository.findAllBusinessPosts(), HttpStatus.OK);
    }
}
