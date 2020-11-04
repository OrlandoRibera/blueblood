import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';


const apiUrl = `${environment.apiURL}/usuarios`;
const headers = new HttpHeaders({
  'Content-Type': 'application/json'
});

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private isUserLoggedIn: boolean;
  public usserLogged: User;

  constructor(
    private _httpClient: HttpClient
  ) {}

  getAll() {
    return this._httpClient.get<User[]>(apiUrl, {headers});
  }

  getUsersToSearch() {
    return this._httpClient.get<User[]>(`${apiUrl}/list`, {headers});
  }

  registerUser(user: User): Observable<any> {
    const params = {
      codigo_id: user.id,
      nombre: user.name,
      nick: user.name,
      correo: user.email,
      sucursal_id: user.branch_id,
      area_id: user.area_id,
      cargo_id: user.position_id,
      ciudad_id: user.city_id,
      dtnacimiento: user.birthdate,
      dtcontrato: user.contractDate,
      jefe_id: user.boss_id,
      spassword: user.password
    };

    return this._httpClient.post(apiUrl, params, {headers});
  }

  subirFoto(foto: ArrayBuffer | string, id, nombreUsuario) {
    const params = {foto, id, nick: nombreUsuario};
    return this._httpClient.post(apiUrl + '/foto', params, {headers, responseType: 'text'});
  }

  loginUser(user: string, password: string) {
    return this._httpClient.post(apiUrl + '/login?correo=' + user + '&spassword=' + password, {headers});
  }

  setUserLoggedIn(user: User) {
    this.isUserLoggedIn = true;
    this.usserLogged = user;
    sessionStorage.setItem('currentUser', JSON.stringify(user));
  }

  getUserLoggedIn(): User {
    if (sessionStorage.getItem('currentUser') == null ||
      sessionStorage.getItem('currentUser') === undefined) {
      return null;
    }
    return JSON.parse(sessionStorage.getItem('currentUser'));
  }

  logOut() {
    sessionStorage.removeItem('currentUser');
  }

  getUser() {
    const userLogged: any = this.getUserLoggedIn();
    return this._httpClient.get(apiUrl + '/' + userLogged.codigo_id, {headers});
  }

  getUserById(idPerfilVisitado, idUsuarioSesion) {
    // return this._httpClient.get(`${apiUrl}/${idPerfilVisitado}?usuario=${idUsuarioSesion}`, {headers});
    return this._httpClient.get(`${apiUrl}/${idPerfilVisitado}`, {headers});
  }

  verificarJefe(idPerfilVisitado, idUsuarioSesion) {
    return this._httpClient.get(`${apiUrl}/${idPerfilVisitado}/jefe/${idUsuarioSesion}`, {headers});
  }

  getUsersByName(query: string): Observable<any> {
    return this._httpClient.get<any[]>(`${apiUrl}?query=${query}`, {headers});
  }

  async getUsersByNameLocalStorage(query: string) {
    let listUsers =  await this.getUsersLocalStorage();
    // Sino pilla usuarios hace la peticion de nuevo
    if (listUsers === null || listUsers === undefined) { 
      this.getUsersToSearch().subscribe(
        result => {
          this.setUsersLocalStorage(result);
        },
        err => console.log(err)
      );
    }
    
    
    const result = listUsers.filter(persona => {
      const regex = new RegExp(query, 'gi');
      return persona.nick.match(regex);
    });
    return result;
  }

  async getUsersLocalStorage() {
    const listUsers = localStorage.getItem('listUsers');
    
    if (listUsers == null || listUsers === undefined) {
      const resultadoAPI = await this.getUsersToSearch().toPromise();
      this.setUsersLocalStorage(resultadoAPI);
      return resultadoAPI;
    }
    return JSON.parse(listUsers);
  }

  setUsersLocalStorage(listUsers) {
    localStorage.setItem('listUsers', JSON.stringify(listUsers));
  }
}
