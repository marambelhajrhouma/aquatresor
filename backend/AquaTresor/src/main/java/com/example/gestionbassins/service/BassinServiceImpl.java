package com.example.gestionbassins.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.gestionbassins.dto.BassinDTO;
import com.example.gestionbassins.dto.ImageDTO;
import com.example.gestionbassins.entities.Bassin;
import com.example.gestionbassins.entities.Categorie;
import com.example.gestionbassins.repos.BassinRepository;
import com.example.gestionbassins.repos.ImageRepository;



@Service
public class BassinServiceImpl implements BassinService{

	@Autowired
	BassinRepository bassinRepository;
	
	@Autowired
	ImageRepository imageRepository;
	
	//@PreAuthorize("hasAuthority('ADMIN')")
	@Override
	public Bassin saveBassin(Bassin b) {
		
		return bassinRepository.save(b) ;
	}

	
	@Override
	public Bassin upadteBassin(Bassin b) {
		return bassinRepository.save(b) ;
	}
	
	/*
	@Override
	public Evenement upadteEvenement(Evenement ev) {
		//Long oldEvImageId = this.getEvenement(ev.getIdEvenement()).getImage().getIdImage();
		//Long newEvImageId = ev.getImage().getIdImage();
		Evenement evUpdated = evenementRepository.save(ev);
		//if (oldEvImageId != newEvImageId)  // si l'image a été modifiée
			//imageRepository.deleteById(oldEvImageId);
		
		return evUpdated;
	}*/

	@Override
	public void deleteBassin(Bassin b) {
		bassinRepository.delete(b) ;	
	}

	@Override
	public void deleteBassinById(Long id) {
		Bassin b = getBassin(id);
		//suuprimer l'image avant de supprimer le produit
		try {
			Files.delete(Paths.get(System.getProperty("user.home")+"/imagesBassins/"+b.getImagePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		bassinRepository.deleteById(id) ;
	}

	@Override
	public Bassin getBassin(Long id) {
		return bassinRepository.findById(id).get() ;
	}

	@Override
	public List<Bassin> getAllBassins() {
		return bassinRepository.findAll() ;
	}

	
	//new
	@Override
	public List<Bassin> findByNomBassin(String nom) {
		
		return bassinRepository.findByNomBassin(nom);
	}

	@Override
	public List<Bassin> findByNomBassinContains(String nom) {
		// TODO Auto-generated method stub
		return bassinRepository.findByNomBassinContains(nom);
	}

	@Override
	public List<Bassin> findByNomPrix(String nom, Double prix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bassin> findByCategorie(Categorie c) {
		// TODO Auto-generated method stub
		return bassinRepository.findByCategorie(c);
	}

	@Override
	public List<Bassin> findByCategorieIdCategorie(Long id) {
		// TODO Auto-generated method stub
		return bassinRepository.findByCategorieIdCategorie(id);
	}

	@Override
	public List<Bassin> findByOrderByNomBassinAsc() {
		// TODO Auto-generated method stub
		return bassinRepository.findByOrderByNomBassinAsc();
	}

	@Override
	public List<Bassin> trierBassinsNomsPrix() {
		// TODO Auto-generated method stub
		return bassinRepository.trierBassinNomPrix();
	}

	
	public BassinDTO toBassinDTO(Bassin bassin) {
	    BassinDTO dto = new BassinDTO();
	    dto.setIdBassin(bassin.getIdBassin());
	    dto.setNomBassin(bassin.getNomBassin());
	    dto.setDescription(bassin.getDescription());
	    dto.setPrix(bassin.getPrix());
	    dto.setMateriau(bassin.getMateriau());
	    dto.setCouleur(bassin.getCouleur());
	    dto.setDimensions(bassin.getDimensions());
	    dto.setDisponible(bassin.isDisponible());
	    dto.setStock(bassin.getStock());

	    List<ImageDTO> imageDTOs = bassin.getImages().stream()
	        .map(image -> {
	            ImageDTO imageDTO = new ImageDTO();
	            imageDTO.setIdImage(image.getIdImage());
	            imageDTO.setName(image.getName());
	            imageDTO.setType(image.getType());
	            return imageDTO;
	        })
	        .collect(Collectors.toList());

	    dto.setImages(imageDTOs);
	    return dto;
	}
}
