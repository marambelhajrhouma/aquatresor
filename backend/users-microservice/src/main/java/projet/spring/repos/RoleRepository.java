package projet.spring.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import projet.spring.entities.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	
	
	Role findByRole(String role);
}
