package com.example.AquaTresor.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderDemo {
    public static void main(String[] args) {
        // Crée un instance de BCryptPasswordEncoder
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        // Encode le mot de passe "nadhir123"
        String encodedPassword = encoder.encode("nadhir123");
        
        // Affiche le mot de passe encodé
        System.out.println(encodedPassword);
    }
}
