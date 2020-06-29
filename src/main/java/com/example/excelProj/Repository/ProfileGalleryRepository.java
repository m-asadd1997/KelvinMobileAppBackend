package com.example.excelProj.Repository;

import com.example.excelProj.Model.ProfileGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileGalleryRepository extends JpaRepository<ProfileGallery,Long> {

    public List<ProfileGallery> findProfileGalleriesByUserId(Long id);
}
