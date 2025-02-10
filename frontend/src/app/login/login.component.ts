import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../model/user.model';
import { AuthService } from '../Services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  user = new User(); // Initialize user object
  err: number = 0; // Error flag
  message: string = ''; // Error message

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  onLoggedin() {
    const credentials = {
      username: this.user.username,
      password: this.user.password
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          this.authService.saveToken(jwt);

          // Debug: Log the roles
          console.log('User roles:', this.authService.roles);

          // Redirect based on roles
          if (this.authService.isAdmin()) {
            this.router.navigate(['/admin/dashboard']);
          } else {
            this.router.navigate(['/client/homepage']);
          }
        } else {
          this.err = 1;
          this.message = 'Token not found in response';
          this.showErrorAlert();
        }
      },
      error: (err) => {
        this.err = 1;
        if (err.error && err.error.errorCause === 'disabled') {
          this.message = "L'utilisateur est désactivé !";
        } else {
          this.message = 'Nom d\'utilisateur ou mot de passe incorrect';
        }
        this.showErrorAlert();
      }
    });
  }

  showErrorAlert() {
    Swal.fire({
      icon: 'error',
      title: 'Erreur',
      text: this.message,
      confirmButtonText: 'OK',
    });
  }
}