import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ClientAuthService } from '../../../Services/client/client-auth.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  constructor(private authService: ClientAuthService, private router: Router) {}

  ngOnInit(): void {
    // Vérifiez si le client est authentifié
    if (!this.authService.getToken()) {
      this.router.navigate(['/client/signin']); // Rediriger vers la page de connexion client
    }
  }
}