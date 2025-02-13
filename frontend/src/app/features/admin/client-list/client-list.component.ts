import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/authentication/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: any[] = [];
  filteredClients: any[] = []; // Liste filtrée des clients
  showOnlineOnly: boolean = false;
  searchName: string = ''; // Variable pour stocker la recherche par nom
  isLoading: boolean = true; // Variable pour gérer le chargement


  constructor(
    private authService: AuthService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadClients();
  }
 
  loadClients(): void {
      this.isLoading = true; // Activer le chargement
      const token = this.authService.getToken();
      const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
  
      this.http.get<any[]>('http://localhost:8002/users/all', { headers })
          .subscribe(
              (data) => {
                  this.clients = data;
                  this.filteredClients = data;
                  this.isLoading = false; // Désactiver le chargement
              },
              (error) => {
                  console.error('Error fetching clients:', error);
                  alert('Failed to load clients. Please try again.');
                  this.isLoading = false; // Désactiver le chargement en cas d'erreur
              }
          );
  }
 /* 
toggleOnlineStatus(userId: number, online: boolean): void {
  this.authService.setUserOnlineStatus(userId, online).subscribe(
      () => this.loadClients(), // Recharger la liste après succès
      (error) => console.error('Erreur de mise à jour du statut en ligne:', error)
  );
}
*/

  toggleFilter(): void {
    this.showOnlineOnly = !this.showOnlineOnly;
    this.filterClientsByName(); // Appliquer le filtre après avoir changé l'état du filtre en ligne
  }

  filterClientsByName(): void {
    this.filteredClients = this.clients.filter(client =>
        (!this.searchName || client.username.toLowerCase().includes(this.searchName.toLowerCase()))
        && (!this.showOnlineOnly || client.online)
    );
}

  logout(): void {
    this.authService.logout(); // Appeler la méthode de déconnexion du service
    this.router.navigate(['/admin/signin']); // Rediriger vers la page de connexion admin
  }
}