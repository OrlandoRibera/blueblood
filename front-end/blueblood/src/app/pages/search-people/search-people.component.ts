import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-search-people',
  templateUrl: './search-people.component.html',
  styleUrls: ['./search-people.component.scss']
})
export class SearchPeopleComponent implements OnInit {

  public busqueda: string;
  public listUsers: any[] = [];
  public searching = false;

  constructor(
    private _userService: UserService,
    private _rute: ActivatedRoute,
    private _router: Router
  ) { }

  ngOnInit() {
    this.getParamUrl();
  }

  getListUsers(): void {
    this.searching = true;
    this._userService.getUsersByName(this.busqueda)
      .subscribe(
        result => {
          this.listUsers = result;
          this.searching = false;
        },
        err => {
          console.log('Error al obtener lista de busqueda', err);
        }
      );
  }

  getParamUrl(): void {
    this._rute.params.subscribe(
      params =>  {
        this.busqueda = params.name;
        this.getListUsers();
      }, err => {
        console.log('ERROR AL OBTENER PARAMETRO', err);
      }
    );
  }

  redirectTo(id) {
    this._router.navigate(['/perfil', id]);
  }
}
