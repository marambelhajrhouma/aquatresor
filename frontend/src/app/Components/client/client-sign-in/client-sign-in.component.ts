import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ClientAuthService } from '../../../Services/client/client-auth.service';

@Component({
  selector: 'app-client-sign-in',
  templateUrl: './client-sign-in.component.html',
  styleUrls: ['./client-sign-in.component.css']
})
export class ClientSignInComponent {
  email: string = '';
  password: string = '';

  constructor(private authService: ClientAuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.email, this.password).subscribe(
      (response: any) => {
        console.log('Connexion réussie', response);
        this.authService.setToken(response.token); // Stocker le token
        this.router.navigate(['/client/homepage']); // Rediriger vers le tableau de bord
      },
      error => {
        console.error('Échec de la connexion', error);
        alert('Identifiants incorrects');
      }
    );
  }
}