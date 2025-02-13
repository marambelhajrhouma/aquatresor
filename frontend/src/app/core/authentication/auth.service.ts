import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import Swal from 'sweetalert2';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiURL: string = 'http://localhost:8002/users';
  token!: string;

  public loggedUser!: string;
  public isloggedIn: Boolean = false;
  public roles!: string[];
  private helper = new JwtHelperService();
  public regitredUser: User = new User();

  constructor(private router: Router, private http: HttpClient) {
    this.loadToken();
  }

  
  login(user: { username: string, password: string }) {
    return this.http.post<any>(`${this.apiURL}/login`, user, {
      observe: 'response',
      withCredentials: true 
    }).pipe(
      tap(response => {
        console.log('Response headers:', response.headers.keys());
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          this.saveToken(jwt);
        } else {
          console.error('Authorization header not found in response');
        }
      })
    );
  }

  saveToken(jwt: string) {
    if (jwt?.startsWith('Bearer ')) {
      jwt = jwt.substring(7);
    }
    localStorage.setItem('jwt', jwt);

    this.token = jwt;
    this.isloggedIn = true;
    this.decodeJWT(); 
  }

  decodeJWT() {
    if (!this.token) return;
    const decodedToken = this.helper.decodeToken(this.token);
    console.log('Decoded Token:', decodedToken); // Debug: Log the decoded token
    this.roles = decodedToken.roles;
    this.loggedUser = decodedToken.sub;
}

  registerUser(user: User) {
    return this.http.post<User>(`${this.apiURL}/register`, user, { observe: 'response' });
  }

   validateEmail(code: string) {
    return this.http.get<User>(`${this.apiURL}/verifyEmail/${code}`).pipe(
      tap((user) => {

        this.regitredUser = user;
        this.roles = user.roles;
      })
    );
  }

  loadToken() {
    this.token = localStorage.getItem('jwt')!;
    this.decodeJWT();
  }

  getToken(): string {
    return this.token;
  }

  logout() {
    this.loggedUser = undefined!;
    this.roles = undefined!;
    this.token = undefined!;
    this.isloggedIn = false;
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }

  isAdmin(): boolean {
    return this.roles?.includes('ADMIN') || false;
  }

  isTokenExpired(): Boolean {
    return this.helper.isTokenExpired(this.token);
  }

  setRegistredUser(user: User) {
    this.regitredUser = user;
  }

  getRegistredUser() {
    return this.regitredUser;
  }

  public get isLoggedIn(): boolean {
    return !!this.getToken();
  }

  updateProfile(username: string, newEmail?: string, newPassword?: string, currentPassword?: string) {
    const payload: any = { username };
    if (newEmail) payload.newEmail = newEmail;
    if (newPassword && currentPassword) {
      payload.newPassword = newPassword;
      payload.currentPassword = currentPassword;
    }

    console.log('Payload:', payload);  // Log pour vérifier le payload

    return this.http.put<any>(`${this.apiURL}/updateProfile`, payload, {
      headers: { Authorization: `Bearer ${this.getToken()}` }
    }).pipe(
      tap(response => {
        console.log('Server Response:', response);
      })
    );
  }




  getOnlineUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/online`);
  }

  getOfflineUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiURL}/offline`);
  }

  private setUserOnlineStatus(online: boolean): void {
    const userId = this.getUserIdFromToken(); // Récupérer l'ID de l'utilisateur depuis le token
    if (userId) {
      this.http.put(`${this.apiURL}/${userId}/online`, null, {
        params: { online: online.toString() },
      }).subscribe(
        () => console.log(`User ${userId} is now ${online ? 'online' : 'offline'}`),
        (error) => console.error('Failed to update online status:', error)
      );
    }
  }

  private getUserIdFromToken(): number | null {
    if (!this.token) return null;
    const decodedToken = this.helper.decodeToken(this.token);
    return decodedToken.userId; // Assurez-vous que le token contient l'ID de l'utilisateur
  }
  
}