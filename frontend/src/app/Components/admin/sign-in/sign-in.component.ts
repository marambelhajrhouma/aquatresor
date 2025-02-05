import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../Services/admin/auth.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  email: string = '';
  password: string = '';
  focusedField: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  // Soumission du formulaire
  onSubmit() {
    console.log('Tentative de connexion avec:', this.email, this.password);
    this.authService.login(this.email, this.password).subscribe(
      response => {
        console.log('Login successful', response);
        // Optionally store the token in local storage or a service if needed
        this.router.navigate(['/admin/dashboard']);
      },
      error => {
        console.error('Login failed', error);
        alert('Identifiants incorrects');
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