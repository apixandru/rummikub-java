import { Component } from '@angular/core';

@Component({
  selector: 'app-board',
  templateUrl: './app.board.html'
})
export class AppBoard {
  slots = new Array(20 * 7);

  addTo($event: any) {
    console.log($event)
  }

}
