package com.example.AquaTresor.services;

import com.example.AquaTresor.entities.Admin;
import com.example.AquaTresor.repositories.AdminRepository;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class AdminService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + email));
        
        return User.withUsername(admin.getEmail())
            .password(admin.getPassword())
            .authorities("ADMIN") // Important: donner l'autorité "ADMIN"
            .build();
    }


    public void saveAdmin(Admin admin) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(admin.getPassword())); // Hacher le mot de passe
        adminRepository.save(admin);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public boolean updatePassword(String email, String currentPassword, String newPassword) {
        Optional<Admin> adminOptional = findByEmail(email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(currentPassword, admin.getPassword())) {
                admin.setPassword(encoder.encode(newPassword));
                adminRepository.save(admin);
                System.out.println("Password updated successfully for email: " + email);
                return true;
            } else {
                System.out.println("Current password does not match for email: " + email);
            }
        } else {
            System.out.println("Admin not found with email: " + email);
        }
        return false;
    }
    
    
    
}