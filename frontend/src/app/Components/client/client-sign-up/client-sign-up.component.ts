import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../Services/client/auth.service';

@Component({
  selector: 'app-client-sign-up',
  templateUrl: './client-sign-up.component.html',
  styleUrls: ['./client-sign-up.component.css']
})
export class ClientSignUpComponent {
  name: string = '';
  email: string = '';
  password: string = '';
  repeatPassword: string = '';
  termsAccepted: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (this.password !== this.repeatPassword) {
      alert('Les mots de passe ne correspondent pas');
      return;
    }

    const client = {
      name: this.name,
      email: this.email,
      password: this.password
    };

    this.authService.register(client).subscribe(
      response => {
        console.log('Inscription réussie', response);
        this.router.navigate(['/client/signin']); // Rediriger vers la page de connexion
      },
      error => {
        console.error('Échec de l\'inscription', error);
        alert('Erreur lors de l\'inscription');
      }
    );
  }
}