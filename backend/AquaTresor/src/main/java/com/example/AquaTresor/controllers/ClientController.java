package com.example.AquaTresor.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.AquaTresor.entities.Client;
import com.example.AquaTresor.security.SecParams;
import com.example.AquaTresor.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/client")
@CrossOrigin("*")  // ✅ Permettre les requêtes Cross-Origin
public class ClientController {
    private final ClientService clientService;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientController(ClientService clientService, BCryptPasswordEncoder passwordEncoder) {
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

  
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Client client) {
        if (client.getFullName() == null || client.getEmail() == null || client.getPassword() == null ||
            client.getPhoneNumber() == null || client.getAddress() == null || client.getCity() == null ||
            client.getZipCode() == null || client.getCountry() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Tous les champs sont obligatoires"));
        }

        // Hacher le mot de passe avant d'enregistrer
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientService.saveClient(client);

        // Renvoyer une réponse JSON
        return ResponseEntity.ok(Map.of("message", "Client enregistré avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        Client client = clientService.findClientByEmail(loginRequest.get("email"));
        if (client != null && passwordEncoder.matches(loginRequest.get("password"), client.getPassword())) {
            // Générer un token JWT
            String jwt = JWT.create()
                    .withSubject(client.getEmail())
                    .withClaim("roles", "CLIENT")  // ✅ Correction : utiliser une simple chaîne "CLIENT"
                    .withExpiresAt(new Date(System.currentTimeMillis() + SecParams.EXP_TIME)) 
                    .sign(Algorithm.HMAC256(SecParams.SECRET));

            // Retourner le token et les infos du client
            Map<String, Object> response = new HashMap<>();
            response.put("token", SecParams.PREFIX + jwt); // ✅ Préfixe "Bearer "
            response.put("fullName", client.getFullName());
            response.put("email", client.getEmail());

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Identifiants incorrects");
    }
}
