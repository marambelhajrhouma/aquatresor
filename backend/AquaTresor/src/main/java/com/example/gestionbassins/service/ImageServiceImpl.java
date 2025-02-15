package com.example.gestionbassins.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestionbassins.entities.Bassin;
import com.example.gestionbassins.entities.Image;
import com.example.gestionbassins.entities.ImageProjection;
import com.example.gestionbassins.repos.BassinRepository;
import com.example.gestionbassins.repos.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
	@Autowired
	ImageRepository imageRepository;

	@Autowired
	BassinService bassinService;
	
	@Autowired
	BassinRepository bassinRepository;
	
	@Override
	 public Image uplaodImage(MultipartFile file) throws IOException {
	 /*Ce code commenté est équivalent au code utilisant le design pattern Builder
	 * Image image = new Image(null, file.getOriginalFilename(),
	 file.getContentType(), file.getBytes(), null);
	 return imageRepository.save(image);*/
	 return imageRepository.save(Image.builder()
	 .name(file.getOriginalFilename())
	 .type(file.getContentType())
	 .image(file.getBytes()).build() );
	}
	
	 @Override
	 public Image getImageDetails(Long id) throws IOException{
		 final Optional<Image> dbImage = imageRepository. findById (id);
		 return Image.builder()
		 .idImage(dbImage.get().getIdImage())
		 .name(dbImage.get().getName())
		 .type(dbImage.get().getType())
		 .image(dbImage.get().getImage()).build() ;
	 }
	 
	 /*
	 @Override
	 public ResponseEntity<byte[]> getImage(Long id) throws IOException{
		 final Optional<Image> dbImage = imageRepository. findById (id);
		 return ResponseEntity
		 .ok()
		 .contentType(MediaType.valueOf(dbImage.get().getType()))
		 .body(dbImage.get().getImage());
	 }*/
	 
	 @Override
	 public ResponseEntity<byte[]> getImage(Long id) throws IOException {
	     Optional<Image> dbImage = imageRepository.findById(id);
	     return ResponseEntity
	         .ok()
	         .contentType(MediaType.valueOf(dbImage.get().getType()))
	         .body(dbImage.get().getImage());
	 }
	 
	 @Override
	 public void deleteImage(Long id) {
		 imageRepository.deleteById(id);
	 }
	 
	@Override
	public Image uplaodImageBassin(MultipartFile file, Long idB) throws IOException {
		Bassin b = new Bassin();
		b.setIdBassin(idB);;
			
		 byte[] imageBytes = file.getBytes();
		    System.out.println("Image bytes length: " + imageBytes.length); // Debugging
		    
		return imageRepository
				.save(Image.builder()
				.name(file.getOriginalFilename())
				.type(file.getContentType())
				.image(file.getBytes())
				.bassin(b).build());
	}
		
	
	/*
	@Override
	public List<Image> getImagesParB(Long bId) {
		Bassin b = bassinRepository.findById(bId).get();
		return b.getImages();
	}*/
	
	@Override
	public List<ImageProjection> getImagesParB(Long bId) {
	    return imageRepository.findImagesByBassinId(bId);
	}
	
	
	
	
}