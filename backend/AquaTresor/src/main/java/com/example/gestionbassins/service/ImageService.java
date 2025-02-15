package com.example.gestionbassins.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestionbassins.entities.Image;
import com.example.gestionbassins.entities.ImageProjection;

public interface ImageService {
	Image uplaodImage(MultipartFile file) throws IOException;

	Image getImageDetails(Long id) throws IOException;

	ResponseEntity<byte[]> getImage(Long id) throws IOException;

	void deleteImage(Long id);
	
	Image uplaodImageBassin(MultipartFile file,Long idB) throws IOException; 
	
	
	
	/*List<Image> getImagesParB(Long bId);*/
	List<ImageProjection> getImagesParB(Long bId);
}
