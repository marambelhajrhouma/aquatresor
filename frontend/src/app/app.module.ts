import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { ApiService } from './Services/api.service';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';
import { ServerModule } from '@angular/platform-server';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { EditProfileComponent } from './Components/admin/edit-profile/edit-profile.component';
import { HomePageComponent } from './Components/client/home-page/home-page.component';
import { ClientListComponent } from './Components/admin/client-list/client-list.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { VerifEmailComponent } from '../verif-email/verif-email.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ServerModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    ApiService,
    provideHttpClient(withFetch()),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }