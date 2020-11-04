import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/services/post.service';
import { InteresesService } from '../../services/intereses.service';
import { MatDialog } from '@angular/material/dialog';
import { PostDTO } from '../../models/DTOs/postDTO';

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {

  user: any;
  name_display: string;
  public intereses = new Array();
  hay_intereses = false;
  barra_pulsos: number;
  id: number;
  verMas = false;
  userLogged_id: number;
  esMiPerfil = false;
  postsList: PostDTO[] = [];
  esJefe = false;

  constructor(
    private _userService: UserService,
    private _postService: PostService,
    private _rute: ActivatedRoute,
    private _interesesService: InteresesService,
    private _router: Router,
    public _dialog: MatDialog,
  ) { }

  ngOnInit() {
    this.user = this._userService.getUserLoggedIn();
    this.userLogged_id = this.user.codigo_id;
    this._rute.params.subscribe(
      parametros => {
        this.id = parametros.id;
        if (this.id !== undefined) {
          this.verificarJefe(this.id, this.userLogged_id);
          this._userService.getUserById(this.id, this.userLogged_id).subscribe(
            (result: any) => {
              if (this.user.codigo_id === result.codigo_id) {
                this.esMiPerfil = true;
              }
              this.user = result;
              this.barra_pulsos = this.user.total_pulsos > 10 ? 10 : this.user.total_pulsos;
              this.getIntereses();
              this.getPosts();
            }
          );
        } else {
          this.getUser();
          this.esMiPerfil = true;
        }
      }
    );
  }

  verificarJefe(idPerfil, UsuarioSesion) {
    this._userService.verificarJefe(idPerfil, UsuarioSesion)
      .subscribe(
        (result: boolean) => {
          this.esJefe = result;
        }, err => {
          console.log(err);
        }
      );
  }
  getIntereses() {
    this._interesesService.getInteresesById(this.user.codigo_id).subscribe(
      (result: []) => {
        this.intereses = result;
        if (this.intereses.length > 0) {
          this.hay_intereses = true;
        }
      }, error => {
        console.error(error);
      }
    );
  }

  getUser() {
    this._userService.getUser().subscribe(
      (result: any) => {
        this.user = result;
        this.barra_pulsos = this.user.total_pulsos > 10 ? 10 : this.user.total_pulsos;
        this.getPosts();
        this.getIntereses();
      },
      err => {
        this._router.navigate(['/noticias']);
      }
    );
  }

  getPosts() {
    const userSession: any = this._userService.getUserLoggedIn();
    this._postService.postByUser(this.user.codigo_id, userSession.codigo_id)
      .subscribe(
        (result: any) => {
          this.postsList = result === null ? [] : result;
        },
        err => {
          console.log('Error cargar post', err);
        }
      );
  }

  verIntereses() {
    this.verMas = !this.verMas;
  }
}
