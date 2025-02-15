import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from './auth.guard';

import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { VerifEmailComponent } from '../verif-email/verif-email.component';
import { DashboardComponent } from './features/admin/dashboard/dashboard.component';
import { EditProfileComponent } from './features/admin/edit-profile/edit-profile.component';
import { HomePageComponent } from './features/client/home-page/home-page.component';
import { ClientListComponent } from './features/admin/client-list/client-list.component';
import { InstallerHomeComponent } from './features/installer/installer-home/installer-home.component';
import { InstallerRegisterComponent } from './features/installer/installer-register/installer-register.component';
import { SendInstallerInvitationComponent } from './features/admin/send-installer-invitation/send-installer-invitation.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { RequestResetPasswordComponent } from './request-reset-password/request-reset-password.component';
import { ValidateCodeComponent } from './validate-code/validate-code.component';
import { BassinComponent } from './features/admin/bassin/bassin/bassin.component';

const routes: Routes = [
  // Admin Routes (Only accessible by users with the 'ADMIN' role)
  { 
    path: 'admin/dashboard', 
    component: DashboardComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'admin/edit-profile', 
    component: EditProfileComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'admin/clients', 
    component: ClientListComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'admin/send-installer-invitation', 
    component: SendInstallerInvitationComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] } 
  },
  { 
    path: 'admin/bassin', 
    component: BassinComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['ADMIN'] } 
  },

  // Client Routes (Only accessible by authenticated users)
  { 
    path: 'client/homepage', 
    component: HomePageComponent, 
    canActivate: [AuthGuard] 
  },

  // Installer Routes (Only accessible by authenticated users with the 'INSTALLATEUR' role)
  { 
    path: 'installer-home', 
    component: InstallerHomeComponent, 
    canActivate: [AuthGuard], 
    data: { roles: ['INSTALLATEUR'] } 
  },
  { 
    path: 'installer-register', 
    component: InstallerRegisterComponent 
  },

  // Authentication Routes (Public routes)
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'verifEmail', component: VerifEmailComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'request-reset-password', component: RequestResetPasswordComponent },
  { path: 'validate-code', component: ValidateCodeComponent },

  // Forbidden Route (For unauthorized access)
  { path: 'forbidden', component: ForbiddenComponent },

  // Default and Fallback Routes
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirect to login by default
  { path: '**', redirectTo: '/login' } // Redirect to login for unknown routes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }