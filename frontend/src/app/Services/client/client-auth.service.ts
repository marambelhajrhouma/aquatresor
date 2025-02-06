import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router'; // Importez le Router

@Injectable({
  providedIn: 'root'
})
export class ClientAuthService {
  private apiUrl = 'http://localhost:8090/api/client'; // URL de votre backend

  constructor(
    private http: HttpClient,
    private router: Router // Injectez le Router
  ) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    console.log('Sending login request with:', body);
    return this.http.post(`${this.apiUrl}/login`, body, { observe: 'response' });
  }

  register(client: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, client, { responseType: 'json' });
  }

  // Stocker le token dans le localStorage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Récupérer le token du localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/client/signin']); // Rediriger vers la page de connexion client
  }
}