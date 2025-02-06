import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router'; // Importez le Router

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api/admin'; 

  constructor(
    private http: HttpClient,
    private router: Router // Injectez le Router
  ) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    console.log('Sending login request with:', body); // Vérifiez les données envoyées
    return this.http.post(`${this.apiUrl}/login`, body, { observe: 'response' });
  }

  updatePassword(email: string, currentPassword: string, newPassword: string): Observable<any> {
    const body = { email, currentPassword, newPassword };
    console.log('Sending update password request with:', body);
    return this.http.post(`${this.apiUrl}/update-password`, body);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  
  logout(): void {
    localStorage.removeItem('token'); // Supprimer le token
    this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin
  }
}