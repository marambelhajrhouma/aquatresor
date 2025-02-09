import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';
import { EditProfileComponent } from './Components/admin/edit-profile/edit-profile.component';
import { HomePageComponent } from './Components/client/home-page/home-page.component';
import { AuthGuard } from './auth.guard';
import { ClientListComponent } from './Components/admin/client-list/client-list.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { VerifEmailComponent } from '../verif-email/verif-email.component';

const routes: Routes = [
  { path: 'admin/dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'admin/edit-profile', component: EditProfileComponent, canActivate: [AuthGuard] },
  { path: 'admin/clients', component: ClientListComponent, canActivate: [AuthGuard] }, // Route pour la liste des clients
  { path: 'client/homepage', component: HomePageComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent }, // Route pour le login
  { path: 'register', component: RegisterComponent }, // Route pour l'inscription
  { path: 'verifEmail', component: VerifEmailComponent }, // Route pour la vérification de l'email
  { path: 'admin/edit-profile', component: EditProfileComponent, canActivate: [AuthGuard] },
  
  
  
  { path: 'forbidden', component: ForbiddenComponent }, // Route pour l'accès interdit
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // Redirection par défaut vers le login
  { path: '**', redirectTo: '/login' } // Redirection pour les routes inconnues
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }