package projet.spring.dto;

public class UpdateProfileRequest {
    private String username;
    private String newEmail;
    private String newPassword;
    private String currentPassword;  // Ajout du champ manquant

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {  // Ajout du getter
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {  // Ajout du setter
        this.currentPassword = currentPassword;
    }
}
