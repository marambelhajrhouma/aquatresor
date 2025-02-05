import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';
import { EditProfileComponent } from './Components/admin/edit-profile/edit-profile.component';
import { SignInComponent } from './Components/admin/sign-in/sign-in.component';
import { ClientSignUpComponent } from './Components/client/client-sign-up/client-sign-up.component';
import { ClientSignInComponent } from './Components/client/client-sign-in/client-sign-in.component';
import { HomePageComponent } from './Components/client/home-page/home-page.component';

const routes: Routes = [
   { path: 'admin/dashboard', component: DashboardComponent },
    { path: 'admin/signin', component: SignInComponent },
    { path: 'admin/edit-profile', component: EditProfileComponent },
    { path: 'client/signin', component: ClientSignInComponent },
    { path: 'client/signup', component: ClientSignUpComponent  },
    {path:'client/homepage',component:HomePageComponent}
 


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
