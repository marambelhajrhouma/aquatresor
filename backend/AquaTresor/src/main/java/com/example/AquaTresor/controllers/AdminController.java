package com.example.AquaTresor.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.AquaTresor.entities.Admin;
import com.example.AquaTresor.security.SecParams;
import com.example.AquaTresor.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List; // ✅ Ajout de l'import manquant
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        System.out.println("Registering admin: " + admin.getEmail());
        adminService.saveAdmin(admin);
        return ResponseEntity.ok("Admin enregistré avec succès");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        Admin admin = adminService.findByEmail(loginRequest.get("email")).orElse(null);
        
        if (admin != null && new BCryptPasswordEncoder().matches(loginRequest.get("password"), admin.getPassword())) {
            String jwt = JWT.create()
                    .withSubject(admin.getEmail())
                    .withArrayClaim("roles", new String[]{"ADMIN"}) // ✅ Correction ici
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecParams.EXP_TIME))
                    .sign(Algorithm.HMAC256(SecParams.SECRET));

            return ResponseEntity.ok(Map.of("token", jwt));
        }
        return ResponseEntity.status(401).body(Map.of("message", "Identifiants incorrects"));
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> passwordRequest) {
        System.out.println("Received update password request: " + passwordRequest);

        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        System.out.println("Authenticated user email: " + email);

        // Vérifier que l'email de la requête correspond à l'utilisateur authentifié
        if (!email.equals(passwordRequest.get("email"))) {
            return ResponseEntity.status(403).body("Accès interdit : vous ne pouvez pas modifier le mot de passe d'un autre utilisateur");
        }

        // Mettre à jour le mot de passe
        boolean isUpdated = adminService.updatePassword(
                passwordRequest.get("email"),
                passwordRequest.get("currentPassword"),
                passwordRequest.get("newPassword")
        );
        if (isUpdated) {
            return ResponseEntity.ok("Mot de passe mis à jour");
        }
        return ResponseEntity.status(400).body("Mot de passe incorrect");
    }
}
