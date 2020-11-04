import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const apiUrl = `${environment.apiURL}/globulos`;
const headers = new HttpHeaders({
  'Content-Type': 'application/json'
});

@Injectable({
  providedIn: 'root'
})
export class GlobuloService {

  constructor(
    private _httpClient: HttpClient
  ) { }

  globulo(post_id, usuario_id): Observable<any> {
    const params = {
      post_id,
      usuario_id
    };
    return this._httpClient.post(apiUrl, params, {headers});
  }
}
