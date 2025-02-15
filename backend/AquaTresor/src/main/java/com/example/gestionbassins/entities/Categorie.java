package com.example.gestionbassins.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategorie;

    private String nomCategorie;
    private String description;

	
  //Association one to many
  	@OneToMany (mappedBy = "categorie") //si on enlève cette ligne ça sera une 3 eme table qui constitue la relation entre les 2 tables 
  	@JsonIgnore //pour ne pas envoyer la liste des evenements
  	private List<Bassin> Bassins;



  	
    // Constructors
  	public Categorie(String nomCategorie, String description, List<Bassin> bassins) {
  		super();
  		this.nomCategorie = nomCategorie;
  		this.description = description;
  		Bassins = bassins;
  	}
    //getters and setters

}




