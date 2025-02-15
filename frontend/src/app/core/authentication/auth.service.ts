import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import Swal from 'sweetalert2';
import { catchError, tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';
import { SocialUser } from '@abacritt/angularx-social-login';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiURL: string = 'http://localhost:8002/users';
  token!: string;

  public loggedUser!: string;
  public isloggedIn: Boolean = false;
  public roles!: string[];
  public regitredUser: User = new User();

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private router: Router,
    private http: HttpClient,
    private jwtHelper: JwtHelperService // Ensure this is correctly injected
  ) {
    this.loadToken();
  }

  login(user: { username: string, password: string }) {
    return this.http.post<any>(`${this.apiURL}/login`, user, {
      observe: 'response',
      withCredentials: true,
    }).pipe(
      tap((response) => {
        console.log('Response headers:', response.headers.keys());
        const jwt = response.headers.get('Authorization');
        if (jwt) {
          this.saveToken(jwt);
        } else {
          console.error('Authorization header not found in response');
          throw new Error('Authorization header not found in response');
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
    const decodedToken = this.jwtHelper.decodeToken(this.token);
    console.log('Decoded Token:', decodedToken);
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
    if (isPlatformBrowser(this.platformId)) {
      this.token = localStorage.getItem('jwt')!;
      this.decodeJWT();
    }
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

 
 
  isTokenExpired(): boolean {
    const token = localStorage.getItem('jwt');
    return this.jwtHelper.isTokenExpired(token);
  }



  
  setRegistredUser(user: User) {
    this.regitredUser = user;
  }

  getRegistredUser() {
    return this.regitredUser;
  }

  updateProfile(username: string, newEmail?: string, newPassword?: string, currentPassword?: string) {
    const payload: any = { username };
    if (newEmail) payload.newEmail = newEmail;
    if (newPassword && currentPassword) {
      payload.newPassword = newPassword;
      payload.currentPassword = currentPassword;
    }

    console.log('Payload:', payload); // Log to verify the payload

    return this.http.put<any>(`${this.apiURL}/updateProfile`, payload, {
      headers: { Authorization: `Bearer ${this.getToken()}` },
    }).pipe(
      tap((response) => {
        console.log('Server Response:', response);
      })
    );
  }
 
 
  getAllClients(): Observable<User[]> {
    const token = this.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<User[]>(`${this.apiURL}/all`, { headers }).pipe(
      catchError((error) => {
        console.error('Error fetching clients:', error);
        throw new Error('Failed to load clients. Please try again.');
      })
    );
  }

  isAdmin(): boolean {
    return this.roles?.includes('ADMIN') || false;
  }

  isInstaller(): boolean {
    return this.roles?.includes('INSTALLATEUR') || false;
  }

  isUser(): boolean {
    return this.roles?.includes('USER') || false;
  }

  socialLogin(user: SocialUser): Observable<HttpResponse<any>> {
    return this.http.post<any>(`${this.apiURL}/social-login`, user, {
      observe: 'response',
    });
  }

  requestResetPassword(email: string): Observable<any> {
    return this.http.post(`${this.apiURL}/request-reset-password`, { email });
  }

  validateCode(email: string, code: string): Observable<any> {
    return this.http.post(`${this.apiURL}/validate-code`, { email, code });
  }

  resetPassword(email: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.apiURL}/reset-password`, { email, newPassword });
  }

  get isLoggedIn(): boolean {
    return !!this.token && !this.jwtHelper.isTokenExpired(this.token); // Use jwtHelper
  }

  hasAnyRole(requiredRoles: string[]): boolean {
    return requiredRoles.some((role) => this.roles.includes(role));
  }

  getRequiredRoles(): string[] {
    return this.roles; // Replace with logic to fetch roles from route data if needed
  }


  
}