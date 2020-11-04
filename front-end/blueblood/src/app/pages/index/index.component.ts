import { Component, OnInit, HostListener } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.scss'],
})
export class IndexComponent implements OnInit {

  innerWidth: any
  mobileBackground = false;

  constructor(
    private _userService: UserService,
    private _router: Router
  ) { }

  ngOnInit() {
    this.innerWidth = window.innerWidth;
    this.validarTamanoPantalla()
    // const userID = this._userService.getUserLoggedIn();
    // if ( userID !== null || userID !== undefined ) {
    //   this._router.navigate(['/noticias']);
    // }
  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    this.innerWidth = window.innerWidth;
    this.validarTamanoPantalla()
  }

  validarTamanoPantalla() {
    if (this.innerWidth < 768) {
      this.mobileBackground = true;
    } else {
      this.mobileBackground = false;
    }
  }
}
