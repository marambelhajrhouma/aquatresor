import { Component, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../Services/admin/auth.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  email: string = '';
  password: string = '';
  focusedField: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID
  ) {}

  onSubmit() {
    console.log('Tentative de connexion avec:', this.email, this.password);
    this.authService.login(this.email, this.password).subscribe(
      (response: any) => {
        console.log('Login successful', response);
        if (isPlatformBrowser(this.platformId)) { // Vérifiez si vous êtes dans un navigateur
          if (response.body && response.body.token) {
            localStorage.setItem('token', response.body.token);
            localStorage.setItem('email', this.email);
            this.router.navigate(['/admin/dashboard']);
          } else {
            console.error('Token not found in response');
            this.errorMessage = 'Identifiants incorrects';
          }
        }
      },
      error => {
        console.error('Login failed', error);
        this.errorMessage = 'Identifiants incorrects';
      }
    );
  }

  // Activation du focus
  setFocus(field: string) {
    this.focusedField = field;
  }

  // Désactivation du focus
  removeFocus(field: string) {
    if (field === 'email' && !this.email) {
      this.focusedField = '';
    }
    if (field === 'password' && !this.password) {
      this.focusedField = '';
    }
  }
}