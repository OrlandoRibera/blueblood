import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../../models/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public correo = '';
  public password = '';
  public responseError = false;
  public errorMessage = '';
  public login = false;
  public href: string;

  constructor(
    private _rute: ActivatedRoute,
    private _userService: UserService,
    private _router: Router,
  ) { }

  ngOnInit() {
    this._rute.queryParams.subscribe(params => {
      this.href = params['href'];
      if (this._userService.getUserLoggedIn() !== null && this._userService.getUserLoggedIn() !== undefined ) {
        if (this.href) {
          this._router.navigate([this.href]);
        } else {
          this._router.navigate(['/noticias']);
        }
      }
    });
  }

  loginUser(): void {
    this.login = true;
    this._userService.loginUser(this.correo, this.password)
      .subscribe(
        (result: User) => {
          this._userService.setUserLoggedIn(result);
          if (this.href) {
            if (result.nick === null) { this._router.navigate(['fotoPerfil']); return; }
            this._router.navigate([this.href]);
          } else {
            if (result.nick === null) { this._router.navigate(['fotoPerfil']); return; }
            this._router.navigate(['/noticias']);
          }
          this.getUsersToLocalStorage();
          this.login = false;
        },
        err => {
            this.login = false;
            this.responseError = true;
            this.errorMessage = err.error.nombre;
        }
      );
  }

  getUsersToLocalStorage() {
    this._userService.getUsersToSearch()
      .subscribe(
        result => {
          this._userService.setUsersLocalStorage(result);
        },
        err => console.log(err)
      );
  }
}
