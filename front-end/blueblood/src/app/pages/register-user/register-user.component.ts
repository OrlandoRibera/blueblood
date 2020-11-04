import { Component, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.scss']
})
export class RegisterUserComponent implements OnInit {

  public user: User = new User(0, '', '', '', '', 0, 0, 0, new Date(), new Date, 0, 0, false);
  public confirmPasswordText = '';
  public responseError = false;
  public errorMessage = '';

  constructor(
    private _userService: UserService,
    private _router: Router
  ) { }

  ngOnInit() {
  }

  registerUser(): void {
    this._userService.registerUser(this.user)
      .subscribe(
        result => {
          this._userService.setUserLoggedIn(result);
          this.getUsersToLocalStorage();
          this._router.navigate(['/fotoPerfil']);
        },
        err => {
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
