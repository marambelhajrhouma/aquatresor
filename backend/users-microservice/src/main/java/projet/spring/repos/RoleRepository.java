package projet.spring.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.spring.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	
	   Optional<Role> findByRole(String role);
}
