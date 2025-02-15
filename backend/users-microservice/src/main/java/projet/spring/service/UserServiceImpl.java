package projet.spring.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projet.spring.entities.Role;
import projet.spring.entities.User;
import projet.spring.repos.RoleRepository;
import projet.spring.repos.UserRepository;
import projet.spring.service.exceptions.EmailAlreadyExistsException;
import projet.spring.service.exceptions.ExpiredTokenException;
import projet.spring.service.exceptions.InvalidTokenException;
import projet.spring.service.register.RegistrationRequest;
import projet.spring.service.register.VerificationToken;
import projet.spring.service.register.VerificationTokenRepository;
import projet.spring.util.EmailSender;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	    @Autowired
	    private UserRepository userRepository;

    private final UserRepository userRep;
    private final RoleRepository roleRep;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationTokenRepository verificationTokenRepo;
  
    @Autowired
    private EmailSender emailSender;
 
    @Autowired
    public UserServiceImpl(UserRepository userRep, RoleRepository roleRep, BCryptPasswordEncoder bCryptPasswordEncoder,
                          VerificationTokenRepository verificationTokenRepo, EmailSender emailSender) {
        this.userRep = userRep;
        this.roleRep = roleRep;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.emailSender = emailSender;
    }
    
    @Override
    public User saveUser(User user) {
        if (!user.getPassword().startsWith("$2a$")) { // Check if already encoded (BCrypt)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        return userRep.save(user);
    }

    @Override
    public User addRoleToUser(String username, String rolename) {
        User usr = userRep.findByUsername(username);
        if (usr == null) {
            throw new RuntimeException("User not found: " + username);
        }

        Optional<Role> roleOptional = roleRep.findByRole(rolename);
        if (roleOptional.isEmpty()) {
            throw new RuntimeException("Role not found: " + rolename);
        }

        Role r = roleOptional.get();
        usr.getRoles().add(r);
        return usr;
    }

    @Override
    public Role addRole(Role role) {
        return roleRep.save(role);
    }
    
    
    public void createInstallerRole() {
        if (roleRep.findByRole("INSTALLATEUR").isEmpty()) {
            Role installerRole = new Role("INSTALLATEUR");
            roleRep.save(installerRole);
        }
    }
    
    

    @Override
    public User findUserByUsername(String username) {
        User user = userRep.findByUsername(username);
        if (user != null) {
            System.out.println("User found: " + user.getUsername());
            System.out.println("User roles: " + user.getRoles());
        } else {
            System.out.println("User not found: " + username);
        }
        return user;
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optionalUser = userRep.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email déjà existant!");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        newUser.setEnabled(false);

        userRep.save(newUser);

        // Assign USER role by default
        Optional<Role> userRoleOptional = roleRep.findByRole("USER");
        if (userRoleOptional.isEmpty()) {
            throw new RuntimeException("Role USER not found!");
        }
        Role userRole = userRoleOptional.get();

        // Use a Set<Role> instead of List<Role>
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);

        // If the user is an admin, assign the ADMIN role
        if (request.getUsername().equals("nadhir")) {
            Optional<Role> adminRoleOptional = roleRep.findByRole("ADMIN");
            if (adminRoleOptional.isEmpty()) {
                throw new RuntimeException("Role ADMIN not found!");
            }
            Role adminRole = adminRoleOptional.get();
            roles.add(adminRole);
        }

        // Set the roles as a Set<Role>
        newUser.setRoles(roles);

        userRep.save(newUser);

        String code = this.generateCode();
        VerificationToken token = new VerificationToken(code, newUser);
        verificationTokenRepo.save(token);

        sendEmailUser(newUser, token.getToken());

        return userRep.save(newUser);
    }
    
    
    
    private String generateCode() {
        Random random = new Random();
        Integer code = 100000 + random.nextInt(900000);
        return code.toString();
    }

    @Override
    public void sendEmailUser(User u, String code) {
        String emailBody = "Bonjour " + "<h1>" + u.getUsername() + "</h1>" +
                " Votre code de validation est " + "<h1>" + code + "</h1>";

        emailSender.sendEmail(u.getEmail(), emailBody);
    }

    @Override
    public User validateToken(String code) {
        VerificationToken token = verificationTokenRepo.findByToken(code);

        if (token == null) {
            throw new InvalidTokenException("Invalid Token !!!!!!!");
        }

        User user = token.getUser();

        Calendar calendar = Calendar.getInstance();

        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            verificationTokenRepo.delete(token);
            throw new ExpiredTokenException("expired Token");
        }

        user.setEnabled(true);
        userRep.save(user);
        return user;
    }
    
    @Override
    public boolean updateUserProfile(String username, String newEmail, String newPassword, String currentPassword) {
        User user = userRep.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: " + username);
        }

        // Log pour vérifier les valeurs des champs
        System.out.println("Username: " + username);
        System.out.println("Current Password (provided): " + currentPassword);
        System.out.println("Current Password (stored): " + user.getPassword());

        // Vérifier si le mot de passe actuel est correct
        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            System.out.println("Current password does not match.");
            return false; // Mot de passe incorrect
        }

        if (newEmail != null && !newEmail.isEmpty()) {
            user.setEmail(newEmail);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        }

        userRep.save(user);
        System.out.println("Profile updated successfully.");
        return true; // Mise à jour réussie
    }


   
    
    /***********************************/
    @Override
    public void sendInstallerInvitation(String email) {
        String token = UUID.randomUUID().toString();
        String registrationUrl = "http://localhost:4200/installer-register?token=" + token;
        String emailContent = "Cliquez sur ce lien pour vous inscrire : " + registrationUrl;
        emailSender.sendEmail(email, emailContent);
    }

    @Override
    public User registerInstaller(RegistrationRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setEnabled(true);

        Role installerRole = roleRep.findByRole("INSTALLATEUR")
                .orElseThrow(() -> new RuntimeException("Role INSTALLATEUR not found"));
        user.setRoles(Set.of(installerRole));

        return userRep.save(user); // Utilisez userRep ici
    }


    @Override
    public User findUserByEmail(String email) {
        Optional<User> user = userRep.findByEmail(email);
        return user.orElse(null);
    }
   
    
    @Override
    public String generateResetToken(String email) {
        User user = userRep.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email: " + email));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token); // Utilisez le setter pour définir le token
        userRep.save(user);

        return token;
    }
    
    @Override
    public String generateValidationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000); // Génère un code à 4 chiffres
        return String.valueOf(code);
    }

    @Override
    public boolean validateCode(String email, String code) {
        User user = userRep.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email: " + email));

        return code.equals(user.getValidationCode());
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRep.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'email: " + email));

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setResetToken(null); // Réinitialiser le token
        user.setValidationCode(null); // Réinitialiser le code de validation
        userRep.save(user);
    }
    
}