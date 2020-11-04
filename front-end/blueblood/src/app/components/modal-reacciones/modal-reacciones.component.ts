import { Component, OnInit, Inject, ViewEncapsulation } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from '../../services/post.service';
import { LikeDetail } from '../../models/DTOs/likeDetail';

@Component({
  selector: 'app-modal-reacciones',
  templateUrl: './modal-reacciones.component.html',
  styleUrls: ['./modal-reacciones.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ModalReaccionesComponent implements OnInit {

  listReactions: LikeDetail[] = [];
  listPulse: LikeDetail[] = [];
  listGlobule: LikeDetail[] = [];

  constructor(
    public dialogRef: MatDialogRef<ModalReaccionesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private _postService: PostService
  ) { }

  ngOnInit() {
    this.showReactions();
  }

  showReactions(): void {
    this._postService.getLikesDetail(this.data.postID, this.data.userID)
      .subscribe(
        (result: any) => {
          if ( result.detalle_likes === null ) {
            return;
          }
          this.listReactions = result.detalle_likes;
          this.listGlobule = this.listReactions.filter(list => list.clase === 'Globulo');
          this.listPulse = this.listReactions.filter(list => list.clase === 'Pulso');
        },
        err => {
          console.log(err);
        }
      );
  }

  onNoClick() {
    this.dialogRef.close();
  }

}
