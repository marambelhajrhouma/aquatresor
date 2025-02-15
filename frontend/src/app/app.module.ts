import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BassinComponent } from './features/admin/bassin/bassin/bassin.component';

// Importations pour l'authentification sociale
import { SocialLoginModule, SocialAuthServiceConfig } from '@abacritt/angularx-social-login';
import { GoogleLoginProvider, FacebookLoginProvider } from '@abacritt/angularx-social-login';
// Composants
import { AppComponent } from './app.component';

import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { EditProfileComponent } from './features/admin/edit-profile/edit-profile.component';
import { HomePageComponent } from './features/client/home-page/home-page.component';
import { ClientListComponent } from './features/admin/client-list/client-list.component';
import { VerifEmailComponent } from '../verif-email/verif-email.component';
import { SendInstallerInvitationComponent } from './features/admin/send-installer-invitation/send-installer-invitation.component';
import { InstallerRegisterComponent } from './features/installer/installer-register/installer-register.component';
import { InstallerHomeComponent } from './features/installer/installer-home/installer-home.component';

// Services
import { ApiService } from './core/services/api.service';
import { AuthService } from './core/authentication/auth.service';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { RequestResetPasswordComponent } from './request-reset-password/request-reset-password.component';
import { ValidateCodeComponent } from './validate-code/validate-code.component';
import { JwtHelperService } from '@auth0/angular-jwt';
import { LoginComponent } from './login/login.component';
import { JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

export function tokenGetter() {
  return localStorage.getItem('jwt');
}


@NgModule({
  declarations: [
    AppComponent,
    
    DashboardComponent,
    EditProfileComponent,
    HomePageComponent,
    ClientListComponent,
    LoginComponent,
    RegisterComponent,
    VerifEmailComponent,
    ForbiddenComponent,
    SendInstallerInvitationComponent,
    InstallerRegisterComponent,
    InstallerHomeComponent,
    ResetPasswordComponent,
    RequestResetPasswordComponent,
    ValidateCodeComponent,
 BassinComponent,

  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'my-angular-app' }),
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    CommonModule,
  
      SocialLoginModule, 
      JwtModule.forRoot({
        config: {
          tokenGetter: tokenGetter, // Function to get the token
          allowedDomains: ['localhost:8002'], 
          disallowedRoutes: ['http://localhost:8002/users/login'], 
        },
      }),
  ],
  providers: [
    JwtHelperService, 
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              '133465243893-e12ji07goq4n2r1dcclmkc291qtonkd5.apps.googleusercontent.com' // Votre ID client Google
            ),
          },
          {
            id: FacebookLoginProvider.PROVIDER_ID,
            provider: new FacebookLoginProvider('904207095126084', {
              scope: 'email,public_profile', // Permissions
            }),
          },
        ],
      } as SocialAuthServiceConfig,
    },
    provideHttpClient(withFetch()), 
    provideAnimationsAsync(), 
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}