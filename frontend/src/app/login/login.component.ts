import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../core/authentication/auth.service';
import { User } from '../core/models/user.model';
import { 
  SocialAuthService, 
  SocialUser, 
  GoogleLoginProvider, 
  FacebookLoginProvider 
} from '@abacritt/angularx-social-login';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {
  GoogleLoginProvider = GoogleLoginProvider;
  FacebookLoginProvider = FacebookLoginProvider;
  
  user = new User();
  err: number = 0;
  message: string = '';
  socialUser!: SocialUser;

  constructor(
    private authService: AuthService,
    private router: Router,
    public socialAuthService: SocialAuthService
  ) {}

  ngOnInit(): void {
    this.socialAuthService.authState.subscribe((user) => {
      if (user) {
        this.handleSocialLogin(user);
      }
    });
  }

  handleSocialLogin(user: SocialUser) {
    this.authService.socialLogin(user).subscribe({
      next: (response) => {
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          this.authService.saveToken(jwt);
          this.redirectBasedOnRole();
        } else {
          this.err = 1;
          this.message = 'Token not found in response';
          this.showErrorAlert();
        }
      },
      error: (err) => {
        this.err = 1;
        this.message = 'Erreur lors de la connexion sociale: ' + (err.error?.message || err.message || 'Erreur inconnue');
        this.showErrorAlert();
      },
    });
  }

  redirectBasedOnRole() {
    if (this.authService.isAdmin()) {
      this.router.navigate(['/admin/dashboard']);
    } else if (this.authService.isInstaller()) {
      this.router.navigate(['/installer-home']);
    } else if (this.authService.isUser()) {
      this.router.navigate(['/client/homepage']);
    } else {
      this.err = 1;
      this.message = 'Rôle non reconnu';
      this.showErrorAlert();
    }
  }

  onLoggedin() {
    const credentials = {
      username: this.user.username,
      password: this.user.password,
    };

    this.authService.login(credentials).subscribe({
      next: (response) => {
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          this.authService.saveToken(jwt);
          this.redirectBasedOnRole();
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
      },
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