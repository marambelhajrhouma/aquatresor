package projet.spring.restControllers;


import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projet.spring.dto.UpdateProfileRequest;
import projet.spring.entities.Role;
import projet.spring.entities.SocialUser;
import projet.spring.entities.User;
import projet.spring.repos.UserRepository;
import projet.spring.security.JwtUtil;
import projet.spring.service.UserService;
import projet.spring.service.register.RegistrationRequest;



@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/users")
public class UserRestController {

    @Autowired
    UserRepository userRep;

    @Autowired
    UserService userService;
    
    @Autowired
    JwtUtil jwtUtil;
    
    


    @GetMapping("/all")
    public List<User> getAllUsers() {
        System.out.println("Fetching all users with role USER");
        List<User> users = userRep.findByRoles_Role("USER");
        System.out.println("Users found: " + users.size());
        return users;
    }
    
    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest request) {
        return userService.registerUser(request);
    }

    @GetMapping("/verifyEmail/{token}")
    public User verifyEmail(@PathVariable("token") String token) {
        return userService.validateToken(token);
    }
    
    @PutMapping("/updateProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request) {
        boolean isUpdated = userService.updateUserProfile(request.getUsername(), request.getNewEmail(), request.getNewPassword(), request.getCurrentPassword());
        
        if (isUpdated) {
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Profil mis à jour avec succès."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Mot de passe actuel incorrect."));
        }
    }
    
    
    

    
    /*************************************/
    
    @PostMapping("/send-installer-invitation")
    public ResponseEntity<?> sendInstallerInvitation(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        userService.sendInstallerInvitation(email);
        return ResponseEntity.ok().body(Map.of("message", "Invitation envoyée avec succès !"));
    }

    @PostMapping("/register-installer")
    public ResponseEntity<?> registerInstaller(@RequestBody RegistrationRequest request) {
        System.out.println("Received request: " + request);
        try {
            User user = userService.registerInstaller(request);
            System.out.println("User registered: " + user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/social-login")
    public ResponseEntity<?> socialLogin(@RequestBody SocialUser socialUser) {
        // Vérifiez si l'utilisateur existe déjà
        User user = userService.findUserByEmail(socialUser.getEmail());
        
        if (user == null) {
            // Créez un nouvel utilisateur avec le rôle USER par défaut
            user = new User();
            user.setEmail(socialUser.getEmail());
            user.setUsername(socialUser.getName());
            user.setEnabled(true);
            
            // Créer un Set de roles au lieu d'une List
            Role userRole = new Role("USER");
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);
            
            userService.saveUser(user);
        }
        
        // Générez un JWT et renvoyez-le
        String jwt = jwtUtil.generateToken(user);
        return ResponseEntity.ok()
            .header("Authorization", "Bearer " + jwt)
            .body(user);
    }
    
    
    
    
    @PostMapping("/request-reset-password")
    public ResponseEntity<?> requestResetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Vérifier si l'email existe
        User user = userService.findUserByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Email non trouvé."));
        }

        // Générer un code à 4 chiffres
        String validationCode = userService.generateValidationCode();
        user.setValidationCode(validationCode);
        userRep.save(user);

        // Envoyer le code par email
        String emailContent = "Votre code de validation est : " + validationCode;
        userService.sendEmailUser(user, emailContent);

        return ResponseEntity.ok().body(Map.of("message", "Un code de validation a été envoyé à votre email."));
    }

    @PostMapping("/validate-code")
    public ResponseEntity<?> validateCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        if (userService.validateCode(email, code)) {
            return ResponseEntity.ok().body(Map.of("message", "Code valide."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Code invalide."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        userService.resetPassword(email, newPassword);
        return ResponseEntity.ok().body(Map.of("message", "Mot de passe réinitialisé avec succès."));
    }
    
    
}
