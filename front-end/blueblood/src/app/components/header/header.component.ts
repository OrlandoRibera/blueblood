import { Component, OnInit, HostListener } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  usuario_id: number;
  user: any;
  showNotifications = false;
  showProfileOptions = false;
  showSettings = false;
  showTitleInicio = false;
  booleanPopoverOpen = false;
  inputWidthMax = false;

  // Top
  verTop = false;
  topPulsos: any = [];
  topGlobulos: any = [];
  cargandoTopPulsos = false;
  cargandoTopGlobulos = false;
  innerWidth: any;
  tamano = false;

  buscador = false;
  listUsers: any[] = [];
  searching = false;
  contenidoBusqueda = '';

  constructor(
    private _userService: UserService,
    private _postService: PostService,
    private _router: Router
  ) { }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.validarTamanoPantalla();
    this.getUser();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.innerWidth = window.innerWidth;
    this.validarTamanoPantalla();
  }

  getUser() {
    const user: any = this._userService.getUserLoggedIn();

    this.user =  user;
    this.usuario_id = user.codigo_id;
  }

  toggleInput(): void {
    if (screen.width < 768 && this.inputWidthMax === false) {
      this.inputWidthMax = !this.inputWidthMax;
    }
  }
  closeInputMax(event): void {
    this.inputWidthMax = false;
    event.target.previousElementSibling.value = '';
    this.buscador = false;
  }

  search(event): void {
    if (this.contenidoBusqueda.length === 0) {
      this.buscador = false;
    } else {
        this.buscador = true;
        this.buscarPersona(this.contenidoBusqueda);
    }
  }

  async buscarPersona(busqueda) {
    this.searching = true;
    const result = await this._userService.getUsersByNameLocalStorage(busqueda);
    
    if (result === undefined) { return; }
    this.listUsers = result;
    this.searching = false;
  }

  logOut() {
    this._userService.logOut();
    this._router.navigate(['/']);
  }

  toggleTituloPlaceholder() {
    this.showTitleInicio = !this.showTitleInicio;
  }

  toggleNotifications () {
    this.showNotifications = !this.showNotifications;
    this.showProfileOptions = false;
    this.showSettings = false;
    this.booleanPopoverOpen = true;
  }

  toggleProfileOptions () {
    this.showProfileOptions = !this.showProfileOptions;
    this.showNotifications = false;
    this.showSettings = false;
    this.booleanPopoverOpen = true;
  }

  toggleSettings () {
    this.showSettings = !this.showSettings;
    this.showProfileOptions = false;
    this.showNotifications = false;
    this.booleanPopoverOpen = true;
  }
  closeAllPopover() {
    if (this.booleanPopoverOpen) {
      this.booleanPopoverOpen = false;
      return;
    }
    this.showSettings = false;
    this.showProfileOptions = false;
    this.showNotifications = false;
    this.booleanPopoverOpen = false;
  }

  verTopPersonas() {
    this.verTop = !this.verTop;

    if ( this.topPulsos !== null && this.topPulsos.length === 0 && this.topGlobulos.length === 0) {
      this.cargandoTopGlobulos = true;
      this.cargandoTopPulsos = true;
      this._postService.topPulsos().subscribe((result: any) => {
        this.topPulsos = result.detalle_likes;
        this.cargandoTopPulsos = false;
        // this._postService.topGlobulos().subscribe((res: any) => {
        //   this.topGlobulos = res.detalle_likes;
        //   this.cargandoTopGlobulos = false;
        // }, err => {
        //   console.error(err);
        // });
      }, err => {
        console.error(err);
      });
    }
  }


  /**
   * Éste método es para validar si el tamaño de
   * la pantalla alcanza para ver de entrada el top de
   * Pulsos
   */
  validarTamanoPantalla() {
    if (this.innerWidth > 1350) {
      this.verTop = false;
      this.verTopPersonas();
    } else {
      this.tamano = true;
    }
  }
  redirectTo(id) {
    this._router.navigate(['/perfil', id]);
    this.contenidoBusqueda = '';
    this.buscador = false;
  }
}
