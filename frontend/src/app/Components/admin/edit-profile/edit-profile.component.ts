import { Component } from '@angular/core';
import { AuthService } from '../../../Services/admin/auth.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent {
  currentPassword: string = '';
  newPassword: string = '';
  message: string = '';
  isSuccess: boolean = false;

  constructor(private authService: AuthService) {}

  onUpdatePassword() {
    const email = localStorage.getItem('email'); // Récupérer l'email depuis le localStorage
    if (!email) {
      this.message = 'Vous devez être connecté pour modifier votre mot de passe.';
      this.isSuccess = false;
      return;
    }
  
    this.authService.updatePassword(email, this.currentPassword, this.newPassword).subscribe(
      response => {
        this.message = 'Mot de passe mis à jour avec succès';
        this.isSuccess = true;
        this.currentPassword = '';
        this.newPassword = '';
      },
      error => {
        this.message = 'Échec de la mise à jour du mot de passe. Vérifiez votre mot de passe actuel.';
        this.isSuccess = false;
      }
    );
  }
}