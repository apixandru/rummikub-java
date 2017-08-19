import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  cardSlots = new Array(20 * 7);
  handSlots = new Array(20 * 2);

  addTo($event: any) {
    console.log($event)
  }

}
