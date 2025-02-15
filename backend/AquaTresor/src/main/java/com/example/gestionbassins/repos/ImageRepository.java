package com.example.gestionbassins.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.gestionbassins.entities.Image;
import com.example.gestionbassins.entities.ImageProjection;

public interface ImageRepository extends JpaRepository<Image, Long> {
	/*
	@Query("SELECT i FROM Image i JOIN i.bassin ing WHERE ing.id = :id")
	List<Image> findImagesByBassinId(@Param("id") Long id);*/
	
	/*
	@Query("SELECT i.idImage as idImage, i.name as name, i.type as type FROM Image i WHERE i.bassin.idBassin = :idB")
    List<ImageProjection> findImagesByBassinId(@Param("idB") Long idB);*/
	
	@Query("SELECT i.idImage as idImage, i.name as name, i.type as type FROM Image i WHERE i.bassin.idBassin = :idB")
	List<ImageProjection> findImagesByBassinId(@Param("idB") Long idB);
	}

