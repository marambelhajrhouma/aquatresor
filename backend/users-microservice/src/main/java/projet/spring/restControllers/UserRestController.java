package projet.spring.restControllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import projet.spring.dto.UpdateProfileRequest;
import projet.spring.entities.User;
import projet.spring.repos.UserRepository;
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

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRep.findAll();
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
}