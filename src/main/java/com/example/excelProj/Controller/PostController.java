package com.example.excelProj.Controller;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.EventDto;
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

    @PostMapping("/post/{id}")
    public ResponseEntity<String> submitUserPost(@PathVariable("id") Long userId , @RequestBody PostDto postDto)
    {
        return postService.submitUserPost(userId,postDto);
    }


    @GetMapping("/user-posts/{id}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable("id") Long id)
    { return postService.getAllUserPosts(id);}

    @GetMapping("/business-posts")
    public ResponseEntity<List<PostDto>> getAllBusinessPosts()
    { return postService.getAllBusinessPosts();}

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id)
    {
        return postService.getPostById(id);
    }

    @DeleteMapping("/post/delete-post/{id}")
    public ApiResponse deletePostById(@PathVariable("id") Long id){
        return postService.deletePost(id);
    }

    @PutMapping("/post/update-post/{id}")
    public ApiResponse updatePost(@PathVariable("id") Long id,@RequestBody PostDto postDto){
        return postService.editUserPost(id,postDto);
    }

}
