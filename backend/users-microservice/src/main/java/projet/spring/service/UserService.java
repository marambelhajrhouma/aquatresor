package projet.spring.service;

import projet.spring.entities.Role;
import projet.spring.entities.User;
import projet.spring.service.register.RegistrationRequest;

import java.util.*;


public interface UserService {
	User saveUser(User user);
	User findUserByUsername (String username);
	Role addRole(Role role);
	User addRoleToUser(String username, String rolename);
	
	User registerUser(RegistrationRequest request);
	
	public void sendEmailUser(User u, String code);
	public User validateToken(String code);

}
