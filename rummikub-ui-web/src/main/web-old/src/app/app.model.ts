import {Injectable} from "@angular/core";
import {Constants} from "./constants";

@Injectable()
export class AppModel {

  grid = this.createGrid();
  private numRows = 7;
  private numCols = 20;
  board = new Array(this.numCols * this.numRows);
  private constants: Constants;

  constructor(constants: Constants) {
    this.constants = constants;
  }

  getCardAt(index: number): any {
    return this.board[index];
  }

  canDropAt(index: number): boolean {
    let element = this.board[index];
    return !element || element.row % 2 === 0;
  }

  private createGrid() {
    let array = new Array(this.numCols * this.numRows);
    for (let row = 0; row < this.numRows; row++) {
      for (let column = 0; column < this.numCols; column++) {
        const index = row * this.numCols + column;
        array[index] = {
          row: row,
          col: column
        };
      }
    }
    return array;
  }


}
