import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientAuthService } from '../../../Services/client/client-auth.service';

@Component({
  selector: 'app-client-sign-up',
  templateUrl: './client-sign-up.component.html',
  styleUrls: ['./client-sign-up.component.css']
})
export class ClientSignUpComponent {
  fullName: string = '';
  email: string = '';
  password: string = '';
  confirmPassword: string = '';
  phoneNumber: string = '';
  address: string = '';
  city: string = '';
  zipCode: string = '';
  country: string = '';
  termsAccepted: boolean = false;

  constructor(private authService: ClientAuthService, private router: Router) {}

  onSubmit() {
    // Vérifier que les mots de passe correspondent
    if (this.password !== this.confirmPassword) {
      alert('Les mots de passe ne correspondent pas');
      return;
    }

    // Vérifier que les conditions sont acceptées
    if (!this.termsAccepted) {
      alert('Vous devez accepter les conditions d\'utilisation');
      return;
    }

    // Créer l'objet client à envoyer au backend
    const client = {
      fullName: this.fullName,
      email: this.email,
      password: this.password,
      phoneNumber: this.phoneNumber,
      address: this.address,
      city: this.city,
      zipCode: this.zipCode,
      country: this.country
    };

    // Envoyer la requête d'inscription
    this.authService.register(client).subscribe(
      (response: any) => {
        console.log('Inscription réussie', response);
        alert(response.message); // Afficher le message de succès
        this.router.navigate(['/client/signin']); // Rediriger vers la page de connexion
      },
      error => {
        console.error('Échec de l\'inscription', error);
        alert('Erreur lors de l\'inscription');
      }
    );
  }
}