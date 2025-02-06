import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';
import { Router } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api/admin'; 

  constructor(
    private http: HttpClient,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object // Inject PLATFORM_ID
  ) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    console.log('Sending login request with:', body);
    return this.http.post(`${this.apiUrl}/login`, body, { observe: 'response' }).pipe(
      tap((response: any) => {
        if (isPlatformBrowser(this.platformId)) { // Vérifiez si vous êtes dans un navigateur
          if (response.body && response.body.token) {
            localStorage.setItem('token', response.body.token);
            localStorage.setItem('email', email);
          }
        }
      })
    );
  }

  updatePassword(email: string, currentPassword: string, newPassword: string): Observable<any> {
    const body = { email, currentPassword, newPassword };
    const headers = new HttpHeaders({
        'Authorization': `Bearer ${this.getToken()}`,
        'Content-Type': 'application/json'
    });
    return this.http.post(`${this.apiUrl}/update-password`, body, { headers, observe: 'response' }).pipe(
        map((response: { status: number; body: any; }) => {
            if (response.status === 200) {
                return response.body; // Renvoyer le corps de la réponse
            } else {
                throw new Error('Échec de la mise à jour du mot de passe');
            }
        })
    );
}

  getToken(): string | null {
    if (isPlatformBrowser(this.platformId)) { // Vérifiez si vous êtes dans un navigateur
      return localStorage.getItem('token');
    }
    return null;
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) { // Vérifiez si vous êtes dans un navigateur
      localStorage.removeItem('token');
      localStorage.removeItem('email');
    }
    this.router.navigate(['/admin/signin']);
  }
}