import {Component} from '@angular/core';

@Component({
  selector: 'app-board',
  templateUrl: './app.board.html'
})
export class AppBoard {
  slots = this.createGrid();

  createGrid() {
    const numRows = 7;
    const numCols = 20;
    let array = new Array(numCols * numRows);
    for (let row = 0; row < numRows; row++) {
      for (let column = 0; column < numCols; column++) {
        const index = row * numCols + column;
        array[index] = {
          row: row,
          col: column
        };
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
