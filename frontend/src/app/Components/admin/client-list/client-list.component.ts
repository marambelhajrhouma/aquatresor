import { Component, OnInit } from '@angular/core';
import { ClientService } from '../../../Services/admin/client.service';
import { AuthService } from '../../../Services/admin/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: any[] = [];

  constructor(private clientService: ClientService,
    private authService: AuthService, private router: Router
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }

  // Méthode pour charger la liste des clients
  loadClients(): void {
    this.clientService.getClients().subscribe(
      (data) => {
        this.clients = data;
      },
      (error) => {
        console.error('Erreur lors de la récupération des clients', error);
      }
    );
  }
   // Méthode pour gérer la déconnexion
   logout(): void {
    this.authService.logout(); // Appeler la méthode de déconnexion du service
    this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin
  }
}