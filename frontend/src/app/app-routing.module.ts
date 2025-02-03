import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';
import { SignInComponent } from './Components/admin/sign-in/sign-in.component';

const routes: Routes = [
  { path: 'admin/dashboard', component: DashboardComponent } ,
  { path: 'admin/signin', component: SignInComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
