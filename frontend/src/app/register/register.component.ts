import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../Services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent implements OnInit {
  myForm!: FormGroup;
  err!: string;
  loading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.myForm = this.formBuilder.group(
      {
        username: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]],
      },
      { validators: this.passwordsMatchValidator }
    );
  }

  passwordsMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onRegister() {
    if (this.myForm.invalid) {
      return;
    }
  
    this.loading = true;
    this.authService.registerUser(this.myForm.value).subscribe({
      next: (response) => {
        this.loading = false;
  
        Swal.fire({
          icon: 'success',
          title: 'Inscription réussie',
          text: 'Votre compte a été créé avec succès',
          confirmButtonText: 'OK',
        }).then(() => {
          this.router.navigate(['/verifEmail']);
        });
      },
      error: (err) => {
        this.loading = false;
        this.err = err.error?.errorCode === 'USER_EMAIL_ALREADY_EXISTS'
          ? 'Cet email est déjà utilisé'
          : 'Une erreur est survenue. Veuillez réessayer.';
  
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
