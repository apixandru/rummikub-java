import { Component } from '@angular/core';

@Component({
  selector: 'app-board',
  templateUrl: './app.board.html'
})
export class AppBoard {
  slots = this.createGrid();

  createGrid() {
    let array = new Array(20 * 7);
    for (let row = 0; row < 7; row++) {
      for (let column = 0; column < 20; column++) {
        array[row * 7 + column] = {
          row: row,
          col: column
        }
      }
    }
    return array;
  }

  canDropOn(slot: any): any {
    return (dragData: any) => slot.row % 2 === 0;
  }

  addTo($event: any) {
    console.log($event)
  }

}
