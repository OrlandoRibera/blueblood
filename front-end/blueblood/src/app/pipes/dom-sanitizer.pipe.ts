import { Pipe, PipeTransform } from '@angular/core';
import { environment } from 'src/environments/environment';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'domSanitizer'
})
export class DomSanitizerPipe implements PipeTransform {

  constructor(
    private _domSanitizer: DomSanitizer
  ) {}

  transform(usuario_id: any, origin: string): any {
    if (usuario_id === undefined ) {
      return;
    }
    let url = '';
    let domImg = '';
    switch (origin) {
      case 'server':
        url = environment.apiURL;
        domImg = `background-image: url('${url}/usuarios/${usuario_id}/profile')`;
        break;
      case 'local':
        // Si es local se le pasa el path como primer parametro al pipe
        domImg = `background-image: url('${usuario_id}')`;
        break;
    }
    return this._domSanitizer.bypassSecurityTrustStyle(domImg);
  }

}
