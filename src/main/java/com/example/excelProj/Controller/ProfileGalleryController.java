package com.example.excelProj.Controller;

import com.example.excelProj.Commons.ApiResponse;
import com.example.excelProj.Dto.ProfileGalleryDto;
import com.example.excelProj.Service.ProfileGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/gallery")
public class ProfileGalleryController {

    @Autowired
    ProfileGalleryService profileGalleryService;

    @PostMapping("/")
    public ApiResponse saveGalleryPicture(@RequestParam("galleryImage") MultipartFile image, ProfileGalleryDto profileGalleryDto){
            profileGalleryDto.setGalleryImage(image);
            return profileGalleryService.saveGalleryPicture(profileGalleryDto);
    }

    @GetMapping("/{user}/{filename:.+}")
    public ResponseEntity<InputStreamResource> getGalleryImage(@PathVariable("user") String userIdAndName, @PathVariable("filename") String filename)
            throws IOException {
        return profileGalleryService.getImage(filename,userIdAndName);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteGalleryImage(@PathVariable("id") Long id){
        return profileGalleryService.deleteGalleryImageById(id);
    }

    @GetMapping("/{id}")
    public ApiResponse getAllImagesByUserId(@PathVariable("id") Long id){
        return profileGalleryService.getAllImagesByUserId(id);
    }

    @GetMapping("/singleimage/{id}")
    public ApiResponse getImageById(@PathVariable("id") Long id){
        return profileGalleryService.getImageById(id);
    }
}
