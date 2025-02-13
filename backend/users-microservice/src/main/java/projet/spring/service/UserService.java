package projet.spring.service;

import projet.spring.entities.Role;
import projet.spring.entities.User;
import projet.spring.service.register.RegistrationRequest;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findUserByUsername(String username);
    Role addRole(Role role);
    User addRoleToUser(String username, String rolename);
    User registerUser(RegistrationRequest request);
    void sendEmailUser(User u, String code);
    User validateToken(String code);
    boolean updateUserProfile(String username, String newEmail, String newPassword, String currentPassword);


    List<User> getOnlineUsers();
    List<User> getOfflineUsers();
    void setUserOnlineStatus(Long userId, boolean online);
}