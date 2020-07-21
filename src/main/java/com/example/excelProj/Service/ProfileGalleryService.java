package com.example.excelProj.Service;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Commons.Constants;
import com.example.excelProj.Dto.ProfileGalleryDto;
import com.example.excelProj.Model.ProfileGallery;
import com.example.excelProj.Model.User;
import com.example.excelProj.Repository.ProfileGalleryRepository;
import com.example.excelProj.Repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileGalleryService {

    @Autowired
    ProfileGalleryRepository profileGalleryRepository;

    @Autowired
    UserDaoRepository userDaoRepository;

    @Value("${gallery.image.url}")
    String galleryImageUrl;

    public ApiResponse saveGalleryPicture(ProfileGalleryDto profileGalleryDto){
        Optional<User> user = userDaoRepository.findById(profileGalleryDto.getUserId());
        String unique = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
        if(user.isPresent()){
            User user1 = user.get();
            ProfileGallery profileGallery = new ProfileGallery();
            if(saveImage(profileGalleryDto.getGalleryImage(),user1.getName(),user1.getId(),unique)) {
                profileGallery.setGalleryImage(Constants.galleryImagePath+user1.getId()+"-"+user1.getName()+"/"+unique+profileGalleryDto.getGalleryImage().getOriginalFilename());
                profileGallery.setUser(user1);
                profileGallery.setDate(new Date());
                profileGalleryRepository.save(profileGallery);
                return new ApiResponse(200,"Picture uploaded",profileGallery);
            }
            else{
                return new ApiResponse(400,"Error uploading picture",null);
            }
        }
        return new ApiResponse(400,"User not found",null);


    }


    public Boolean saveImage(MultipartFile file, String name,Long id, String unique  ){
        try{

            String UPLOADED_FOLDER_NEW = Constants.SERVER_PATH +"//"+"serverFiles//"+id+"-"+name+"//";

            File dir = new File(UPLOADED_FOLDER_NEW);
            dir.setExecutable(true);
            dir.setReadable(true);
            dir.setWritable(true);

            if(!dir.exists()){
                dir.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER_NEW + unique+ file.getOriginalFilename());
            Files.write(path, bytes);

        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResponseEntity<InputStreamResource> getImage(String filename, String userIdAndName) throws IOException{
        String filepath = Constants.SERVER_PATH+"//"+"serverFiles//"+userIdAndName+"//"+filename;
        File f = new File(filepath);
        Resource file = new UrlResource(f.toURI());
        return  ResponseEntity
                .ok()
                .contentLength(file.contentLength())
                .contentType(
                        MediaType.parseMediaType("image/JPG"))
                .body(new InputStreamResource(file.getInputStream()));
    }



    public  ApiResponse deleteGalleryImageById(Long id) {

        Optional<ProfileGallery> profileGallery = profileGalleryRepository.findById(id);
        if(profileGallery.isPresent()){
            String imgPath = profileGallery.get().getGalleryImage();
            String[] path = imgPath.split("/");
            String path1="";
            for(Integer i = 5; i < path.length ; i++){
                if(i == path.length-1){
                    path1 +=  path[i];
                }
                else{
                    path1 +=  path[i]+"//";
                }
            }
            if (deleteGalleryImage(path1)){

                profileGalleryRepository.deleteById(id);

                return new ApiResponse(200, "Picture deleted", null);

            }
            else{
                return new ApiResponse(400, "Error deleting Picture", null);
            }
        }
        return new ApiResponse(400,"Picture not found" , null);
    }

    public Boolean deleteGalleryImage(String path) {
        String filepath = Constants.SERVER_PATH+"//"+"serverFiles//"+path;
        File f = new File(filepath);

        try {

            if(f.delete()) {
                System.out.println(f.getName() + " is deleted!");

            } else {
                System.out.println("Delete operation is failed.");
            }
        }
        catch(Exception e)
        {
            System.out.println("Failed to Delete image !!");
        }

        return  true;
    }

    public ApiResponse getAllImagesByUserId(Long id){
        List<ProfileGallery> profileGalleries = profileGalleryRepository.findProfileGalleriesByUserId(id);
        if(!profileGalleries.isEmpty()){
            return new ApiResponse(200,"Gallery images found",profileGalleries);
        }
        else {
            return new ApiResponse(400,"Images not found",null);
        }
    }

    public ApiResponse getImageById(Long id){
        Optional<ProfileGallery> profileGallery = profileGalleryRepository.findById(id);
        if(profileGallery.isPresent()){
            ProfileGallery profileGallery1 = profileGallery.get();
            return new ApiResponse(200,"Image found",profileGallery1);
        }
        else{
            return new ApiResponse(400,"Image not found",null);
        }
     }
}
