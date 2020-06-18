package com.example.excelProj.Repository;

import com.example.excelProj.Dto.PostDto;
import com.example.excelProj.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,f.friend.id,f.friend.profilePicture,f.friend.name,p.image," +
            "p.type,p.url,p.date) " +
            "from Post p join Friend f on" +
            " p.user.id=f.friend.id where f.userObj.id=:id \n" +
            "AND f.status='accepted' AND p.type='USER'")
    List<PostDto> getAllUserPosts(@Param("id") Long id);


    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,f.friend.id,f.friend.profilePicture,f.friend.name,p.image," +
            "p.type,p.url,p.date) " +
            "from Post p join Friend f on" +
            " p.user.id=f.friend.id where p.type='BUSINESS'")
    List<PostDto> findAllBusinessPosts();


}
