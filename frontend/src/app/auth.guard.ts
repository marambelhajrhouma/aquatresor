import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './Services/admin/auth.service';
import { ClientAuthService } from './Services/client/client-auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private authService: AuthService, // Service d'authentification admin
    private clientAuthService: ClientAuthService, // Service d'authentification client
    private router: Router
  ) {}

  canActivate(): boolean {
    // Vérifiez si l'utilisateur est authentifié (admin ou client)
    const isAdminAuthenticated = this.authService.getToken() !== null;
    const isClientAuthenticated = this.clientAuthService.getToken() !== null;
  
    if (isAdminAuthenticated) {
      // Si l'utilisateur est un admin, autoriser l'accès
      return true;
    } else if (isClientAuthenticated) {
      // Si l'utilisateur est un client, autoriser l'accès
      return true;
    } else {
      // Si l'utilisateur n'est pas authentifié, rediriger vers la page de connexion appropriée
      const currentRoute = this.router.url; // Récupérer la route actuelle
  
      if (currentRoute.startsWith('/admin')) {
        this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin
      } else if (currentRoute.startsWith('/client')) {
        this.router.navigate(['/client/signin']); // Rediriger vers la page de connexion client
      } else {
        this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin par défaut
      }
  
      return false;
    }
  }
}
