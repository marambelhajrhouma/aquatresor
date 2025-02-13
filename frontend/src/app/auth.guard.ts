import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './core/authentication/auth.service';



@Injectable({
    providedIn: 'root'
  })
  export class AuthGuard implements CanActivate {
  
    constructor(private authService: AuthService, private router: Router) { }
  
    canActivate(): boolean {
      if (this.authService.getToken() && !this.authService.isTokenExpired()) {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    }

    
    
  }