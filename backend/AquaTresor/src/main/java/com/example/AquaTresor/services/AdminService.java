package com.example.AquaTresor.services;

import com.example.AquaTresor.entities.Admin;
import com.example.AquaTresor.repositories.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Rechercher l'administrateur par email
        Admin admin = adminRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Retourner un utilisateur avec son email, mot de passe et rôle
        return User.builder()
                .username(admin.getEmail())
                .password(admin.getPassword())  // Le mot de passe est déjà encodé
                .roles("ADMIN")
                .build();
    }
}
