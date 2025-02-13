package projet.spring.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import projet.spring.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRoles_Role(String role); // Récupérer les utilisateurs par rôle
    List<User> findByOnline(Boolean online); // Récupérer les utilisateurs en ligne ou hors ligne
}