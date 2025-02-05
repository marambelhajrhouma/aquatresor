import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http'; 
import { AppComponent } from './app.component';

import { ApiService } from './Services/api.service';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';
import { ServerModule } from '@angular/platform-server';
import { SignInComponent } from './Components/admin/sign-in/sign-in.component';

import { FormsModule } from '@angular/forms';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { EditProfileComponent } from './Components/admin/edit-profile/edit-profile.component';
import { ClientSignInComponent } from './Components/client/client-sign-in/client-sign-in.component';
import { ClientSignUpComponent } from './Components/client/client-sign-up/client-sign-up.component';


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    SignInComponent,
    EditProfileComponent,

    ClientSignInComponent,
    ClientSignUpComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule ,
    ServerModule,
    FormsModule 
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
