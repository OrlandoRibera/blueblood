import { Component, OnInit } from '@angular/core';
import { InteresesService } from 'src/app/services/intereses.service';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-intereses',
  templateUrl: './intereses.component.html',
  styleUrls: ['./intereses.component.scss']
})
export class InteresesComponent implements OnInit {

  intereses: any[] = [];
  intereses_anadidos = [];
  user: any;

  constructor(
    private _interesesService: InteresesService,
    private _userService: UserService,
    private _snackBar: MatSnackBar,
    private _router: Router) { }

  ngOnInit() {
    this.user = this._userService.getUserLoggedIn();
    this.getIntereses();
  }

  getIntereses() {
    this._snackBar.open('Cargando, espere...', '', {});
    this._interesesService.getIntereses().subscribe(
      (result: any) => {
        this.intereses = result;
        this._snackBar.dismiss();
      }, err => {
        this._snackBar.dismiss();
        this._snackBar.open('Ocurrió un error, intentelo de nuevo...', '', {duration: 3000});
      }
    );
  }

  mostrarInteres(id) {
    /* Obtenemos el componente donde se encuentran los intereses y los mostramos */
    const obj = document.getElementById('idInteresGroup' + id);
    obj.hidden = !obj.hidden;
  }

  anadir_eliminar(interes, categoria) {
    /* Verificamos si es una añadida o una eliminada del interes de la lista de intereses añadidos */
    const foundInteres = this.intereses_anadidos.filter(_interes =>  _interes.nombre_id === interes);

    if (foundInteres[0] === undefined) {  // Si es añadida
      document.getElementById('idGroup' + categoria).classList.add('marcadoCategoria');
      document.getElementById('idInteres' + interes).classList.add('marcadoSubCategoria');
      this.intereses_anadidos.push({
        'usuario_id': this.user.codigo_id,
        'grupo_id': categoria,
        'nombre_id': interes
      });
    } else { // Si es eliminada
      document.getElementById('idInteres' + interes).classList.remove('marcadoSubCategoria');
      this.intereses_anadidos = this.intereses_anadidos.filter((_interes) => {
        return _interes.nombre_id !== interes;
      });
      if (!this.existeGrupo(categoria)) { // Si no siguen existiendo intereses añadidos en esa categoría, eliminamos el estilo de "marcado"
        document.getElementById('idGroup' + categoria).classList.remove('marcadoCategoria');
      }
    }
  }

  existeGrupo(categoria) {
    // Recorremos la lista y verificamos si hay intereses añadidos con esa categoría
    const grupo = this.intereses_anadidos.filter((interes) => interes.grupo_id === categoria);

    if (grupo[0] !== undefined) {
      return true;
    } else {
      return false;
    }
  }

  submit() {
    if (this.intereses_anadidos.length === 0) { // Si le da finalizar y no hay intereses, directo a perfil
      this._router.navigate(['/perfil']);
      return;
    }
    this._snackBar.open('Guardando información...', '');
    this._interesesService.postIntereses(this.intereses_anadidos).subscribe(
      result => {
        this._snackBar.dismiss();
        this._router.navigate(['/perfil']);
      },
      err => {
        this._snackBar.open('Error al guardar...', '', {duration: 2000});
      }
    );
  }

  saltar() {
    this._router.navigate(['/perfil']);
  }
}
