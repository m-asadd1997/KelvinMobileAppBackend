package com.example.excelProj.Controller;

import com.example.excelProj.Dto.PostDto;
import com.example.excelProj.Repository.PostRepository;
import com.example.excelProj.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/post")
    public ResponseEntity<String> submitUserPost(@RequestBody PostDto postDto)
    { return postService.submitUserPost(postDto);}


    @GetMapping("/user-post/{id}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestParam("id") Long id)
    { return postService.getAllUserPosts(id);}

    @GetMapping("/business-post")
    public ResponseEntity<List<PostDto>> getAllBusinessPosts()
    { return postService.getAllBusinessPosts();}

}
