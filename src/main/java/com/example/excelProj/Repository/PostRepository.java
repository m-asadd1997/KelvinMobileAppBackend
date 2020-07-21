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

//    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,f.friend.id,f.friend.profilePicture,f.friend.name,p.image," +
////            "p.type,p.url,p.date) " +
////            "from Post p join Friend f on" +
////            " p.user.id=f.friend.id where ( f.userObj.id=:id \n" +
////            "AND f.status='accepted' AND p.type='USER' ) OR (p.user.id=:id)")
////    List<PostDto> getAllUserPosts(@Param("id") Long id);

    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,p.user.id,p.user.profilePicture,p.user.name,p.image,p.type,p.url,p.date) from Post p, User u where p.user.id = u.id and p.user.id in ( select un.id from User un left join Friend fa on fa.userObj.id = un.id where un.id = :id or fa.friend.id = :id and fa.status='accepted') and p.type='USER' order by p.date DESC")
    List<PostDto> getAllUserPosts(@Param("id") Long id);

    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,u.id,u.profilePicture,u.name,p.image," +
            "p.type,p.url,p.date) " +
            "from Post p join User u on" +
            " p.user.id=u.id where p.type='BUSINESS' order by p.date DESC")
    List<PostDto> findAllBusinessPosts();


    @Query("select new com.example.excelProj.Dto.PostDto(p.id,p.description,p.user.id,p.user.profilePicture,p.user.name,p.image,p.type,p.url,p.date)" +
            " from Post p ,User u where p.user.id = u.id AND p.id=:id")
    PostDto findPostById(@Param("id") Long id);


}
