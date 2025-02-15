package com.example.gestionbassins.entities;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Bassin {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBassin;

    private String nomBassin;
    private String description;
    
    
    @Column(name = "prix", nullable = false)
    private Double prix;
    
    
    private String materiau; // Ex: Béton, Polyester, Acier...
    private String couleur;
    private String dimensions; // Ex: "3m x 5m x 1.5m"
    
    private boolean disponible; // Indique si le bassin est en stock

    private int stock;
    
    @ManyToOne //optional
	private Categorie categorie;
    
    /*@OneToOne
	private Image image;*/
	
	@OneToMany(mappedBy = "bassin", fetch = FetchType.EAGER)
	//@JsonManagedReference 
    private List<Image> images;
	
	
	private String imagePath;


    //Constructor
    public Bassin(String nomBassin, String description, double prix, String materiau, String couleur,
			String dimensions, boolean disponible, int stock, Categorie categorie) {
		super();
		this.nomBassin = nomBassin;
		this.description = description;
		this.prix = prix;
		this.materiau = materiau;
		this.couleur = couleur;
		this.dimensions = dimensions;
		this.disponible = disponible;
		this.stock = stock;
		this.categorie = categorie;
	}

    
    
    

    
/* A FAIRE APRES
    @OneToMany(mappedBy = "bassin", cascade = CascadeType.ALL)
    private List<Avis> avisClients; // Liste des avis des clients

    @OneToMany(mappedBy = "bassin", cascade = CascadeType.ALL)
    private List<Installation> installations; // Liste des installations réalisées

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie; // Catégorie du bassin

    @ElementCollection
    private List<String> images; // Liste d'URLs des images du bassin

    @ManyToMany
    @JoinTable(
        name = "bassin_promotion",
        joinColumns = @JoinColumn(name = "bassin_id"),
        inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
  
    private List<Promotion> promotions; // Promotions appliquées
	
	//La partie de l'image à ajouter
	@OneToOne
	private Image image;
	
	@OneToMany(mappedBy = "evenement")
    private List<Image> images;
*/
}

