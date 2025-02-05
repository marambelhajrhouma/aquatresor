import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api/client'; // URL de votre backend

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    const body = { email, password };
    return this.http.post(`${this.apiUrl}/login`, body);
  }

  register(client: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, client);
  }

  // Stocker le token dans le localStorage
  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  // Récupérer le token du localStorage
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // Supprimer le token du localStorage
  logout(): void {
    localStorage.removeItem('token');
  }
}