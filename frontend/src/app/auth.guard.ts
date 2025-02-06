import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './Services/admin/auth.service';
import { ClientAuthService } from './Services/client/client-auth.service';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService, // Service d'authentification admin
    private clientAuthService: ClientAuthService, // Service d'authentification client
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID pour vérifier l'environnement
  ) {}

  canActivate(): boolean {
    if (isPlatformBrowser(this.platformId)) { // Vérifiez si vous êtes dans un navigateur
      // Vérifiez si l'utilisateur est authentifié (admin ou client)
      const isAdminAuthenticated = this.authService.getToken() !== null;
      const isClientAuthenticated = this.clientAuthService.getToken() !== null;

      if (isAdminAuthenticated || isClientAuthenticated) {
        return true; // Autoriser l'accès si un utilisateur est authentifié
      } else {
        // Rediriger vers la page de connexion appropriée
        const currentRoute = this.router.url;

        if (currentRoute.startsWith('/client')) {
          this.router.navigate(['/client/signin']);
        } else {
          this.router.navigate(['/admin/signin']);
        }

        return false;
      }
    } else {
      // Si vous êtes côté serveur, redirigez vers la page de connexion par défaut
      this.router.navigate(['/admin/signin']);
      return false;
    }
  }
}