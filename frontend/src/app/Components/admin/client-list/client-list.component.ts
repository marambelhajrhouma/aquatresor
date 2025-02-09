import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../Services/auth.service';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: any[] = [];

  constructor(
    private authService: AuthService, private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  // Méthode pour charger la liste des clients
  loadClients(): void {
    
  }
   // Méthode pour gérer la déconnexion
   logout(): void {
    this.authService.logout(); // Appeler la méthode de déconnexion du service
    this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin
  }
}