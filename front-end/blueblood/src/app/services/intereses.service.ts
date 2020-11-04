import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

const apiUrl = `${environment.apiURL}/intereses`;
const headers = new HttpHeaders({
  'Content-Type': 'application/json',
});

@Injectable({
  providedIn: 'root'
})
export class InteresesService {

  constructor(
    private _httpClient: HttpClient
  ) { }

  getIntereses(): Observable<any> {
    return this._httpClient.get(apiUrl, {headers});
  }

  postIntereses(arrayIntereses): Observable<any> {
    return this._httpClient.post(apiUrl, arrayIntereses, {headers, responseType: 'text'});
  }

  getInteresesById(id) {
    return this._httpClient.get(apiUrl + '/' + id, { headers });
  }
}
