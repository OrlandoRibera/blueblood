import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { PostService } from '../../services/post.service';
import { PostDTO } from '../../models/DTOs/postDTO';
import { PostReportado } from '../../models/DTOs/postReportado';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-reportes',
  templateUrl: './reportes.component.html',
  styleUrls: ['./reportes.component.scss']
})
export class ReportesComponent implements OnInit {

  post: PostDTO = new PostDTO(0, new Date(), 0 , 'Mensaje', '--', 'image', 'image', 0, 0, 'fsd', false, false, [], false, [], 0, '', []);
  user_session: number;
  listReportados: PostReportado[] = [];
  motivo = '';
  denunciante_nm = '';
  user_Reporto: number;

  constructor(
              private _postService: PostService,
              private _userService: UserService,
              private _route: Router,
              private _snackBar: MatSnackBar
  ) { }

  ngOnInit() {
          const user: any = this._userService.getUserLoggedIn();
          this.user_session = user.codigo_id;
          if ( !user.moderador) {
            this._route.navigate(['/noticias']);
          }
          this.obtenerReportados();
  }

  obtenerReportados(): void {
    this._postService.getReportados()
      .subscribe(
        result => {
          this.listReportados = result;
        },
        err => {
          console.log(err);
        }
      );
  }

  changePost(post: PostReportado) {
    const postContainer = document.getElementById('post-container');
    this._postService.getPost(post.post_id, this.user_session)
      .subscribe(
        result => {
          this.post = result[0];
          this.motivo = post.motivo;
          this.denunciante_nm = post.denunciante_nm;
          postContainer.scrollTop = 0;
          this.user_Reporto = post.usuario_id;
        },
        err => {
          console.log(err);
        }
      );
  }

  aceptarReporte() {
    this._postService.aceptarReporte(this.post.codigo_id, this.user_Reporto)
      .subscribe(
        result => {
          this._snackBar.open('Publicación eliminada', '' , {
            duration: 2000
          });
          this.obtenerReportados();
          this.post.codigo_id = 0;
        }, err => {
          this._snackBar.open('Error al eliminar publicación, intentalo más tarde', '' , {
            duration: 2000
          });
          console.log(err);
        }
      );
  }

  rechazarReporte() {
    this._postService.rechazarReporte(this.post.codigo_id, this.user_Reporto)
    .subscribe(
      result => {
        this._snackBar.open('Reporte rechazado', '' , {
          duration: 2000
        });
        this.obtenerReportados();
        this.post.codigo_id = 0;
      }, err => {
        this._snackBar.open('Ocurrio un error al rechazar el reporte, intentelo más tarde', '' , {
          duration: 2000
        });
        console.log(err);
      }
    );
  }

  updatePost(postResult): void {
    if (typeof postResult === 'string') {
        postResult = JSON.parse(postResult);
    }
    this.post = postResult;
  }

  deletePost(): void {
    this._route.navigate(['/noticias']);
  }
}
