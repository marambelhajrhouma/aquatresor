import { Injectable } from '@angular/core';

import { Bassin } from '../models/bassin.models';
import { Categorie } from '../models/categorie.models';
import { Image } from '../models/image.models';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BassinService {

  /** Les variables **/
  bassins: Bassin[]=[];

  categories: Categorie[]=[];
  
  apiURL: string ='http://localhost:8089/aquatresor/api';
  //URL de spring Data REST
  apiURLCategorie: string='http://localhost:8089/aquatresor/api/categories'; 

  /** Constructor **/
  constructor(private http: HttpClient) { }

  /** les fonctions **/

  /********************* */
    /** BASSIN **/
      //la liste des bassins
  listeBassin(): Observable<Bassin[]>{ 
    return this.http.get<Bassin[]>(this.apiURL + "/all"); 
  }

      // ajouter un bassin
  ajouterBassin( b: Bassin){ 
    this.bassins.push(b); 
  }

  /*ajouterEvenement(ev: Evenement): Observable<Evenement> {
    let jwt = this.authService.getToken();
    jwt = "Bearer " + jwt;
    let httpHeaders = new HttpHeaders({ "Authorization": jwt })
    return this.http.post<Evenement>(apiURL + "/addev", ev, { headers: httpHeaders });
  }*/

  /********************* */
    /** Image **/
  uploadImage(file: File, filename: string): Observable<Image> {
    const imageFormData = new FormData();
    imageFormData.append('image', file, filename);
    const url = `${this.apiURL + '/image/upload'}`;
    return this.http.post<Image>(url, imageFormData);
  }

  loadImage(id: number): Observable<Image> {
    const url = `${this.apiURL + '/image/get/info'}/${id}`;
    return this.http.get<Image>(url);
  }

  uploadImageBassin(file: File, filename: string, idBassin: number): Observable<any> { 
    const imageFormData = new FormData(); 
    imageFormData.append('image', file, filename); 
    const url = `${this.apiURL + '/image/uploadImageB'}/${idBassin}`; 
    
    return this.http.post(url, imageFormData); 
  }

  /********************* */
    /** CATEGORIE **/
    /*listeThemes(): Observable<ThemeWrapper> {
      let jwt = this.authService.getToken();
      jwt = "Bearer " + jwt;
      let httpHeaders = new HttpHeaders({ "Authorization": jwt })
      return this.http.get<ThemeWrapper>(this.apiURLTheme, { headers: httpHeaders });
    }*/
    
  listeCategorie():Observable<Categorie[]>{
    return this.http.get<Categorie[]>(this.apiURL+"/theme");
  }

}
