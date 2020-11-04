import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PostDTO } from '../../models/DTOs/postDTO';
import { GlobuloService } from '../../services/globulo.service';
import { PulsoService } from '../../services/pulso.service';
import { PostService } from '../../services/post.service';
import { Comment } from '../../models/comments';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environments/environment';
import { MatDialog } from '@angular/material/dialog';
import { ModalReaccionesComponent } from '../modal-reacciones/modal-reacciones.component';
import { Router } from '@angular/router';
import { ModalReportarComponent } from '../modal-reportar/modal-reportar.component';

@Component({
  selector: 'app-new',
  templateUrl: './new.component.html',
  styleUrls: ['./new.component.scss'],
})
export class NewComponent implements OnInit {

  @Input() post: PostDTO;
  @Input() userID: any;
  @Output() updatePosts = new EventEmitter();
  @Output() deletePosts = new EventEmitter();
  public comment = '';
  public apiUrl = environment.apiURL;
  public showPopoverPost = false;
  public showPopoverPulse = false;
  public closeAllPopoverPosts = true;
  /* Comentarios logic (Ver más)*/
  public comentariosMostrados: any[] = [];
  public hayMas = false; // Si hay más de 3 comentarios
  public verMasComentarios = false;
  public texto = 'Ver más';
  public url = window.location.href;
  public deshabilitar = 0;

  constructor(
    private _globulosService: GlobuloService,
    private _pulsoService: PulsoService,
    private _postService: PostService,
    private _snackBar: MatSnackBar,
    public _dialog: MatDialog,
    public _route: Router
  ) { }

  ngOnInit() {
    this.lastComments();
  }

  lastComments() {
    if (this.post.comments === null ) { return; }
    this.comentariosMostrados = (this.post.comments.length === undefined) ? null : this.post.comments ;

    if (this.comentariosMostrados === null) { return; }
      this.comentariosMostrados =  this.comentariosMostrados.slice(-3);
    if (this.post.comments.length > 3) { this.hayMas = true; }
  }

  showReactionsModal() {
    const dialogRef = this._dialog.open(ModalReaccionesComponent, {
      width: '450px',
      data: {
        postID: this.post.codigo_id,
        userID: this.userID
      }
    });

    dialogRef.afterClosed().subscribe(
      (result) => {
        // console.log(result);
    });
  }

  showReportesModal() {
    const dialogRef = this._dialog.open(ModalReportarComponent, {
      width: '540px',
      data: {
        postID: this.post.codigo_id,
        userID: this.userID
      },
      panelClass: 'custom-dialog-container'
    });

    dialogRef.afterClosed().subscribe(
      (result) => {
        if ( result === 'ok') {
          this._snackBar.open('Publicación reportada', '' , {
            duration: 2000
          });
        }
        if ( result === 'err') {
          this._snackBar.open('Error al reportar publicación, intentalo más tarde', '' , {
            duration: 2000
          });
        }
    });
  }

  togglePopoverPost() {
    this.showPopoverPost = !this.showPopoverPost;
    this.closeAllPopoverPosts = true;
  }

  addComment() {
    this.deshabilitar++;
    if (this.deshabilitar > 1) { return; }
    if ( this.comment === undefined || this.comment.length === 0 ) {
      return;
    }
    this._snackBar.open('Publicando comentario...', '');
    const commentTemp: Comment = new Comment(0, this.userID, null, this.comment, '',
    this.post.codigo_id);

    this._postService.addComment(commentTemp)
      .subscribe(
        result => {
          this._snackBar.dismiss();
          this.updatePosts.emit(result);
          this.deshabilitar = 0;
        },
        err => {
          this._snackBar.dismiss();
          this._snackBar.open('Error al publicar comentario...', '' , {
            duration: 2000
          });
          console.log('Ocurrio un error al comentar ', err);
        }
      );
  }


  deletePost(): void {
    this._snackBar.open('Eliminando publicacion...', '');
    this._postService.deletePost(this.post.codigo_id)
      .subscribe(
        result => {
          this._snackBar.dismiss();
          this.deletePosts.emit(result);
        },
        err => {
          this._snackBar.open('Error al eliminar publicacion...', '' , {
            duration: 2000
          });
          console.log(err);
        }
      );
  }

  togglePulsePopover(): void {
    if (this.post.pulseado) {
      this.changePulso(null);
      return;
    }
    this.showPopoverPulse = !this.showPopoverPulse;
    this.closeAllPopoverPosts = true;
  }

  closeAllPopover() {
    if (this.closeAllPopoverPosts) {
      this.closeAllPopoverPosts = false;
      return;
    }
    this.showPopoverPulse = false;
    this.showPopoverPost = false;
  }

  changePulso(tipo): void {
    if (!this.post.jefe && this.userID !== 1096 && tipo !== null) {
      this._snackBar.open('Usted no puede dar pulsos', '', {
        duration: 2000
      });
      return;
    }
    if (!this.post.pulseado) {
      this.post.pulseado = true;
      this.post.pulsos++;
    } else {
      this.post.pulseado = false;
      this.post.pulsos--;
    }
    this._pulsoService.pulsos(this.post.codigo_id, this.userID, tipo)
      .subscribe(
        result => {
          this.updatePosts.emit(result);
        },
        err => {
          console.log(err);
          if (this.post.pulseado) {
            this.post.pulseado = false;
            this.post.pulsos--;
            this._snackBar.open('Ha ocurrido un error al dar el pulso', '', {
              duration: 2000
            });
          } else {
            this._snackBar.open('Ha ocurrido un error al quitar el pulso', '', {
              duration: 2000
            });
            this.post.pulseado = true;
            this.post.pulsos++;
          }
        }
      );
  }

  changeGlobulo(): void {
    this._globulosService.globulo(this.post.codigo_id, this.userID)
      .subscribe(
        result => {
          this.updatePosts.emit(result);
        },
        err => {
          console.log(err);
        }
      );
  }

  verMas() { // Comentarios
    this.verMasComentarios = !this.verMasComentarios;
    if (this.verMasComentarios) {
      this.texto = 'Ver menos';
      this.comentariosMostrados = this.post.comments;
    } else {
      this.texto = 'Ver más';
      this.lastComments();
    }
  }
}
