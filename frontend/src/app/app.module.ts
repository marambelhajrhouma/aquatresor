import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';  // Importer HttpClientModule
import { AppComponent } from './app.component';
import { DashboardAdminComponent } from './Components/dashboard-admin/dashboard-admin.component';
import { ApiService } from './Services/api.service';
import { DashboardComponent } from './Components/admin/dashboard/dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardAdminComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule 
  ],
  providers: [ApiService],  
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA] 

})
export class AppModule { }
