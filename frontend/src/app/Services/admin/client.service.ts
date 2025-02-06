import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = 'http://localhost:8090'; // URL de base de l'API backend

  constructor(private http: HttpClient) {}

  // Méthode pour récupérer la liste des clients
  getClients(): Observable<any[]> {
    const token = localStorage.getItem('token');
    console.log('Token:', token); // Vérifier le token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(`${this.apiUrl}/api/admin/users`, { headers });
  }

  
}