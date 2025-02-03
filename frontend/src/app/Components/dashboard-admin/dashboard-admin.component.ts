import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../Services/api.service';

@Component({
  selector: 'app-dashboard-admin',
  templateUrl: './dashboard-admin.component.html',
})
export class DashboardAdminComponent implements OnInit {

  message: string = '';

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    this.apiService.getHello().subscribe(
      data => {
        console.log('Message reçu:', data);  // Affiche le message reçu du backend
        this.message = data;
      },
      error => {
        console.error('Erreur:', error);  // Capture et affiche les erreurs
      }
    );
  }
}
