import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PostDTO } from '../models/DTOs/postDTO';
import { Comment } from '../models/comments';
import { LikeDetail } from '../models/DTOs/likeDetail';
import { PostReportado } from '../models/DTOs/postReportado';



const apiUrl = `${environment.apiURL}/posts`;
const headers = new HttpHeaders({
  'Content-Type': 'application/json'
});

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(
    private _httpClient: HttpClient
  ) { }

  getAll(userID): Observable<PostDTO[]> {
    return this._httpClient.get<PostDTO[]>(`${apiUrl}?usuario_id=${userID}`, { headers});
  }

  post(base64: any, id: number, mensaje:  string) {
    const params = {
      mensaje,
      usuario_id: id,
      path_media: base64
    };
    return this._httpClient.post(apiUrl, params, { headers });
  }

  postOtroPerfil(base64: any, id: number, mensaje: string, destino_id: number, tipo_pulso: string, etiquetas: any[]) {
    const params = {
      mensaje,
      usuario_id: id,
      destino_id,
      path_media: base64,
      tipo_pulso,
      etiquetas
    };
    return this._httpClient.post(apiUrl, params, { headers });
  }

  deletePost(postID) {
    return this._httpClient.delete(`${apiUrl}/${postID}`, { headers });
  }

  addComment(comment: Comment) {
    const url = `${environment.apiURL}/comentarios`;
    return this._httpClient.post(url, comment, {headers});
  }

  postByUser(profilelID: number, userID: number) {
    return this._httpClient.get(`${environment.apiURL}/usuarios/${profilelID}/posts?usuario_id=${userID}`);
  }

  getPost(postID, userID): Observable<PostDTO> {
    return this._httpClient.get<PostDTO>(`${apiUrl}?usuario_id=${userID}&post_id=${postID}`, { headers});
  }

  getLikesDetail(postID, userID) {
    return this._httpClient.get<LikeDetail[]>(`${apiUrl}/${postID}/detail?usuario_id=${userID}`, { headers});
  }

  topPulsos() {
    return this._httpClient.get(apiUrl + '/top/pulsos', {headers});
  }

  topGlobulos() {
    return this._httpClient.get(apiUrl + '/top/globulos', {headers});
  }

  getReportados() {
    return this._httpClient.get<PostReportado[]>(`${apiUrl}/reportados`, {headers});
  }

  reportarPost(postID, userID, problema) {
    const fecha = new Date();
    const params = {
      post_id: postID,
      usuario_id: userID,
      motivo: problema,
      fecha
    };
    return this._httpClient.post(`${apiUrl}/reportados`, params, {headers});
  }

  rechazarReporte(postID, userID) {
    const fecha = new Date();
    const params = {
      post_id: postID,
      usuario_id: userID,
      motivo: 'rechazar',
      fecha
    };
    return this._httpClient.put(`${apiUrl}/reportados`, params, {headers});
  }

  aceptarReporte(postID, userID) {
    const fecha = new Date();
    const params = {
      post_id: postID,
      usuario_id: userID,
      motivo: 'aceptar',
      fecha
    };
    return this._httpClient.put(`${apiUrl}/reportados`, params, {headers});
  }

}
