package com.example.AquaTresor.controllers;

import com.example.AquaTresor.entities.Admin;
import com.example.AquaTresor.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Page de login (connexion)
    @GetMapping("/admin/signin")
    public String loginPage() {
        return "admin/signin";
    }

    // Page d'inscription (création automatique si pas encore existant)
    @GetMapping("/admin/signup")
    public String signupPage(Model model) {
        // Vérifiez si un admin existe déjà
        if (adminRepository.findByEmail("nadhir.mazlout@gmail.com").isEmpty()) {
            Admin admin = new Admin();
            admin.setEmail("nadhir.mazlout@gmail.com");
            // Utilisation du PasswordEncoder pour encoder le mot de passe
            admin.setPassword(passwordEncoder.encode("nadhir123"));
            admin.setName("Nadhir Mazlout");

            adminRepository.save(admin); // Sauvegarder dans la base de données
        }
        return "admin/signup";
    }

    // Modifier profil admin
    @PostMapping("/admin/updateProfile")
    public String updateProfile(Admin updatedAdmin) {
        Admin admin = adminRepository.findByEmail(updatedAdmin.getEmail()).orElseThrow();
        admin.setName(updatedAdmin.getName());
        adminRepository.save(admin);
        return "redirect:/admin/dashboard"; // Redirige vers le dashboard après mise à jour
    }
}
