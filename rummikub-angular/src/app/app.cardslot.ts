import { Component, Input } from '@angular/core';

@Component({
  selector: 'card-slot',
  templateUrl: './app.cardslot.html'
})
export class AppCardSlot {
  @Input() cardSlotId: any;
}
