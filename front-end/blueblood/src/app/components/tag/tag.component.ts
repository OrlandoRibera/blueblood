import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.scss']
})
export class TagComponent implements OnInit {

  public  autocompleteItems = [];
  public personasEtiquetadas = [];
  @Output() public eventoPersonasEtiquetadas = new EventEmitter();

  constructor(
    private userService: UserService
    ) {}

  ngOnInit() {
    this.getUsers();
  }

  public getUsers() {
    this.userService.getUsersLocalStorage()
      .then(
        result => {
          this.autocompleteItems = result;
        }
      );
  }

  public onAdd(item) {
    this.personasEtiquetadas.push(Number(item.codigo_id));
    this.eventoPersonasEtiquetadas.emit(this.personasEtiquetadas);
  }

  public onRemove(item) {
    const indexPersona = this.personasEtiquetadas.indexOf(item);
    this.personasEtiquetadas.splice(indexPersona, 1);
    this.eventoPersonasEtiquetadas.emit(this.personasEtiquetadas);
  }

}
