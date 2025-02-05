package com.example.AquaTresor.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName; // Nom complet du client

    @Column(nullable = false)
    private String phoneNumber; // Numéro de téléphone

    @Column(nullable = false)
    private String address; // Adresse complète

    @Column(nullable = false)
    private String city; // Ville

    @Column(nullable = false)
    private String zipCode; // Code postal

    @Column(nullable = false)
    private String country; // Pays
}
