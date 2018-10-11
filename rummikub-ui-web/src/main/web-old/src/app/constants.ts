import {Injectable} from "@angular/core";
import {Card, Color, Rank} from "./card";

@Injectable()
export class Constants {
  static readonly NUM_ROWS = 7;
  static readonly NUM_COLS = 20;
  static readonly NUM_CARDS = 106;

  readonly cards: Array<Card>;

  constructor() {
    let cards = [];

    let colors = Constants.getValues(Color);
    let ranks = Constants.getValues(Rank);
    for (let i = 0; i < 2; i++) {

      for (const rank of ranks) {
        for (const color of colors) {
          cards.push(new Card(rank, color));
        }
      }
      // joker
      cards.push(new Card(undefined, undefined));
    }
    this.cards = cards;
  }

  private static getValues(enums: object): any {
    return Object.keys(enums)
      .filter(key => isNaN(+key))
      .map(name => enums[name]);
  };

}
