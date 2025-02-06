import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { AuthService } from '../../../Services/admin/auth.service';
import { isPlatformBrowser } from '@angular/common';

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

  constructor(
    private authService: AuthService,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID
  ) {}

  onUpdatePassword() {
    if (isPlatformBrowser(this.platformId)) {
        const email = localStorage.getItem('email');
        if (!email) {
            this.message = 'Vous devez être connecté pour modifier votre mot de passe.';
            this.isSuccess = false;
            return;
        }

        console.log('Updating password with:', { email, currentPassword: this.currentPassword, newPassword: this.newPassword });

        this.authService.updatePassword(email, this.currentPassword, this.newPassword).subscribe(
            (response: any) => {
                console.log('Password update successful', response);
                this.message = response.message || 'Mot de passe mis à jour avec succès';
                this.isSuccess = true;
                this.currentPassword = '';
                this.newPassword = '';
            },
            error => {
                console.error('Password update failed', error);
                this.message = error.error?.message || 'Échec de la mise à jour du mot de passe. Vérifiez votre mot de passe actuel.';
                this.isSuccess = false;
            }
        );
    }
}


}