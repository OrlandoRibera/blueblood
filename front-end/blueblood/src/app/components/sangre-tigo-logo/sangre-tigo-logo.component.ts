import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-sangre-tigo-logo',
  templateUrl: './sangre-tigo-logo.component.html',
  styleUrls: ['./sangre-tigo-logo.component.scss']
})
export class SangreTigoLogoComponent implements OnInit {

  @Input() align: string;
  @Input() color = '';

  constructor() { }

  ngOnInit() {
  }

}
