import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';
import { Router, ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-upload-photo',
  templateUrl: './upload-photo.component.html',
  styleUrls: ['./upload-photo.component.scss']
})
export class UploadPhotoComponent implements OnInit {

  image: ArrayBuffer | string = '';
  file: File;
  cargo = false;
  isEdit = false;
  usuario_id: number;
  nombreUsuario = '';
  mensajeError = '';

  constructor(
    private _userService: UserService,
    private _router: Router,
    private _snackBar: MatSnackBar,
    private _rute: ActivatedRoute) { }

  ngOnInit() {
    this._rute.params.subscribe(
      parametros => {
        const edit = parametros.edit;
        if (edit !== undefined) {
          const user: any = this._userService.getUserLoggedIn();
          this.usuario_id = user.codigo_id;
          this.isEdit = true;
        }
      }
    );
  }

  changeListener($event): void {
    this.readThis($event.target);
  }

  readThis(inputValue: any): void {
    this._snackBar.open('Cargando imagen...', '');
    this.file = inputValue.files[0];
    const myReader: FileReader = new FileReader();

    myReader.onloadend = (e) => {
      this.image = myReader.result;
      this._snackBar.dismiss();
    };
    myReader.readAsDataURL(this.file);
    this.cargo = true;
  }

  enviarFoto() {
    const user: any = this._userService.getUserLoggedIn();

    // if (this.image === '' || this.image === undefined || this.image == null) {
    //   if (this.isEdit) {
    //     this._router.navigate(['/perfil']);
    //   } else {
    //     this._router.navigate(['/intereses']);
    //   }
    // } else {
      if (this.nombreUsuario === '' && this.isEdit === false) { this.mensajeError = 'El nombre es obligatorio' ; return; }
      this._userService.subirFoto(this.image, user.codigo_id, this.nombreUsuario)
      .subscribe(
        (result) => {
          if (this.isEdit) {
            window.location.href = '/#/perfil';
            window.location.reload();
          } else {
            this._router.navigate(['/intereses']);
          }
        },
        err => {
          this._snackBar.open('Error al cargar imagen...', '', {
            duration: 2000
          });
          console.error(err);
        }
      );
    // }
  }
}
