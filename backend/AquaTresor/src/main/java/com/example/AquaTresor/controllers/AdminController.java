package com.example.AquaTresor.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.AquaTresor.entities.Admin;
import com.example.AquaTresor.entities.Client;
import com.example.AquaTresor.security.SecParams;
import com.example.AquaTresor.services.AdminService;
import com.example.AquaTresor.services.ClientService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	    private final ClientService clientService;

	    public AdminController(AdminService adminService, ClientService clientService) {
	        this.adminService = adminService;
	        this.clientService = clientService;
	    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        System.out.println("Registering admin: " + admin.getEmail());
        adminService.saveAdmin(admin);
        return ResponseEntity.ok("Admin enregistré avec succès");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        System.out.println("Received login request with email: " + email + " and password: " + password);

        Admin admin = adminService.findByEmail(email).orElse(null);

        if (admin != null) {
            System.out.println("Admin found in DB with email: " + admin.getEmail());
            System.out.println("Stored hashed password: " + admin.getPassword());

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(password, admin.getPassword());

            System.out.println("Password matches: " + passwordMatches);

            if (passwordMatches) {
            	String jwt = JWT.create()
            		    .withSubject(admin.getEmail())
            		    .withArrayClaim("roles", new String[]{"ROLE_ADMIN"}) // Ajouter "ROLE_" devant "ADMIN"
            		    .withExpiresAt(new Date(System.currentTimeMillis() + SecParams.EXP_TIME))
            		    .sign(Algorithm.HMAC256(SecParams.SECRET));
            	// Renvoyer le token dans la réponse
                return ResponseEntity.ok(Map.of("token", jwt));
            }
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
            return ResponseEntity.status(403).body(Map.of("message", "Accès interdit : vous ne pouvez pas modifier le mot de passe d'un autre utilisateur"));
        }

        // Mettre à jour le mot de passe
        boolean isUpdated = adminService.updatePassword(
                passwordRequest.get("email"),
                passwordRequest.get("currentPassword"),
                passwordRequest.get("newPassword")
        );
        if (isUpdated) {
            return ResponseEntity.ok(Map.of("message", "Mot de passe mis à jour"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "Mot de passe incorrect"));
    }
    

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }
    
}
