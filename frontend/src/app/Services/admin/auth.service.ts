import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090/api/admin'; 

  constructor(private http: HttpClient) {}

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
}