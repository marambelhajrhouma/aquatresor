import { Injectable } from '@angular/core';
import { User } from '../model/user.model';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { JwtHelperService } from '@auth0/angular-jwt';
import Swal from 'sweetalert2';
import { tap } from 'rxjs/operators'; // Import tap

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  apiURL: string = 'http://localhost:8002'; // URL du microservice users
  token!: string;

  public loggedUser!: string;
  public isloggedIn: Boolean = false;
  public roles!: string[];
  private helper = new JwtHelperService();
  public regitredUser: User = new User();

  constructor(private router: Router, private http: HttpClient) {
    this.loadToken(); // Charger le token au démarrage
  }
  
  // Connexion pour les administrateurs et les clients
  login(user: { username: string, password: string }) {
    return this.http.post<any>(`${this.apiURL}/login`, user, {
      observe: 'response',
      withCredentials: true // Include credentials
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
    this.decodeJWT(); // Decode the token to extract roles
  }
  
  decodeJWT() {
    if (!this.token) return;
    const decodedToken = this.helper.decodeToken(this.token);
    console.log('Decoded Token:', decodedToken); // Debug: Log the decoded token
    this.roles = decodedToken.roles;
    this.loggedUser = decodedToken.sub;
  }
  
 
  // Enregistrement uniquement pour les utilisateurs (clients)
  registerUser(user: User) {
    return this.http.post<User>(this.apiURL + '/register', user, { observe: 'response' });
  }
  // Validation de l'email
  validateEmail(code: string) {
    return this.http.get<User>(this.apiURL + '/verifyEmail/' + code).pipe(
      tap((user) => {
        // Save the user details in the AuthService
        this.regitredUser = user;
        this.roles = user.roles;
      })
    );
  }
  // Chargement du token depuis le localStorage
  loadToken() {
    this.token = localStorage.getItem('jwt')!;
    this.decodeJWT();
  }

  // Récupération du token
  getToken(): string {
    return this.token;
  }

  // Déconnexion
  logout() {
    this.loggedUser = undefined!;
    this.roles = undefined!;
    this.token = undefined!;
    this.isloggedIn = false;
    localStorage.removeItem('jwt');
    this.router.navigate(['/login']);
  }

  // Vérification si l'utilisateur est un administrateur
  isAdmin(): boolean {
    return this.roles?.includes('ADMIN') || false;
  }
  // Vérification si le token est expiré
  isTokenExpired(): Boolean {
    return this.helper.isTokenExpired(this.token);
  }

  // Définir l'utilisateur enregistré
  setRegistredUser(user: User) {
    this.regitredUser = user;
  }

  // Récupérer l'utilisateur enregistré
  getRegistredUser() {
    return this.regitredUser;
  }
  public get isLoggedIn(): boolean {
    return !!this.getToken(); // Check if a token exists
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
      console.log('Server Response:', response);  // Log pour vérifier la réponse du serveur
    })
  );
}



}