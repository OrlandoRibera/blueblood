import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-modal-reportar',
  templateUrl: './modal-reportar.component.html',
  styleUrls: ['./modal-reportar.component.scss']
})
export class ModalReportarComponent {

  problema = '';
  validacion = '';
  constructor(
    public dialogRef: MatDialogRef<ModalReportarComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private postService: PostService
  ) { }

  onNoClick() {
    this.dialogRef.close('close');
  }

  changeValue(event) {
    this.problema = event.value;
  }

  setInfo() {
    if (this.problema === '' ) {
      this.validacion = 'Debes escoger una opción para reportar el post';
      return;
    }
    this.postService.reportarPost(this.data.postID, this.data.userID, this.problema)
      .subscribe(
        result => {
          this.dialogRef.close('ok');
        },
        err => {
          this.dialogRef.close('err');
          console.log(err);
        }
      );
  }
}
