export class Card {

  private rank;
  private color;
  private joker;

  constructor(rank: Rank, color: Color) {
    this.rank = rank;
    this.color = color;
    this.joker = color === undefined;
  }

}

export enum Rank {
  ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN
}

export enum Color {
  RED, BLUE, BLACK, YELLOW
}
