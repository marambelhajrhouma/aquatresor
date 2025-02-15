import { Component } from '@angular/core';
import { Bassin } from '../../../../core/models/bassin.models';
import { BassinService } from '../../../../core/services/bassin.service';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/authentication/auth.service';

@Component({
  selector: 'app-bassin',
  templateUrl: './bassin.component.html',
  styleUrl: './bassin.component.css'
})
export class BassinComponent {
  bassins: Bassin[] = [];

  apiurl:string='http://localhost:8089/aquatresor/api';

  constructor(private bassinService: BassinService, private router: Router
    , private authService: AuthService,
  ) {
  
   
  }

  ngOnInit(): void {
    this.chargerBassin();
    if (!this.authService.isLoggedIn) {
      this.router.navigate(['/admin/signin']);
    }
  }

 

  chargerBassin() {
    this.bassinService.listeBassin().subscribe(bs => {
      console.log(bs);
      this.bassins = bs;
      this.bassins.forEach((b) => {
        
     if (b.images && b.images.length > 0 && b.images[0]) {
          b.imageStr = 'data:' + b.images[0].type + ';base64,' + b.images[0].image;
        } else {
          // Set a default image or placeholder if no image is available
          b.imageStr = 'assets/default-image.png'; // or any default image path
          console.log(`No image found for bassin: ${b.nomBassin}`);
        }
      });
    });
  }


  logout(): void {
    this.authService.logout();
    this.router.navigate(['/admin/signin']);
  }

}
