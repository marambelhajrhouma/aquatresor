import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './core/authentication/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    // Check if the user is logged in
    if (!this.authService.isLoggedIn) {
      this.router.navigate(['/login']); // Redirect to login if not authenticated
      return false;
    }

    // Check if the user is trying to access an admin route
    const isAdminRoute = route.data['roles']?.includes('ADMIN');

    // If it's an admin route and the user is not an admin, redirect to forbidden
    if (isAdminRoute && !this.authService.isAdmin()) {
      this.router.navigate(['/forbidden']); // Redirect to forbidden page
      setTimeout(() => {
        this.router.navigate(['/client/homepage']); // Redirect to client homepage after showing forbidden page
      }, 3000); // Wait for 3 seconds before redirecting
      return false;
    }

    // Check if the user has the required role to access the route
    const requiredRoles = route.data['roles'] as Array<string>; // Get roles from route data
    if (requiredRoles && !this.authService.hasAnyRole(requiredRoles)) {
      this.router.navigate(['/forbidden']); // Redirect to forbidden page if role is not allowed
      return false;
    }

    return true; // Allow access if the user is authenticated and has the required role
  }
}