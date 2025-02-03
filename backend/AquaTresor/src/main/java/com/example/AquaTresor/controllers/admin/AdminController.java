package com.example.AquaTresor.controllers.admin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200") // Autoriser les requêtes depuis Angular
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String getAdminDashboard() {
        return "Bienvenue sur le tableau de bord Admin";
    }
}
