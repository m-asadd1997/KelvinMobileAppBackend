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
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    PostRepository postRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

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
                return new ResponseEntity<>("\"Post Submitted\"", HttpStatus.OK);
            } else if (user.get().getUserType().equalsIgnoreCase("ADMIN")) {
                Post post = new Post(postDto.getDescription(), postDto.getImage(), "BUSINESS", postDto.getUrl(), new Date(), user.get());
                postRepository.save(post);
                return new ResponseEntity<>("\"Post Submitted\"", HttpStatus.OK);
            }

        }


        return new ResponseEntity<>("\"Something went Wrong\"", HttpStatus.NOT_FOUND);
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
}
