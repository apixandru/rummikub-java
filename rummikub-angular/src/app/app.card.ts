import { Component, Input } from '@angular/core';

@Component({
  selector: 'card',
  templateUrl: './app.card.html'
})

export class AppCard {
  @Input() rank: number;
}
