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

  
  onSubmit() {

    console.log('Tentative de connexion avec:', this.email, this.password);
    this.authService.login(this.email, this.password).subscribe(
      (response: any) => {
        console.log('Login successful', response);
        if (response.body && response.body.token) {
          localStorage.setItem('token', response.body.token);
          this.router.navigate(['/admin/dashboard']);
        } else {
          console.error('Token not found in response');
          alert('Identifiants incorrects');
        }
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