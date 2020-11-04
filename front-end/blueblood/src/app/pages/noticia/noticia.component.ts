import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostService } from '../../services/post.service';
import { UserService } from '../../services/user.service';
import { PostDTO } from '../../models/DTOs/postDTO';

@Component({
  selector: 'app-noticia',
  templateUrl: './noticia.component.html',
  styleUrls: ['./noticia.component.scss']
})
export class NoticiaComponent implements OnInit {

  id: number;
  post: PostDTO = new PostDTO(0, new Date(), 0 , 'Mensaje', '--', 'image', 'image', 0, 0, 'fsd', false, false, [], false, [], 0, '', []);
  user_id: number;

  constructor(private _rute: ActivatedRoute,
              private _postService: PostService,
              private _route: Router,
              private _userService: UserService) { }

  ngOnInit() {
    this._rute.params.subscribe(
      parametros => {
        this.id = parametros.id; // Obtenemos el id del post para ver
        if (this.id !== undefined) { // Si existe el id
          const user: any = this._userService.getUserLoggedIn();
          this.user_id = user.codigo_id;
          this._postService.getPost(this.id, this.user_id).subscribe(
            result => {
              this.post = result[0];
            }, err => {
              console.error('ocurrió un error');
            }
          );
        } else { // Si no existe el id
          this._route.navigate(['/noticias']);
        }
      }
    );
  }

  updatePost(postResult): void {
    if (typeof postResult === 'string') {
        postResult = JSON.parse(postResult);
    }
    this.post = postResult;
  }

  deletePost(): void {
    this._route.navigate(['/noticias']);
  }
}
