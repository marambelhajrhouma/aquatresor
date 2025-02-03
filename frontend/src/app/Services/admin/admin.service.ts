import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = 'http://localhost:8090/api/admin';

  constructor(private http: HttpClient) {}

  getDashboardMessage(): Observable<string> {
    return this.http.get(this.baseUrl + '/dashboard', { responseType: 'text' });
  }
}
