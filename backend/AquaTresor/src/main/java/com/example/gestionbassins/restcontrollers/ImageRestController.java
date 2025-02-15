package com.example.gestionbassins.restcontrollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestionbassins.entities.Image;
import com.example.gestionbassins.entities.ImageProjection;
import com.example.gestionbassins.repos.ImageRepository;
import com.example.gestionbassins.service.BassinService;
import com.example.gestionbassins.service.ImageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/image")
@CrossOrigin(origins = "*")
public class ImageRestController {
	
	@Autowired
	ImageService imageService ;
	
	@Autowired
	BassinService bassinService;
	
	@Autowired
	ImageRepository imageRepository;
	
	@RequestMapping(value = "/upload" , method = RequestMethod.POST)
	public Image uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
		return imageService.uplaodImage(file);
	}
	
	//faire ajouter une image à un bassin bien définit!
	@PostMapping(value = "/uploadImageB/{idB}")
	public Image uploadMultiImages(@RequestParam("image") MultipartFile file, @PathVariable("idB") Long idB)
			throws IOException {
		return imageService.uplaodImageBassin(file, idB);
	}

	

	//retourne la liste des images d'un bassin
	/*@RequestMapping(value = "/getImagesB/{idB}", method = RequestMethod.GET)
	public List<Image> getImagesBassin(@PathVariable("idB") Long idB) throws IOException {
		return imageService.getImagesParB(idB);
	}*/
	
	/*
	@RequestMapping(value = "/getImagesB/{idB}", method = RequestMethod.GET)
	public List<ImageProjection> getImagesBassin(@PathVariable("idB") Long idB) throws IOException {
	    return imageRepository.findImagesByBassinId(idB);
	}*/
	
	@RequestMapping(value = "/getImagesB/{idB}", method = RequestMethod.GET)
	public List<ImageProjection> getImagesBassin(@PathVariable("idB") Long idB) throws IOException {
	    return imageService.getImagesParB(idB);
	}
	
	
	
	@RequestMapping(value = "/get/info/{id}" , method = RequestMethod.GET)
	public Image getImageDetails(@PathVariable("id") Long id) throws IOException {
		return imageService.getImageDetails(id) ;
	}
		
	/*
	@RequestMapping(value = "/load/{id}" , method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException{
		return imageService.getImage(id);
	}*/
	@RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {
	    return imageService.getImage(id);
	}
		
	@RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
	public void deleteImage(@PathVariable("id") Long id){
		imageService.deleteImage(id);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.PUT)
	public Image UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
		return imageService.uplaodImage(file);
	}
	
	

	/****/
	/*
	@RequestMapping(value = "/uploadFS/{id}", method = RequestMethod.POST)
	public void uploadImageFS(@RequestParam("image") MultipartFile file, @PathVariable("id") Long id)
			throws IOException {
		Evenement ev = evenementService.getEvenement(id);
		ev.setImagePath(id + ".jpg");
		Files.write(Paths.get(System.getProperty("user.home") + "/imagesEvenements/" + ev.getImagePath()), file.getBytes());
		evenementService.saveEvenemet(ev);
	}
	
	
	@RequestMapping(value = "/loadfromFS/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageFS(@PathVariable("id") Long id) throws IOException {
		Evenement ev = evenementService.getEvenement(id);
		
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") 
				+ "/imagesEvenements/" + ev.getImagePath()));
	}*/
	
	/******Les lignes de codes pour stocker une images dans le file imagesEvenements*******/
	/*@RequestMapping(value = "/uploadFS/{id}", method = RequestMethod.POST)
	public void uploadImageFS(@RequestParam("image") MultipartFile file, @PathVariable("id") Long id)
	       throws IOException {
	   String uploadDir = System.getProperty("user.home") + "/imagesEvenements";
	   Path uploadPath = Paths.get(uploadDir);
	   
	   // Créer le dossier s'il n'existe pas
	   if (!Files.exists(uploadPath)) {
	       Files.createDirectories(uploadPath);
	       System.out.println("Dossier créé: " + uploadDir);
	   }
	   
	   // Obtenir l'extension du fichier original
	   String originalFileName = file.getOriginalFilename();
	   String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
	   
	   Evenement ev = evenementService.getEvenement(id);
	   String fileName = id + extension;  // Utilise l'extension du fichier uploadé
	   ev.setImagePath(fileName);
	   
	   Path filePath = uploadPath.resolve(fileName);
	   
	   // Log avant l'écriture
	   System.out.println("Écriture du fichier: " + filePath);
	   System.out.println("Taille du fichier: " + file.getSize() + " bytes");
	   System.out.println("Extension du fichier: " + extension);
	   
	   // Supprimer l'ancien fichier s'il existe
	   Files.deleteIfExists(filePath);
	   
	   // Écrire le nouveau fichier
	   Files.write(filePath, file.getBytes());
	   
	   // Vérifier que le fichier a bien été écrit
	   if (Files.exists(filePath)) {
	       System.out.println("Fichier écrit avec succès: " + filePath);
	       System.out.println("Taille du fichier écrit: " + Files.size(filePath) + " bytes");
	   }
	   
	   evenementService.saveEvenemet(ev);
	}

	
	@RequestMapping(value = "/loadfromFS/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getImageFS(@PathVariable("id") Long id) throws IOException {
	    Evenement ev = evenementService.getEvenement(id);
	    
	    // Afficher le chemin de l'image dans l'événement
	    System.out.println("ImagePath dans l'événement: " + ev.getImagePath());
	    
	    String fullPath = System.getProperty("user.home") + "/imagesEvenements/" + ev.getImagePath();
	    Path imagePath = Paths.get(fullPath);
	    
	  
	    // Lister les fichiers dans le dossier
	    Files.list(Paths.get(System.getProperty("user.home") + "/imagesEvenements"))
	         .forEach(file -> System.out.println("Fichier trouvé: " + file.getFileName()));
	    
	    if (!Files.exists(imagePath)) {
	        throw new RuntimeException("Image non trouvée pour l'événement avec l'ID: " + id 
	            + "\nChemin recherché: " + fullPath);
	    }
	    
	    return Files.readAllBytes(imagePath);
	}*/
	/******************/
	
}