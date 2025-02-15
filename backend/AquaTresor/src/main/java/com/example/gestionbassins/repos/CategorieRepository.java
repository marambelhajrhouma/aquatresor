package com.example.gestionbassins.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.gestionbassins.entities.Categorie;

import java.util.List;

@RepositoryRestResource(path="categorie")
@CrossOrigin(origins="http://localhost:4200/") //pour autoriser angular
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

	/*
    // Trouver les thèmes par nom
    List<Theme> findByNomTheme(String nom);

    // Recherche partielle du nom du thème (avec un nom qui contient une chaîne de caractères)
    List<Theme> findByNomThemeContains(String nom);

    // Query personnalisée pour rechercher les événements liés à un thème spécifique (selon le nom de l'événement)
    @Query("select p from Evenement p where p.nomEvenement like %?1%")
    List<Evenement> findEvenementsByNomEvenement(String nom);
*/
	
	
}
