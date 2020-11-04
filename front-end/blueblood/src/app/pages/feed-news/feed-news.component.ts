import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { PostService } from '../../services/post.service';
import { PostDTO } from '../../models/DTOs/postDTO';
import { NewsComponent } from '../../components/news/news.component';


@Component({
  selector: 'app-feed-news',
  templateUrl: './feed-news.component.html',
  styleUrls: ['./feed-news.component.scss'],
})
export class FeedNewsComponent implements OnInit {

  user: any;
  postsList: PostDTO[] = [];

  constructor(
    private _userService: UserService,
    private _postService: PostService
  ) { }

  ngOnInit() {
    this.user = this._userService.getUserLoggedIn();
    this.getPosts();
  }

  getPosts(): void {
    this._postService.getAll(this.user.codigo_id)
      .subscribe(
        (result: PostDTO[]) => {
          this.postsList = result;
        },
        err => {
          console.log('Error cargar post', err);
        }
      );
  }

}
