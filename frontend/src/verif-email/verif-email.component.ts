import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../app/core/authentication/auth.service';
import Swal from 'sweetalert2';
import { User } from '../app/core/models/user.model';

@Component({
  selector: 'app-verif-email',
  templateUrl: './verif-email.component.html',
})
export class VerifEmailComponent implements OnInit {

  code: string = "";
  user: User = new User();
  err = "";

  constructor(private route: ActivatedRoute, private authService: AuthService,
    private router: Router
  ) { }


  ngOnInit(): void {
    this.user = this.authService.regitredUser;
  }



  onValidateEmail() {
    this.authService.validateEmail(this.code).subscribe({
      next: (res) => {
        alert("Email verification successful");
  
        // Debug: Log the user roles
        console.log('User roles:', this.authService.roles);
  
        // Redirect based on user role
        if (this.authService.isAdmin()) {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.router.navigate(['/client/homepage']);
        }
      },
      error: (err: any) => {
        if (err.error.errorCode === "INVALID_TOKEN") {
          this.err = "Votre code n'est pas valide !";
        } else if (err.error.errorCode === "EXPIRED_TOKEN") {
          this.err = "Votre code a expiré !";
        } else {
          this.err = "Une erreur est survenue. Veuillez réessayer.";
        }
  
        // Show SweetAlert2 error message
        Swal.fire({
          icon: 'error',
          title: 'Erreur',
          text: this.err,
          confirmButtonText: 'OK',
        });
      },
    });
  }

}