import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { environment } from 'src/environments/environment';
import {MatSnackBar} from '@angular/material/snack-bar';
declare var $: any;

@Component({
  selector: 'app-publisher',
  templateUrl: './publisher.component.html',
  styleUrls: ['./publisher.component.scss']
})
export class PublisherComponent implements OnInit {
  btn_deshabilitar = false;
  foto: any;
  file: File;
  contenido = '';
  @Input() idPerfil;
  @Input() placeholder;
  @Input() padre;
  @Output() return = new EventEmitter<boolean>();
  userInSession: any = this._userService.getUserLoggedIn();
  url = environment.apiURL;
  pulso = '';
  showTags = false;
  etiquetados = [];

  constructor(
    private _postService: PostService,
    private _userService: UserService,
    private _snackBar: MatSnackBar) { }

  ngOnInit() {}

  changeListener($event): void {
    this.readThis($event.target);
  }

  readThis(inputValue: any): void {
    this.file = inputValue.files[0];
    const myReader: FileReader = new FileReader();
    myReader.onloadend = (e) => {
      this.foto = myReader.result;
    };
    myReader.readAsDataURL(this.file);
  }

  getEtiquetados(event) {
    this.etiquetados = event;
  }

  openInput() {
    document.getElementById('photo').click();
  }

  eliminarFoto() {
    this.foto = '';
  }

  publicar() {
    if ((this.foto !== '' && this.foto !== undefined) ||
    (this.contenido !== '' && this.contenido !== undefined)) {
      this.contenido = this.contenido.trim();
      if (this.contenido.length === 0 && (this.foto === '' || this.foto === undefined)) {
        this.openSnackBar('La publicación esta vacía', '');
        return;
      }
      this.btn_deshabilitar = true;
      this._snackBar.open('Publicando...', '');
      if (this.idPerfil === undefined || this.idPerfil == null) {
        this._postService.post(this.foto, this.userInSession.codigo_id, this.contenido)
          .subscribe((data) => {
            this.return.emit(true);
            this.contenido = '';
            this.foto = '';
            this.pulso = '';
            this._snackBar.dismiss();
            this.openSnackBar('¡Publicación Exitosa!', '');
            this.btn_deshabilitar = false;
          }, error => {
            this._snackBar.dismiss();
            this.btn_deshabilitar = false;
            this.openSnackBar('¡Ocurrió un error al publicar!', '');
            console.error(error);
        });
      } else {
          if (this.idPerfil !== this.userInSession.codigo_id &&
              (this.pulso.length === 0 || this.pulso === '')) {
            this.openSnackBar('Debes escoger una tarjeta de reconocimiento', '');
            this.btn_deshabilitar = false;
            return;
          }
          this._postService.postOtroPerfil(this.foto, this.userInSession.codigo_id, this.contenido,
            this.idPerfil, this.pulso, this.etiquetados)
            .subscribe((data) => {
              this.return.emit(true);
              this.contenido = '';
              this.foto = '';
              this.pulso = '';
              this.showTags = false;
              this._snackBar.dismiss();
              this.openSnackBar('¡Publicación Exitosa!', '');
              this.btn_deshabilitar = false;
            }, error => {
              this._snackBar.dismiss();
              this.btn_deshabilitar = false;
              this.openSnackBar('¡Ocurrió un error al publicar!', '');
              console.error(error);
          });
      }
    } else {
      alert('La Publicación debe contener al menos una foto o un contenido');
    }
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, {
      duration: 2000,
    });
  }

  selectCard(card) {
    this.pulso = (card === this.pulso) ? '' : card;
  }
  openTags() {
    this.showTags = !this.showTags;
  }
}
