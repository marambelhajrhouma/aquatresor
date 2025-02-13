import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../../../core/authentication/auth.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  editProfileForm: FormGroup;
  message: string = '';
  isSuccess: boolean = false;
  isEditing: boolean = false; // Initialize as boolean

  constructor(
    private fb: FormBuilder,
    public authService: AuthService, // Assurez-vous que authService est public
    private router: Router
  ) {
    this.editProfileForm = this.fb.group({
      // Déclaration classique du FormControl pour "username"
      username: [{ value: this.authService.loggedUser, disabled: true }, [Validators.required]],
      newEmail: ['', [Validators.email]], // Email facultatif
      currentPassword: ['', [Validators.required]],
      newPassword: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {}

  enableEdit() {
    this.isEditing = true;
  }

  disableEdit() {
    this.isEditing = false;
  }

  // Méthode de mise à jour du profil
  onUpdateProfile() {
    if (this.editProfileForm.invalid) {
      return;
    }

    const { newEmail, currentPassword, newPassword } = this.editProfileForm.value;
    const username = this.authService.loggedUser;

    console.log('Form Data:', { username, newEmail, currentPassword, newPassword });  // Log pour vérifier les valeurs du formulaire

    this.authService.updateProfile(username, newEmail, newPassword, currentPassword).subscribe({
      next: (response) => {
        console.log('Update Profile Response:', response);  // Log pour vérifier la réponse
        this.isSuccess = true;
        this.message = response.message || "Profil mis à jour avec succès.";  // Utilisez le message du serveur
        Swal.fire('Succès', this.message, 'success');
        this.editProfileForm.reset();
      },
      error: (err) => {
        console.error('Update Profile Error:', err);  // Log pour vérifier l'erreur
        this.isSuccess = false;
        this.message = err.error?.message || "Erreur lors de la mise à jour du profil.";  // Utilisez le message d'erreur du serveur
        Swal.fire('Erreur', this.message, 'error');
      }
    });
  }
}