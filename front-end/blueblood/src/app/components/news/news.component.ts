import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PostDTO } from '../../models/DTOs/postDTO';

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.scss'],
})
export class NewsComponent implements OnInit, OnChanges {

  desde = 4;
  hasta = 8;
  posts: PostDTO[] = [];
  @Input() postsList: PostDTO[];
  @Input() user: any;

  constructor() { }

  ngOnInit() {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.posts = this.postsList.slice(0, 4);
  }

  updatePost(postResult): void {
    if (typeof postResult === 'string') {
        postResult = JSON.parse(postResult);
    }
    const postTemp = this.posts.find(post => post.codigo_id === postResult.codigo_id);
    const indexPost = this.posts.indexOf(postTemp);
    this.posts.splice(indexPost, 1, postResult);
  }

  deletePosts(postResult): void {
    const indexPost = this.posts.indexOf(postResult);
    this.posts.splice(indexPost, 1);
  }

  onScroll() {
    if (this.desde >=  this.postsList.length ) {
      return;
    }
    this.posts.push(... this.postsList.slice(this.desde, this.hasta));
    this.desde += 4;
    this.hasta += 4;
  }

}
