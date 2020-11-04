import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

const apiUrl = `${environment.apiURL}/pulsos`;
const headers = new HttpHeaders({
  'Content-Type': 'application/json'
});

@Injectable({
  providedIn: 'root'
})
export class PulsoService {

  constructor(
    private _httpClient: HttpClient
  ) { }

  pulsos(post_id, usuario_id, tipo): Observable<any> {
    const params = {
      post_id,
      usuario_id,
      tipo
    };
    return this._httpClient.post(apiUrl, params, {headers, responseType: 'text'});
  }
}
