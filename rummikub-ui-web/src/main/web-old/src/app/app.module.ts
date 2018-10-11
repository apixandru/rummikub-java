import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { AppCardSlot } from "./app.cardslot";
import { AppCard } from "./app.card";
import { AppBoard } from "./app.board";

import { DndModule } from 'ng2-dnd';

@NgModule({
  declarations: [
    AppComponent,
    AppCardSlot,
    AppCard,
    AppBoard
  ],
  imports: [
    DndModule.forRoot(),
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
